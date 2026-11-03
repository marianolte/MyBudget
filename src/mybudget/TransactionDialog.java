package mybudget;

import java.awt.*;
import java.awt.event.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.swing.*;

/**
 * The window to add a new transaction or edit an existing one.
 * Save checks the information and saves the transaction.
 * Cancel closes the window without saving.
 *
 * @author Maria Nolte De Sena
 * @version 0.9 (beta)
 */
public class TransactionDialog extends JDialog {

    private AccountBook accountBook;
    private Transaction editing;   // null when we add a new transaction
    private boolean saved;

    private JTextField dateField;
    private JTextField amountField;
    private JRadioButton incomeButton;
    private JRadioButton expenseButton;
    private JComboBox<String> categoryBox;
    private JTextField descriptionField;

    /**
     * Creates the window.
     *
     * @param owner       the window that opens this one
     * @param accountBook the account book
     * @param editing     the transaction to edit, or null to add a new one
     */
    public TransactionDialog(Window owner, AccountBook accountBook, Transaction editing) {
        super(owner, "Add Transaction");
        setModal(true);
        this.accountBook = accountBook;
        this.editing = editing;
        this.saved = false;
        if (editing != null) {
            setTitle("Edit Transaction");
        }
        setSize(400, 290);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // the form (5 rows: label + field)
        JPanel form = new JPanel(new GridLayout(5, 2, 10, 10));
        form.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));   // margin
        dateField = new JTextField();
        amountField = new JTextField();
        incomeButton = new JRadioButton("Income");
        expenseButton = new JRadioButton("Expense");
        ButtonGroup group = new ButtonGroup();   // only one of the two can be selected
        group.add(incomeButton);
        group.add(expenseButton);
        JPanel typePanel = new JPanel();
        typePanel.add(incomeButton);
        typePanel.add(expenseButton);
        String[] categories = {"Salary", "Allowance", "Food", "Transport",
            "Shopping", "Entertainment", "Other"};
        categoryBox = new JComboBox<String>(categories);
        descriptionField = new JTextField();

        form.add(new JLabel("Date (YYYY-MM-DD)"));
        form.add(dateField);
        form.add(new JLabel("Amount (KRW)"));
        form.add(amountField);
        form.add(new JLabel("Type"));
        form.add(typePanel);
        form.add(new JLabel("Category"));
        form.add(categoryBox);
        form.add(new JLabel("Description"));
        form.add(descriptionField);
        add(form, BorderLayout.CENTER);

        // buttons
        JPanel buttonPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        add(buttonPanel, BorderLayout.SOUTH);

        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                save();
            }
        });
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();   // close without saving
            }
        });

        // fill in the fields
        if (editing == null) {
            dateField.setText(new SimpleDateFormat("yyyy-MM-dd").format(new Date()));   // today
            expenseButton.setSelected(true);
        } else {
            dateField.setText(editing.getDateText());
            amountField.setText(String.format("%.0f", editing.getAmount()));
            if (editing.getType().equals("Income")) {
                incomeButton.setSelected(true);
            } else {
                expenseButton.setSelected(true);
            }
            categoryBox.setSelectedItem(editing.getCategory());
            descriptionField.setText(editing.getDescription());
        }
    }

    /**
     * Shows the window and waits until it is closed.
     *
     * @return true if the transaction was saved, false if the user cancelled
     */
    public boolean showDialog() {
        setVisible(true);
        return saved;
    }

    /**
     * Checks the information and adds or edits the transaction.
     */
    private void save() {
        // 1. read the date
        Date date;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateField.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please write a valid date, for example 2026-10-13.");
            return;
        }

        // 2. read the amount
        double amount;
        try {
            amount = Double.parseDouble(amountField.getText().trim());
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Please write the amount as a number, for example 15000.");
            return;
        }
        if (amount <= 0) {
            JOptionPane.showMessageDialog(this, "The amount must be greater than zero.");
            return;
        }

        // 3. read the description
        String description = descriptionField.getText().trim();
        if (description.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please write a description.");
            return;
        }

        // 4. read the type and the category
        String type;
        if (incomeButton.isSelected()) {
            type = "Income";
        } else {
            type = "Expense";
        }
        String category = (String) categoryBox.getSelectedItem();

        // 5. add or edit the transaction
        if (editing == null) {
            accountBook.addTransaction(date, amount, type, category, description);
        } else {
            accountBook.editTransaction(editing.getId(), date, amount, type, category, description);
        }
        saved = true;
        dispose();   // close the window
    }
}
