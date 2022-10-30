package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.Sda;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SdaRepository  extends JpaRepository<Sda, String>  {
    @Query(value = "Select s.SDAID from SDA s", nativeQuery = true)
    List<String> getSdaIdList();
}
