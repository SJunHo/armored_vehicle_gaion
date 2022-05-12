package kr.gaion.railroad2.spark.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.spark.ml.linalg.Vector;

import java.io.Serializable;

@Getter
@Setter
public class PredictedLabeledData implements Serializable {
  private Double prediction;
  private Double index;
  private Vector features;
}
