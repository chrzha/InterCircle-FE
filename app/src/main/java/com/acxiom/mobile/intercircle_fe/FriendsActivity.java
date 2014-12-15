package com.acxiom.mobile.intercircle_fe;

import android.app.Activity;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class FriendsActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_friends);

        ListView list = (ListView) findViewById(R.id.lvFriends);

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
                Toast.makeText(FriendsActivity.this,"点击第" + arg2 + "个项目",Toast.LENGTH_LONG);
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_friends, menu);
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
}
