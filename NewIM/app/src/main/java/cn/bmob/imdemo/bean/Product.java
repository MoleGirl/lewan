package cn.bmob.imdemo.bean;

import java.util.List;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/2/13.
 * 产品
 */

public class Product extends BmobObject {
    private String name;//名称
    private String price;//价格
    private String category;//类别
    private String diameter; //丝经
    private String rhole; //网空/目
    private String wide; //宽
    private String high; //高
    private String surface; //表面类型
    private String state; //产品状态
    private String company; //产品单位
    private String number; //数量
    private String address;//产品所在地
    private String tel;//联系方式
    private String username;//发布人
    private List<BmobFile> imgs;//上传图片集合

    private Boolean advert_bool;//是否轮播展示
    private Boolean recommend;//是否推荐展示





    public Product(String name, String price, String category, String diameter, String rhole, String wide, String high, String surface, String state, String company, String number, String address, String tel,String username,List<BmobFile> imgs,Boolean advert_bool,Boolean recommend) {
        this.name = name;
        this.price = price;
        this.category = category;
        this.diameter = diameter;
        this.rhole = rhole;
        this.wide = wide;
        this.high = high;
        this.surface = surface;
        this.state = state;
        this.company = company;
        this.number = number;
        this.address = address;
        this.tel = tel;
        this.username = username;
        this.imgs = imgs;
        this.advert_bool = advert_bool;
        this.recommend = recommend;

    }

    public Product() {
    }


    public Boolean getRecommend() {
        return recommend;
    }

    public void setRecommend(Boolean recommend) {
        this.recommend = recommend;
    }

    public List<BmobFile> getImgs() {
        return imgs;
    }

    public void setImgs(List<BmobFile> imgs) {
        this.imgs = imgs;
    }

    public String getName() {
        if (name==null){
            name = "null";
        }
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        if (price==null){
            price = "null";
        }
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCategory() {
        if (category==null){
            category = "null";
        }
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDiameter() {
        if (diameter==null){
            diameter = "null";
        }
        return diameter;
    }

    public void setDiameter(String diameter) {
        this.diameter = diameter;
    }
    public String getRhole() {
        if (rhole==null){
            rhole = "null";
        }
        return rhole;
    }

    public void setRhole(String rhole) {
        this.rhole = rhole;
    }
    public String getWide() {
        if (wide==null){
            wide = "null";
        }
        return wide;
    }

    public void setWide(String wide) {
        this.wide = wide;
    }

    public String getHigh() {
        if (high==null){
            high = "null";
        }
        return high;
    }

    public void setHigh(String high) {
        this.high = high;
    }

    public String getSurface() {
        if (surface==null){
            surface = "null";
        }
        return surface;
    }

    public void setSurface(String surface) {
        this.surface = surface;
    }

    public String getState() {
        if (state==null){
            state = "null";
        }
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCompany() {
        if (company==null){
            company = "null";
        }
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getNumber() {
        if (number==null){
            number = "null";
        }
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAddress() {
        if (address==null){
            address = "null";
        }
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
    public String getTel() {
        if (tel==null){
            tel = "null";
        }
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getAdvert_bool() {
        return advert_bool;
    }

    public void setAdvert_bool(Boolean advert_bool) {
        this.advert_bool = advert_bool;
    }
}
