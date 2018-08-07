package com.test.wechat.service.impl;

import com.github.pagehelper.PageInfo;
import com.test.entity.User;
import com.test.entity.UserFocus;
import com.test.entity.UserLocation;
import com.test.entity.Viptxt;
import com.test.service.IUserFocusService;
import com.test.service.IUserLocationService;
import com.test.service.IUserService;
import com.test.service.IVipTxtSerivce;
import com.test.utils.TextSplitUtils;
import com.test.wechat.common.TokenTimer;
import com.test.wechat.resp.Article;
import com.test.wechat.resp.NewsMessage;
import com.test.wechat.resp.TextMessage;
import com.test.wechat.service.ICoreService;
import com.test.wechat.util.BaiduMapUtil;
import com.test.wechat.util.EmojiFilter;
import com.test.wechat.util.MessageUtil;
import com.test.wechat.util.WxApiUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class CoreServiceImpl implements ICoreService {

    private static Logger logger = LoggerFactory.getLogger(CoreServiceImpl.class);

    @Autowired
    private IUserLocationService userLocationService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserFocusService userFocusService;

    @Autowired
    private IVipTxtSerivce vipTxtSerivce;


    @Override
    public String processRequest(HttpServletRequest request) {

        logger.info("已经到这里了，这里要进行判断用户输入的东西了");
        String type = "";
        String fromUserName = "";
        String searchName = "";
        String respMessage = null;
        try {
            Date addTime = new Date();
            String respContent = "请求处理异常，请稍候尝试！";
            type = "请求处理异常";
            Map<String, String> requestMap = MessageUtil.parseXml(request);
            fromUserName = requestMap.get("FromUserName");
            String toUserName = requestMap.get("ToUserName");
            String msgType = requestMap.get("MsgType");
            TextMessage textMessage = new TextMessage();
            textMessage.setToUserName(fromUserName);
            textMessage.setFromUserName(toUserName);
            textMessage.setCreateTime(new Date().getTime());
            textMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_TEXT);
            NewsMessage newsMessage = new NewsMessage();
            newsMessage.setToUserName(fromUserName);
            newsMessage.setFromUserName(toUserName);
            newsMessage.setCreateTime(new Date().getTime());
            newsMessage.setMsgType(MessageUtil.RESP_MESSAGE_TYPE_NEWS);
            List<Article> articleList = new ArrayList<Article>();
            String content_ = requestMap.get("Content");
            if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_TEXT)) {
                if (StringUtils.isNotBlank(content_)) {
                    Viptxt txt = new Viptxt();
                    txt.setTitle(TextSplitUtils.getMaxText(content_));
                    PageInfo<Viptxt> txtList = vipTxtSerivce.findPage(1,10,txt);
                    for (Viptxt viptxt: txtList.getList()) {
                        setArticleInfo(articleList,viptxt.getTitle(),"这里是简介"+viptxt.getTitle(),viptxt.getImgurl(),viptxt.getAurl());
                    }
                } else {
                    setArticleInfo(articleList,"对不起","请输入文字","http://uploads.xuexila.com/allimg/1602/681-16021FS9153C.png","");

                }
                if (articleList.size()<=0) {
                    //单图文
                    setArticleInfo(articleList,"对不起","您检索的问题我们暂时没有提供","http://uploads.xuexila.com/allimg/1602/681-16021FS9153C.png","");
                }

                newsMessage.setArticleCount(articleList.size());
                newsMessage.setArticles(articleList);
                respMessage = MessageUtil.messageToXml(newsMessage);
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_IMAGE)) {
                respContent = "您发送的是图片消息！";
                textMessage.setContent(respContent);
                respMessage = MessageUtil.messageToXml(textMessage);
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LOCATION)) {
                String lng = requestMap.get("Location_Y");
                String lat = requestMap.get("Location_X");
                String bd09Lng = null;
                String bd09Lat = null;
                UserLocation userLocation = BaiduMapUtil.convertCoord(lng, lat);
                if (null != userLocation) {
                    bd09Lng = userLocation.getLocationBdX();
                    bd09Lat = userLocation.getLocationBdY();
                }
                UserLocation userLocation2 = new UserLocation(null, null, fromUserName, lng, lat, bd09Lng, bd09Lat);
                int count = userLocationService.add(userLocation2);
                StringBuffer buffer = new StringBuffer();
                buffer.append("[愉快]").append("成功接收您的位置！").append("\n\n");
                buffer.append("您可以输入搜索关键词获取周边信息了，例如：").append("\n");
                buffer.append("        附近ATM").append("\n");
                buffer.append("        附近KTV").append("\n");
                buffer.append("        附近厕所").append("\n");
                buffer.append("必须以“附近”两个字开头！");
                respContent = buffer.toString();
                textMessage.setContent(respContent);
                respMessage = MessageUtil.messageToXml(textMessage);
                type = "地理位置";
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_LINK)) {
                respContent = "您发送的是链接消息！";
                textMessage.setContent(respContent);
                respMessage = MessageUtil.messageToXml(textMessage);

            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_VOICE)) {
                respContent = "您发送的是音频消息！";
                textMessage.setContent(respContent);
                respMessage = MessageUtil.messageToXml(textMessage);
            } else if (msgType.equals(MessageUtil.REQ_MESSAGE_TYPE_EVENT)) {
                String eventType = requestMap.get("Event");
                if (eventType.equals(MessageUtil.EVENT_TYPE_SUBSCRIBE)) {
                    User wxUser = userService.findByOpenId(fromUserName);
                    if (wxUser == null) {
                        String accessToken = TokenTimer.getAccessToken();
                        wxUser = WxApiUtil.getUserInfo(accessToken, fromUserName);
                        wxUser.setCreateTime(addTime);
                        wxUser.setStatus(1);
                        //设置昵称
                        String nickName = EmojiFilter.filterEmoji(wxUser.getNickname());
                        wxUser.setNickname(nickName);
                        userService.add(wxUser);

                    }
                    String userId = wxUser.getUid();
                    UserFocus wxUserFocus = new UserFocus(null, userId, addTime, 2);
                    userFocusService.add(wxUserFocus);
                    newsMessage = getFirstArticle(newsMessage, articleList, "/");
                    respMessage = MessageUtil.messageToXml(newsMessage);
                    type = "微信关注";
                }
                // 取消关注
                else if (eventType.equals(MessageUtil.EVENT_TYPE_UNSUBSCRIBE)) {
                    User wxUser = userService.findByOpenId(fromUserName);

                    wxUser.setStatus(0);
                    userService.add(wxUser);
                    String userId = wxUser.getUid();
                    UserFocus wxUserFocus = new UserFocus(null, userId, addTime, 0);
                    userFocusService.add(wxUserFocus);
                    type = "取消关注";
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_SCAN)) {
                } else if (eventType.equals(MessageUtil.EVENT_TYPE_LOCATION)) {
                    String latitude = requestMap.get("Latitude");
                    String longitude = requestMap.get("Longitude");
                    String bd09Lng = null;
                    String bd09Lat = null;
                    UserLocation userLocation = BaiduMapUtil.convertCoord(longitude, latitude);
                    if (null != userLocation) {
                        bd09Lng = userLocation.getLocationBdX();
                        bd09Lat = userLocation.getLocationBdY();
                    }
                    UserLocation userLocation2 = new UserLocation(null, null, fromUserName, longitude, latitude, bd09Lng, bd09Lat);
                    int count = userLocationService.add(userLocation2);
                    respContent = "[愉快]成功接收您的位置！";
                    textMessage.setContent(respContent);
                    respMessage = MessageUtil.messageToXml(textMessage);
                }
                // 自定义菜单
                else if (eventType.equals(MessageUtil.EVENT_TYPE_CLICK)) {
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        String url = request.getRequestURL().toString();

        logger.info("searchname--->"+searchName);
        logger.info("fromUser--->"+fromUserName);
        logger.info("type--->"+type);
        logger.info("url--->"+url);
        logger.info("最后结束");

        return respMessage;
    }

    /**
     * 设置范文内容
     * @param articleList
     * @param title
     * @param description
     * @param imgurl
     * @param url
     */
    private void setArticleInfo(List<Article> articleList,String title,String description,String imgurl,String url) {
        //单图文
        Article article = new Article();
        article.setTitle(title);
        article.setDescription(description);
        article.setPicUrl(imgurl);
        article.setUrl(url==null?"":url);
        articleList.add(article);
    }

    private NewsMessage getFirstArticle(NewsMessage newsMessage, List<Article> articleList, String WX_URL) {
        Article article1 = new Article();
        article1.setTitle("艳辉网，分享福利的网站");
        article1.setDescription("");
        //article.setUrl(WX_URL+"wx/index.html");
        article1.setPicUrl(WX_URL + "resource/img/blog/tuwen/wx-first.jpg");
        article1.setUrl(WX_URL + "wx/index.html");
        Article article2 = new Article();
        article2.setTitle("一键加群");
        article2.setDescription("");
        article2.setPicUrl(WX_URL + "resource/img/blog/tuwen/wx1我.jpg");
        article2.setUrl("https://jq.qq.com/?_wv=1027&k=5NtFofZ");
        Article article3 = new Article();
        article3.setTitle("关注博客");
        article3.setDescription("");
        article3.setPicUrl(WX_URL + "resource/img/blog/tuwen/wx2要.jpg");
        article3.setUrl("http://blog.csdn.net/sinat_15153911");

        Article article4 = new Article();
        article4.setTitle("关于我们");
        article4.setDescription("");
        article4.setPicUrl(WX_URL + "resource/img/blog/tuwen/wx3福.jpg");
        article4.setUrl("https://user.qzone.qq.com/490647751/blog/1511836766");

        Article article5 = new Article();
        article5.setTitle("福利活动");
        article5.setDescription("暂未开通");
        article5.setPicUrl(WX_URL + "resource/img/blog/tuwen/wx4利.jpg");
        article5.setUrl("http://blog.csdn.net/sinat_15153911");
        articleList.add(article1);
        articleList.add(article2);
        articleList.add(article3);
        articleList.add(article4);
        articleList.add(article5);
        newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);
        return newsMessage;
    }
}
