package kr.gaion.armoredVehicle.dataset.service;

import kr.gaion.armoredVehicle.dataset.exception.StorageFileNotFoundException;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {

	void init();

	void store(MultipartFile file);

	Stream<Path> loadAll();

	Path load(String filename);

	Resource loadAsResource(String filename) throws StorageFileNotFoundException;

	void deleteAll();

}