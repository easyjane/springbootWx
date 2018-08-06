package com.test.wechat.interceptor;

import com.test.constant.Constant;
import com.test.entity.User;
import com.test.service.IUserService;
import com.test.wechat.entity.WeixinTokenConstant;
import com.test.wechat.util.EmojiFilter;
import com.test.wechat.util.HttpUtils;
import com.test.wechat.util.WxApiUtil;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

public class WxLoginInterceptor implements HandlerInterceptor {

    Logger logger = LoggerFactory.getLogger(WxLoginInterceptor.class);

    @Autowired
    private WeixinTokenConstant weixinTokenConstant;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        String appid = weixinTokenConstant.getAppid();
        String appSecret = weixinTokenConstant.getAppsecret();

        User user = (User) session.getAttribute(Constant.WX_LOGIN_SESSION);
        if (user == null) {
            String queryStr = request.getQueryString();
            String param = "";
            if (StringUtils.isNotBlank(queryStr)) {
                param = "?"+queryStr;
            }
            String url = request.getRequestURL().toString()+param;
            if (StringUtils.isEmpty(request.getParameter("code"))) {
                if (StringUtils.isEmpty(request.getParameter("state"))){
                    String uri = URLEncoder.encode(url, "UTF-8");
                    String redirect_uri = WxApiUtil.WX_OAUTH2_AUTHORIZE.replace("APPID", appid).replace("REDIRECT_URI", uri);
                    response.sendRedirect(redirect_uri);
                    return false;
                }else {
                    logger.info("获取到state，但code没有，表示用户拒绝授权了，返回错误的提示；");
                    return false;
                }
            }else {
                try {
                    String code = request.getParameter("code");
                    String get_token_url = WxApiUtil.WX_OAUTH2_TOKEN.replace("APPID", appid).replace("SECRET", appSecret).replace("CODE", code);
                    String accJson = HttpUtils.doHttpGet(get_token_url);
                    Map accMap = (Map) JSONObject.toBean(JSONObject.fromObject(accJson), Map.class);
                    String openid = (String) accMap.get("openid");
                    String access_token = (String) accMap.get("access_token");
                    WebApplicationContext webContext = ContextLoader.getCurrentWebApplicationContext();
                    IUserService userService = (IUserService) webContext.getBean("IUserService");
                    user = userService.findByOpenId(openid);
                    if(user == null){
                        String get_user_url = WxApiUtil.WX_GET_USERINFO.replace("ACCESS_TOKEN", access_token).replace("OPENID", openid);
                        String userJson = HttpUtils.doHttpGet(get_user_url);
                        Map userMap = (Map) JSONObject.toBean(JSONObject.fromObject(userJson),Map.class);
                        //获取用户信息
                        String openId = (String) userMap.get("openid");
                        String nickname = (String) userMap.get("nickname");
                        Integer sex = (Integer) userMap.get("sex");
                        String province = (String) userMap.get("province");
                        String city = (String) userMap.get("city");
                        String country = (String) userMap.get("country");
                        String language = (String) userMap.get("language");
                        String headImgUrl = (String)userMap.get("headimgurl");
                        nickname = EmojiFilter.filterEmoji(nickname);
                        user = new User(null,nickname, openId, new Date(), headImgUrl,1,nickname, sex, country, province, city, language
                                );
                        userService.add(user);
                    }
                    session.setAttribute(Constant.WX_LOGIN_SESSION,user);
                } catch (IOException e) {
                    logger.error("从微信获取用户信息失败" + e.getMessage(), e);
                }
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
