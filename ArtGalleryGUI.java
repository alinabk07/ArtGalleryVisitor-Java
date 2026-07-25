package ArtGalleryVisitior;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.border.TitledBorder;
import javax.swing.plaf.FontUIResource;
import javax.swing.table.DefaultTableModel;


// Custom Exception for Duplicate Visitor ID
class DuplicateVisitorIdException extends Exception {
    public DuplicateVisitorIdException(String msg) {
        super(msg);
    }
}

public class ArtGalleryGUI extends JFrame implements ActionListener {
    private JTextField idText, fullNameText, contactNumberText, ticketPriceText, artworkNameText, expensesValueText, withdrawalReasonsText;
    private JRadioButton maleBtn, femaleBtn, otherBtn;
    private ButtonGroup genderGroup;
    private JComboBox<String> dayCombo, monthCombo, yearCombo, ticketTypeBox;
    private JButton addVisitorBtn, logVisitBtn, buyProductBtn, assignAdvisorBtn, checkUpgradeBtn,calcDiscountBtn, calcRewardBtn, cancelProductBtn, billBtn, displayBtn,clearBtn, saveFileBtn, readFileBtn, exitBtn, eventAccessBtn;
    private final ArrayList<ArtGalleryVisitor> visitorList = new ArrayList<>();
    
    // Global font
    private static void setUIFont(FontUIResource f, Color textColor) {
        Enumeration<Object> keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof FontUIResource) UIManager.put(key, f);
            if (key.toString().toLowerCase().contains("foreground")) UIManager.put(key, textColor);
        }
    }

    public ArtGalleryGUI() {
        //// set font + window properties
        setUIFont(new FontUIResource(new Font("Arial", Font.PLAIN, 14)), Color.BLACK);
        setTitle("Art Gallery Management System");
        setSize(800, 800);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Common sizes
        Dimension btnSize = new Dimension(180, 32);
        Dimension tfSize  = new Dimension(180, 24);

        // MAIN CONTAINER (6 stacked panels)
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(12, 15, 12, 15));
        mainPanel.setBackground(Color.WHITE);

        // ROW 1: Visitor Information
        JPanel visitorPanel = createSectionPanel("Visitor Information");
        visitorPanel.setLayout(new GridBagLayout());
        GridBagConstraints g = gbc(6, 6);
        int rowL = 0, rowR = 0;

        addLabel(visitorPanel, "Visitor ID:", g, 0, rowL);
        idText = new JTextField();
        idText.setPreferredSize(tfSize);
        // only allow numbers
        idText.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '\b') {
                    e.consume();
                    JOptionPane.showMessageDialog(null, "Only numbers allowed in Visitor ID!");
                }
            }
        });
        addField(visitorPanel, idText, g, 1, rowL++);

        addLabel(visitorPanel, "Full Name:", g, 0, rowL);
        fullNameText = new JTextField();
        fullNameText.setPreferredSize(tfSize);
        // only allow letters
        fullNameText.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isLetter(e.getKeyChar()) && e.getKeyChar() != ' ' && e.getKeyChar() != '\b') {
                    e.consume();
                    JOptionPane.showMessageDialog(null, "Only letters allowed in Full Name!");
                }
            }
        });
        // Gender radio buttons
        addField(visitorPanel, fullNameText, g, 1, rowL++);

        addLabel(visitorPanel, "Gender:", g, 0, rowL);
        JPanel genderPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        genderPanel.setOpaque(false);
        maleBtn = new JRadioButton("Male");
        femaleBtn = new JRadioButton("Female");
        otherBtn = new JRadioButton("Others");
        genderGroup = new ButtonGroup();
        genderGroup.add(maleBtn); genderGroup.add(femaleBtn); genderGroup.add(otherBtn);
        genderPanel.add(maleBtn); genderPanel.add(femaleBtn); genderPanel.add(otherBtn);
        addField(visitorPanel, genderPanel, g, 1, rowL++);

        addLabel(visitorPanel, "Contact Number:", g, 0, rowL);
        contactNumberText = new JTextField();
        contactNumberText.setPreferredSize(tfSize);
         // only digits allowed
        contactNumberText.addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                if (!Character.isDigit(e.getKeyChar()) && e.getKeyChar() != '\b') {
                    e.consume();
                    JOptionPane.showMessageDialog(null, "Only numbers allowed in Contact Number!");
                }
            }
        });
        addField(visitorPanel, contactNumberText, g, 1, rowL++);
        addLabel(visitorPanel, "Registration Date:", g, 0, rowL);
        String[] days = new String[31]; for (int i = 1; i <= 31; i++) days[i - 1] = String.valueOf(i);
        dayCombo = new JComboBox<>(days);
        String[] months = {"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};
        monthCombo = new JComboBox<>(months);
        String[] years = {"2022","2023","2024","2025"};
        yearCombo = new JComboBox<>(years);
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 6, 0));
        datePanel.setOpaque(false);
        datePanel.add(dayCombo); datePanel.add(monthCombo); datePanel.add(yearCombo);
        addField(visitorPanel, datePanel, g, 1, rowL++);

        addLabel(visitorPanel, "Ticket Type:", g, 2, rowR);
        ticketTypeBox = new JComboBox<>(new String[]{"Standard", "Elite"});
        ((JComponent)ticketTypeBox).setPreferredSize(tfSize);
        addField(visitorPanel, ticketTypeBox, g, 3, rowR++);

        addLabel(visitorPanel, "Artwork Name:", g, 2, rowR);
        artworkNameText = new JTextField();
        artworkNameText.setPreferredSize(tfSize);
        addField(visitorPanel, artworkNameText, g, 3, rowR++);

        addLabel(visitorPanel, "Artwork Price:", g, 2, rowR);
        expensesValueText = new JTextField();
        expensesValueText.setPreferredSize(tfSize);
        addField(visitorPanel, expensesValueText, g, 3, rowR++);

        addLabel(visitorPanel, "Ticket Price:", g, 2, rowR);
        ticketPriceText = new JTextField();
        ticketPriceText.setPreferredSize(tfSize);
        addField(visitorPanel, ticketPriceText, g, 3, rowR++);

        addLabel(visitorPanel, "Cancellation Reason:", g, 2, rowR);
        withdrawalReasonsText = new JTextField();
        withdrawalReasonsText.setPreferredSize(tfSize);
        addField(visitorPanel, withdrawalReasonsText, g, 3, rowR++);

        addVisitorBtn = makeButton("Add Visitor", btnSize);
        addVisitorBtn.addActionListener(this);
        GridBagConstraints gBtn = gbc(6, 6);
        gBtn.gridx = 2; gBtn.gridy = Math.max(rowL, rowR) + 1;
        gBtn.gridwidth = 2;
        gBtn.anchor = GridBagConstraints.SOUTHEAST;
        visitorPanel.add(addVisitorBtn, gBtn);

        ticketTypeBox.addActionListener(e ->ticketPriceText.setText(Objects.equals(ticketTypeBox.getSelectedItem(), "Standard") ? "1000" : "2000"));

        // ROW 2: Visit Management
        JPanel visitPanel = createButtonRowPanel("Visit Management", 2);
        logVisitBtn = makeButton("Log Visit", btnSize);
        logVisitBtn.addActionListener(this);
        displayBtn = makeButton("Display Visitor Details", btnSize);
        displayBtn.addActionListener(this);
        addButtonsHorizontally(visitPanel, logVisitBtn, displayBtn);

        // ROW 3: Purchase Management
        JPanel purchasePanel = createButtonRowPanel("Purchase Management", 3);
        buyProductBtn = makeButton("Buy Product", btnSize);
        buyProductBtn.addActionListener(this);
        cancelProductBtn = makeButton("Cancel Product", btnSize);
        cancelProductBtn.addActionListener(this);
        billBtn = makeButton("Generate Bill", btnSize);
        billBtn.addActionListener(this);
        addButtonsHorizontally(purchasePanel, buyProductBtn, cancelProductBtn, billBtn);

        // ROW 4: Benefits & Rewards (FIXED to match sizes)
        JPanel benefitsPanel = createButtonRowPanel("Benefits & Rewards", 4);
        benefitsPanel.setLayout(new GridLayout(2, 2, 10, 10));
        calcDiscountBtn = makeButton("Calculate Discount", btnSize);
        calcDiscountBtn.addActionListener(this);

        calcRewardBtn = makeButton("Reward Points", btnSize);
        calcRewardBtn.addActionListener(this);

        checkUpgradeBtn = makeButton("Check Upgrade", btnSize);
        checkUpgradeBtn.addActionListener(this);

        assignAdvisorBtn = makeButton("Personal Art Advisor", btnSize);
        assignAdvisorBtn.addActionListener(this);
        
        eventAccessBtn = makeButton("Exclusive Event Access", new Dimension(180, 32));
        eventAccessBtn.addActionListener(this);
        
        addButtonsHorizontally(benefitsPanel, calcDiscountBtn, calcRewardBtn, checkUpgradeBtn, assignAdvisorBtn, eventAccessBtn);

        // ROW 5: File Operations
        JPanel filePanel = createButtonRowPanel("File Operations", 5);
        saveFileBtn = makeButton("Save File", btnSize);
        saveFileBtn.addActionListener(this);
        readFileBtn = makeButton("Read File", btnSize);
        readFileBtn.addActionListener(this);
        addButtonsHorizontally(filePanel, saveFileBtn, readFileBtn);

        // ROW 6: Others
        JPanel othersPanel = createButtonRowPanel("Others", 6);
        clearBtn = makeButton("Clear Fields", btnSize);
        clearBtn.addActionListener(this);
        exitBtn = makeButton("Exit", btnSize);
        exitBtn.addActionListener(this);
        addButtonsHorizontally(othersPanel, clearBtn, exitBtn);

        mainPanel.add(visitorPanel);
        mainPanel.add(Box.createVerticalStrut(12));
        mainPanel.add(visitPanel);
        mainPanel.add(Box.createVerticalStrut(12));
        mainPanel.add(purchasePanel);
        mainPanel.add(Box.createVerticalStrut(12));
        mainPanel.add(benefitsPanel); 
        mainPanel.add(Box.createVerticalStrut(12));
        mainPanel.add(filePanel);
        mainPanel.add(Box.createVerticalStrut(12));
        mainPanel.add(othersPanel);

        add(mainPanel);
        setVisible(true);
    }

    // UI Helpers 
    private JPanel createSectionPanel(String title) {
        JPanel panel = new JPanel();
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(70,70,70), 2),
                title, TitledBorder.LEFT, TitledBorder.TOP,
                new Font("Arial", Font.BOLD, 14), Color.BLACK
        ));
        panel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return panel;
    }

    private JPanel createButtonRowPanel(String title, int rowIndex) {
        JPanel p = createSectionPanel(title);
        p.setLayout(new GridBagLayout());
        return p;
    }

    private JButton makeButton(String text, Dimension size) {
        JButton b = new JButton(text);
        b.setPreferredSize(size);
        b.setMinimumSize(size);
        b.setMaximumSize(size);
        b.setFocusPainted(false);
        return b;
    }

    private GridBagConstraints gbc(int ix, int iy) {
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(ix, ix, iy, iy);
        g.anchor = GridBagConstraints.WEST;
        g.fill = GridBagConstraints.HORIZONTAL;
        g.weightx = 1.0;
        return g;
    }

    private void addLabel(JPanel panel, String text, GridBagConstraints g, int x, int y) {
        GridBagConstraints c = (GridBagConstraints) g.clone();
        c.gridx = x; c.gridy = y;
        c.weightx = 0.0;
        JLabel lbl = new JLabel(text);
        panel.add(lbl, c);
    }

    private void addField(JPanel panel, JComponent comp, GridBagConstraints g, int x, int y) {
        GridBagConstraints c = (GridBagConstraints) g.clone();
        c.gridx = x; c.gridy = y;
        c.weightx = 1.0;
        panel.add(comp, c);
    }

    private void addButtonsHorizontally(JPanel panel, JButton... buttons) {
        GridBagConstraints c = gbc(6, 6);
        for (int i = 0; i < buttons.length; i++) {
            c.gridx = i;
            c.gridy = 0;
            c.weightx = 0.0;
            panel.add(buttons[i], c);
        }
        c.gridx = buttons.length;
        c.weightx = 1.0;
        panel.add(Box.createHorizontalGlue(), c);
    }

    //  Action handling for buttons
    @Override
    public void actionPerformed(ActionEvent e) {
        try {
            Object src = e.getSource();
            if (src == addVisitorBtn) {
                addVisitor();
            } else if (src == logVisitBtn) {
                logVisit();
            } else if (src == buyProductBtn) {
                buyProduct();
            } else if (src == assignAdvisorBtn) {
                assignAdvisor();
            } else if (src == checkUpgradeBtn) {
                checkUpgrade();
            }else if  (src == eventAccessBtn){
                checkEventAccess();
            }else if (src == calcDiscountBtn) {
                calculateDiscount();
            } else if (src == calcRewardBtn) {
                calculateRewardPoints();
            } else if (src == cancelProductBtn) {
                cancelProduct();
            } else if (src == billBtn) {
                generateBillToFile();
            } else if (src == displayBtn) {
                displayVisitorDetails();
            } else if (src == clearBtn) {
                clearFields();
            } else if (src == saveFileBtn) {
                saveToFile();
            } else if (src == readFileBtn) {
                readFromFile();
            } else if (src == exitBtn) {
                System.exit(0);
            }
        } catch (DuplicateVisitorIdException dupe) {
            JOptionPane.showMessageDialog(this, dupe.getMessage(), "Duplicate ID", JOptionPane.WARNING_MESSAGE);
        } catch (NumberFormatException nfe) {
            JOptionPane.showMessageDialog(this, "Please enter valid numeric values where required.", "Invalid Number", JOptionPane.ERROR_MESSAGE);
        } catch (FileNotFoundException fnf) {
            JOptionPane.showMessageDialog(this, "File not found: " + fnf.getMessage(), "File Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ioe) {
            JOptionPane.showMessageDialog(this, "I/O Error: " + ioe.getMessage(), "I/O Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    //Helper: find visitor by ID (throws if not found) ---
    private ArtGalleryVisitor findVisitorById() throws Exception {
        String idStr = idText.getText().trim();
        if (idStr.isEmpty()) throw new Exception("Enter Visitor ID first.");
        int id = Integer.parseInt(idStr);
        for (ArtGalleryVisitor v : visitorList) {
            if (v.getVisitorId() == id) return v;
        }
        throw new Exception("Visitor ID " + id + " not found.");
    }

    // 1) Add Visitor (with duplicate check)
    private void addVisitor() throws Exception {
        if (idText.getText().trim().isEmpty() || fullNameText.getText().trim().isEmpty()
                || contactNumberText.getText().trim().isEmpty() || ticketPriceText.getText().trim().isEmpty()) {
            throw new Exception("Please fill all required fields (ID, Name, Contact, Ticket Price).");
        }

        int id = Integer.parseInt(idText.getText().trim());

        // duplicate ID check
        for (ArtGalleryVisitor v : visitorList) {
            if (v.getVisitorId() == id) throw new DuplicateVisitorIdException("Visitor ID already exists!");
        }

        String name = fullNameText.getText().trim();
        String gender = maleBtn.isSelected() ? "Male" : (femaleBtn.isSelected() ? "Female" : "Others");
        String number = contactNumberText.getText().trim();
        String date = dayCombo.getSelectedItem() + "/" + monthCombo.getSelectedItem() + "/" + yearCombo.getSelectedItem();
        String ticketType = (String) ticketTypeBox.getSelectedItem();
        double ticketPrice = Double.parseDouble(ticketPriceText.getText().trim());

        ArtGalleryVisitor visitor = ticketType.equals("Standard")
                ? new StandardVisitor(id, name, gender, number, date, ticketPrice, ticketType)
                : new EliteVisitor(id, name, gender, number, date, ticketPrice, ticketType);

        visitorList.add(visitor);
        JOptionPane.showMessageDialog(this, "Visitor Added Successfully!");
    }

    // 2) Log Visit
    private void logVisit() throws Exception {
        ArtGalleryVisitor v = findVisitorById();
        v.logVisit();
        JOptionPane.showMessageDialog(this, "Visit logged. Total visits: " + v.visitCount);
    }

    // 3) Buy Product
    private void buyProduct() throws Exception {
        ArtGalleryVisitor v = findVisitorById();
        String name = artworkNameText.getText().trim();
        if (name.isEmpty()) throw new Exception("Enter Artwork Name.");
        if (expensesValueText.getText().trim().isEmpty()) throw new Exception("Enter Artwork Price.");
        double price = Double.parseDouble(expensesValueText.getText().trim());

        String msg = v.buyProduct(name, price);
        JOptionPane.showMessageDialog(this, msg);
    }

    // 4) Assign Personal Art Advisor (Elite only)
    private void assignAdvisor() throws Exception {
        ArtGalleryVisitor v = findVisitorById();
        if (v instanceof EliteVisitor) {
            EliteVisitor ev = (EliteVisitor) v;
            boolean assigned = ev.assignPersonalArtAdvisor();
            JOptionPane.showMessageDialog(this,
                    assigned ? "Personal Art Advisor assigned." : "Not eligible yet (need > 5000 reward points).");
        } else {
            JOptionPane.showMessageDialog(this, "Only Elite visitors can be assigned a Personal Art Advisor.");
        }
    }

    // 5) Check Upgrade (Standard only)
    private void checkUpgrade() throws Exception {
        ArtGalleryVisitor v = findVisitorById();
        if (v instanceof StandardVisitor) {
            StandardVisitor sv = (StandardVisitor) v;
            boolean upgraded = sv.checkDiscountUpgrade();
            JOptionPane.showMessageDialog(this,
                    upgraded ? "Upgrade achieved! Discount increased." : "Not upgraded yet. Visit more times.");
        } else {
            JOptionPane.showMessageDialog(this, "Only Standard visitors have discount upgrade checks.");
        }
    }
    
    // 6) Event Access
    private void checkEventAccess() throws Exception {
        ArtGalleryVisitor v = findVisitorById();
        if (v instanceof EliteVisitor) {
            EliteVisitor ev = (EliteVisitor) v;
            boolean access = ev.exclusiveEventAccess();
            JOptionPane.showMessageDialog(this,
                access ? "This Elite visitor has Exclusive Event Access!" : "No exclusive access yet.");
        } else {
            JOptionPane.showMessageDialog(this, "Only Elite visitors can have Event Access.");
        }
    }
    
    // 7) Calculate Discount
    private void calculateDiscount() throws Exception {
        ArtGalleryVisitor v = findVisitorById();
        double d = v.calculateDiscount();
        JOptionPane.showMessageDialog(this, "Discount calculated: " + String.format("%.2f", d));
    }

    // 8) Calculate Reward Points
    private void calculateRewardPoints() throws Exception {
        ArtGalleryVisitor v = findVisitorById();
        double rp = v.calculateRewardPoint();
        JOptionPane.showMessageDialog(this, "Reward Points: " + String.format("%.2f", rp));
    }

    // 9) Cancel Product
    private void cancelProduct() throws Exception {
        ArtGalleryVisitor v = findVisitorById();
        String name = artworkNameText.getText().trim();
        String reason = withdrawalReasonsText.getText().trim();
        if (name.isEmpty()) throw new Exception("Enter Artwork Name to cancel.");
        if (reason.isEmpty()) throw new Exception("Enter Cancellation Reason.");
        String msg = v.cancelProduct(name, reason);
        JOptionPane.showMessageDialog(this, msg);
    }

    // 10) Generate Bill (call visitor.generateBill() and also export to .txt)
    private void generateBillToFile() throws Exception {
        ArtGalleryVisitor v = findVisitorById();
        // Ensure discount/reward are updated if needed
        v.calculateDiscount();
        v.calculateRewardPoint();

        // Call provided method (prints to console)
        v.generateBill();

        // Export to txt with file handling & try-catch-finally
        String fileName = "Bill_Visitor_" + v.getVisitorId() + ".txt";
        PrintWriter pw = null;
        try {
            pw = new PrintWriter(new BufferedWriter(new FileWriter(fileName)));
            pw.println("=========== ART GALLERY BILL ===========");
            pw.println("Visitor ID         : " + v.getVisitorId());
            pw.println("Name               : " + v.getFullName());
            pw.println("Ticket Type        : " + v.getTicketType());
            pw.println("Ticket Price       : " + String.format("%.2f", v.getTicketPrice()));
            pw.println("----------------------------------------");
            pw.println("Artwork Name       : " + (v.getArtworkName() == null ? "-" : v.getArtworkName()));
            pw.println("Artwork Price      : " + String.format("%.2f", v.getArtworkPrice()));
            // Access protected fields inside same package:
            pw.println("Discount Amount    : " + String.format("%.2f", v.discountAmount));
            pw.println("Final Price        : " + String.format("%.2f", v.finalPrice));
            pw.println("Reward Points      : " + String.format("%.2f", v.rewardPoints));
            pw.println("----------------------------------------");
            pw.println("Active Status      : " + v.isActive());
            pw.println("Bought Status      : " + v.isBought());
            pw.println("Cancellations      : " + v.cancelCount);
            pw.println("========================================");
            JOptionPane.showMessageDialog(this, "Bill exported as: " + fileName);
        } catch (FileNotFoundException fnf) {
            throw fnf; // rethrow to show in UI
        } catch (IOException ioe) {
            throw ioe;
        } finally {
            if (pw != null) pw.close();
        }

        // Also display bill content in a GUI dialog
        showTextFileInDialog(fileName, "Generated Bill");
    }

    // 11) Display Visitor Details (nice table or single visitor info)
    private void displayVisitorDetails() {
        if (visitorList.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No visitors to display.");
            return;
        }
        String[] columns = {"ID", "Name", "Gender", "Contact", "Reg. Date", "Ticket", "Price", "Visits", "Buys", "Cancels"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);
        for (ArtGalleryVisitor v : visitorList) {
            model.addRow(new Object[]{
                    v.getVisitorId(), v.getFullName(), v.getGender(), v.getContactNumber(),
                    v.getRegistrationDate(), v.getTicketType(), v.getTicketPrice(),
                    v.visitCount, v.buyCount, v.cancelCount
            });
        }
        JTable table = new JTable(model);
        table.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(table);
        JDialog dialog = new JDialog(this, "Visitor Details", true);
        dialog.add(scrollPane);
        dialog.setSize(820, 420);
        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    // 12) Clear Fields
    private void clearFields() {
        idText.setText("");
        fullNameText.setText("");
        contactNumberText.setText("");
        ticketPriceText.setText("");
        artworkNameText.setText("");
        expensesValueText.setText("");
        withdrawalReasonsText.setText("");
        genderGroup.clearSelection();
        dayCombo.setSelectedIndex(0);
        monthCombo.setSelectedIndex(0);
        yearCombo.setSelectedIndex(0);
        ticketTypeBox.setSelectedIndex(0);
    }

    // 13) Save to File (formatted table of all visitors)
    private void saveToFile() throws IOException {
        String file = "VisitorDetails.txt";
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(new BufferedWriter(new FileWriter(file)));
            // Header (as per your hint, adjusted to our fields)
            writer.printf("%-5s %-15s %-10s %-15s %-15s %-10s %-12s %-8s %-8s %-8s%n",
                    "ID", "Name", "Gender", "Phone", "RegDate", "Plan", "TicketCost", "Visits", "Buys", "Cancels");
            writer.println("-------------------------------------------------------------------------------------");
            for (ArtGalleryVisitor v : visitorList) {
                writer.printf("%-5d %-15s %-10s %-15s %-15s %-10s %-12.2f %-8d %-8d %-8d%n",
                        v.getVisitorId(), v.getFullName(), v.getGender(), v.getContactNumber(),
                        v.getRegistrationDate(), v.getTicketType(), v.getTicketPrice(),
                        v.visitCount, v.buyCount, v.cancelCount);
            }
            JOptionPane.showMessageDialog(this, "Visitor details saved to " + file);
        } finally {
            if (writer != null) writer.close();
        }
    }

    // 14) Read from File (show previously saved details in GUI)
    private void readFromFile() throws IOException {
        String file = "VisitorDetails.txt";
        // Use BufferedReader (plus show FileNotFound separately)
        File f = new File(file);
        if (!f.exists()) throw new FileNotFoundException(file + " does not exist.");

        StringBuilder sb = new StringBuilder();
        BufferedReader br = null;
        try {
            br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null) sb.append(line).append("\n");
        } finally {
            if (br != null) try { br.close(); } catch (IOException ignored) {}
        }

        JTextArea area = new JTextArea(sb.toString(), 25, 80);
        area.setEditable(false);
        JDialog dlg = new JDialog(this, "VisitorDetails.txt (Read Only)", true);
        dlg.add(new JScrollPane(area));
        dlg.pack();
        dlg.setLocationRelativeTo(this);
        dlg.setVisible(true);
    }

    // Utility: show a small text file in dialog
    private void showTextFileInDialog(String fileName, String title) {
        try {
            StringBuilder sb = new StringBuilder();
            Scanner sc = new Scanner(new File(fileName));
            while (sc.hasNextLine()) sb.append(sc.nextLine()).append("\n");
            sc.close();
            JTextArea area = new JTextArea(sb.toString(), 22, 60);
            area.setEditable(false);
            JDialog dlg = new JDialog(this, title, true);
            dlg.add(new JScrollPane(area));
            dlg.pack();
            dlg.setLocationRelativeTo(this);
            dlg.setVisible(true);
        } catch (Exception ignored) {
            // Silent; file already confirmed written.
        }
    }

    public static void main(String[] args) {
        new ArtGalleryGUI();
    }
}
