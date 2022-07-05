package kr.gaion.armoredVehicle.dataset.repository;

import kr.gaion.armoredVehicle.database.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, String> {
    @Query(value = "Select FILENM from FILEINFO f where f.FILETYPE = 'T'", nativeQuery = true)
    String[] findTrainingDataNameList();
}
