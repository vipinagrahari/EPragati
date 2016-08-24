package io.github.vipinagrahari.epragati.data.model;

import android.content.ContentValues;

import io.github.vipinagrahari.epragati.data.db.DbContract;

/**
 * Created by vivek on 22/8/16.
 */
public class Dream {

    String title;
    String description;
    int deadline;
    boolean isComplete;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public boolean isComplete() {
        return isComplete;
    }

    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    public ContentValues getContentValues() {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DbContract.DreamEntry.COLUMN_TITLE, getTitle());
        contentValues.put(DbContract.DreamEntry.COLUMN_DREAM_DESCRIPTION, getDescription());
        contentValues.put(DbContract.DreamEntry.COLUMN_DREAM_DEADLINE, getDeadline());
        contentValues.put(DbContract.DreamEntry.COLUMN_DREAM_COMPLETE, isComplete());
        return contentValues;
    }


}
