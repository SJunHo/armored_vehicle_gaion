package kr.co.gaion.scas.monitoring.model;

import lombok.Setter;

import lombok.Getter;

@Getter
@Setter

public class FileInfo {
  private String name;
  private String url;
	public FileInfo(String name, String url) {
		super();
		this.name = name;
		this.url = url;
	}

}
