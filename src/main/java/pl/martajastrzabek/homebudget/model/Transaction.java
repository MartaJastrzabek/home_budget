package pl.martajastrzabek.homebudget.model;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.ResultSet;

public class Transaction {
    private long id;
    private TransactionType type;
    private String description;
    private BigDecimal amount;
    private Date date;

    public Transaction() {
    }

    public Transaction(TransactionType type, String description, BigDecimal amount, Date date) {
        this.type = type;
        this.description = description;
        this.amount = amount;
        this.date = date;
    }

    public Transaction(long id, TransactionType type, String description, BigDecimal amount, Date date) {
        this(type, description, amount, date);
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public TransactionType getType() {
        return type;
    }

    public void setType(TransactionType type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    @Override
    public String toString() {
        return "Transaction id: " + id +
                ", type: " + type +
                ", description: '" + description +
                ", amount: " + amount +
                ", date: " + date + ".";
    }
}
