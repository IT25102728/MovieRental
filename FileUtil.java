package util;

import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

/**
 * FileUtil - Reusable utility class for all file I/O operations
 * Uses BufferedReader/BufferedWriter for efficient file handling
 */
public class FileUtil {

    /**
     * Read all non-empty lines from a file
     * @param filePath absolute path to the .txt file
     * @return list of lines
     */
    public static List<String> readLines(String filePath) {
        List<String> lines = new ArrayList<>();
        File file = new File(filePath);
        if (!file.exists()) return lines;

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("[FileUtil] Error reading " + filePath + ": " + e.getMessage());
        }
        return lines;
    }

    /**
     * Write all lines to a file (overwrites existing content)
     * @param filePath absolute path
     * @param lines    list of lines to write
     */
    public static void writeLines(String filePath, List<String> lines) {
        ensureFileExists(filePath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        } catch (IOException e) {
            System.err.println("[FileUtil] Error writing " + filePath + ": " + e.getMessage());
        }
    }

    /**
     * Append a single line to a file
     */
    public static void appendLine(String filePath, String line) {
        ensureFileExists(filePath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            System.err.println("[FileUtil] Error appending to " + filePath + ": " + e.getMessage());
        }
    }

    /**
     * Ensure file and parent directories exist
     */
    public static void ensureFileExists(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.getParentFile().mkdirs();
                file.createNewFile();
            } catch (IOException e) {
                System.err.println("[FileUtil] Cannot create file " + filePath + ": " + e.getMessage());
            }
        }
    }

    /**
     * Generate a simple unique ID based on timestamp + random
     */
    public static String generateId(String prefix) {
        return prefix + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }

    /**
     * Check if an ID already exists in the lines (first field of CSV)
     */
    public static boolean idExists(List<String> lines, String id) {
        for (String line : lines) {
            String[] parts = line.split(",", 2);
            if (parts.length > 0 && parts[0].trim().equals(id)) return true;
        }
        return false;
    }

    /**
     * Find a line by first field (ID)
     */
    public static String findById(List<String> lines, String id) {
        for (String line : lines) {
            String[] parts = line.split(",", 2);
            if (parts.length > 0 && parts[0].trim().equals(id)) return line;
        }
        return null;
    }

    /**
     * Delete a line by first field (ID)
     * @return true if deleted
     */
    public static boolean deleteById(String filePath, String id) {
        List<String> lines = readLines(filePath);
        List<String> updated = new ArrayList<>();
        boolean found = false;
        for (String line : lines) {
            String[] parts = line.split(",", 2);
            if (parts.length > 0 && parts[0].trim().equals(id)) {
                found = true; // skip this line (delete it)
            } else {
                updated.add(line);
            }
        }
        if (found) writeLines(filePath, updated);
        return found;
    }

    /**
     * Update a line that starts with the given ID
     */
    public static boolean updateById(String filePath, String id, String newLine) {
        List<String> lines = readLines(filePath);
        boolean found = false;
        for (int i = 0; i < lines.size(); i++) {
            String[] parts = lines.get(i).split(",", 2);
            if (parts.length > 0 && parts[0].trim().equals(id)) {
                lines.set(i, newLine);
                found = true;
                break;
            }
        }
        if (found) writeLines(filePath, lines);
        return found;
    }
}
