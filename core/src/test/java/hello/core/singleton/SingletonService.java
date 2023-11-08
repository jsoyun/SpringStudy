package hello.core.singleton;

public class SingletonService {
    private static final SingletonService instance = new SingletonService();
    public static SingletonService getInstance(){
        return instance;
    }
    //외부에서 객체 생성 못하도록 생성자를 private으로 선언
    private  SingletonService(){

    }

   public void logic(){
        System.out.println("싱글톤 객체 로직 호출");
   }
}
