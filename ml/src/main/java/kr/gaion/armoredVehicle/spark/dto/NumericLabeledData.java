package kr.gaion.armoredVehicle.spark.dto;

import lombok.Getter;
import lombok.Setter;
import org.apache.spark.ml.linalg.Vector;

import java.io.Serializable;

@Getter
@Setter
public class NumericLabeledData implements Serializable {
  private static final long serialVersionUID = -4464834311755737301L;

  private Double label;
  private Vector features;
}
