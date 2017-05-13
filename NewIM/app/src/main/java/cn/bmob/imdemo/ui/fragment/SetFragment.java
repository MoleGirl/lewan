package cn.bmob.imdemo.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.imdemo.BmobIMApplication;
import cn.bmob.imdemo.R;
import cn.bmob.imdemo.base.ParentWithNaviFragment;
import cn.bmob.imdemo.bean.User;
import cn.bmob.imdemo.model.UserModel;
import cn.bmob.imdemo.ui.LoginActivity;
import cn.bmob.imdemo.ui.activity.CollectionActivity;
import cn.bmob.imdemo.ui.activity.DetailsActivity;
import cn.bmob.newim.BmobIM;
import cn.bmob.imdemo.ui.activity.UserActivity;
import cn.bmob.v3.BmobUser;

/**
 * 设置
 *
 * @author :smile
 * @project:SetFragment
 * @date :2016-01-25-18:23
 */
public class SetFragment extends ParentWithNaviFragment implements View.OnClickListener {

    @Bind(R.id.tv_set_name)
    TextView tv_set_name;

    @Bind(R.id.layout_info)
    RelativeLayout layout_info;
    @Bind(R.id.layout_collection)
    RelativeLayout layout_collection;
    @Bind(R.id.layout_release)
    RelativeLayout layout_release;


    SimpleDraweeView mUser_avatar;
    Intent intent;
    @Override
    protected String title() {
        return "我的";
    }

//    public static SetFragment newInstance() {
//        SetFragment fragment = new SetFragment();
//        Bundle args = new Bundle();
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    public SetFragment() {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_set, container, false);
        initNaviView();
        ButterKnife.bind(this, rootView);
        String username = UserModel.getInstance().getCurrentUser().getUsername();
        tv_set_name.setText(TextUtils.isEmpty(username) ? "" : username);
        layout_collection.setOnClickListener(this);
        layout_release.setOnClickListener(this);
        mUser_avatar = (SimpleDraweeView) rootView.findViewById(R.id.user_avatar);
        if (BmobIMApplication.path != null) {//本地不为空
            Glide.with(getContext()).load(BmobIMApplication.path).centerCrop().into(mUser_avatar);
        } else {
            if (UserModel.getInstance().getCurrentUser().getHand() != null) {
                Glide.with(getContext()).load(UserModel.getInstance().getCurrentUser().getHand().getUrl()).centerCrop().into(mUser_avatar);
            }
        }
        initdatas();
        return rootView;
    }

    private void initdatas() {

    }

    @OnClick(R.id.layout_info)
    public void onInfoClick(View view) {
        Bundle bundle = new Bundle();
        //bundle.putSerializable("u", BmobUser.getCurrentUser(getActivity(), User.class));
        bundle.putSerializable("u", UserModel.getInstance().getCurrentUser());
        //startActivity(UserInfoActivity.class, bundle);
        startActivity(UserActivity.class, bundle);


    }

    @OnClick(R.id.btn_logout)
    public void onLogoutClick(View view) {
        UserModel.getInstance().logout();
        //可断开连接
        BmobIM.getInstance().disConnect();
        getActivity().finish();
        startActivity(LoginActivity.class, null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_collection:
                intent = new Intent(getContext(), CollectionActivity.class);
                intent.putExtra("title", "我的收藏");
                startActivity(intent);
                //startActivity(CollectionActivity.class, null);
                break;
            case R.id.layout_release:
                intent = new Intent(getContext(), CollectionActivity.class);
                intent.putExtra("title", "我的发布");
                startActivity(intent);
                //startActivity(CollectionActivity.class, null);
                break;

        }


    }
}
