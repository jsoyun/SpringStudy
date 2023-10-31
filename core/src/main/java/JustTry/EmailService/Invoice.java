package JustTry.EmailService;

public class Invoice {
    Sender sender ;
    Reciever reciever;
    String text ="";

    public Invoice(Sender sender, Reciever reciever, String text) {
        this.sender = sender;
        this.reciever = reciever;
        this.text = text;


    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }



    public Sender getSender() {
        return sender;
    }

    public void setSender(Sender sender) {
        this.sender = sender;
    }

    public Reciever getReciever() {
        return reciever;
    }

    public void setReciever(Reciever reciever) {
        this.reciever = reciever;
    }


    @Override
    public String toString() {
        return "Invoice{" +
                "sender=" + sender +
                ", reciever=" + reciever +
                ", text='" + text + '\'' +
                '}';
    }
}
