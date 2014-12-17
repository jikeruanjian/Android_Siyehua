Android_Siyehua
===============

自定ListView,类似NumberPicker
最近要做一个类似于时钟滑动的效果,网上有这个例子:http://blog.csdn.net/leehong2005/article/details/8623694


效果:
![image](https://github.com/ButBueatiful/dotvim/raw/master/screenshots/vim-screenshot.jpg)

分析:

可以自定义一个ListView,按照图中展示,需要至少5个Item,其中中间一个颜色最深,紧挨着的两个颜色淡之,最后更加淡,剩下的都不显示

假如每个Item的大小是50,那么这个ListView的高度是250.

中间的Item之间还必须有两条先,第一条线与ListView顶部相差2个Item,第二条线是3个Item,所以布局可以这么布置


ListView:

[html] view plaincopy在CODE上查看代码片派生到我的代码片
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"  
    xmlns:tools="http://schemas.android.com/tools"  
    android:layout_width="match_parent"  
    android:layout_height="match_parent" >  
  
    <RelativeLayout  
        android:layout_width="match_parent"  
        android:layout_height="wrap_content"  
        android:layout_centerInParent="true" >  
  
        <ListView  
            android:id="@+id/listview"  
            android:layout_width="match_parent"  
            android:layout_height="250dp"  
            android:divider="@null"  
            android:dividerHeight="0dp" />  
  
        <FrameLayout  
            android:layout_width="match_parent"  
            android:layout_height="2dp"  
            android:layout_marginTop="100dp"  
            android:background="#52BDFB" >  
        </FrameLayout>  
  
        <FrameLayout  
            android:layout_width="match_parent"  
            android:layout_height="2dp"  
            android:layout_marginTop="150dp"  
            android:background="#52BDFB" >  
        </FrameLayout>  
    </RelativeLayout>  
  
</RelativeLayout>  


item:

[html] view plaincopy在CODE上查看代码片派生到我的代码片
<?xml version="1.0" encoding="utf-8"?>  
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"  
    android:layout_width="match_parent"  
    android:layout_height="50dp"  
    android:orientation="vertical" >  
  
    <TextView  
        android:id="@+id/content"  
        android:layout_width="match_parent"  
        android:layout_height="50dp"  
        android:gravity="center"  
        android:text="浦发银行"  
        android:textColor="#000000"  
        android:textSize="24sp" />  
  
</LinearLayout>  



由上面的图可以知道,item字体的颜色分为三种,一种是颜色最深的,一种次之,另外一种最淡.

新建一个color:

[html] view plaincopy在CODE上查看代码片派生到我的代码片
<?xml version="1.0" encoding="utf-8"?>  
<resources>  
  
    <color name="first_001">#000000</color>  
    <color name="first_002">#A0808080</color>  
    <color name="first_003">#55DCDCDC</color>  
  
</resources>  

资源准备好了,由上面的分析可知道,listview显示5个item
第一个3级颜色-淡

第二个2级颜色-次之
第三个1级颜色-正常
第四个3级颜色-次之
第五个3级颜色-淡

item的Postion是随着滚动而不断变化的,所以我们需要用setOnScrollListener方法来监听ListView滚动到了第几个位置

监听到滚动以后还需要根据不同的位置做不同的改变,如果在Activity里操作,必须得到每一个item的View,并一一设置对应的颜色值和字体大小

用getchildview这个方法得到的是空的,可以根据改变adapter中的数据,让adapter自己设置

setOnScrollListener监听代码:
[java] view plaincopy在CODE上查看代码片派生到我的代码片
listView.setOnScrollListener(new OnScrollListener() {  
            @Override  
            public void onScrollStateChanged(AbsListView view, int scrollState) {  
            }  
  
            @Override  
            public void onScroll(AbsListView view, int firstVisibleItem,  
                    int visibleItemCount, int totalItemCount) {  
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

适配器设置关键代码:
[java] view plaincopy在CODE上查看代码片派生到我的代码片
if (list.get(position).getMode() == 1) {  
            holder.contentTextView.setTextColor(context.getResources()  
                    .getColor(R.color.first_001));  
            holder.contentTextView.setTextSize(24);  
        } else if (list.get(position).getMode() == 2) {  
            holder.contentTextView.setTextColor(context.getResources()  
                    .getColor(R.color.first_002));  
            holder.contentTextView.setTextSize(20);  
        } else {  
            holder.contentTextView.setTextColor(context.getResources()  
                    .getColor(R.color.first_003));  
            holder.contentTextView.setTextSize(16);  
        }  
适配器根据list中的mode不同,设置不同的布局,这样,效果就出来了


注意:添加数据的时候

[java] view plaincopy在CODE上查看代码片派生到我的代码片
private void addData() {  
    // list.add("浦发银行");  
    // list.add("工商银行");  
    // list.add("农业银行");  
    // list.add("建设银行");  
    for (int i = 0; i < 10; i++) {  
        list.add(new Blank("邮政储蓄" + i, 3));  
    }  
    list.add(0, new Blank("", 3));  
    list.add(1, new Blank("", 3));  
    list.add(new Blank("", 3));  
    list.add(new Blank("", 3));  
    myadapter.notifyDataSetChanged();  
}  

最后list添加了四个空的数据,分别添加到了首尾两个地方,目的是让listView真实的item第一个可以滚到到中间的位置

缺陷:没有实现数据循环滚动


源码下载:

[java] view plaincopy在CODE上查看代码片派生到我的代码片
//http://pan.baidu.com/share/link?shareid=1085645725&uk=705537436  

--------------------------------我是可爱的分割线--------------------------------------
2014-12-17更新
部分人反应滚动的时候,如果一个item只露出一半,甚至更多,都会导致第三个,也就是中间那个item露出来,但是却没完全选中ta
这样就不会有选中的特效.
这个时候我们只要修改监听的onScrollViewListener

listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {// 如果listView停止了
                    listView.setSelection(currentFirstPositon);//关键在于这一部分,如果滚动静止了,我们就选中当前第一个
                    //这样就自动选中了
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
        
