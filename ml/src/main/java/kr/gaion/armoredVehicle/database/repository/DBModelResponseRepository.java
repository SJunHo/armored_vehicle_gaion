package kr.gaion.armoredVehicle.database.repository;

import kr.gaion.armoredVehicle.database.model.DbModelResponse;
import lombok.extern.log4j.Log4j;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//@Repository
public interface DBModelResponseRepository extends JpaRepository<DbModelResponse, Long> {
    @Query(value = "SELECT * from DB_MODEL_RESPONSE dmr where dmr.Algorithm_Type =?1 ", nativeQuery = true)
    List<DbModelResponse> getModelResponseListByAlgorithm(String algorithm);

    @Query(value = "SELECT * from DB_MODEL_RESPONSE dmr where dmr.Algorithm_Type =?1 and dmr.MODEL_NAME=?2", nativeQuery = true)
    DbModelResponse findDbModelResponseByAlgorithmNameAndModelName(String algorithm, String modelName);
}
