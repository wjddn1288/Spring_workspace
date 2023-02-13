package com.edu.db.model.board;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.edu.db.domain.Board;
import com.edu.db.exception.BoardException;

//아래의 어노테이션은 무조건 붙이는게 아니라 bean 설정파일의 양을 줄이고자 xml 에 bean등록을
//하지 않았을때 사용되는 방법이다. 즉 component-scan을 이용하고자 할때 사용하는 것임
@Repository
public class MybatisBoardDAO implements BoardDAO{
	
	@Autowired
	private SqlSessionTemplate sqlSessionTemplate;
	
	@Override
	public List selectAll() {
		return sqlSessionTemplate.selectList("Board.selectAll");
	}

	@Override
	public Board select(int board_idx) {
		return sqlSessionTemplate.selectOne("Board.select", board_idx);
	}

	@Override
	public void insert(Board board) throws BoardException{
		int result=sqlSessionTemplate.insert("Board.insert",board);
		if(result<1) {
			throw new BoardException("등록 실패");
		}
	}

	@Override
	public void update(Board board) throws BoardException{
		int result=sqlSessionTemplate.update("Board.update",board);
		if(result<1) {
			throw new BoardException("수정 실패");
		}
	}

	@Override
	public void delete(int board_idx) throws BoardException {
		int result=sqlSessionTemplate.delete("Board.delete",board_idx);
		if(result<1) {
			throw new BoardException("삭제 실패");
		}
	
	}

}
