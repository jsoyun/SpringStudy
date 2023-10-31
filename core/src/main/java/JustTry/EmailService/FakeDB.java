package JustTry.EmailService;

public class FakeDB {
    public String DB ="";

    public String getDB() {
        return DB;
    }

    public void setDB(String DB) {
        this.DB = DB;
    }

    //처리된
    public void FileCompletedInvoice(Invoice invoice){


        // StringBuilder를 사용하여 문자열에 한 줄씩 띄워서 덧붙임
        StringBuilder stringBuilder = new StringBuilder(DB);
        stringBuilder.append("\n추가됨:"+ invoice.toString());


        // 결과 문자열
        String resultString = stringBuilder.toString();

        // 결과 출력
        System.out.println("FileInvoice확인:"+ resultString);
    }

    @Override
    public String toString() {
        return "FakeDBConnection{" +
                "DB='" + DB + '\'' +
                '}';
    }
}
