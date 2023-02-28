package com.edu.springshop.aop;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.ProceedingJoinPoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.edu.springshop.exception.AdminException;

public class AdminLoginCheckAdvice {
	
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	
	public Object sessionCheck(ProceedingJoinPoint joinPoint) throws AdminException, Throwable{
		Object result=null; //proceed() 후 반환 되는 객체를 담기위한 변수(ModelAndView, String...) 
		
		//ProceedingJoinPoint : 주로 타켓에 대한 정보를 가지고 있다..
		//								  원래 호출하려면 타겟 객체가 무엇인지, 그 타겟 객체의 무슨 메서드
		//								  를 호출하려 했는지, 그 타겟 메서드의 매개변수는 무엇인지..
		
		Class targetClass=joinPoint.getTarget().getClass();
		Object[] args=joinPoint.getArgs(); //매개변수를 배열로 반환함..
		
		logger.info("호출하려는 타켓 클래스는"+targetClass.getName());
		logger.info("호출하려는 메서드의 매개변수는"+args.length);
		
		//현재 이 요청에 대해 session에 값이 들어있는지 여부를 조사해보자
		//원래 호출하려했던 메서드의 매개변수 정보를 현재 대리객체가 추출할 수 있으므로,
		//원래 호출하려했던 메서드에는 HTTpServletRequest가 명시되어 있어야 한다..
		
		//타켓 메서드에서 HttpServletRequest 추출하여 session에 관리자 객체가 들어있는지 체크하기
		HttpServletRequest request=null;
		HttpSession session=null;
		
		for(int i=0; i<args.length; i++) {
			if(args[i] instanceof HttpServletRequest) {
				request = (HttpServletRequest)args[i];
			}
		}

		//로그인을 체크해야 되는 경우와, 그냥 보내야 하는 경우를 나눈다
		String uri=request.getRequestURI();
		if(
				uri.equals("/admin/loginform") ||
				uri.equals("/admin/rest/login/admin") 
		   ) {
			result=joinPoint.proceed();
		}else {
			//로그인이 필요한 서비스에서만 아래의 코드들이 수행되어야 한다..
			session=request.getSession();
			
			if(session.getAttribute("admin")==null) {
				logger.info("aop에 의한 로그인 체크 : 세션없음");
				throw new AdminException("로그인이 필요한 서비스입니다.");
			}else {
				result=joinPoint.proceed(); //로그인한 사람은 가던 길 가게...
			}
			
		}
		
		return result;
	}
	
}
