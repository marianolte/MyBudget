package mybudget;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 * The window that shows the total income, total expenses and balance.
 *
 * @author Maria Nolte De Sena
 * @version 0.9 (beta)
 */
public class SummaryDialog extends JDialog {

    /**
     * Creates the window with the values of the summary.
     *
     * @param owner   the window that opens this one
     * @param summary the summary to show
     */
    public SummaryDialog(Window owner, FinancialSummary summary) {
        super(owner, "Financial Summary");
        setModal(true);
        setSize(420, 220);
        setLocationRelativeTo(owner);
        setLayout(new BorderLayout());

        // 3 rows: name + value
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 15));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 10, 20));   // margin
        JLabel income = new JLabel(String.format("%,.0f KRW", summary.getTotalIncome()));
        JLabel expenses = new JLabel(String.format("%,.0f KRW", summary.getTotalExpenses()));
        JLabel balance = new JLabel(String.format("%,.0f KRW", summary.getBalance()));
        income.setForeground(new Color(30, 123, 52));     // green
        expenses.setForeground(new Color(198, 40, 40));   // red

        panel.add(new JLabel("Total Income"));
        panel.add(income);
        panel.add(new JLabel("Total Expenses"));
        panel.add(expenses);
        panel.add(new JLabel("Balance (income - expenses)"));
        panel.add(balance);
        add(panel, BorderLayout.CENTER);

        // close button
        JPanel buttonPanel = new JPanel();
        JButton closeButton = new JButton("Close");
        buttonPanel.add(closeButton);
        add(buttonPanel, BorderLayout.SOUTH);

        closeButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
    }
}
