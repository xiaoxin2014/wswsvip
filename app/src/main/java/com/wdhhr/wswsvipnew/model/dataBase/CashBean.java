package com.wdhhr.wswsvipnew.model.dataBase;

/**
 * Created by Administrator on 2017/8/31 0031.
 */

public class CashBean {

    /**
     * cashForm : 支付宝
     * cashId : dasdas
     * cashNum : 20
     * ctime : {"date":29,"day":2,"hours":16,"minutes":3,"month":7,"seconds":45,"time":1503993825000,"timezoneOffset":-480,"year":117}
     * ctimeStr : 2017-08-29 16:03
     * etime : null
     * flag : 0
     * id : 3
     * "status":0
     * status : 0
     * user : null
     * usersId : 3U2LY06j4081M7171t3d2
     */

    private String cashForm;
    private String cashId;
    private double cashNum;
    private TimeBean ctime;
    private String ctimeStr;
    private TimeBean etime;
    private int flag;
    private int id;
    private int status;
    private Object user;
    private String usersId;

    public String getCashForm() {
        return cashForm;
    }

    public void setCashForm(String cashForm) {
        this.cashForm = cashForm;
    }

    public String getCashId() {
        return cashId;
    }

    public void setCashId(String cashId) {
        this.cashId = cashId;
    }

    public double getCashNum() {
        return cashNum;
    }

    public void setCashNum(double cashNum) {
        this.cashNum = cashNum;
    }

    public TimeBean getCtime() {
        return ctime;
    }

    public void setCtime(TimeBean ctime) {
        this.ctime = ctime;
    }

    public String getCtimeStr() {
        return ctimeStr;
    }

    public void setCtimeStr(String ctimeStr) {
        this.ctimeStr = ctimeStr;
    }

    public TimeBean getEtime() {
        return etime;
    }

    public void setEtime(TimeBean etime) {
        this.etime = etime;
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

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Object getUser() {
        return user;
    }

    public void setUser(Object user) {
        this.user = user;
    }

    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

}
