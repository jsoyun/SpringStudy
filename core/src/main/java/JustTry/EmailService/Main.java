package JustTry.EmailService;

public class Main {
    public static void main(String[] args) {
        Service serivice = new Service();
        System.out.println(serivice.getServiceName());
        InvoiceService invoiceService = new InvoiceService();
        invoiceService.execute();



    }
}
