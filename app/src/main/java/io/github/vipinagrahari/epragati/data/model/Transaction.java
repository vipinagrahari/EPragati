package io.github.vipinagrahari.epragati.data.model;

import android.content.ContentValues;

import io.github.vipinagrahari.epragati.data.db.DbContract;

/**
 * Created by vivek on 22/8/16.
 */
public class Transaction {

    String description;
    String type;
    int amount;
    int date;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();

        contentValues.put(DbContract.TransactionEntry.COLUMN_DESCRIPTION, getDescription());
        contentValues.put(DbContract.TransactionEntry.COLUMN_TRANSACTION_AMOUNT, getAmount());
        contentValues.put(DbContract.TransactionEntry.COLUMN_TRANSACTION_DATE, getDate());
        contentValues.put(DbContract.TransactionEntry.COLUMN_TRANSACTION_TYPE, getType());
        return contentValues;
    }

}
