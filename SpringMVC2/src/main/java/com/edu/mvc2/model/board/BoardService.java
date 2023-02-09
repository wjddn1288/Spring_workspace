package com.edu.mvc2.model.board;

import java.util.List;

import com.edu.mvc2.domain.Board;

//업무가 워낙 크니까 인터페이스로 바꿔줌 부모는 기준만 세울뿐
public interface BoardService {
	
	public List selectAll(); 
	public Board select(int board_idx);
	public void insert(Board board);
	public void update(Board board);
	public void delete(int board_idx);
	
}
