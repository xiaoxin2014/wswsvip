package com.wdhhr.wswsvipnew.model.cache;

/**
 * Created by felear on 2017/8/26 0026.
 */

public class NameValuePair {

    private String name;
    private String value;

    public NameValuePair() {

    }

    public NameValuePair(String name, String value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
