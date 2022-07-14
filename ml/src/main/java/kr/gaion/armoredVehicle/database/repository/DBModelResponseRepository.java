package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.DbModelResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface DBModelResponseRepository extends JpaRepository<DbModelResponse, Long> {
    @Query(value = "SELECT * from DB_MODEL_RESPONSE dmr where dmr.Algorithm_Type =?1 ", nativeQuery = true)
    List<DbModelResponse> getModelResponseListByAlgorithm(String algorithm);


}
