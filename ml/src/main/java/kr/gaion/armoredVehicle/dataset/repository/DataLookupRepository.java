package kr.gaion.armoredVehicle.dataset.repository;

import kr.gaion.armoredVehicle.dataset.model.DataLookup;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface DataLookupRepository extends PagingAndSortingRepository<DataLookup, String> {
}
