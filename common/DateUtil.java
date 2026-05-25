package util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * DateUtil - Date formatting and calculation helper
 */
public class DateUtil {

    public static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static String today() {
        return LocalDate.now().format(FMT);
    }

    public static LocalDate parse(String dateStr) {
        if (dateStr == null || dateStr.trim().isEmpty()) return null;
        try { return LocalDate.parse(dateStr.trim(), FMT); }
        catch (Exception e) { return null; }
    }

    /**
     * Days between two date strings (positive = daysLate)
     */
    public static long daysBetween(String from, String to) {
        LocalDate d1 = parse(from);
        LocalDate d2 = parse(to);
        if (d1 == null || d2 == null) return 0;
        return ChronoUnit.DAYS.between(d1, d2);
    }

    /**
     * Calculate fine: rental period is 3 days; after that $1.50/day
     */
    public static double calculateFine(String rentDate, double dailyFee) {
        LocalDate rented = parse(rentDate);
        if (rented == null) return 0;
        long days = ChronoUnit.DAYS.between(rented, LocalDate.now());
        long lateDays = days - 3; // 3-day free rental period
        return lateDays > 0 ? lateDays * dailyFee : 0;
    }
}
