package service;

import model.Payment;
import util.AppConfig;
import util.DateUtil;
import util.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * PaymentService - handles payment processing and history
 */
public class PaymentService {

    private String getFile() { return AppConfig.getPaymentsFile(); }

    /**
     * Process a new payment
     */
    public String processPayment(String userId, double amount, String paymentMethod) {
        if (amount <= 0) return "Invalid payment amount.";
        String paymentId = FileUtil.generateId("PAY");
        String line = paymentId + "," + userId + "," + amount + ","
                + paymentMethod + ",completed," + DateUtil.today();
        FileUtil.appendLine(getFile(), line);
        return null; // success
    }

    public List<Payment> getAllPayments() {
        List<Payment> list = new ArrayList<>();
        for (String line : FileUtil.readLines(getFile())) {
            Payment p = Payment.fromFileString(line);
            if (p != null) list.add(p);
        }
        return list;
    }

    public List<Payment> getPaymentsByUser(String userId) {
        List<Payment> list = new ArrayList<>();
        for (Payment p : getAllPayments()) {
            if (p.getUserId().equals(userId)) list.add(p);
        }
        return list;
    }

    /**
     * Total revenue across all completed payments
     */
    public double getTotalRevenue() {
        double total = 0;
        for (Payment p : getAllPayments()) {
            if ("completed".equalsIgnoreCase(p.getStatus())) total += p.getAmount();
        }
        return total;
    }

    /**
     * Total payments by a specific user
     */
    public double getTotalByUser(String userId) {
        double total = 0;
        for (Payment p : getPaymentsByUser(userId)) {
            if ("completed".equalsIgnoreCase(p.getStatus())) total += p.getAmount();
        }
        return total;
    }
}
