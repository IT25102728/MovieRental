package model;

/**
 * Payment base class - paymentId,userId,amount,paymentMethod,status,date
 */
public class Payment {
    private String paymentId;
    private String userId;
    private double amount;
    private String paymentMethod; // "card" or "cash"
    private String status;        // "completed", "pending", "failed"
    private String date;

    public Payment() {}

    public Payment(String paymentId, String userId, double amount,
                   String paymentMethod, String status, String date) {
        this.paymentId = paymentId;
        this.userId = userId;
        this.amount = amount;
        this.paymentMethod = paymentMethod;
        this.status = status;
        this.date = date;
    }

    public String getPaymentId() { return paymentId; }
    public void setPaymentId(String paymentId) { this.paymentId = paymentId; }

    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }

    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }

    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getDate() { return date; }
    public void setDate(String date) { this.date = date; }

    public String getPaymentType() { return "General"; }

    public String toFileString() {
        return paymentId + "," + userId + "," + amount + "," + paymentMethod + "," + status + "," + date;
    }

    public static Payment fromFileString(String line) {
        String[] parts = line.split(",", -1);
        if (parts.length < 6) return null;
        double amount = 0;
        try { amount = Double.parseDouble(parts[2].trim()); } catch (Exception ignored) {}
        String method = parts[3].trim();
        Payment p;
        if ("card".equalsIgnoreCase(method)) {
            p = new CardPayment(parts[0].trim(), parts[1].trim(), amount, method, parts[4].trim(), parts[5].trim());
        } else {
            p = new CashPayment(parts[0].trim(), parts[1].trim(), amount, method, parts[4].trim(), parts[5].trim());
        }
        return p;
    }
}
