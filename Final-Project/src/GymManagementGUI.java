import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.List;
import java.util.Iterator;

public class GymManagementGUI extends JFrame {
    private GymDatabase db = GymDatabase.getInstance();
    private CommandInvoker invoker = new CommandInvoker();
    private JTextArea displayArea;

    public GymManagementGUI() {
        // --- Setup Window ---
        setTitle("Titan Gym Management System");
        setSize(900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // --- Styles ---
        Color primaryColor = new Color(44, 62, 80);
        Color accentColor = new Color(52, 152, 219);

        // --- Sidebar Menu ---
        JPanel sidebar = new JPanel();
        sidebar.setLayout(new GridLayout(8, 1, 5, 5));
        sidebar.setBackground(primaryColor);
        sidebar.setBorder(new EmptyBorder(10, 10, 10, 10));

        JButton btnRegister = createStyledButton("1. Register Member", accentColor);
        JButton btnView = createStyledButton("2. View Member List", accentColor);
        JButton btnBuy = createStyledButton("3. Buy Membership", accentColor);
        JButton btnReport = createStyledButton("4. View Strategy Report", accentColor);
        JButton btnStats = createStyledButton("5. Database Stats", accentColor);
        JButton btnUndo = createStyledButton("6. Undo Last Action", new Color(231, 76, 60));
        JButton btnExit = createStyledButton("7. Exit", Color.GRAY);

        sidebar.add(btnRegister);
        sidebar.add(btnView);
        sidebar.add(btnBuy);
        sidebar.add(btnReport);
        sidebar.add(btnStats);
        sidebar.add(btnUndo);
        sidebar.add(new JLabel("")); // Spacer
        sidebar.add(btnExit);

        // --- Main Display Area ---
        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
        displayArea.setBackground(new Color(245, 245, 245));
        JScrollPane scrollPane = new JScrollPane(displayArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Console Output / Member List"));

        // --- Header ---
        JLabel header = new JLabel("TITAN GYM MANAGEMENT", JLabel.CENTER);
        header.setFont(new Font("Verdana", Font.BOLD, 24));
        header.setForeground(Color.WHITE);
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(accentColor);
        headerPanel.add(header, BorderLayout.CENTER);
        headerPanel.setPreferredSize(new Dimension(0, 60));

        // --- Adding Observers for the GUI ---
        // This leverages your Observer pattern to update the UI automatically
        db.addObserver(member -> {
            updateDisplay("Notification: New member registered - " + member.getName());
            showMembers();
        });
        db.addObserver(new EmailNotification()); // Keeping your original observers
        db.addObserver(new SMSNotification());

        // --- Button Actions ---
        btnRegister.addActionListener(e -> registerMemberForm());
        btnView.addActionListener(e -> showMembers());
        btnBuy.addActionListener(e -> buyMembershipForm());
        btnReport.addActionListener(e -> showMembershipReport());
        btnStats.addActionListener(e -> showStats());
        btnUndo.addActionListener(e -> {
            invoker.undoLastCommand();
            showMembers();
        });
        btnExit.addActionListener(e -> System.exit(0));

        add(headerPanel, BorderLayout.NORTH);
        add(sidebar, BorderLayout.WEST);
        add(scrollPane, BorderLayout.CENTER);

        setLocationRelativeTo(null); // Center on screen
    }

    // --- Logic Methods ---

    private void registerMemberForm() {
        JTextField idField = new JTextField();
        JTextField nameField = new JTextField();
        JTextField ageField = new JTextField();

        Object[] message = {
                "ID:", idField,
                "Name:", nameField,
                "Age:", ageField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Register New Member", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            try {
                Member newMember = new Member.Builder(idField.getText(), nameField.getText())
                        .setAge(Integer.parseInt(ageField.getText()))
                        .build();

                Command registerCmd = new RegisterMemberCommand(newMember, db);
                invoker.executeCommand(registerCmd);
                JOptionPane.showMessageDialog(this, "Member " + nameField.getText() + " successfully registered!");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error: Invalid Input", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void showMembers() {
        displayArea.setText("");
        List<Member> members = db.getMembers();
        if (members.isEmpty()) {
            displayArea.setText("No members found in database.");
            return;
        }
        StringBuilder sb = new StringBuilder("--- CURRENT MEMBERS ---\n\n");
        Iterator<Member> it = members.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString()).append("\n");
        }
        displayArea.setText(sb.toString());
    }

    private void buyMembershipForm() {
        String id = JOptionPane.showInputDialog(this, "Enter Member ID:");
        Member m = db.getMembers().stream()
                .filter(mem -> mem.getId().equals(id))
                .findFirst()
                .orElse(null);

        if (m == null) {
            JOptionPane.showMessageDialog(this, "Member not found!");
            return;
        }

        String[] plans = {"1. Monthly Basic ($50)", "2. VIP Platinum ($120)"};
        String planInput = (String) JOptionPane.showInputDialog(this, "Select Base Plan:", "Membership Factory",
                JOptionPane.QUESTION_MESSAGE, null, plans, plans[0]);

        if (planInput == null) return;
        int planChoice = planInput.startsWith("1") ? 1 : 2;
        Membership finalMembership = MembershipFactory.getMembership(planChoice);

        // Decorator selections
        JCheckBox trainerBox = new JCheckBox("Personal Trainer (+$30)");
        JCheckBox lockerBox = new JCheckBox("Locker Access (+$10)");
        Object[] additions = {"Add Extras:", trainerBox, lockerBox};

        JOptionPane.showConfirmDialog(null, additions, "Customize Membership", JOptionPane.OK_CANCEL_OPTION);

        if (trainerBox.isSelected()) finalMembership = new PersonalTrainerDecorator(finalMembership);
        if (lockerBox.isSelected()) finalMembership = new LockerDecorator(finalMembership);

        m.setActiveMembership(finalMembership);
        updateDisplay("Success: Activated " + finalMembership.getPlanName() + " for " + m.getName());
        showMembers();
    }

    private void showMembershipReport() {
        if (db.getMembers().isEmpty()) {
            displayArea.setText("No data for Strategy report.");
            return;
        }
        // Redirect System.out to our JTextArea temporarily or use a custom Strategy
        displayArea.setText("--- GENERATING STRATEGY REPORT ---\n");
        MemberDisplayStrategy displayStrategy = new GroupByPlanStrategy();

        // Custom implementation to capture Strategy output for GUI
        List<Member> members = db.getMembers();
        String[] plans = {"Monthly Basic", "VIP Platinum", "None"};
        StringBuilder sb = new StringBuilder("\n--- PLAN-WISE STRATEGY REPORT ---\n");
        for (String plan : plans) {
            sb.append("\n>>> ").append(plan).append("\n");
            boolean found = false;
            for (Member mem : members) {
                String memberPlan = (mem.getActiveMembership() == null) ? "None" : mem.getActiveMembership().getPlanName();
                if (memberPlan.contains(plan)) { // contains used to match decorated names
                    sb.append("   - ").append(mem.getName()).append(" (ID: ").append(mem.getId()).append(")\n");
                    found = true;
                }
            }
            if (!found) sb.append("   (No members found)\n");
        }
        displayArea.setText(sb.toString());
    }

    private void showStats() {
        List<Member> members = db.getMembers();
        long active = members.stream().filter(m -> m.getActiveMembership() != null).count();
        String stats = String.format("--- DATABASE STATISTICS ---\nTotal Registered: %d\nActive Memberships: %d\n---------------------------",
                members.size(), active);
        JOptionPane.showMessageDialog(this, stats, "Gym Stats (Singleton DB)", JOptionPane.INFORMATION_MESSAGE);
    }

    private void updateDisplay(String text) {
        displayArea.append("\n> " + text + "\n");
    }

    private JButton createStyledButton(String text, Color color) {
        JButton btn = new JButton(text);
        btn.setBackground(color);
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Tahoma", Font.BOLD, 12));
        return btn;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new GymManagementGUI().setVisible(true);
        });
    }
}