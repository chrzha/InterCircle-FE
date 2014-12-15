package com.acxiom.mobile.intercircle_fe;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;


public class MainActivity extends Activity {
    private View view1, view2, view3;
    private TextView tv1, tv2, tv3;
    private ImageView imageView;
    private List<View> viewList;
    private List<String> titleList;
    private PagerAdapter pagerAdapter;
    private ViewPager viewPager;
    private PagerTabStrip pagertab;
    private int offset = 0;// image offset
    private int currIndex = 0;// current pager index
    private int bmpW;// width of the bmp picture
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        InitImageView();
        InitTextView();
        InitViewPager();


        //Init data

    }

    public void PullFriends (View view) {
        ListView list = (ListView) view.findViewById(R.id.lvFriends);

        ArrayList<HashMap<String, Object>> listItem = new ArrayList<HashMap<String, Object>>();
        for(int i = 0; i < 10; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", R.drawable.face);//图像资源的ID
            map.put("ItemTitle", "Level "+i);
            map.put("ItemMessage", "Finished in 1 Min 54 Secs, 70 Moves! ");
            map.put("ItemTime", new Date().toString());
            listItem.add(map);
        }
        //生成适配器的Item和动态数组对应的元素
        SimpleAdapter listItemAdapter = new SimpleAdapter(this,listItem,//数据源
                R.layout.layout_listview_friends_item,//ListItem的XML实现
                //动态数组与ImageItem对应的子项
                new String[] {"ItemImage","ItemTitle", "ItemMessage","ItemTime"},
                //ImageItem的XML文件里面的一个ImageView,两个TextView ID
                new int[] {R.id.ItemImage,R.id.ItemTitle,R.id.ItemMessage, R.id.ItemTime}
        );

        list.setAdapter(listItemAdapter);


        //添加点击
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
//                Toast.makeText(FriendsActivity.this,"点击第" + arg2 + "个项目",Toast.LENGTH_LONG);
            }
        });


        //添加长按点击

        list.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {

            @Override
            public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
                menu.setHeaderTitle("Operations");
                menu.add(0, 0, 0, "Talk");
                menu.add(0, 1, 0, "Call");
                menu.add(0, 2, 0, "View Information");
                menu.add(0, 3, 0, "Delete");
            }
        });
    }

    private void InitImageView() {
        imageView= (ImageView) findViewById(R.id.cursor);
        bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.a).getWidth();// 获取图片宽度
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int screenW = dm.widthPixels;
        offset = (screenW / 3 - bmpW) / 2;
        Matrix matrix = new Matrix();
        matrix.postTranslate(offset, 0);
        imageView.setImageMatrix(matrix);
    }

    private void InitTextView () {
        tv1 = (TextView)findViewById(R.id.text1);
        tv2 = (TextView)findViewById(R.id.text2);
        tv3 = (TextView)findViewById(R.id.text3);

        tv1.setOnClickListener(new MyOnClickListener(0));
        tv2.setOnClickListener(new MyOnClickListener(1));
        tv3.setOnClickListener(new MyOnClickListener(2));
    }
    private void InitViewPager () {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewList = new ArrayList<View>();
        LayoutInflater inflater = getLayoutInflater();
        view1 = inflater.inflate(R.layout.layout_conversation, null);
        view2 = inflater.inflate(R.layout.layout_friends, null);

        //Pull Friends
        PullFriends(view2);
        view3 = inflater.inflate(R.layout.layout_config, null);
        viewList.add(view1);
        viewList.add(view2);
        viewList.add(view3);
        viewPager.setAdapter(new MyViewPagerAdapter(viewList));
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());

        titleList = new ArrayList<String>();
        titleList.add("Chat");
        titleList.add("Friends");
        titleList.add("Me");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    //Listener class
    private class MyOnClickListener implements View.OnClickListener {
        private int index=0;
        public MyOnClickListener(int i){
            index=i;
        }
        public void onClick(View v) {
            viewPager.setCurrentItem(index);
        }
    }

    public class MyViewPagerAdapter extends PagerAdapter{
        private List<View> mListViews;

        public MyViewPagerAdapter(List<View> mListViews) {
            this.mListViews = mListViews;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object)   {
            container.removeView(mListViews.get(position));
        }


        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(mListViews.get(position), 0);
            return mListViews.get(position);
        }

        @Override
        public int getCount() {
            return  mListViews.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }

        @Override
        public boolean isViewFromObject(View arg0, Object arg1) {
            return arg0 == arg1;
        }
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        int one = offset * 2 + bmpW;
        int two = one * 2;
        public void onPageScrollStateChanged(int arg0) {
        }

        public void onPageScrolled(int arg0, float arg1, int arg2) {


        }

        public void onPageSelected(int arg0) {

            Animation animation = new TranslateAnimation(one*currIndex, one * arg0, 0, 0);
            currIndex = arg0;
            animation.setFillAfter(true);
            animation.setDuration(300);
            imageView.startAnimation(animation);
            Toast.makeText(MainActivity.this, "您选择了第 "+ viewPager.getCurrentItem()+" 页卡", Toast.LENGTH_SHORT).show();
        }

    }
}
