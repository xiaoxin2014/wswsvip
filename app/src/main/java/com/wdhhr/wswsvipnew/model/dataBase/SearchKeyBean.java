package com.wdhhr.wswsvipnew.model.dataBase;

import org.litepal.crud.DataSupport;

/**
 * Created by felear on 2017/8/16 0016.
 */

public class SearchKeyBean extends DataSupport {

    private int id;
    private String keyWord;

    @Override
    public String toString() {
        return "SearchKeyBean{" +
                "id=" + id +
                ", keyWord='" + keyWord + '\'' +
                '}';
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getKeyWord() {
        return keyWord;
    }

    public void setKeyWord(String keyWord) {
        this.keyWord = keyWord;
    }
}
