package mybudget;

/**
 * The total income, total expenses and balance of the account book.
 *
 * @author Maria Nolte De Sena
 * @version 0.9 (beta)
 */
public class FinancialSummary {

    private double totalIncome;
    private double totalExpenses;
    private double balance;

    /**
     * Creates the summary. The balance is income minus expenses.
     *
     * @param totalIncome   sum of all the income
     * @param totalExpenses sum of all the expenses
     */
    public FinancialSummary(double totalIncome, double totalExpenses) {
        this.totalIncome = totalIncome;
        this.totalExpenses = totalExpenses;
        this.balance = totalIncome - totalExpenses;
    }

    /** @return the total income */
    public double getTotalIncome() {
        return totalIncome;
    }

    /** @return the total expenses */
    public double getTotalExpenses() {
        return totalExpenses;
    }

    /** @return the balance (income - expenses) */
    public double getBalance() {
        return balance;
    }
}
