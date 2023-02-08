package com.mvc3.model.emp;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.mvc3.domain.Emp;
import com.mvc3.exception.EmpException;

public class EmpDAO {
private SqlSession sqlSession;
	
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
		//insert 에서 직접 취득할수 있지만 트랜잭션이 안된다!!
	}
	
	public void insert(Emp Emp) throws EmpException{
		int result=sqlSession.insert("Emp.insert",Emp);
		if(result<1) {
			throw new EmpException("부서등록 실패");
		}
	}
	
	public List selectAll() {
		return sqlSession.selectList("Emp.selectAll");
	}
	
	public Emp select(int empno) {
		return sqlSession.selectOne("Emp.select",empno);
	}
	
	public void delete(int empno) throws EmpException{
		int result=sqlSession.delete("Emp.delete", empno);
		if(result<1) {
			throw new EmpException("사원삭제 실패");
		}
	}
}
