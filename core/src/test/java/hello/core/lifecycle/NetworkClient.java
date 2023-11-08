package hello.core.lifecycle;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

//가짜 네트워크 클라이언트
public class NetworkClient  {
    private  String url;
    public NetworkClient(){
        System.out.println("생성자 호출, url=" + url);

        
    }
    public void setUrl(String url) {
        this.url = url;
    }
    //서비스 시작시 호출
    public void connect(){
        System.out.println("connect = " + url);
    }
    
    //연결한 서버에 메시지를 던진다
    public void call(String message){
        System.out.println("call " + url + " message =" + message);
    }

    //서비스 종료시 호출 - 연결끊기
    public void disConnect(){
        System.out.println("close = " + url);


    }


    //초기화 , 소멸 인터페이스 사용시 !
    //InitializingBean, DisposableBean
//    //의존관계 주입이 끝나면 호출해주겠다는 뜻
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        connect();
//        call("초기화 연결 메시지");
//    }
//
//    //빈이 종료될때 호출
//    @Override
//    public void destroy() throws Exception {
//        disConnect();
//
//    }
    @PostConstruct
    public void init(){
        System.out.println("NetworkClient.init");
        connect();
        call("초기화 연결 메세지");
    }
    @PreDestroy
    public void close() throws Exception{
        System.out.println("NetworkClient.close");
        disConnect();
    }
}
