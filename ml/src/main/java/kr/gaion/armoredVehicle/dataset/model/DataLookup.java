package kr.gaion.armoredVehicle.dataset.model;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

@Entity(name = "DATA_LOOKUP")
@Getter
@Setter
public class DataLookup {
  @Id
  private String lookupName;

  @Column(name = "es_index")
  private String index;

  @UpdateTimestamp
  @Column(name = "updated_time")
  private Date updatedTime;

  private String delimiter;

  @Column(name = "index_of_labeled_field")
  private Integer indexOfLabeledField;
}
