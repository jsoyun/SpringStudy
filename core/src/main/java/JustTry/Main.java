package JustTry;



public class Main{
    public static Callback getCallback() {
        return callback;
    }
    private static Callback callback;



    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public static void main(String[] args) {

//        EventProcessor eventProcessor = new EventProcessor();
        Main mainInstance = new Main();
//        //근데 인터페이스는 객체생성할 수 없는데.. 구현객체로 어떻게 만들어서 담는거지?
//        Callback callback1 = () -> {
//            System.out.println("첫 번째 객체의 콜백 함수 호출됨");
//        };




        // 첫 번째 객체의 콜백 함수 정의

        Callback callback1 = () -> {
            System.out.println("gmdgmd");

        };

        // 두 번째 객체의 콜백 함수 정의

        mainInstance.setCallback(callback1);
        getCallback().Say();

        NewProccessor newProccessor = new NewProccessor();
        newProccessor.newMethod();



//        // 객체에 콜백 함수를 전달
//        processor.setCallback(callback1);
//        processor.doWork();
//
//        processor.setCallback(callback2);
//        processor.doWork();



    }

}
