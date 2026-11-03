package mybudget;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The Home window of MyBudget. It has six buttons to open the main
 * functions and a text at the bottom with the number of transactions.
 *
 * @author Maria Nolte De Sena
 * @version 0.9 (beta)
 */
public class MainWindow extends JFrame {

    private AccountBook accountBook;
    private String filePath;
    private JLabel statusLabel;
    private TransactionListWindow listWindow;

    /**
     * Creates the Home window and loads the saved transactions.
     *
     * @param accountBook the account book
     * @param filePath    the file where the transactions are saved
     */
    public MainWindow(AccountBook accountBook, String filePath) {
        this.accountBook = accountBook;
        this.filePath = filePath;

        setTitle("MyBudget - Household Account Book");
        setSize(520, 340);
        setLocationRelativeTo(null);   // open in the centre of the screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // title at the top
        JLabel title = new JLabel("MyBudget", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 26));
        add(title, BorderLayout.NORTH);

        // six buttons in the middle (3 rows, 2 columns)
        JPanel buttonPanel = new JPanel(new GridLayout(3, 2, 12, 12));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 20, 20));   // margin
        JButton addButton = new JButton("Add Transaction");
        JButton viewButton = new JButton("View Transactions");
        JButton searchButton = new JButton("Search and Filter");
        JButton summaryButton = new JButton("Financial Summary");
        JButton saveButton = new JButton("Save Data");
        JButton loadButton = new JButton("Load Data");
        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(searchButton);
        buttonPanel.add(summaryButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(loadButton);
        add(buttonPanel, BorderLayout.CENTER);

        // text at the bottom
        statusLabel = new JLabel();
        statusLabel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));   // margin
        add(statusLabel, BorderLayout.SOUTH);

        // what each button does
        addButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openAddTransaction();
            }
        });
        viewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openTransactionList();
            }
        });
        searchButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                openTransactionList();
            }
        });
        summaryButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                showSummary();
            }
        });
        saveButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                saveData();
            }
        });
        loadButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                loadData();
            }
        });

        // load the saved transactions when the program starts
        if (accountBook.loadFromFile(filePath) == null) {
            JOptionPane.showMessageDialog(this, "The saved information could not be opened.");
        }
        updateStatus();
    }

    /**
     * Opens the window to add a new transaction.
     */
    public void openAddTransaction() {
        TransactionDialog dialog = new TransactionDialog(this, accountBook, null);
        if (dialog.showDialog()) {
            updateStatus();
        }
    }

    /**
     * Opens the window with the list of transactions.
     */
    public void openTransactionList() {
        if (listWindow == null) {
            listWindow = new TransactionListWindow(this, accountBook);
        }
        listWindow.refresh();
        listWindow.setVisible(true);
        updateStatus();
    }

    /**
     * Calculates and shows the financial summary.
     */
    public void showSummary() {
        FinancialSummary summary = accountBook.calculateSummary();
        SummaryDialog dialog = new SummaryDialog(this, summary);
        dialog.setVisible(true);
    }

    /**
     * Saves the transactions in the file.
     *
     * @return true if the data was saved
     */
    public boolean saveData() {
        if (accountBook.saveToFile(filePath)) {
            JOptionPane.showMessageDialog(this, "Data saved successfully to " + filePath);
            return true;
        } else {
            JOptionPane.showMessageDialog(this, "The data could not be saved.");
            return false;
        }
    }

    /**
     * Loads the transactions from the file.
     */
    public void loadData() {
        if (accountBook.loadFromFile(filePath) == null) {
            JOptionPane.showMessageDialog(this, "The saved information could not be opened.");
        } else {
            updateStatus();
            JOptionPane.showMessageDialog(this, "Data loaded from " + filePath);
        }
    }

    /**
     * Shows the number of transactions at the bottom of the window.
     */
    private void updateStatus() {
        int number = accountBook.getAllTransactions().size();
        statusLabel.setText("File: " + filePath + " - " + number + " transactions");
    }
}
