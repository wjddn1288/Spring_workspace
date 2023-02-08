package gui.cook;

public class Cook {
	//요리사 객체는 팬 객채에 의존하고 있다...(의존은 나쁜거)
	//(1)DI를 구현하기 위해서는 객체가 보유할 자료형은 하위보단 
	//상위 객체일수록 유연해진다.
	//(2)직접 new 연산자로 객체를 생성하지 말라
	// new 연산자 사용시 대상 자료형을 명시해야 한다
	Pan pan; //요리사가 팬을 가진다
	
	public Cook() {
		//pan = new Fripan();
	}
	public void cook() {
		pan.heat();
	}
}
