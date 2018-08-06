package com.test.controller;

import com.test.service.IArticleService;
import com.test.wechat.common.TokenTimer;
import com.test.wechat.entity.WeixinTokenConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/article")
@EnableConfigurationProperties(WeixinTokenConstant.class)
public class ArticleController {


   @Autowired
   private WeixinTokenConstant weixinTokenConstant;

    @Autowired
    private IArticleService articleService;

    @RequestMapping("/index")
    @ResponseBody
    public Object index(Model model) {
        Map map = new HashMap();
//        int i = articleService.SaveArticle();
//        map.put("result","成功了"+i+"条数据");
        map.put("token",weixinTokenConstant.getToken());
        map.put("appid" ,weixinTokenConstant.getAppid());
        TokenTimer tt = new TokenTimer();
        tt.getAccessToken();

        return map;
    }
}
