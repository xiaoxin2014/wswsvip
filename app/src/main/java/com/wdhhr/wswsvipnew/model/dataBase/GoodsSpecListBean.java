package com.wdhhr.wswsvipnew.model.dataBase;

import android.text.TextUtils;

import org.litepal.crud.DataSupport;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by felear on 2017/8/18 0018.
 */

public class GoodsSpecListBean extends DataSupport {
    /**
     * c_id : 58
     * costPrice : 1000
     * ctime : {"date":25,"day":5,"hours":14,"minutes":2,"month":7,"seconds":2,"time":1503640922000,"timezoneOffset":-480,"year":117}
     * discountPrice : 0
     * earn : 78.24
     * etime : null
     * flag : 0
     * goodsDetailCount : 10000
     * goodsDetailId : 14027e18b5801402pJN73
     * goodsDetailInventory : 502
     * goodsDetailOff : 0
     * goodsDetailPic : http://39.108.105.63:8080/upload/goodImg/20170825/1170kj2Z168Olp2004245.png,http://39.108.105.63:8080/upload/goodImg/20170825/1RB008154U2s2w4t01174.png,http://39.108.105.63:8080/upload/goodImg/20170825/0124o8P7051yQ21k0gz54.png,http://39.108.105.63:8080/upload/goodImg/20170825/127oh0471j080t5294G1x.png
     * goodsDetailPrice : 1100
     * goodsDetailSpec :
     * goodsId : m25840Tsk7218P0f0X141
     * isHost : 0
     * postage : 2
     * profit : 0
     * specA : 1米
     * specB :
     * specC :
     * specD :
     * specE :
     * vipPrice : 950
     */

    private int c_id;
    private String costPrice;
    private TimeBean ctime;
    private String discountPrice;
    private String earn;
    private TimeBean etime;
    private int flag;
    private int goodsDetailCount;
    private String goodsDetailId;
    private int goodsDetailInventory;
    private int goodsDetailOff;
    private String goodsDetailPic;
    private String goodsDetailPrice;
    private String goodsDetailSpec;
    private String goodsId;
    private int isHost;
    private int postage;
    private String profit;
    private String specA;
    private String specB;
    private String specC;
    private String specD;
    private String specE;
    private String vipPrice;

    @Override
    public String toString() {
        return "GoodsSpecListBean{" +
                ", c_id=" + c_id +
                ", costPrice=" + costPrice +
                ", ctime=" + ctime +
                ", discountPrice=" + discountPrice +
                ", earn=" + earn +
                ", etime=" + etime +
                ", flag=" + flag +
                ", goodsDetailCount=" + goodsDetailCount +
                ", goodsDetailId='" + goodsDetailId + '\'' +
                ", goodsDetailInventory=" + goodsDetailInventory +
                ", goodsDetailOff=" + goodsDetailOff +
                ", goodsDetailPic='" + goodsDetailPic + '\'' +
                ", goodsDetailPrice=" + goodsDetailPrice +
                ", goodsDetailSpec='" + goodsDetailSpec + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", isHost=" + isHost +
                ", postage=" + postage +
                ", profit=" + profit +
                ", specA='" + specA + '\'' +
                ", specB='" + specB + '\'' +
                ", specC='" + specC + '\'' +
                ", specD='" + specD + '\'' +
                ", specE='" + specE + '\'' +
                ", vipPrice=" + vipPrice +
                '}';
    }

    public int getC_id() {
        return c_id;
    }

    public void setC_id(int c_id) {
        this.c_id = c_id;
    }

    public String getCostPrice() {
        return costPrice;
    }

    public void setCostPrice(String costPrice) {
        this.costPrice = costPrice;
    }

    public TimeBean getCtime() {
        return ctime;
    }

    public void setCtime(TimeBean ctime) {
        this.ctime = ctime;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getEarn() {
        return earn;
    }

    public void setEarn(String earn) {
        this.earn = earn;
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

    public int getGoodsDetailCount() {
        return goodsDetailCount;
    }

    public void setGoodsDetailCount(int goodsDetailCount) {
        this.goodsDetailCount = goodsDetailCount;
    }

    public String getGoodsDetailId() {
        return goodsDetailId;
    }

    public void setGoodsDetailId(String goodsDetailId) {
        this.goodsDetailId = goodsDetailId;
    }

    public int getGoodsDetailInventory() {
        return goodsDetailInventory;
    }

    public void setGoodsDetailInventory(int goodsDetailInventory) {
        this.goodsDetailInventory = goodsDetailInventory;
    }

    public int getGoodsDetailOff() {
        return goodsDetailOff;
    }

    public void setGoodsDetailOff(int goodsDetailOff) {
        this.goodsDetailOff = goodsDetailOff;
    }

    public String getGoodsDetailPic() {
        return goodsDetailPic;
    }

    public void setGoodsDetailPic(String goodsDetailPic) {
        this.goodsDetailPic = goodsDetailPic;
    }

    public String getGoodsDetailPrice() {
        return goodsDetailPrice;
    }

    public void setGoodsDetailPrice(String goodsDetailPrice) {
        this.goodsDetailPrice = goodsDetailPrice;
    }

    public String getGoodsDetailSpec() {
        return goodsDetailSpec;
    }

    public void setGoodsDetailSpec(String goodsDetailSpec) {
        this.goodsDetailSpec = goodsDetailSpec;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public int getIsHost() {
        return isHost;
    }

    public void setIsHost(int isHost) {
        this.isHost = isHost;
    }

    public int getPostage() {
        return postage;
    }

    public void setPostage(int postage) {
        this.postage = postage;
    }

    public String getProfit() {
        return profit;
    }

    public void setProfit(String profit) {
        this.profit = profit;
    }

    public String getSpecA() {
        return specA;
    }

    public void setSpecA(String specA) {
        this.specA = specA;
    }

    public String getSpecB() {
        return specB;
    }

    public void setSpecB(String specB) {
        this.specB = specB;
    }

    public String getSpecC() {
        return specC;
    }

    public void setSpecC(String specC) {
        this.specC = specC;
    }

    public String getSpecD() {
        return specD;
    }

    public void setSpecD(String specD) {
        this.specD = specD;
    }

    public String getSpecE() {
        return specE;
    }

    public void setSpecE(String specE) {
        this.specE = specE;
    }

    public String getVipPrice() {
        return vipPrice;
    }

    public void setVipPrice(String vipPrice) {
        this.vipPrice = vipPrice;
    }

    public List<String> getSpecList() {
        ArrayList<String> list = new ArrayList<>();
        if (specA != null && !TextUtils.equals(specA, "")) {
            list.add(specA);
        }
        if (specB != null && !TextUtils.equals(specB, "")) {
            list.add(specB);
        }
        if (specC != null && !TextUtils.equals(specC, "")) {
            list.add(specC);
        }
        if (specD != null && !TextUtils.equals(specD, "")) {
            list.add(specD);
        }
        if (specE != null && !TextUtils.equals(specE, "")) {
            list.add(specE);
        }
        return list;
    }
}
