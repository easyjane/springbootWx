package com.test.wechat.util;

import com.test.wechat.resp.Menu;
import com.test.wechat.resp.ViewButton;

public class MenuUtil {

    /**
    * 封装菜单数据
    * */
    public static Menu getMenu(){
        //首先创建二级菜单
        /*CommonButton cb_1 = new CommonButton();
        cb_1.setKey("key1");
        cb_1.setName("子菜单1");
        cb_1.setType("click");*/

        ViewButton cb_1 = new ViewButton();
        cb_1.setName("那你那今日");
        cb_1.setType("view");
        cb_1.setUrl("http://www.baidu.com");



        /*CommonButton cb_2 = new CommonButton();
        cb_2.setKey("key2");
        cb_2.setName("子菜单2");
        cb_2.setType("click");*/

        //创建第一个一级菜单
        //ComplexButton cx_1 = new ComplexButton();
        //cx_1.setName("艳辉网");
        //cx_1.setSub_button(new Button[]{cb_1});
        //
        //
        ////继续创建二级菜单
        ////CommonButton cb_3 = new CommonButton();
        ////cb_3.setKey("key3");
        ////cb_3.setName("子菜单3");
        ////cb_3.setType("click");
        //
        //ViewButton cb_21 = new ViewButton();
        //cb_21.setName("我的博客");
        //cb_21.setType("view");
        //cb_21.setUrl("http://blog.csdn.net/sinat_15153911");
        //
        //ViewButton cb_22 = new ViewButton();
        //cb_22.setName("开通VIP");
        //cb_22.setType("view");
        //cb_22.setUrl("http://localhost/yhSSM/wexin/openVIP.html");
        //
        //
        ///*ViewButton cb_4 = new ViewButton();
        //cb_4.setName("访问网页");
        //cb_4.setType("view");
        ////需要使用网页授权获取微信用户的信息
        //cb_4.setUrl("https://open.weixin.qq.com/connect/oauth2/authorize?appid=APPID&redirect_uri=要访问的网页&response_type=code&scope=snsapi_base&state=xxx");
        //
        //ViewButton cb_5 = new ViewButton();
        //cb_5.setName("访问网页2");
        //cb_5.setType("view");
        ////需要使用网页授权获取微信用户的信息
        //cb_5.setUrl("https://www.baidu.com");
        //*/
        //
        ////创建第二个一级菜单
        //ComplexButton cx_2 = new ComplexButton();
        //cx_2.setName("关于我们");
        //cx_2.setSub_button(new Button[]{cb_21,cb_22});
        //
        //ViewButton cb_31 = new ViewButton();
        //cb_31.setName("蜗蜗洗发水");
        //cb_31.setType("view");
        //cb_31.setUrl("http://localhost/yhSSM/wexin/ggXifashui.html");
        //
        //ComplexButton cx_3 = new ComplexButton();
        //cx_3.setName("女友广告");
        //cx_3.setSub_button(new Button[]{cb_31});

        //封装菜单数据
        Menu menu=new Menu();
        menu.setButton(new ViewButton[]{cb_1});

        return menu;
    }
}