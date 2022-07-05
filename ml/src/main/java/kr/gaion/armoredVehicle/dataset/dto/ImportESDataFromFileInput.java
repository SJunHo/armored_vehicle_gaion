package kr.gaion.armoredVehicle.dataset.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class ImportESDataFromFileInput {
  /**
   * fieldOpt = INDEX_NUMBER: this is index of field<br>
   * fieldOpt = FIELD_NAME: this is name of field<br>
   */
  private int fieldOpt;

  /**
   * index of labeled field
   */
  private int labeledIndex;

  /**
   * The value which user inputed
   */
  private String fieldValue;

  /**
   * _index to save to ElasticSearch
   */
  private String indexW;

  /**
   * source name to save to ElasticSearch
   */
  private String sourceName;

  /**
   * format of data file: DENSE or SPARSE
   */
  private DataFormat format;

  /**
   * file name (full path) to index
   */
  private String fileName;

  /**
   * delimiter to split data
   */
  private String delimiter;
  /**
   * if this set, old data will be deleted from ElasticSearch
   */
  private boolean clearOldData;

  /**
   * to send list of columns
   */
  private int[] listColumns;

  /**
   * index of ID column in data BASKET format
   */
  private int indexOfColumnID;

  /**
   * index of Category column in data BASKET format
   */
  private int indexOfColumnCategory;

  /**
   * amount of features (using for SPARSE format)
   */
  private int numberFeatures;

  /**
   * column is used as primary key (`_id` in Elasticsearch)
   */
  private String columnPrimaryKey;


  private String[] listUploadedFiles;
}
