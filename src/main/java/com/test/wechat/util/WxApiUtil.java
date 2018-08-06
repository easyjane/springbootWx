package com.test.wechat.util;

import com.test.entity.User;
import com.test.wechat.resp.Menu;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

public class WxApiUtil {

    private static Logger logger = LoggerFactory.getLogger(WxApiUtil.class);

    public static final String WX_TOKEN_API = "https://api.weixin.qq.com/cgi-bin/token?grant_type=GRANT_TYPE&appid=APPID&secret=SECRET";

    public static final String WX_OAUTH2_AUTHORIZE = "https://open.weixin.qq.com/connect/oauth2/authorize?"
            + "appid=APPID&redirect_uri=REDIRECT_URI&response_type=code&scope=snsapi_userinfo&state=test#wechat_redirect";

    public static final String WX_OAUTH2_TOKEN = "https://api.weixin.qq.com/sns/oauth2/access_token?"
            + "appid=APPID&secret=SECRET&code=CODE&grant_type=authorization_code";

    public static final String WX_GET_USERINFO = "https://api.weixin.qq.com/sns/userinfo?"
            + "lang=zh_CN&access_token=ACCESS_TOKEN&openid=OPENID";

    public static final String WX_GET_USERINFO2 = "https://api.weixin.qq.com/cgi-bin/user/info?"
            + "access_token=ACCESS_TOKEN&openid=OPENID";

    public static final String MENU_CREATE_URL = "https://api.weixin.qq.com/cgi-bin/menu/create?access_token=ACCESS_TOKEN";

    /**
     * 得到微信用户信息
     * @param accessToken
     * @param openId
     * @return
     * @throws Exception
     */
    public static User getUserInfo(String accessToken, String openId) throws Exception {
        User weixinUserInfo = null;
        // 拼接请求地址
        String requestUrl = WX_GET_USERINFO2.replace("ACCESS_TOKEN", accessToken).replace("OPENID", openId);
        // 获取用户信息
        Map map = (Map) JSONObject.toBean(JSONObject.fromObject(HttpUtils.doHttpGet(requestUrl)), Map.class);
        if (null != map) {
            try {
                weixinUserInfo = new User();
                // 用户的标识
                weixinUserInfo.setOpenId((String)map.get("openid"));
                // 昵称
                weixinUserInfo.setNickname((String)map.get("nickname"));
                // 用户的性别（1是男性，2是女性，0是未知）
                weixinUserInfo.setSex((Integer)map.get("sex"));
                // 用户所在国家
                weixinUserInfo.setCountry((String)map.get("country"));
                // 用户所在省份
                weixinUserInfo.setProvince((String)map.get("province"));
                // 用户所在城市
                weixinUserInfo.setCity((String)map.get("city"));
                // 用户头像
                weixinUserInfo.setImg((String)map.get("headimgurl"));
                // 用户语言
                weixinUserInfo.setLanguage((String)map.get("language"));
            } catch (Exception e) {
                int errorCode = (Integer) map.get("errcode");
                String errorMsg = (String) map.get("errmsg");
                logger.info("获取用户信息失败 errcode:{} errmsg:{}", errorCode,
                        errorMsg);
            }
        }
        return weixinUserInfo;
    }




    public static void main(String[] args) {
        String url = WX_TOKEN_API.replace("GRANT_TYPE", "a").replace("APPID", "").replace("SECRET", "a");
        System.out.println(url);

        int result = createMenu(MenuUtil.getMenu(),"12_ZOtKmOnaqdWemeMG2ftqq49pXADr6t5rqAcgha20asYihOBvl_DR0RTKzbrhLef3q6yp4fnLk1qBmWZPHYaSS2kAJ0sng-G5NU3imppNj3p-_-YTA0AEV3AyvIVvhFg-DpdoDtDbBW4A29BgOAKeACAGSC");
        logger.info(result+"--------");
    }




    /**
     * 创建菜单
     *
     * @param menu 菜单实例
     * @param accessToken 有效的access_token
     * @return 0 表示成功，其他值表示失败
     */
    public static int createMenu(Menu menu, String accessToken) {
        int result = 0;

        // 拼装创建菜单的url
        String url = MENU_CREATE_URL.replace("ACCESS_TOKEN", accessToken);
        // 将菜单对象转换成json 字符串
        String jsonMenu = JSONObject.fromObject(menu).toString();
        // 调用接口创建菜单
        JSONObject jsonObject = httpsRequest(url, "POST", jsonMenu);

        if (null != jsonObject) {
            if (0 != jsonObject.getInt("errcode")) {
                result = jsonObject.getInt("errcode");
                logger.error("创建菜单失败errcode:{} errmsg:{}", jsonObject.getInt("errcode"), jsonObject.getString("errmsg"));
            }
        }

        return result;
    }


    /**
     * 发起https 请求并获取结果
     *
     * @param requestUrl 请求地址
     * @param requestMethod 请求方式（GET、POST）
     * @param outputStr 提交的数据
     * @return JSONObject(通过JSONObject.get(key)的方式获取json 对象的属性值)
     */
    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) {
        JSONObject jsonObject = null;
        StringBuffer buffer = new StringBuffer();
        try {
            // 创建SSLContext 对象，并使用我们指定的信任管理器初始化
//		            TrustManager[] tm = { new MyX509TrustManager() };
//		            SSLContext sslContext = SSLContext.getInstance("SSL", "SunJSSE");
//		            sslContext.init(null, tm, new java.security.SecureRandom());
//		            // 从上述SSLContext 对象中得到SSLSocketFactory 对象
//		            SSLSocketFactory ssf = sslContext.getSocketFactory();
//
            URL url = new URL(requestUrl);
//		            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url.openConnection();
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
//		            httpUrlConn.setSSLSocketFactory(ssf);

            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            // 设置请求方式（GET/POST）
            httpUrlConn.setRequestMethod(requestMethod);

            if ("GET".equalsIgnoreCase(requestMethod))
                httpUrlConn.connect();

            // 当有数据需要提交时
            if (null != outputStr) {
                OutputStream outputStream = httpUrlConn.getOutputStream();
                // 注意编码格式，防止中文乱码
                outputStream.write(outputStr.getBytes("UTF-8"));
                outputStream.close();
            }

            // 将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "utf-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            // 释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
            jsonObject = JSONObject.fromObject(buffer.toString());
        } catch (ConnectException ce) {
            System.out.println("Weixin server connection timed out.");
        } catch (Exception e) {
            System.out.println("https request error:{"+e+"}");
        }
        return jsonObject;
    }
}
