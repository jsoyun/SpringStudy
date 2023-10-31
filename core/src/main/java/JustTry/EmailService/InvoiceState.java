package JustTry.EmailService;

public enum InvoiceState {
    READY, //Box에 쌓여있음
    DOING, // Management가 BOX에서 가져옴
    COMPLETED //처리돼서 FakeDB에 저장됨
}
