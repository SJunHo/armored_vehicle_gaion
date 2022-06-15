package kr.co.gaion.scas.monitoring.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Board {

	private int id;
	
	private String title;
	
	private String description;
	
	private boolean published;
		
	public Board(int id, String title, String description, boolean published) {
		this.id = id;
		this.title = title;
		this.description = description;
		this.published = published;
	}
		
	public Board(String title, String description, boolean published) {
		this.title = title;
		this.description = description;
		this.published = published;
	}
}
