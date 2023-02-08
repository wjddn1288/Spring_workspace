package com.mvc3.model.emp;

import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.mvc3.domain.Emp;
import com.mvc3.exception.DeptException;
import com.mvc3.exception.EmpException;
import com.mvc3.mybatis.MybatisConfig;

/*이 객체는 모델 파트에서의 서비스 역할을 수행한다
 * 만일 서비스의 존재가 없을 경우, 컨트롤러가 너무 세부적인 model 
 * 영역의 업무를 수행하게 된다 또한 트랜잭션 상황에서 
 * 각각의 DAO들의 업무 수행이 성공했는지 여부를 판단하여 
 * 트랜잭션을 commit 할지 rollback을 결정짓는 역할 수행한다
 * 주의) 직접 일하지 않고 각종 모델영역의 객체들에게 일을 시킨다 
 * */
public class EmpService {
	MybatisConfig config = MybatisConfig.getInstance();
	DeptDAO deptDAO; //사원
	EmpDAO empDAO; //사원

	public EmpService() { //부장님 모델 파트에 지휘자
		deptDAO = new DeptDAO();
		empDAO = new EmpDAO();

	}
	
	//사원등록
	public void regist(Emp emp) {
		// 세션 얻어와서 배분하기(주입)
		SqlSession sqlSession = config.getSqlSession();
		deptDAO.setSqlSession(sqlSession);
		empDAO.setSqlSession(sqlSession);
		
		try {
			deptDAO.insert(emp.getDept()); //부서등록 commit때문에 트,캐
			empDAO.insert(emp); //사원등록
			sqlSession.commit();
		} catch (DeptException e) {
			sqlSession.rollback();
			e.printStackTrace();
		} catch (EmpException e) {
			sqlSession.rollback();
			e.printStackTrace();
		}finally {
			config.release(sqlSession);
		}
	}
	
	public Emp select(int empno) {
		SqlSession sqlSession= config.getSqlSession();
		empDAO.setSqlSession(sqlSession);
		Emp emp=empDAO.select(empno);
		config.release(sqlSession);
		return emp;
	}

	public List selectAll() {
		SqlSession sqlSession = config.getSqlSession();
		empDAO.setSqlSession(sqlSession);
		List list = empDAO.selectAll();
		config.release(sqlSession);
		return list;
	}

	//사원 퇴사처리(부서, 사원 둘다 삭제)
	public void remove(Emp emp) {
		SqlSession sqlSession= config.getSqlSession();
		empDAO.setSqlSession(sqlSession);
		deptDAO.setSqlSession(sqlSession);
		
		//사원 삭제
		try {
			empDAO.delete(emp.getEmpno()); //사원삭제
			deptDAO.delete(emp.getDept().getDeptno()); //부서삭제 
			sqlSession.commit();
		} catch (DeptException e) {
			e.printStackTrace();
			sqlSession.rollback();
		} catch (EmpException e) {
			e.printStackTrace();
			sqlSession.rollback();
		}finally {
			config.release(sqlSession);
		}
	}

}


