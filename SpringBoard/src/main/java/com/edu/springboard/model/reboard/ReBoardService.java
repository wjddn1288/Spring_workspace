package com.edu.springboard.model.reboard;

import java.util.List;

import com.edu.springboard.domain.ReBoard;

public interface ReBoardService {
	//DAO와 Service는 메서드 명이 같아야 한다는 기준은 없다!
	//하지만 다르게 주면 업무내용이 동일한데 굳이, 다르게 줄 이유도 없다 
	public List selectAll();
	public ReBoard select(int reboard_idx);
	public void insert(ReBoard reboard);
	public void update(ReBoard reboard);
	public void delete(int reboard_idx);
	
	//답변관련
	public void registReply(ReBoard reboard);  //답변등록
		
}
