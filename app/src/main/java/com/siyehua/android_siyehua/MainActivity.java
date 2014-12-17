package com.siyehua.android_siyehua;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.AbsListView;
import android.widget.ListView;

import java.util.ArrayList;


public class MainActivity extends ActionBarActivity {

    private ListView listView;
    private Myadapter myadapter;
    private ArrayList<Blank> list = new ArrayList<Blank>();
    private int currentFirstPositon = -1;// 当前第一个显示的Item

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listview);
        myadapter = new Myadapter(this, list);
        listView.setAdapter(myadapter);
        addData();

        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {// 如果listView停止了
                    listView.setSelection(currentFirstPositon);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                currentFirstPositon = firstVisibleItem;
                if (list.size() < 5) {
                    return;
                }
                list.get(firstVisibleItem).setMode(3);
                list.get(firstVisibleItem + 4).setMode(3);
                list.get(firstVisibleItem + 1).setMode(2);
                list.get(firstVisibleItem + 3).setMode(2);
                list.get(firstVisibleItem + 2).setMode(1);
                myadapter.notifyDataSetChanged();
            }
        });
    }

    private void addData() {
        for (int i = 0; i < 10; i++) {
            list.add(new Blank("浦发银行" + i, 3));
        }
        list.add(0, new Blank("", 3));
        list.add(1, new Blank("", 3));
        list.add(new Blank("", 3));
        list.add(new Blank("", 3));
        myadapter.notifyDataSetChanged();
    }


}
