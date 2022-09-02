package kr.gaion.armoredVehicle.web.analysis.mapper;

import java.util.List;
import java.util.Optional;

import kr.gaion.armoredVehicle.web.analysis.model.Board;
import org.apache.ibatis.annotations.Mapper;

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
