package gui.school;

public class Student {
//	Bell bell;// 의존성 약화시키기 위한 상위 객체 이용!!!
	
	//생성자 주입도 주입으로 인정된다!
	/*
	public Student(Bell bell) {
		this.bell=bell;
	}
	*/
	
	public void goschool() {
		System.out.println("등교합니다");
	}
	
	public void startStudy() {
		System.out.println("수업 시작합니다");
	}
	
	public void endStudy() {
		System.out.println("수업 종료합니다");
	}	
	
	public void haveLunch() {
		System.out.println("점심 식사합니다");
	}	
	
	public void startStudy2() {
		System.out.println("오후 수업 시작합니다");
	}	
	
	public void gohome() {
		System.out.println("하교 합니다");
	}	
}