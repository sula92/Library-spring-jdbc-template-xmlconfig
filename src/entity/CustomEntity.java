package entity;

import java.sql.Date;

public class CustomEntity {

    private String borrowId;
    private Date borrowDate;
    private  Date returnDate;
    private int amount;

    public CustomEntity() {
    }

    public CustomEntity(String borrowId, Date borrowDate, Date returnDate, int amount) {
        this.borrowId = borrowId;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
        this.amount = amount;
    }

    public String getBorrowId() {
        return borrowId;
    }

    public void setBorrowId(String borrowId) {
        this.borrowId = borrowId;
    }

    public Date getBorrowDate() {
        return borrowDate;
    }

    public void setBorrowDate(Date borrowDate) {
        this.borrowDate = borrowDate;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "CustomEntity{" +
                "borrowId='" + borrowId + '\'' +
                ", borrowDate=" + borrowDate +
                ", returnDate=" + returnDate +
                ", amount=" + amount +
                '}';
    }
}
