package JustTry.EmailService;

public class InvoiceService extends Service {


    @Override
    public String getServiceName() {
        return super.getServiceName();
    }

    @Override
    public void setServiceName(String serviceName) {
        super.setServiceName(serviceName);
    }

    @Override
    public void execute() {
        //보낼 편지함에 Invoice가 있으면 전송
        //모든 동작을 Management 하나가 처리.
        Sender sender = new Sender();
        Reciever reciever = new Reciever();
        sender.setName("이소윤");
        reciever.setName("햄스터");
        Invoice invoice = new Invoice(sender, reciever,"잘지내?");
        InvoiceBox invoiceBox = new InvoiceBox(invoice);


        FakeDB fakeDB = new FakeDB();
//        fakeDB.FileCompletedInvoice();




//        management.

        //
    }
    //    public void




    private void writeInvoice( Sender sender){

        System.out.println(sender.getName());


    };
}
