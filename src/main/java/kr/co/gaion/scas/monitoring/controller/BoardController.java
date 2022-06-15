package kr.co.gaion.scas.monitoring.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;

import kr.co.gaion.scas.monitoring.mapper.BoardMapper;
import kr.co.gaion.scas.monitoring.model.Board;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api/board")
public class BoardController {

	@Autowired
	BoardMapper boardRepository;
	
	@GetMapping("/tutorials")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<Board>> getAllBoard(@RequestParam(required = false) String title){
		try {
			List<Board> boards = new ArrayList<Board>();
			
			if (title == null) {
				boards = boardRepository.findBoards();
		    }
		    else {
		    	boards = boardRepository.findByTitleContaining(title);
		    }
			if(boards.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			}else {
				return new ResponseEntity<>(boards, HttpStatus.OK);
			}
		}catch(Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/tutorials/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Board> getTutorialById(@PathVariable("id") int id) {
	  Board tutorialData = boardRepository.findById(id);
	  if (null != tutorialData) {
	    return new ResponseEntity<>(tutorialData, HttpStatus.OK);
	  } else {
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	  }
	}
	
	@PostMapping("/tutorials")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Board> createTutorial(@RequestBody Board tutorial) {
	  try {
	    boardRepository.insertBoard(new Board(tutorial.getTitle(), tutorial.getDescription(), false));
	    return new ResponseEntity<>(null, HttpStatus.CREATED);
	  } catch (Exception e) {
		  e.printStackTrace();
	    return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	@PutMapping("/tutorials/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<Board> updateTutorial(@PathVariable("id") int id, @RequestBody Board tutorial) {
	  Board tutorialData = boardRepository.findById(id);
	  if (null != tutorialData) {
		  tutorialData.setTitle(tutorial.getTitle());
		  tutorialData.setDescription(tutorial.getDescription());
		  tutorialData.setPublished(tutorial.isPublished());
		  boardRepository.updateBoard(tutorialData);
	    return new ResponseEntity<>(tutorialData, HttpStatus.OK);
	  } else {
	    return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	  }
	}
	
	@DeleteMapping("/tutorials/{id}")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteTutorial(@PathVariable("id") int id) {
	  try {
	    boardRepository.deleteById(id);
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  } catch (Exception e) {
	    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	@DeleteMapping("/tutorials")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<HttpStatus> deleteAllTutorials() {
	  try {
	    boardRepository.deleteAll();
	    return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	  } catch (Exception e) {
	    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
	@GetMapping("/tutorials/published")
	@PreAuthorize("hasRole('USER') or hasRole('MODERATOR') or hasRole('ADMIN')")
	public ResponseEntity<List<Board>> findByPublished() {
	  try {
	    List<Board> tutorials = boardRepository.findByPublished(true);
	    if (tutorials.isEmpty()) {
	      return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	    }
	    return new ResponseEntity<>(tutorials, HttpStatus.OK);
	  } catch (Exception e) {
	    return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
	  }
	}
	
}
