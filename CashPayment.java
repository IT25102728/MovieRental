package model;

public class CashPayment extends Payment {
    public CashPayment() { super(); }
    public CashPayment(String paymentId, String userId, double amount,
                       String paymentMethod, String status, String date) {
        super(paymentId, userId, amount, paymentMethod, status, date);
    }
    @Override
    public String getPaymentType() { return "Cash"; }
}
