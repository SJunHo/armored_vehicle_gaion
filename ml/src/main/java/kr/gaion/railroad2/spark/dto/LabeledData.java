package kr.gaion.railroad2.spark.dto;


import lombok.Getter;
import lombok.Setter;
import org.apache.spark.ml.linalg.Vector;

import java.io.Serializable;

@Getter
@Setter
public class LabeledData implements Serializable {
  private static final long serialVersionUID = 6847391030863644368L;

  private String label;
  private Vector features;
}