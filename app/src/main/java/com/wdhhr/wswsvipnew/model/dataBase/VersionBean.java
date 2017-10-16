package com.wdhhr.wswsvipnew.model.dataBase;

/**
 * Created by Administrator on 2017/9/24 0024.
 */

public class VersionBean {

    /**
     * editionCode : 1.0.2
     * isChoice : 1
     * isUpdate : 1
     */

    private String editionCode;
    private int isChoice;
    private int isUpdate;
    private int androidUpdate;

    public int getAndroidUpdate() {
        return androidUpdate;
    }

    public void setAndroidUpdate(int androidUpdate) {
        this.androidUpdate = androidUpdate;
    }

    public String getEditionCode() {
        return editionCode;
    }

    public void setEditionCode(String editionCode) {
        this.editionCode = editionCode;
    }

    public int getIsChoice() {
        return isChoice;
    }

    public void setIsChoice(int isChoice) {
        this.isChoice = isChoice;
    }

    public int getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(int isUpdate) {
        this.isUpdate = isUpdate;
    }
}
