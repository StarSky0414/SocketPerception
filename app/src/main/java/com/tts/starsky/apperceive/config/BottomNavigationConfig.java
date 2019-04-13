package com.tts.starsky.apperceive.config;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.ashokvarma.bottomnavigation.TextBadgeItem;
import com.tts.starsky.apperceive.MainActivity;
import com.tts.starsky.apperceive.R;
import com.tts.starsky.apperceive.bean.UserStateInfo;

/**
 * bottomNavigation 设置
 */
public class BottomNavigationConfig {
    private MainActivity activity;
    private TextBadgeItem numberBadge;
    int lastSelectedPosition = 0;

    public BottomNavigationConfig(MainActivity activity) {
        this.activity = activity;
    }

    /**
     *  底部导航信息初始化
     */
    public void ButtonNavigationConfigInit() {
        /**
         * bottomNavigation 设置
         */
        BottomNavigationBar bottomNavigationBar = (BottomNavigationBar) activity.findViewById(R.id.bottom_navigation_bar);

        /** 导航基础设置 包括按钮选中效果 导航栏背景色等 */
        bottomNavigationBar
                .setTabSelectedListener(activity)
                .setMode(BottomNavigationBar.MODE_FIXED)
                /**
                 *  setMode() 内的参数有三种模式类型：
                 *  MODE_DEFAULT 自动模式：导航栏Item的个数<=3 用 MODE_FIXED 模式，否则用 MODE_SHIFTING 模式
                 *  MODE_FIXED 固定模式：未选中的Item显示文字，无切换动画效果。
                 *  MODE_SHIFTING 切换模式：未选中的Item不显示文字，选中的显示文字，有切换动画效果。
                 */

                .setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC)
                /**
                 *  setBackgroundStyle() 内的参数有三种样式
                 *  BACKGROUND_STYLE_DEFAULT: 默认样式 如果设置的Mode为MODE_FIXED，将使用BACKGROUND_STYLE_STATIC
                 *                                    如果Mode为MODE_SHIFTING将使用BACKGROUND_STYLE_RIPPLE。
                 *  BACKGROUND_STYLE_STATIC: 静态样式 点击无波纹效果
                 *  BACKGROUND_STYLE_RIPPLE: 波纹样式 点击有波纹效果
                 */

                .setActiveColor("#FF107FFD") //选中颜色
                .setInActiveColor("#e9e6e6") //未选中颜色
                .setBarBackgroundColor("#1ccbae");//导航栏背景色
        /** 添加导航按钮 */

        numberBadge = new TextBadgeItem()
                .setBackgroundColorResource(R.color.transparent)
                .setHideOnSelect(false);

        BottomNavigationItem bottomNavigationItem = new BottomNavigationItem(R.drawable.find2, "消息").setBadgeItem(numberBadge);

        bottomNavigationBar
                .addItem(new BottomNavigationItem(R.drawable.find1, "发现"))
                .addItem(bottomNavigationItem)
                .addItem(new BottomNavigationItem(R.drawable.trends, "动态"))
                .addItem(new BottomNavigationItem(R.drawable.my2, "我de"))
                .setFirstSelectedPosition(lastSelectedPosition)
                .setBarBackgroundColor(R.color.black)
                .initialise(); //initialise 一定要放在 所有设置的最后一项
    }

    public void setNumber(long number) {
        numberBadge = new TextBadgeItem()
                .setBorderWidth(0);
        if (number != 0) {
            numberBadge.setBackgroundColorResource(R.color.messageRed)
                    .setText(String.valueOf(number))
                    .setHideOnSelect(true);
        }else {
            numberBadge.setBackgroundColorResource(R.color.transparent).setHideOnSelect(false);
        }
    }

    public void addNumber(long number){
        int unReadMassageNum = UserStateInfo.getUnReadMassageNum();
        long result = number + unReadMassageNum;
        setNumber(result);
    }
}
