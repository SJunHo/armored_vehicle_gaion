package kr.gaion.railroad2.dataset.repository;

import kr.gaion.railroad2.dataset.model.DataLookup;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface DataLookupRepository extends PagingAndSortingRepository<DataLookup, String> {
}
