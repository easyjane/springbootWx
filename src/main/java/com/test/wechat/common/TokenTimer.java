package com.test.wechat.common;

import com.test.wechat.entity.WeixinTokenConstant;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;


/**
 * 全局定时器
 */
@Component
public class TokenTimer {

    private static String accessToken;

    private static final Logger logger = LoggerFactory.getLogger(TokenTimer.class);

    // 获取access_token的接口地址（GET） 限200（次/天）
    private final static String access_token_url = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET";


    @Autowired
    private WeixinTokenConstant weixinTokenConstant;

   /* @Scheduled(fixedRate=5000)
    public void testTasks() {

        logger.info("每20秒执行一次"+weixinTokenConstant.getToken());
    }*/

    @Scheduled(fixedDelay=7180000)
    public void run() {
        logger.info("==============开始获取access_token===============");
        String appId= weixinTokenConstant.getAppid();
        String secret = weixinTokenConstant.getAppsecret();
        String url = access_token_url.replace("APPID", appId).replace("APPSECRET", secret);
        try {
            URL urlGet = new URL(url);
            HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
            http.setRequestMethod("GET"); // 必须是get方式请求
            http.setRequestProperty("Content-Type","application/x-www-form-urlencoded");
            http.setDoOutput(true);
            http.setDoInput(true);
            http.connect();
            InputStream is = http.getInputStream();
            int size = is.available();
            byte[] jsonBytes = new byte[size];
            is.read(jsonBytes);
            String message = new String(jsonBytes, "UTF-8");
            JSONObject demoJson = JSONObject.fromObject(message);
            //System.out.println("JSON字符串："+demoJson);
            setAccessToken(demoJson.getString("access_token"));
            is.close();
            logger.info("==============结束获取access_token===============");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }



    }


    public static String getAccessToken() {
        return accessToken;
    }

    public static void setAccessToken(String accessToken) {
        TokenTimer.accessToken = accessToken;
    }
}
