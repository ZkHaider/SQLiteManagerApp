package com.sqlitemanagerapp.database.models;

/**
 * Created by ZkHaider on 5/13/15.
 */
public class AssociatedFile {

    private String mCreateDate;
    private String mCreatedByTablet;
    private String mCreatedDateTablet;
    private String mCreatedTabletID;
    private boolean mDeleted;
    private String mEditDate;
    private String mEditedByTablet;
    private String mEditedDateTablet;
    private String mEditedTabletID;
    private int mId;
    private int mInstanceState;
    private String mGUID;
    private String mFileLocation;
    private boolean mUploaded;

    public String getCreateDate() {
        return mCreateDate;
    }

    public void setCreateDate(String mCreateDate) {
        this.mCreateDate = mCreateDate;
    }

    public String getCreatedByTablet() {
        return mCreatedByTablet;
    }

    public void setCreatedByTablet(String mCreatedByTablet) {
        this.mCreatedByTablet = mCreatedByTablet;
    }

    public String getCreatedDateTablet() {
        return mCreatedDateTablet;
    }

    public void setCreatedDateTablet(String mCreatedDateTablet) {
        this.mCreatedDateTablet = mCreatedDateTablet;
    }

    public String getCreatedTabletID() {
        return mCreatedTabletID;
    }

    public void setCreatedTabletID(String mCreatedTabletID) {
        this.mCreatedTabletID = mCreatedTabletID;
    }

    public boolean isDeleted() {
        return mDeleted;
    }

    public void setDeleted(boolean mDeleted) {
        this.mDeleted = mDeleted;
    }

    public String getEditDate() {
        return mEditDate;
    }

    public void setEditDate(String mEditDate) {
        this.mEditDate = mEditDate;
    }

    public String getEditedByTablet() {
        return mEditedByTablet;
    }

    public void setEditedByTablet(String mEditedByTablet) {
        this.mEditedByTablet = mEditedByTablet;
    }

    public String getEditedDateTablet() {
        return mEditedDateTablet;
    }

    public void setEditedDateTablet(String mEditedDateTablet) {
        this.mEditedDateTablet = mEditedDateTablet;
    }

    public String getEditedTabletID() {
        return mEditedTabletID;
    }

    public void setEditedTabletID(String mEditedTabletID) {
        this.mEditedTabletID = mEditedTabletID;
    }

    public int getId() {
        return mId;
    }

    public void setId(int mId) {
        this.mId = mId;
    }

    public int getInstanceState() {
        return mInstanceState;
    }

    public void setInstanceState(int mInstanceState) {
        this.mInstanceState = mInstanceState;
    }

    public String getGUID() {
        return mGUID;
    }

    public void setGUID(String mGUID) {
        this.mGUID = mGUID;
    }

    public String getFileLocation() {
        return mFileLocation;
    }

    public void setFileLocation(String mFileLocation) {
        this.mFileLocation = mFileLocation;
    }

    public boolean isUploaded() {
        return mUploaded;
    }

    public void setUploaded(boolean mUploaded) {
        this.mUploaded = mUploaded;
    }
}
