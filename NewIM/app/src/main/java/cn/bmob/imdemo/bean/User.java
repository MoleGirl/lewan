package cn.bmob.imdemo.bean;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.imdemo.db.NewFriend;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.datatype.BmobFile;

/**
 * @author :smile
 * @project:User
 */
public class User extends BmobUser {

    private String avatar;//用户头像

    private BmobFile hand;
    //private String pay;//支付状态1=支付
    private BmobDate pay_time;//支付时间
    private Integer release_no;//发布次数
    private Boolean pay;//支付状态 false 未支付



    private List<String> needs_collection;//收藏的需求
    private List<String> product_collection;//收藏产品的集合


    public User(){}

    public User(NewFriend friend){
        setObjectId(friend.getUid());
        setUsername(friend.getName());
        setAvatar(friend.getAvatar());
    }

    public Boolean getPay() {
        if (pay==null){
            pay = false;
        }
        return pay;
    }

    public void setPay(Boolean pay) {
        this.pay = pay;
    }

    public Integer getRelease_no() {
        if (release_no==null){
            release_no = new Integer(0);
        }
        return release_no;
    }

    public void setRelease_no(Integer release_no) {
        this.release_no = release_no;
    }



    public BmobDate getPay_time() {
        return pay_time;
    }

    public void setPay_time(BmobDate pay_time) {
        this.pay_time = pay_time;
    }

    public BmobFile getHand() {
        return hand;
    }

    public void setHand(BmobFile hand) {
        this.hand = hand;
    }

    public List<String> getNeeds_collection() {
        if (needs_collection==null){
            needs_collection = new ArrayList<>();
        }
        return needs_collection;
    }

    public void setNeeds_collection(List<String> needs_collection) {
        this.needs_collection = needs_collection;
    }


    public List<String> getProduct_collection() {
        if (product_collection==null){
            product_collection = new ArrayList<>();
        }
        return product_collection;
    }

    public void setProduct_collection(List<String> product_collection) {
        this.product_collection = product_collection;
    }
    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
