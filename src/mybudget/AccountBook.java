package mybudget;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;

/**
 * The account book. It keeps the list of transactions and has the
 * functions of the Function Specification.
 *
 * @author Maria Nolte De Sena
 * @version 0.9 (beta)
 */
public class AccountBook {

    private ArrayList<Transaction> transactions;
    private int nextId;   // id for the next new transaction

    /**
     * Creates an empty account book.
     */
    public AccountBook() {
        transactions = new ArrayList<Transaction>();
        nextId = 1;
    }

    /**
     * Adds a new transaction. It is only added if the amount is greater
     * than zero and the description is not empty.
     *
     * @param date        date of the transaction
     * @param amount      amount in KRW
     * @param type        "Income" or "Expense"
     * @param category    category of the transaction
     * @param description short description
     * @return true if the transaction is added, otherwise false
     */
    public boolean addTransaction(Date date, double amount, String type,
                                  String category, String description) {
        if (amount <= 0 || description.isEmpty()) {
            return false;
        }
        Transaction t = new Transaction(nextId, date, amount, type, category, description);
        transactions.add(t);
        nextId++;
        return true;
    }

    /**
     * Changes the information of a transaction.
     *
     * @param transactionId id of the transaction to change
     * @param date          new date
     * @param amount        new amount
     * @param type          new type
     * @param category      new category
     * @param description   new description
     * @return true if the transaction is updated, otherwise false
     */
    public boolean editTransaction(int transactionId, Date date, double amount,
                                   String type, String category, String description) {
        if (amount <= 0 || description.isEmpty()) {
            return false;
        }
        for (Transaction t : transactions) {
            if (t.getId() == transactionId) {
                t.setDate(date);
                t.setAmount(amount);
                t.setType(type);
                t.setCategory(category);
                t.setDescription(description);
                return true;
            }
        }
        return false;   // the transaction was not found
    }

    /**
     * Deletes a transaction.
     *
     * @param transactionId id of the transaction to delete
     * @return true if the transaction is deleted, otherwise false
     */
    public boolean deleteTransaction(int transactionId) {
        for (Transaction t : transactions) {
            if (t.getId() == transactionId) {
                transactions.remove(t);
                return true;
            }
        }
        return false;   // the transaction was not found
    }

    /**
     * Returns all the transactions.
     *
     * @return the list of transactions
     */
    public ArrayList<Transaction> getAllTransactions() {
        return transactions;
    }

    /**
     * Searches the transactions that match the filters.
     * If the month is empty or a filter is "All", that filter is not used.
     *
     * @param month    month like "2026-10", or empty
     * @param category category, or "All"
     * @param type     "Income", "Expense" or "All"
     * @return the transactions that match the filters
     */
    public ArrayList<Transaction> searchTransactions(String month, String category, String type) {
        ArrayList<Transaction> result = new ArrayList<Transaction>();
        for (Transaction t : transactions) {
            boolean monthOk = month.isEmpty() || t.getMonth().equals(month);
            boolean categoryOk = category.equals("All") || t.getCategory().equals(category);
            boolean typeOk = type.equals("All") || t.getType().equals(type);
            if (monthOk && categoryOk && typeOk) {
                result.add(t);
            }
        }
        return result;
    }

    /**
     * Calculates the total income, total expenses and balance.
     *
     * @return the financial summary
     */
    public FinancialSummary calculateSummary() {
        double income = 0;
        double expenses = 0;
        for (Transaction t : transactions) {
            if (t.getType().equals("Income")) {
                income = income + t.getAmount();
            } else {
                expenses = expenses + t.getAmount();
            }
        }
        return new FinancialSummary(income, expenses);
    }

    /**
     * Saves all the transactions in a file.
     *
     * @param path name of the file
     * @return true if the data is saved, otherwise false
     */
    public boolean saveToFile(String path) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(path));
            out.writeObject(transactions);
            out.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Loads the transactions from a file. If the file does not exist,
     * the list is empty.
     *
     * @param path name of the file
     * @return the loaded transactions, or null if the file cannot be read
     */
    public ArrayList<Transaction> loadFromFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return transactions;   // no file yet: start with an empty list
        }
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(file));
            transactions = (ArrayList<Transaction>) in.readObject();
            in.close();
        } catch (Exception e) {
            return null;
        }
        // the next id is the biggest id + 1
        nextId = 1;
        for (Transaction t : transactions) {
            if (t.getId() >= nextId) {
                nextId = t.getId() + 1;
            }
        }
        return transactions;
    }
}
