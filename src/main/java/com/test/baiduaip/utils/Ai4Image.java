package com.test.baiduaip.utils;

import cn.xsshome.taip.util.FileUtil;
import com.baidu.aip.util.Base64Util;
import com.test.utils.DateStyle;
import com.test.utils.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Random;

public class Ai4Image {

    private static Logger logger = LoggerFactory.getLogger(Ai4Image.class);

    private boolean myResult;
    private MultipartFile img;
    private HttpServletRequest request;
    private String otherHost;
    private String result;

    public Ai4Image(MultipartFile img, HttpServletRequest request, String otherHost) {
        this.img = img;
        this.request = request;
        this.otherHost = otherHost;
    }

    public boolean is() {
        return myResult;
    }

    public String getResult() {
        return result;
    }

    public Ai4Image invoke() {
        // 获取图片原始文件名
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
            logger.info(url+ fileName + "." + suffix);
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
            result = HttpUtil.post(otherHost, accessToken, params);
            logger.info(result);
            myResult = true;
            return this;
        } catch (Exception e) {
            e.printStackTrace();
        }
        myResult = false;
        return this;
    }
}
