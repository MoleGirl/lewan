package cn.bmob.imdemo;

import android.app.Application;
import android.util.Log;
import android.widget.ListView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
//import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.orhanobut.logger.Logger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import c.b.BP;
import cn.bmob.imdemo.base.UniversalImageLoader;
import cn.bmob.imdemo.bean.Advert;
import cn.bmob.imdemo.bean.Needs;
import cn.bmob.imdemo.bean.Product;
import cn.bmob.imdemo.bean.User;
import cn.bmob.newim.BmobIM;
import cn.bmob.v3.Bmob;


/**
 * @author :smile
 * @project:BmobIMApplication
 * @date :2016-01-13-10:19
 */
public class BmobIMApplication extends Application {

    private static BmobIMApplication INSTANCE;

    public static BmobIMApplication INSTANCE() {
        return INSTANCE;
    }

    private void setInstance(BmobIMApplication app) {
        setBmobIMApplication(app);
    }

    private static void setBmobIMApplication(BmobIMApplication a) {
        BmobIMApplication.INSTANCE = a;
    }

    public static int login;
    public static User user = new User();
    public static String path = null;
    public static List<User> users = new ArrayList<>();//全部用户


    //声明mLocationOption对象
    public static AMapLocationClientOption mLocationOption = null;
    //声明AMapLocationClient类对象
    public AMapLocationClient mLocationClient = null;
    public static List<AMapLocation> amapLocations = new ArrayList<>();//定位结果
    private double lat;
    private double lon;
    //声明定位回调监听器
    public AMapLocationListener mLocationListener = new AMapLocationListener() {
        @Override
        public void onLocationChanged(AMapLocation amapLocation) {
            if (amapLocation != null) {
                if (amapLocation.getErrorCode() == 0) {
                    //定位成功回调信息，设置相关消息
                    amapLocation.getLocationType();//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.getLatitude();//获取纬度
                    amapLocation.getLongitude();//获取经度
                    amapLocation.getAccuracy();//获取精度信息
                    SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    Date date = new Date(amapLocation.getTime());
                    df.format(date);//定位时间
                    amapLocation.getAddress();//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.getCountry();//国家信息
                    amapLocation.getProvince();//省信息
                    amapLocation.getCity();//城市信息
                    amapLocation.getDistrict();//城区信息
                    amapLocation.getStreet();//街道信息
                    amapLocation.getStreetNum();//街道门牌号信息
                    amapLocation.getCityCode();//城市编码
                    amapLocation.getAdCode();//地区编码
                    amapLocation.getAoiName();//获取当前定位点的AOI信息
                    lat = amapLocation.getLatitude();
                    lon = amapLocation.getLongitude();
                    Log.v("pcw", "lat : " + lat + " lon : " + lon);
                    if (amapLocations.size() > 3) {
                        amapLocations.clear();
                    }
                    amapLocations.add(amapLocation);
                } else {
                    //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                    Log.e("AmapError", "location Error, ErrCode:"
                            + amapLocation.getErrorCode() + ", errInfo:"
                            + amapLocation.getErrorInfo());
                }
            }
        }
    };

    public static List<Product> product_list = new ArrayList<>();
    public static List<Needs> needs_list = new ArrayList<>();
    public static List<Advert> advert_list = new ArrayList<>();
    public static List<Product> product_advert = new ArrayList<>();//轮播的数据

    public static List<Product> product = new ArrayList<>();//临时数据
    public static List<Product> productCollections = new ArrayList<>();//收藏的产品
    public static List<Needs> needCollections = new ArrayList<>();//收藏的需求

    public static List<Product> product_release = new ArrayList<>();//发布的产品
    public static List<Needs> needs_release = new ArrayList<>();//发布的需求

    @Override
    public void onCreate() {
        super.onCreate();
        BP.init("9da506c0aee4f77fa6522bc3711335a0");
        Bmob.initialize(this, "9da506c0aee4f77fa6522bc3711335a0");
        //初始化
        Logger.init("smile");
        //只有主进程运行的时候才需要初始化
        if (getApplicationInfo().packageName.equals(getMyProcessName())) {
            //im初始化
            Log.e("****", "初始化im");
            BmobIM.init(this);
            //注册消息接收器
            BmobIM.registerDefaultMessageHandler(new DemoMessageHandler(this));
        }
        setInstance(this);
        //uil初始化
        UniversalImageLoader.initImageLoader(this);
        //初始化Fresco,网络图片
        Fresco.initialize(this);

        //初始化定位
        mLocationClient = new AMapLocationClient(getApplicationContext());
        //设置定位回调监听
        mLocationClient.setLocationListener(mLocationListener);
        setUpMap();

    }


    private void setUpMap() {
        //初始化定位参数
        mLocationOption = new AMapLocationClientOption();
        //设置定位模式为高精度模式，Battery_Saving为低功耗模式，Device_Sensors是仅设备模式
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption.setNeedAddress(true);
        //设置是否只定位一次,默认为false
        mLocationOption.setOnceLocation(false);
        //设置是否强制刷新WIFI，默认为强制刷新
        mLocationOption.setWifiActiveScan(true);
        //设置是否允许模拟位置,默认为false，不允许模拟位置
        mLocationOption.setMockEnable(false);
        //设置定位间隔,单位毫秒,默认为2000ms
        mLocationOption.setInterval(2000);
        //给定位客户端对象设置定位参数
        mLocationClient.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient.startLocation();
    }

    /**
     * 获取当前运行的进程名
     *
     * @return
     */
    public static String getMyProcessName() {
        try {
            File file = new File("/proc/" + android.os.Process.myPid() + "/" + "cmdline");
            BufferedReader mBufferedReader = new BufferedReader(new FileReader(file));
            String processName = mBufferedReader.readLine().trim();
            mBufferedReader.close();
            return processName;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
