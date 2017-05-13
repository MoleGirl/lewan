package cn.bmob.imdemo.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class MyAdapter extends PagerAdapter {
	private List mListViews;

	public MyAdapter(List mListViews) {
		this.mListViews = mListViews;// 构造方法，参数是我们的页卡，这样比较方便。
	}

	// 返回个数
	@Override
	public int getCount() {
		//return mListViews.size();
		return Integer.MAX_VALUE;  //int最大值
	}

	// 3指定复用判断逻辑
	@Override
	public boolean isViewFromObject(View view, Object obj) {
		// 当划到新的条目,又返回来,View是否可被复用
		// 返回当前规则
		return view == obj;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		int newPosition=position%mListViews.size();
		container.removeView((View) mListViews.get(newPosition));// 删除页卡
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) { // 这个方法用来实例化页卡

		int newPosition=position%mListViews.size();

		container.addView((View) mListViews.get(newPosition), 0);// 添加页卡
		return mListViews.get(newPosition);
	}



}
