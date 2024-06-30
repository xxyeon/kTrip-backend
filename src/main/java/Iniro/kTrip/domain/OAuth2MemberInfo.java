package Iniro.kTrip.domain;

public interface OAuth2MemberInfo {
    String getProviderId(); //공급자 아이디 ex) google, facebook
    String getProvider(); //공급자 ex) google, facebook
    String getName(); //사용자 이름 ex) 홍길동
    String getEmail(); //사용자 이메일 ex) gildong@gmail.com

    // name, nickname도 넣어야 하는데 이건 Oauth 더 확인해보고 ㄱㄱ.
    // 받아올 때 추가해야 하는 건지, 아니면 그냥 추가설정이 가능하도록 만들지
}
