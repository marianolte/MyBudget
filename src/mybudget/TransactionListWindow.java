package mybudget;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

/**
 * The window with the list of transactions. The user can filter the
 * transactions by month, category and type, and add, edit or delete them.
 *
 * @author Maria Nolte De Sena
 * @version 0.9 (beta)
 */
public class TransactionListWindow extends JDialog {

    private AccountBook accountBook;

    private JTextField monthField;
    private JComboBox<String> categoryBox;
    private JComboBox<String> typeBox;
    private DefaultTableModel tableModel;
    private JTable table;

    // the transactions shown in the table, in the same order as the rows
    private ArrayList<Transaction> shownTransactions;

    /**
     * Creates the window.
     *
     * @param mainWindow  the Home window
     * @param accountBook the account book
     */
    public TransactionListWindow(MainWindow mainWindow, AccountBook accountBook) {
        super(mainWindow, "Transactions");
        setModal(true);
        this.accountBook = accountBook;
        setSize(760, 420);
        setLocationRelativeTo(mainWindow);
        setLayout(new BorderLayout());

        // filters at the top
        JPanel filterPanel = new JPanel();
        monthField = new JTextField(7);
        String[] categories = {"All", "Salary", "Allowance", "Food", "Transport",
            "Shopping", "Entertainment", "Other"};
        categoryBox = new JComboBox<String>(categories);
        String[] types = {"All", "Income", "Expense"};
        typeBox = new JComboBox<String>(types);
        JButton searchButton = new JButton("Search");
        JButton clearButton = new JButton("Clear");
        filterPanel.add(new JLabel("Month (YYYY-MM)"));
        filterPanel.add(monthField);
        filterPanel.add(new JLabel("Category"));
        filterPanel.add(categoryBox);
        filterPanel.add(new JLabel("Type"));
        filterPanel.add(typeBox);
        filterPanel.add(searchButton);
        filterPanel.add(clearButton);
        add(filterPanel, BorderLayout.NORTH);

        // table in the middle
        String[] columns = {"Date", "Category", "Type", "Description", "Amount (KRW)"};
        tableModel = new DefaultTableModel(columns, 0);
        table = new JTable(tableModel);
        table.setDefaultEditor(Object.class, null);   // the user cannot write in the table
        add(new JScrollPane(table), BorderLayout.CENTER);

        // buttons at the bottom
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add");
        JButton editButton = new JButton("Edit");
        JButton deleteButton = new JButton("Delete");
        JButton backButton = new JButton("Back");
        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(backButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // what each button does
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                search();
            }
        });
        clearButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                monthField.setText("");
                categoryBox.setSelectedItem("All");
                typeBox.setSelectedItem("All");
                refresh();
            }
        });
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addTransaction();
            }
        });
        editButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                editSelected();
            }
        });
        deleteButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteSelected();
            }
        });
        backButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
            }
        });
    }

    /**
     * Fills the table with the transactions that match the filters.
     */
    public void refresh() {
        String month = monthField.getText().trim();
        String category = (String) categoryBox.getSelectedItem();
        String type = (String) typeBox.getSelectedItem();
        shownTransactions = accountBook.searchTransactions(month, category, type);

        tableModel.setRowCount(0);   // delete all the rows
        for (Transaction t : shownTransactions) {
            String[] row = {t.getDateText(), t.getCategory(), t.getType(),
                t.getDescription(), t.getAmountText()};
            tableModel.addRow(row);
        }
    }

    /**
     * Shows only the transactions that match the filters.
     */
    private void search() {
        refresh();
        if (shownTransactions.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No transactions match the selected filter.");
        }
    }

    /**
     * Opens the window to add a new transaction.
     */
    private void addTransaction() {
        TransactionDialog dialog = new TransactionDialog(this, accountBook, null);
        if (dialog.showDialog()) {
            refresh();
        }
    }

    /**
     * Opens the window to edit the selected transaction.
     */
    private void editSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a transaction first.");
            return;
        }
        Transaction t = shownTransactions.get(row);
        TransactionDialog dialog = new TransactionDialog(this, accountBook, t);
        if (dialog.showDialog()) {
            refresh();
        }
    }

    /**
     * Asks for confirmation and deletes the selected transaction.
     */
    private void deleteSelected() {
        int row = table.getSelectedRow();
        if (row == -1) {
            JOptionPane.showMessageDialog(this, "Please select a transaction first.");
            return;
        }
        Transaction t = shownTransactions.get(row);
        int answer = JOptionPane.showConfirmDialog(this,
                "Are you sure you want to delete this transaction?\n" + t,
                "Confirm Deletion", JOptionPane.YES_NO_OPTION);
        if (answer == JOptionPane.YES_OPTION) {
            accountBook.deleteTransaction(t.getId());
            refresh();
        }
    }
}
