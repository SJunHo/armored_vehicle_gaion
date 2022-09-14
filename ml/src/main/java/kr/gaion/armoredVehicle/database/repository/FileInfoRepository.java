package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.FileInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FileInfoRepository extends JpaRepository<FileInfo, String> {
    @Query(value = "Select FILENM from FILEINFO_BACKUP f where f.FILETYPE = 'T' and f.FILEPT = ?1", nativeQuery = true)
    String[] findTrainingDataNameList(String partType);
}
