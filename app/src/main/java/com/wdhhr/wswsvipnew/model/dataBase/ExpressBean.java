package com.wdhhr.wswsvipnew.model.dataBase;

import org.litepal.crud.DataSupport;

/**
 * Created by felear on 2017/8/29 0029.
 */

public class ExpressBean extends DataSupport {

    /**
     * ctime : {"date":29,"day":2,"hours":10,"minutes":5,"month":7,"seconds":57,"time":1503972357000,"timezoneOffset":-480,"year":117}
     * expressName : 圆通
     * id : 419
     * sortName : YTO
     */

    private TimeBean ctime;
    private String expressName;
    private int id;
    private String sortName;

    public TimeBean getCtime() {
        return ctime;
    }

    public void setCtime(TimeBean ctime) {
        this.ctime = ctime;
    }

    public String getExpressName() {
        if (expressName != null) {
            expressName = expressName.trim();
        }
        return expressName;
    }

    public void setExpressName(String expressName) {
        this.expressName = expressName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

}
