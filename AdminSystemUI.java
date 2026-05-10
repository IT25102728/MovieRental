import com.formdev.flatlaf.FlatIntelliJLaf;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class AdminSystemUI extends JFrame {
    private AdminManager manager = new AdminManager(); // Logic handle කරන කෙනා
    private DefaultTableModel tableModel;
    private JTable table;
    private JTextField txtId, txtName;
    private JComboBox<String> cmbPerms;

    public AdminSystemUI() {
        try { UIManager.setLookAndFeel(new FlatIntelliJLaf()); } catch (Exception e) {}

        setTitle("Campus Project - Admin Management");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Sidebar සහ Main Panel එක වෙන් කරමු
        JPanel mainPanel = new JPanel(new BorderLayout());

        // --- Sidebar ---
        JPanel sidebar = new JPanel();
        sidebar.setBackground(new Color(236, 240, 241));
        sidebar.setPreferredSize(new Dimension(200, 0));
        sidebar.add(new JLabel("SYSTEM MENU"));

        // --- Content Area ---
        JPanel contentArea = new JPanel(new BorderLayout(20, 20));
        contentArea.setBorder(new EmptyBorder(20, 20, 20, 20));

        // 1. INPUT FORM (Create කොටස)
        JPanel formPanel = new JPanel(new GridLayout(1, 7, 10, 10));
        txtId = new JTextField();
        txtName = new JTextField();
        String[] options = {"Full Access", "Editor", "Viewer"};
        cmbPerms = new JComboBox<>(options);
        JButton btnAdd = new JButton("Add Admin");

        formPanel.add(new JLabel("ID:")); formPanel.add(txtId);
        formPanel.add(new JLabel("Name:")); formPanel.add(txtName);
        formPanel.add(new JLabel("Role:")); formPanel.add(cmbPerms);
        formPanel.add(btnAdd);

        // 2. TABLE (Read කොටස)
        String[] columns = {"ID", "Full Name", "Permission"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        refreshTable(); // මුලින්ම තියෙන දත්ත පෙන්වන්න

        // 3. ACTION BUTTONS (Update/Delete කොටස)
        JPanel actionPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton btnDelete = new JButton("Delete Selected");
        JButton btnUpdate = new JButton("Update Selected");
        actionPanel.add(btnUpdate);
        actionPanel.add(btnDelete);

        // --- BUTTON LOGIC (CRUD) ---

        // CREATE
        btnAdd.addActionListener(e -> {
            if (txtId.getText().isEmpty() || txtName.getText().isEmpty()) {
                JOptionPane.showMessageDialog(this, "Fields cannot be empty!");
                return;
            }
            AdminUser admin = new AdminUser(txtId.getText(), txtName.getText(), cmbPerms.getSelectedItem().toString());
            manager.saveAdmin(admin); // File එකට ලියනවා
            refreshTable(); // Table එක අලුත් කරනවා
            clearFields();
        });

        // DELETE
        btnDelete.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                String id = tableModel.getValueAt(row, 0).toString();
                manager.deleteAdmin(id); // File එකෙන් මකනවා
                refreshTable();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to delete.");
            }
        });

        // UPDATE
        btnUpdate.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (row != -1) {
                String id = tableModel.getValueAt(row, 0).toString();
                String newPerm = JOptionPane.showInputDialog(this, "Enter New Permission Role:");
                if (newPerm != null && !newPerm.isEmpty()) {
                    manager.updateAdmin(id, newPerm); // File එක update කරනවා
                    refreshTable();
                }
            }
        });

        // Layout එකට එකතු කිරීම
        contentArea.add(formPanel, BorderLayout.NORTH);
        contentArea.add(new JScrollPane(table), BorderLayout.CENTER);
        contentArea.add(actionPanel, BorderLayout.SOUTH);

        mainPanel.add(sidebar, BorderLayout.WEST);
        mainPanel.add(contentArea, BorderLayout.CENTER);
        add(mainPanel);

        setVisible(true);
    }

    // READ - Table එක refresh කරන function එක
    private void refreshTable() {
        tableModel.setRowCount(0); // පරණ දත්ත අයින් කරන්න
        for (AdminUser a : manager.getAllAdmins()) {
            tableModel.addRow(new Object[]{a.getId(), a.getName(), a.getPermissions()});
        }
    }

    private void clearFields() {
        txtId.setText("");
        txtName.setText("");
    }

    public static void main(String[] args) {
        new AdminSystemUI();
    }
}