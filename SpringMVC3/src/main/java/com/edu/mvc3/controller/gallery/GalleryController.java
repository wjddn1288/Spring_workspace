package com.edu.mvc3.controller.gallery;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.edu.mvc3.domain.Gallery;
import com.edu.mvc3.exception.UploadException;
import com.edu.mvc3.model.GalleryService;
import com.edu.mvc3.model.util.FileManager;

import lombok.Setter;

//갤러리와 관련된 모든 요청을 처리할 수 있는 하위 컨트롤러
//POJO 추구 : 즉 개발자에게 구현을 강제하지 않는다..
//더이상 xml에 등록하지 않으므로, 컨트롤러 클래스에 무언가 표시를 해야
//스프링이 인스턴스를 생성해준다..
//특히 스프링이 생성할 인스턴스의 대상이 되는 객체들을 컴포넌트라 하는데
//이 컴포넌트의 대표적인 종류로는... @Controller, @Service,
//@Repository(DAO라 안씀!!), @Component(뭔지 헷갈리면)
@Controller
@Setter
public class GalleryController {
	Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	//스프링의 빈 컨테이너가 보유한 빈들 중, 아래의 ID(멤버변수명)이나 
	//자료형과 일치하는 빈이 있을경우, 자동으로 setter 주입
	@Autowired //자동으로 넣어줘~ wired 엮다
	private FileManager fileManager; //주입받기!
	
	private GalleryService gallerService;
	
	// 글에 목록요청 처리
	// url 매핑도 xml이 아닌 자바클래스에 둔다
	@RequestMapping(value = "/client/gallery/list")
	public String getList() {
		logger.info("갤러리 목록 요청 받음");

		// 비록 string을 반환하지만, 여전히
		return "gallery/list"; 
	}

	// 갤러리 등록폼 요청 처리
	@RequestMapping(value = "/client/gallery/registform")
	public String getRegistForm() {

		return "gallery/regist"; //uri가 아니라 자원(파일)의 위치임!! 내부 경로
	}

	// 갤러리 등록 요청
	@RequestMapping(value = "/client/gallery/regist")
	public String regist(Gallery gallery, HttpServletRequest request) { // DTO 넣으면 알아서 채워줌!

		MultipartFile file = gallery.getFile();
		String filename = file.getOriginalFilename();
		logger.info("파일명은" + filename);

		// 현재 업로드된 파일은 아직 물리적 파일로 존재하지 않고 메모리상에 존재하므로
		// 서버의 로컬 디렉토리에 저장을 해보자
		ServletContext servletContext = request.getSession().getServletContext();
		String realPath=(String)servletContext.getAttribute("dataPath");
		fileManager.save(gallery, realPath+filename);

		// 쓴 후, 클라이언트 브라우저로 하여금 재접속을 유도한다..
		return "redirect:/client/gallery/list"; //웹브라우저의 접근 주소 /외부에서 바라본 경로
	}
	
	//현재 이 컨트롤러의 모든 요청처리 메서드에서, 에러가 발생하면 즉 예외가 발생하면
	//아래의 ExceptionHandler 가 동작하게 되어 있는데, 단 해당 예외클래스에 맞는 메서드만
	//호출됨..
	@ExceptionHandler(UploadException.class)
	public ModelAndView handle(UploadException e) {
		
		ModelAndView mav= new ModelAndView();
		mav.setViewName("error/result");
		mav.addObject("e", e); //에러 객체저장하기
		//개발자가 redirect를 명시하지 않았으므로, error.jsp로 forward된다
		return mav;
	}
}






