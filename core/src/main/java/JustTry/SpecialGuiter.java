package JustTry;

public class SpecialGuiter extends Guiter{
    @Override
    public void play() {
        System.out.println("특별한 음악연주");
    }
    public void playSpecial(){
        System.out.println("특별한 음악연주");
    }

    public static void main(String[] args) {
        SpecialGuiter specialGuiter = new SpecialGuiter();
        specialGuiter.play();
        specialGuiter.playSpecial();

    }
}
