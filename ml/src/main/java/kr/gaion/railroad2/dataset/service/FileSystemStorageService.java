package kr.gaion.railroad2.dataset.service;

import kr.gaion.railroad2.common.Utilities;
import kr.gaion.railroad2.dataset.exception.StorageException;
import kr.gaion.railroad2.dataset.exception.StorageFileNotFoundException;
import lombok.extern.log4j.Log4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Objects;
import java.util.stream.Stream;

@Service
@Log4j
public class FileSystemStorageService implements StorageService {
	private final Path rootLocation;

	@Autowired
	public FileSystemStorageService(Utilities utilities) throws IOException {
		this.rootLocation = Paths.get(utilities.getProjTmpFolder());
	}

	public String saveFile(String fileNameFullPath, byte[] data) throws Exception {
		// Creating the directory to store file
		log.info("Creating the directory to store file");
		Path path = Path.of(fileNameFullPath);
		String rootPath = path.getParent().toString();

		// HDFS is enable => copy to HDFS
//		if (isEnableHdfs) { // #PC0017
//			HdfsInOutUti.writeDataToHdfs(data, fileNameFullPath); // #PC0017
//		} else { // #PC0017
			// make root path if it does not exist
		File rootDir = new File(rootPath);
		if (!rootDir.exists()) {
			rootDir.mkdirs();
		}
		// check file
		File targetFile = new File(fileNameFullPath);
		if (targetFile.exists()) {
			FileUtils.forceDelete(targetFile);
		}
		// write file
		BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(targetFile));
		stream.write(data);
		stream.close();
		log.info(String.format("Successfully created file %s from local FileSystem", fileNameFullPath));
//		}
		return fileNameFullPath;
	}

	@Override
	public void store(MultipartFile file) {
		try {
			if (file.isEmpty()) {
				throw new StorageException("Failed to store empty file.");
			}
			Path destinationFile = this.rootLocation.resolve(
					Paths.get(Objects.requireNonNull(file.getOriginalFilename())))
					.normalize().toAbsolutePath();
			if (!destinationFile.getParent().equals(this.rootLocation.toAbsolutePath())) {
				// This is a security check
				throw new StorageException(
						"Cannot store file outside current directory.");
			}
			try (InputStream inputStream = file.getInputStream()) {
				Files.copy(inputStream, destinationFile,
					StandardCopyOption.REPLACE_EXISTING);
			}
		}
		catch (IOException e) {
			throw new StorageException("Failed to store file.", e);
		}
	}

	@Override
	public Stream<Path> loadAll() {
		try {
			return Files.walk(this.rootLocation, 1)
				.filter(path -> !path.equals(this.rootLocation))
				.map(this.rootLocation::relativize);
		}
		catch (IOException e) {
			throw new StorageException("Failed to read stored files", e);
		}

	}

	@Override
	public Path load(String filename) {
		return rootLocation.resolve(filename);
	}

	@Override
	public Resource loadAsResource(String filename) throws StorageFileNotFoundException {
		try {
			Path file = load(filename);
			Resource resource = new UrlResource(file.toUri());
			if (resource.exists() || resource.isReadable()) {
				return resource;
			}
			else {
				throw new StorageFileNotFoundException(
						"Could not read file: " + filename);

			}
		}
		catch (MalformedURLException e) {
			throw new StorageFileNotFoundException("Could not read file: " + filename, e);
		}
	}

	@Override
	public void deleteAll() {
		FileSystemUtils.deleteRecursively(rootLocation.toFile());
	}

	@Override
	public void init() {
		try {
			Files.createDirectories(rootLocation);
		}
		catch (IOException e) {
			throw new StorageException("Could not initialize storage", e);
		}
	}
}