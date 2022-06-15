package kr.co.gaion.scas.monitoring.mapper;

import java.util.List;
import java.util.Optional;

import org.apache.ibatis.annotations.Mapper;

import kr.co.gaion.scas.monitoring.model.Board;

@Mapper
public interface BoardMapper {

	public List<Board> findBoards();
	
	public Board findById(int id);
	
	public void insertBoard(Board board);
	
	public void updateBoard(Board board);
	
	public void deleteById(int id);
	
	public void deleteAll();
	
	public List<Board> findByPublished(boolean published);
	
	public List<Board> findByTitleContaining(String title);
}
