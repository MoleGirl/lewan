-libraryjars libs/alipaySDK-20150602.jar

-keep class com.alipay.android.app.IAlixPay{*;}
-keep class com.alipay.android.app.IAlixPay$Stub{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback{*;}
-keep class com.alipay.android.app.IRemoteServiceCallback$Stub{*;}
-keep class com.alipay.sdk.app.PayTask{ public *;}
-keep class com.alipay.sdk.app.AuthTask{ public *;}

-libraryjars libs/BmobPay_v3.x.x_xxxxxx.jar
-keepclasseswithmembers class c.b.** { *; }
-keep interface c.b.PListener{ *; }
-keep interface c.b.QListener{ *; }


