package JustTry;

public class NewProccessor implements UpdatedCallback{
    @Override
    public void Say() {
        System.out.println("새로운 말");

    }

    @Override
    public void newMethod() {
        System.out.println("새로운 기능");

    }

    @Override
    public void testMethod() {
        System.out.println("테스트 기능");

    }
}
