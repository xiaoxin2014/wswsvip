package com.wdhhr.wswsvipnew.model.dataBase;

import org.litepal.crud.DataSupport;

/**
 * Created by felear on 2017/8/24 0024.
 */

public class AddressListBean extends DataSupport {

    /**
     * addressDesc : 123123
     * address_id : nq501HU3810r1230072m3
     * area : 13
     * city : 2
     * ctime : {"date":13,"day":0,"hours":15,"minutes":30,"month":7,"seconds":3,"time":1502609403000,"timezoneOffset":-480,"year":117}
     * defualtAddress :
     * flag : 0
     * id : 48
     * isDefualt : 1
     * name : 123123123
     * phone : 13612344584
     * provice : 1
     * tableAreas : null
     * usersId : 7n0117u98170jh22720d7
     * utime : {"date":19,"day":6,"hours":16,"minutes":17,"month":7,"seconds":2,"time":1503130622000,"timezoneOffset":-480,"year":117}
     */

    private String addressDesc;
    private String address_id;
    private String area;
    private String city;
    private TimeBean ctime;
    private String defualtAddress;
    private String flag;
    private int id;
    private int isDefualt;
    private String name;
    private String phone;
    private String provice;
    private Object tableAreas;
    private String usersId;
    private TimeBean utime;

    public String getAddressDesc() {
        return addressDesc;
    }

    public void setAddressDesc(String addressDesc) {
        this.addressDesc = addressDesc;
    }

    public String getAddress_id() {
        return address_id;
    }

    public void setAddress_id(String address_id) {
        this.address_id = address_id;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public TimeBean getCtime() {
        return ctime;
    }

    public void setCtime(TimeBean ctime) {
        this.ctime = ctime;
    }

    public String getDefualtAddress() {
        return defualtAddress;
    }

    public void setDefualtAddress(String defualtAddress) {
        this.defualtAddress = defualtAddress;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIsDefualt() {
        return isDefualt;
    }

    public void setIsDefualt(int isDefualt) {
        this.isDefualt = isDefualt;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvice() {
        return provice;
    }

    public void setProvice(String provice) {
        this.provice = provice;
    }

    public Object getTableAreas() {
        return tableAreas;
    }

    public void setTableAreas(Object tableAreas) {
        this.tableAreas = tableAreas;
    }

    public String getUsersId() {
        return usersId;
    }

    public void setUsersId(String usersId) {
        this.usersId = usersId;
    }

    public TimeBean getUtime() {
        return utime;
    }

    public void setUtime(TimeBean utime) {
        this.utime = utime;
    }

}
