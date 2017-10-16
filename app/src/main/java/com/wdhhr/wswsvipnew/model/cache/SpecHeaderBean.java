package com.wdhhr.wswsvipnew.model.cache;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felear on 2017/8/18 0018.
 */

public class SpecHeaderBean {


    /**
     * SpecA : 适用人群
     * SpecB : 大小
     * SpecC : 容量
     */

    private String SpecA;
    private String SpecB;
    private String SpecC;
    private String SpecD;
    private String SpecE;

    public List<String> toArrayList() {

        ArrayList<String> list = new ArrayList<>();

        if (SpecA != null) {
            list.add(SpecA);
        }

        if (SpecB != null) {
            list.add(SpecB);
        }

        if (SpecC != null) {
            list.add(SpecC);
        }

        if (SpecD != null) {
            list.add(SpecD);
        }

        if (SpecE != null) {
            list.add(SpecE);
        }

        return list;
    }

    public String getSpecA() {
        return SpecA;
    }

    public void setSpecA(String SpecA) {
        this.SpecA = SpecA;
    }

    public String getSpecB() {
        return SpecB;
    }

    public void setSpecB(String SpecB) {
        this.SpecB = SpecB;
    }

    public String getSpecC() {
        return SpecC;
    }

    public void setSpecC(String SpecC) {
        this.SpecC = SpecC;
    }

    public String getSpecD() {
        return SpecD;
    }

    public void setSpecD(String specD) {
        SpecD = specD;
    }

    public String getSpecE() {
        return SpecE;
    }

    public void setSpecE(String specE) {
        SpecE = specE;
    }

    @Override
    public String toString() {
        return "SpecHeaderBean{" +
                "SpecA='" + SpecA + '\'' +
                ", SpecB='" + SpecB + '\'' +
                ", SpecC='" + SpecC + '\'' +
                ", SpecD='" + SpecD + '\'' +
                ", SpecE='" + SpecE + '\'' +
                '}';
    }
}
