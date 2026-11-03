package mybudget;

/**
 * MyBudget, a household account book program (Topic 4).
 * This class starts the program.
 *
 * @author Maria Nolte De Sena
 * @version 0.9 (beta)
 */
public class MyBudgetApp {

    /**
     * Creates the account book and opens the Home window.
     *
     * @param args not used
     */
    public static void main(String[] args) {
        AccountBook accountBook = new AccountBook();
        MainWindow window = new MainWindow(accountBook, "transactions.dat");
        window.setVisible(true);
    }
}
