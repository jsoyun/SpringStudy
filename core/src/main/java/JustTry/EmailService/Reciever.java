package JustTry.EmailService;

public class Reciever {
    public String name;
    private int password;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean checkPassword(int password){
        if(this.password == password){
            return true;
        } else return false;
    };

    @Override
    public String toString() {
        return "Reciever{" +
                "name='" + name + '\'' +
                '}';
    }
}
