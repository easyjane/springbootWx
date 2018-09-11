package com.test.baiduaip.controller;

import cn.xsshome.taip.util.FileUtil;
import com.baidu.aip.util.Base64Util;
import com.test.baiduaip.utils.AuthService;
import com.test.baiduaip.utils.HttpUtil;
import com.test.entity.Result;
import com.test.utils.DateStyle;
import com.test.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
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
    public Object general(Model model, @RequestParam("img")MultipartFile img, HttpServletRequest request) {
        // 通用识别url
        String otherHost = "https://aip.baidubce.com/rest/2.0/ocr/v1/general";
        // 获取图片原始文件名
        String originalFilename = img.getOriginalFilename();
        logger.info(originalFilename);

        String fileName = DateUtil.DateToString(new Date(), DateStyle.YYYY_MM_DD)+new Random().nextInt(100);

        // 获取上传图片的扩展名(jpg/png/...)
        String suffix = img.getOriginalFilename().split(
                "\\.")[1];

        // 图片上传的相对路径（因为相对路径放到页面上就可以显示图片）
        String path = "/upload/" ;

        // 图片上传的绝对路径
        String url = request.getSession().getServletContext().getRealPath("/")  + path;

        File dir = new File(url);
        if(!dir.exists()) {
            dir.mkdirs();
        }

        // 上传图片
        try {
            img.transferTo(new File(url+ fileName + "." + suffix));
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            byte[] imgData = FileUtil.readFileByBytes(url+ fileName + "." + suffix);
            String imgStr = Base64Util.encode(imgData);
            String params = URLEncoder.encode("image", "UTF-8") + "=" + URLEncoder.encode(imgStr, "UTF-8");
            /**
             * 线上环境access_token有过期时间， 客户端可自行缓存，过期后重新获取。
             */
            String accessToken = AuthService.getAuth();
            String result = HttpUtil.post(otherHost, accessToken, params);
            logger.info(result);
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }


        return Result.setResult(200,"转换成功","");
    }

}
