package mybudget;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * One income or expense of the account book.
 *
 * @author Maria Nolte De Sena
 * @version 0.9 (beta)
 */
public class Transaction implements Serializable {

    private int id;
    private Date date;
    private double amount;
    private String type;          // "Income" or "Expense"
    private String category;
    private String description;

    /**
     * Creates a new transaction.
     *
     * @param id          number that identifies the transaction
     * @param date        date of the transaction
     * @param amount      amount in KRW
     * @param type        "Income" or "Expense"
     * @param category    category, for example "Food"
     * @param description short description
     */
    public Transaction(int id, Date date, double amount, String type,
                       String category, String description) {
        this.id = id;
        this.date = date;
        this.amount = amount;
        this.type = type;
        this.category = category;
        this.description = description;
    }

    /** @return the id of the transaction */
    public int getId() {
        return id;
    }

    /** @return the date of the transaction */
    public Date getDate() {
        return date;
    }

    /** @return the amount of the transaction */
    public double getAmount() {
        return amount;
    }

    /** @return "Income" or "Expense" */
    public String getType() {
        return type;
    }

    /** @return the category of the transaction */
    public String getCategory() {
        return category;
    }

    /** @return the description of the transaction */
    public String getDescription() {
        return description;
    }

    /** @param date the new date */
    public void setDate(Date date) {
        this.date = date;
    }

    /** @param amount the new amount */
    public void setAmount(double amount) {
        this.amount = amount;
    }

    /** @param type the new type, "Income" or "Expense" */
    public void setType(String type) {
        this.type = type;
    }

    /** @param category the new category */
    public void setCategory(String category) {
        this.category = category;
    }

    /** @param description the new description */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns the date as text.
     *
     * @return the date, for example "2026-10-07"
     */
    public String getDateText() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * Returns the month of the transaction.
     *
     * @return the month, for example "2026-10"
     */
    public String getMonth() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(date);
    }

    /**
     * Returns the amount as text, with + for income and - for expense.
     *
     * @return the amount, for example "-20,000"
     */
    public String getAmountText() {
        if (type.equals("Income")) {
            return "+" + String.format("%,.0f", amount);
        } else {
            return "-" + String.format("%,.0f", amount);
        }
    }

    /**
     * Returns the main information of the transaction.
     *
     * @return text like "2026-10-07 - Transport - Expense - -20,000"
     */
    public String toString() {
        return getDateText() + " - " + category + " - " + type + " - " + getAmountText();
    }
}
