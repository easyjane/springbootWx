package com.test.baiduaip.controller;

import cn.xsshome.taip.util.FileUtil;
import com.baidu.aip.util.Base64Util;
import com.test.baiduaip.utils.Ai4Image;
import com.test.baiduaip.utils.AuthService;
import com.test.baiduaip.utils.HttpUtil;
import com.test.entity.Result;
import com.test.utils.DateStyle;
import com.test.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

@RestController
@RequestMapping("/baiduaip")
public class BaiduAipController {

    private static Logger logger = LoggerFactory.getLogger(BaiduAipController.class);


    @RequestMapping("/index")
    public Object index() {

        List<Map<String,Object>> list = new ArrayList<>();
        Map map = new HashMap();
        map.put("title","文字识别");
        map.put("src","/aip/word/general_basic.htm");
        map.put("id",100001);

        map = new HashMap();
        map.put("title","身份证识别");
        map.put("src","/aip/word/idcard.htm");
        map.put("id",100002);

        list.add(map);

        return Result.setResult(200,"返回成功",list);
    }

    /**
     * 普通文字识别
     * @param model
     * @param img
     * @param request
     * @return
     */
    @RequestMapping("/general")
    public Object general(Model model, @RequestParam("img")MultipartFile img,Integer flag, HttpServletRequest request) {
        // 通用识别url
        String otherHost = "https://aip.baidubce.com/rest/2.0/ocr/v1/accurate_basic";
        if (1 == flag) {
            // 手写字地址
            otherHost = "https://aip.baidubce.com/rest/2.0/ocr/v1/handwriting";
        }
        Ai4Image ai4Image = new Ai4Image(img, request, otherHost).invoke();
        if (ai4Image.is()) return ai4Image.getResult();
        return Result.setResult(200,"转换成功","");
    }

    /**
     * 普通文字识别
     * @param model
     * @param img
     * @param request
     * @return
     */
    @RequestMapping("/idcard")
    public Object idcard(Model model, @RequestParam("img")MultipartFile img,Integer flag, HttpServletRequest request) {
        // 通用识别url
        String otherHost = "https://aip.baidubce.com/rest/2.0/ocr/v1/idcard";
        Ai4Image ai4Image = new Ai4Image(img, request, otherHost).invoke();
        if (ai4Image.is()) return ai4Image.getResult();


        return Result.setResult(200,"转换成功","");
    }


}
