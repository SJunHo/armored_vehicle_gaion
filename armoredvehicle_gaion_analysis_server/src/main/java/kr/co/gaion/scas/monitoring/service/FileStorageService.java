package kr.co.gaion.scas.monitoring.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import kr.co.gaion.scas.monitoring.model.SdadataEvent;

@Service
public class FileStorageService {
	
	private final Path root = Paths.get("uploads");
	
	  public void init() {
	    try {
	      Files.createDirectory(root);
	    } catch (IOException e) {
	      throw new RuntimeException("Could not initialize folder for upload!");
	    }
	  }

	  public void save(MultipartFile file) {
	    try {
	    	String line;
	    	String fileName = file.getOriginalFilename();
	    	String ext = fileName.substring(fileName.lastIndexOf(".")+1);
	    	BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream(), "EUC-KR"));
	    	int i = 0; 
			while((line=br.readLine()) != null) {
				
				
				if(i > 2) {
					System.out.println(line);
					String [] array = line.split("\\t|\\f|\\r|\\n");
					SdadataEvent data = new SdadataEvent(array);
					System.out.println(data);
				}
				i++;
			}
			br.close();
	    } catch (Exception e) {
	    	e.printStackTrace();
	      throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
	    }
	  }

	  public Resource load(String filename) {
	    try {
	      Path file = root.resolve(filename);
	      Resource resource = new UrlResource(file.toUri());

	      if (resource.exists() || resource.isReadable()) {
	        return resource;
	      } else {
	        throw new RuntimeException("Could not read the file!");
	      }
	    } catch (MalformedURLException e) {
	      throw new RuntimeException("Error: " + e.getMessage());
	    }
	  }

	  public void deleteAll() {
	    FileSystemUtils.deleteRecursively(root.toFile());
	  }

	  public Stream<Path> loadAll() {
	    try {
	      return Files.walk(this.root, 1).filter(path -> !path.equals(this.root)).map(this.root::relativize);
	    } catch (IOException e) {
	      throw new RuntimeException("Could not load the files!");
	    }
	  }
}
