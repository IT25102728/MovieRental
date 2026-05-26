package model;

public class CardPayment extends Payment {
    public CardPayment() { super(); }
    public CardPayment(String paymentId, String userId, double amount,
                       String paymentMethod, String status, String date) {
        super(paymentId, userId, amount, paymentMethod, status, date);
    }
    @Override
    public String getPaymentType() { return "Card"; }
}
