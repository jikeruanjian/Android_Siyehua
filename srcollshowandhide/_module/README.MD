#自定义SrcollView

##效果
![Image](https://github.com/siyehua/Android_Siyehua/blob/master/srcollshowandhide/_module/myScrollView.gif)

##分析
效果主要实现的功能:
 * 在距离头部一定距离的时候,显示头部和底部
 * 在距离底部一定距离的时候,显示底部
 * 其他情况隐藏头部和底部

##关键分析
需要监听ScrollView什么时候滚动到了头部和底部.ScrollView本身没有监听滚动的方法,也没有监听滚动到头部和底部的方法.
可以通过重写Scrollview的onTouchEvent方法来监听手势,在手势中通过getSrollY()方法来得到滚动的距离.从而判断是否
滚动到了头部或者底部.
技术关键:如何监听ScrollView滚动到了底部?
getSrollY() + ScrollView.getHeight() 的高度等于子View 的高度.这个时候就滚到到了底部.

###其他设置
特效设置:
 * fingerMoveDistance//默认手指移动的距离可触发动画.也就是说手指只是轻微的点击一下屏幕,并不会触发特效,需要移动一定的距离才会.默认为30.可修改<br>
 * animationDurationTime //动画播放的时间.默认为750.单位为毫秒,可修改.<br>
 * distanceFromTop //距离顶部多少的时候就开始显示头部和尾部.默认为100,可修改<br>
 * distanceFromBelow //距离底部多少的时候就开始显示尾部.默认为0,可修改<br><br>



