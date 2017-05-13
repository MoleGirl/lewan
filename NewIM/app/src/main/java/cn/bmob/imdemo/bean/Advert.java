package cn.bmob.imdemo.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/2/23.
 */

public class Advert extends BmobObject {

    private String photo_url;

    public String getPhoto_url() {
        return photo_url;
    }

    public void setPhoto_url(String photo_url) {
        this.photo_url = photo_url;
    }

    public Advert(String photo_url) {
        this.photo_url = photo_url;
    }

    public Advert() {
    }
}
