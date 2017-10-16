package com.wdhhr.wswsvipnew.model.dataBase;

/**
 * Created by felear on 2017/8/26 0026.
 */

public class ProfitBean {
    /**
     * ctime : {"date":23,"day":0,"hours":16,"minutes":24,"month":6,"seconds":54,"time":1500798294000,"timezoneOffset":-480,"year":117}
     * flag : 0
     * id : 1
     * ordersId : 1
     * profitForm : 1
     * profitId : 1
     * profitNum : 8.8
     * usersId : 7n0117u98170jh22720d7
     */

    private TimeBean ctime;
    private int flag;
    private int id;
    private String ordersId;
    private int profitForm;
    private String profitId;
    private float profitNum;
    private String usersId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfitBean that = (ProfitBean) o;

        return profitId.equals(that.profitId);

    }

    @Override
    public int hashCode() {
        return profitId.hashCode();
    }

    public TimeBean getCtime() {
        return ctime;
    }

    public void setCtime(TimeBean ctime) {
        this.ctime = ctime;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOrdersId() {
        return ordersId;
    }

    public void setOrdersId(String ordersId) {
        this.ordersId = ordersId;
    }

    public int getProfitForm() {
        return profitForm;
    }

    public void setProfitForm(int profitForm) {
        this.profitForm = profitForm;
    }

    public String getProfitId() {
        return profitId;
    }

    public void setProfitId(String profitId) {
        this.profitId = profitId;
    }

    public float getProfitNum() {
        return profitNum;
    }

    public void setProfitNum(float profitNum) {
        this.profitNum = profitNum;
    }

    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

}
