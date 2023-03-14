package com.edu.springshop.sns;

import lombok.Data;

//구글 서버에서 전송받은 json 문자열을 자바의 객체로 담아놓기 위한 목적
@Data
public class KakaoOAuthToken {
	private String access_token; //회원정보 가져올때 쓰임!
	private String token_type;
	private String refresh_token; //재발급 필요한 정보
	private int expires_in; //유효기간
	private String scope;
	private String refresh_token_expires_in;
	
}
