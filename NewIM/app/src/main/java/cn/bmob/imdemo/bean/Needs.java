package cn.bmob.imdemo.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/2/13.
 */

public class Needs extends BmobObject {
    private String need_title;
    private String need_details;
    private String need_phone;
    private String need_address;
    private String username;






    public Needs(String need_title, String need_details,String need_phone,String need_address,String username) {
        this.need_title = need_title;
        this.need_details = need_details;
        this.need_phone = need_phone;
        this.need_address = need_address;


        this.username=username;
    }




    public String getNeed_phone() {
        return need_phone;
    }

    public void setNeed_phone(String need_phone) {
        this.need_phone = need_phone;
    }

    public String getNeed_address() {
        return need_address;
    }

    public void setNeed_address(String need_address) {
        this.need_address = need_address;
    }

    public String getNeed_details() {
        return need_details;
    }

    public void setNeed_details(String need_details) {
        this.need_details = need_details;
    }

    public String getNeed_title() {
        return need_title;
    }

    public void setNeed_title(String need_title) {
        this.need_title = need_title;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Needs() {
    }
}
