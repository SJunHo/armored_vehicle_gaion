package kr.gaion.railroad2.dataset.service;

import kr.gaion.railroad2.dataset.dto.UpdateDataLookupInput;
import kr.gaion.railroad2.dataset.model.DataLookup;
import kr.gaion.railroad2.dataset.repository.DataLookupRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class DataLookupService {
  @NonNull private final DataLookupRepository dataLookupRepository;

  public List<DataLookup> getAllDataLookup() {
    return StreamSupport.stream(this.dataLookupRepository.findAll().spliterator(), false)
        .collect(Collectors.toList());
  }

  public DataLookup updateDataLookup(UpdateDataLookupInput input) {
    var dataLookupResult = this.dataLookupRepository.findById(input.getLookupName());
    if (dataLookupResult.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid lookup name");
    }
    var dataLookup = dataLookupResult.get();
    dataLookup.setDelimiter(input.getDelimiter());
    dataLookup.setIndex(input.getIndex());
    dataLookup.setIndexOfLabeledField(input.getIndexOfLabeledField());
    dataLookup.setLookupName(input.getLookupName());
    this.dataLookupRepository.save(dataLookup);
    return dataLookup;
  }

  public DataLookup deleteDataLookup(String lookupName) {
    var dataLookup = this.dataLookupRepository.findById(lookupName);
    if (dataLookup.isEmpty()) {
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Invalid lookup name");
    }
    var res = dataLookup.get();
    this.dataLookupRepository.deleteById(res.getLookupName());
    return res;
  }

  public DataLookup createDataLookup(UpdateDataLookupInput input) {
    var dataLookup = new DataLookup();
    dataLookup.setDelimiter(input.getDelimiter());
    dataLookup.setIndex(input.getIndex());
    dataLookup.setIndexOfLabeledField(input.getIndexOfLabeledField());
    dataLookup.setLookupName(input.getLookupName());

    this.dataLookupRepository.save(dataLookup);
    return dataLookup;
  }
}
