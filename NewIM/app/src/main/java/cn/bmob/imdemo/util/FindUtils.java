package cn.bmob.imdemo.util;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.util.Log;

import java.util.List;

import cn.bmob.imdemo.BmobIMApplication;
import cn.bmob.imdemo.bean.Advert;
import cn.bmob.imdemo.bean.Needs;
import cn.bmob.imdemo.bean.Product;
import cn.bmob.imdemo.bean.User;
import cn.bmob.imdemo.model.UserModel;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.GetListener;

/**
 * Created by Administrator on 2017/2/28.
 */

public class FindUtils {
    private Context context;
    private Message msg;


    public void findProduct(Context context) {
        BmobQuery<Product> query_product = new BmobQuery<>();
        query_product.findObjects(context, new FindListener<Product>() {
            @Override
            public void onSuccess(List<Product> list) {
                Log.e("****查询产品", list.size() + "");
                BmobIMApplication.product_list = list;
                for (int i = 0; i < list.size(); i++) {
                    if (list.get(i).getAdvert_bool()) {
                        BmobIMApplication.product_advert.add(list.get(i));
                    }
                }


//                msg = Message.obtain();
//                msg.what = 1;
//                msg.obj = list;
//                Bundle b = new Bundle();
//                b.putParcelable("Product", (Parcelable) list);
//                msg.setData(b);
//                mHanlder.sendMessage(msg);
            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }

    public void findNeeds(Context context) {
        BmobQuery<Needs> query_needs = new BmobQuery<>();
        //query_needs.include("username");
        query_needs.findObjects(context, new FindListener<Needs>() {
            @Override
            public void onSuccess(List<Needs> list) {
                Log.e("****查询需求", list.size() + "");
                BmobIMApplication.needs_list = list;

//                msg = Message.obtain();
//                msg.what = 2;
//                msg.obj = list;
//                Bundle b = new Bundle();
//                b.putParcelable("Needs", (Parcelable) list);
//                msg.setData(b);
//                mHanlder.sendMessage(msg);
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }

    private Handler mHanlder = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    List<Product> listProduct = (List<Product>) msg.obj;
                    BmobIMApplication.product_list = listProduct;
                    for (int i = 0; i < listProduct.size(); i++) {
                        if (listProduct.get(i).getAdvert_bool()) {
                            BmobIMApplication.product_advert.add(listProduct.get(i));
                        }
                    }
                    break;
                case 2:
                    List<Needs> listNeeds = (List<Needs>) msg.obj;
                    BmobIMApplication.needs_list = listNeeds;
                    break;
            }

        }
    };

    /**
     * 产品
     *
     * @param type 根据不同的类型,进行查询不同的数据
     *             1.一次查询最新的10条数据(正常的数据展示,以及刷新数据操作)
     *             2.忽略参数3的条数,查询count个数据(加载更多操作)
     *             3.条件查询
     */
    public void findProduct(int type, final int skip, final Object value, Context context) {
        BmobQuery<Product> query_product = new BmobQuery<>();
        switch (type) {
            case 1:
                query_product.setLimit(10);
                break;
            case 2:
                //设置查询条件为查询10个
                query_product.setLimit(10);
                //忽略的条数
                query_product.setSkip(skip);
                break;
            case 3:
                //设置查询条件为查询10个
                query_product.setLimit(10);
                //忽略的条数
                query_product.setSkip(skip);
                //查询条件字段-值
                query_product.addWhereEqualTo("category", value);
                break;
        }
        query_product.findObjects(context, new FindListener<Product>() {
            @Override
            public void onSuccess(List<Product> list) {
                Log.e("****查询到的全部数据", list.size() + "");
                for (int i = 0; i < list.size(); i++) {
                    Log.e("****查询到的全部", list.get(i).getAdvert_bool() + "");

                }

                if (skip == 0 && value == null) {
                    if (BmobIMApplication.product_list.size() > 0) {
                        BmobIMApplication.product_list.clear();
                        BmobIMApplication.product_list = list;
                    } else {
                        BmobIMApplication.product_list = list;
                    }
                } else if (skip == 0 && value != null) {
                    if (BmobIMApplication.product.size() > 0) {
                        BmobIMApplication.product.clear();
                        BmobIMApplication.product = list;
                    } else {
                        BmobIMApplication.product = list;
                    }
                } else if (skip != 0 && value == null) {
                    BmobIMApplication.product_list.addAll(list);
                } else if (skip != 0 && value != null) {
                    BmobIMApplication.product.addAll(list);
                }
                Log.i("bmob", "成功：" + list.size());
            }

            @Override
            public void onError(int i, String s) {
                Log.i("bmob", "失败：" + s.toString());
            }
        });

    }

    public void findNeeds(final int type, int skip, Context context) {
        BmobQuery<Needs> query_needs = new BmobQuery<>();
        switch (type) {
            case 1:
                query_needs.setLimit(10);
                break;
            case 2:
                //设置查询条件为查询10个
                query_needs.setLimit(10);
                //忽略的条数
                query_needs.setSkip(skip);
                break;
        }
        query_needs.findObjects(context, new FindListener<Needs>() {
            @Override
            public void onSuccess(List<Needs> list) {
                if (type == 1) {
                    if (BmobIMApplication.needs_list.size() > 0) {
                        BmobIMApplication.needs_list.clear();
                        BmobIMApplication.needs_list = list;
                    } else {
                        BmobIMApplication.needs_list = list;
                    }
                } else if (type == 2) {
                    BmobIMApplication.needs_list.addAll(list);
                }

                Log.i("bmob", "成功：" + list.size());
            }

            @Override
            public void onError(int i, String s) {
                Log.i("bmob", "失败：" + s.toString());
            }
        });

    }

    //查询轮播
    public void findProductOf(Context context) {
        BmobQuery<Product> query_productOf = new BmobQuery<>();
        query_productOf.setLimit(3);
        query_productOf.addWhereEqualTo("advert_bool", true);
        query_productOf.findObjects(context, new FindListener<Product>() {
            @Override
            public void onSuccess(List<Product> list) {
                Log.e("****查询到的轮播", list.size() + "");
                for (int i = 0; i < list.size(); i++) {
                    Log.e("****查询到的轮播", list.get(i).getAdvert_bool() + "");

                }


                if (BmobIMApplication.product_advert.size() > 0) {
                    BmobIMApplication.product_advert.clear();
                    BmobIMApplication.product_advert = list;
                } else {
                    BmobIMApplication.product_advert = list;
                }

            }

            @Override
            public void onError(int i, String s) {

            }
        });

    }


    public void findUser(Context context) {
        BmobQuery<User> query_user = new BmobQuery<>();
        query_user.addWhereEqualTo("username", UserModel.getInstance().getCurrentUser().getUsername());
        query_user.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                for (int i = 0; i < list.size(); i++) {
                    BmobIMApplication.user = list.get(0);
                    return;
                }
            }

            @Override
            public void onError(int i, String s) {

            }
        });
    }


    public void findAdvert(Context context) {
        if (BmobIMApplication.advert_list.size() == 0) {
            Log.i("***", "获取网址为空：");
            BmobQuery<Advert> query_advert = new BmobQuery<>();
            query_advert.findObjects(context, new FindListener<Advert>() {
                @Override
                public void onSuccess(List<Advert> list) {
                    if (BmobIMApplication.advert_list.size() > 0) {
                        BmobIMApplication.advert_list.clear();
                        BmobIMApplication.advert_list = list;
                    } else {
                        BmobIMApplication.advert_list = list;
                    }

                    Log.i("*****", "网址成功：" + list.size());
                }

                @Override
                public void onError(int i, String s) {
                    Log.i("*****", "网址失败：" + s.toString());
                }
            });
        } else {
            Log.i("***", "获取网址不为空：");
        }
    }
    //查询全部用户
    public void findHand(Context context) {
        BmobQuery<User> query = new BmobQuery<>();
        query.findObjects(context, new FindListener<User>() {
            @Override
            public void onSuccess(List<User> list) {
                Log.i("***查询到的用户", list.size() + "");
                BmobIMApplication.users = list;
            }
            @Override
            public void onError(int i, String s) {

            }
        });
    }


    public void findCollection(final Context context) {
        BmobQuery<User> query_User = new BmobQuery<>();
        query_User.getObject(context, BmobIMApplication.user.getObjectId(), new GetListener<User>() {
            @Override
            public void onSuccess(User user) {
                if (user.getProduct_collection() != null) {
                    List<String> product_collection = user.getProduct_collection();
                    BmobQuery<Product> query_Product = new BmobQuery<>();
                    for (int i = 0; i < product_collection.size(); i++) {
                        query_Product.getObject(context, product_collection.get(i), new GetListener<Product>() {
                            @Override
                            public void onSuccess(Product product) {
                                BmobIMApplication.productCollections.add(product);
                            }

                            @Override
                            public void onFailure(int i, String s) {
                            }
                        });
                    }
                }
                if (user.getNeeds_collection() != null) {
                    List<String> needs_collection = user.getNeeds_collection();
                    BmobQuery<Needs> query_Needs = new BmobQuery<>();
                    for (int i = 0; i < needs_collection.size(); i++) {
                        query_Needs.getObject(context, needs_collection.get(i), new GetListener<Needs>() {
                            @Override
                            public void onSuccess(Needs needs) {
                                BmobIMApplication.needCollections.add(needs);
                            }

                            @Override
                            public void onFailure(int i, String s) {
                            }
                        });
                    }
                }

            }

            @Override
            public void onFailure(int i, String s) {

            }
        });
    }
}
