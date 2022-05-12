package kr.gaion.railroad2.spark.dto;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class SparkMlVectorExtra extends LabeledData implements Serializable {
	private static final long serialVersionUID = -7078352461303629149L;

	protected String extraColumns;
	protected String tag;
}