package com.wdhhr.wswsvipnew.model;

import com.wdhhr.wswsvipnew.model.dataBase.AddressListBean;
import com.wdhhr.wswsvipnew.model.dataBase.CashBean;
import com.wdhhr.wswsvipnew.model.dataBase.UsersBean;
import com.wdhhr.wswsvipnew.model.dataBase.UsersListBean;
import com.wdhhr.wswsvipnew.model.dataBase.VersionBean;

import org.litepal.crud.DataSupport;

import java.util.List;

/**
 * Created by felear on 2017/8/13 0013.
 */

public class UserCommonBean {

    /**
     * status : 0
     * msg : 操作成功
     * time : 1502614650994
     * data : {"businessId":"1","cashPwd":"","ctime":null,"flag":"0","id":16,"userAccount":"15616518706","userAddress":"","userAge":0,"userBalance":0,"userEmerPhone":"","userEmergency":"","userIDCard":"","userLevel":"1","userName":"啦啦啦","userPhone":"15616518700","userPhoto":"http://39.108.105.63:8080/upload/userImg/20170804/820b1XgY0Y0261304207o.png","userPwd":"505A3BC7C9A4B231","usersId":"7n0117u98170jh22720d7","utime":null}
     */

    private int status;
    private String msg;
    private long time;
    private DataBean data;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {

        /**
         * businessId : 1
         * cashPwd :
         * ctime : null
         * flag : 0
         * id : 16
         * userAccount : 15616518706
         * userAddress :
         * userAge : 0
         * userBalance : 0
         * userEmerPhone :
         * userEmergency :
         * userIDCard :
         * userLevel : 1
         * userName : 啦啦啦
         * userPhone : 15616518700
         * userPhoto : http://39.108.105.63:8080/upload/userImg/20170804/820b1XgY0Y0261304207o.png
         * userPwd : 505A3BC7C9A4B231
         * usersId : 7n0117u98170jh22720d7
         * utime : null
         */

        private int usersCount;
        private double amount;
        private String carNum;
        private String filePath;
        private List<UsersListBean> usersList;
        private UsersBean users;
        private String userName;
        private String userPhone;
        private String usersId;
        private String directPushCount;
        private String janePushedCALLBLE;
        private String userPhoto;
        private String recommenderName;
        private String recommenderPhone;
        private List<AddressListBean> addressList;
        private List<CashBean> cashList;
        private VersionBean getChoice;

        public VersionBean getGetChoice() {
            return getChoice;
        }

        public void setGetChoice(VersionBean getChoice) {
            this.getChoice = getChoice;
        }

        public String getUsersId() {
            return usersId;
        }

        public void setUsersId(String usersId) {
            this.usersId = usersId;
        }

        public String getCarNum() {
            return carNum;
        }

        public void setCarNum(String carNum) {
            this.carNum = carNum;
        }

        public String getRecommenderName() {
            return recommenderName;
        }

        public void setRecommenderName(String recommenderName) {
            this.recommenderName = recommenderName;
        }

        public String getRecommenderPhone() {
            return recommenderPhone;
        }

        public void setRecommenderPhone(String recommenderPhone) {
            this.recommenderPhone = recommenderPhone;
        }

        public List<CashBean> getCashList() {
            return cashList;
        }

        public void setCashList(List<CashBean> cashList) {
            this.cashList = cashList;
        }

        public void setUsersCount(int usersCount) {
            this.usersCount = usersCount;
        }

        public double getAmount() {
            return amount;
        }

        public void setAmount(double amount) {
            this.amount = amount;
        }

        public UsersBean getUsers() {
            return users;
        }

        public void setUsers(UsersBean users) {
            this.users = users;
        }

        public void setDirectPushCount(String directPushCount) {
            this.directPushCount = directPushCount;
        }

        public void setJanePushedCALLBLE(String janePushedCALLBLE) {
            this.janePushedCALLBLE = janePushedCALLBLE;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getUserPhone() {
            return userPhone;
        }

        public void setUserPhone(String userPhone) {
            this.userPhone = userPhone;
        }

        public String getUserPhoto() {
            return userPhoto;
        }

        public void setUserPhoto(String userPhoto) {
            this.userPhoto = userPhoto;
        }

        public UsersBean getUsersBean() {
            return users;
        }

        public void setUsersBean(UsersBean usersBean) {
            this.users = usersBean;
        }

        public List<AddressListBean> getAddressList() {
            return addressList;
        }

        public List<AddressListBean> getAddressListAndSave() {
            if (addressList != null) {
                for (AddressListBean addrss : addressList) {
                    DataSupport.deleteAll(AddressListBean.class, "address_id = ?", addrss.getAddress_id());
                    addrss.save();
                }
            }
            return addressList;
        }

        public void setAddressList(List<AddressListBean> addressList) {
            this.addressList = addressList;
        }

        public List<UsersListBean> getUsersList() {
            return usersList;
        }

        public void setUsersList(List<UsersListBean> usersList) {
            this.usersList = usersList;
        }

        public String getFilePath() {
            return filePath;
        }

        public void setFilePath(String filePath) {
            this.filePath = filePath;
        }

        public String getDirectPushCount() {
            return directPushCount;
        }

        public String getJanePushedCALLBLE() {
            return janePushedCALLBLE;
        }

        public int getUsersCount() {
            return usersCount;
        }

    }

}
