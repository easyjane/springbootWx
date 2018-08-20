package com.test.wechat.service.impl;

import cn.xsshome.taip.nlp.TAipNlp;
import com.github.pagehelper.PageInfo;
import com.test.entity.*;
import com.test.service.*;
import com.test.utils.DateUtil;
import com.test.utils.JsonUtils;
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
import sun.security.provider.MD5;

import javax.servlet.http.HttpServletRequest;
import java.util.*;

@Service
public class CoreServiceImpl implements ICoreService {

    private static Logger logger = LoggerFactory.getLogger(CoreServiceImpl.class);

    private static final String TENCENT_AI_APP_ID = "2107744650";
    private static final String TENCENT_AI_APP_KEY = "Ex9D2TOvJZNGVdsG";

    @Autowired
    private IUserLocationService userLocationService;

    @Autowired
    private IUserService userService;

    @Autowired
    private IUserFocusService userFocusService;

    @Autowired
    private IVipTxtService vipTxtService;

    @Autowired
    private IUserTextService userTextService;

    @Autowired
    private IIndexArticleService indexArticleService;




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

            Map<String, String> requestMap = MessageUtil.parseXml(request);
            fromUserName = requestMap.get("FromUserName");
            String toUserName = requestMap.get("ToUserName");
            String msgType = requestMap.get("MsgType");
            type = requestMap.get("MsgType");
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

                    // 当前用户进入闲聊的记录
                    UserText userText = new UserText();
                    userText.setStatus(2);
                    userText.setOpenId(fromUserName);
                    PageInfo<UserText> utPage = userTextService.findPage(0, 10, userText);
                    userText = utPage.getList().size() > 0 ? utPage.getList().get(0) : null;

                    // 退出闲聊的记录
                    UserText chatOutUt = new UserText();
                    chatOutUt.setStatus(3);
                    chatOutUt.setOpenId(fromUserName);
                    PageInfo<UserText> charOutUtPage = userTextService.findPage(0, 10, chatOutUt);
                    chatOutUt = charOutUtPage.getList().size() > 0 ? charOutUtPage.getList().get(0) : null;

                    Date now = new Date();
                    logger.info("userText"+userText);
                    logger.info("chatOutUt"+chatOutUt);
                    if (content_.equals("那年今日") || content_.equals("那年今天") || content_.equals("历史今天") || content_.equals("今天历史")) {



                    } else if (content_.contains("进入闲聊") || content_.contains("进入聊天") || "闲聊模式".equals(content_) || "闲聊".equals(content_) || "聊天".equals(content_) || "聊天模式".equals(content_)) {
                        logger.info("进入闲聊");
                        return out30Min(fromUserName, respMessage, msgType, textMessage, content_, 2, "你好呀，现在可以和我聊天了哦o(∩_∩)o 哈哈");
                    } else if (content_.contains("退出") || content_.contains("exit")) {
                        logger.info("推出闲聊");
                        return out30Min(fromUserName, respMessage, msgType, textMessage, content_, 3, "您已经退出闲聊模式");
                    } else if (userText != null && userText.getCreateTime().before(DateUtil.addMinute(now, -30)) && ( chatOutUt == null || userText.getCreateTime().after(chatOutUt.getCreateTime()))) {

                        logger.info("时间超过30分钟，退出闲聊");
                        return out30Min(fromUserName, respMessage, msgType, textMessage, content_, 3, "您已经超过了30分钟，自动退出闲聊模式");

                    } else if (userText != null && userText.getCreateTime().before(DateUtil.addMinute(now, -30)) && ( chatOutUt == null || userText.getCreateTime().before(chatOutUt.getCreateTime()))) {
                        logger.info("时间超过30分钟，退出闲聊");
                        return out30Min(fromUserName, respMessage, msgType, textMessage, content_, 3, "您已经超过了30分钟，自动退出闲聊模式");

                    } else if (userText != null && userText.getCreateTime().after(DateUtil.addMinute(now, -30)) && ( chatOutUt == null || userText.getCreateTime().after(chatOutUt.getCreateTime()))) {
                        logger.info("闲聊中");
//                        if (userText.getCreateTime().before(chatOutUt.getCreateTime())) {
//                            return out30Min(fromUserName, respMessage, msgType, textMessage, content_, 3, "您已经超过了30分钟，自动退出闲聊模式");
//                        }

                        TAipNlp aipNlp = new TAipNlp(TENCENT_AI_APP_ID, TENCENT_AI_APP_KEY);
                        String session = new Date().getTime() / 1000 + "";

                        String result = aipNlp.nlpTextchat(session,content_);
                        userText.setCreateTime(now);
                        userTextService.add(userText);

                        Map paraMap = new HashMap();
                        paraMap.put("data",Map.class);
                        Map resultMap = (Map) JsonUtils.str2Obj(result,Map.class,paraMap);
                        if (!resultMap.get("ret").toString().equals("0")) {
                            respContent = "你说什么啊？";
                        } else {
                            Map dataMap = (Map) resultMap.get("data");
                            respContent = dataMap.get("answer").toString();
                        }

                        textMessage.setContent(respContent);
                        respMessage = MessageUtil.messageToXml(textMessage);
                        return respMessage;
                    } else if (content_.contains("菜谱") || content_.contains("吃饭") || content_.contains("饿了") || content_.contains("好饿")) {

                    } else{
                        logger.info("判断失败了");
                        UserText ut = new UserText();
                        ut.setContent(content_);
                        ut.setCreateTime(new Date());
                        ut.setOpenId(fromUserName);
                        ut.setType(msgType);
                        ut.setStatus(1);
                        userTextService.add(ut);
                        Viptxt txt = new Viptxt();
                        txt.setTitle(TextSplitUtils.getMaxText(content_));
                        PageInfo<Viptxt> txtList = vipTxtService.findPage(1, 10, txt);
                        for (Viptxt viptxt : txtList.getList()) {
                            setArticleInfo(articleList, viptxt.getTitle(), "这里是简介" + viptxt.getTitle(), viptxt.getImgurl(), viptxt.getAurl());
                        }
                    }


                }

                if (articleList.size() <= 0) {
                    //单图文
                    setArticleInfo(articleList, "对不起", "您检索的问题我们暂时没有提供", "http://c.hiphotos.baidu.com/image/pic/item/f9198618367adab4b025268587d4b31c8601e47b.jpg", "");
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


        logger.info("searchname--->" + searchName);
        logger.info("fromUser--->" + fromUserName);
        logger.info("type--->" + type);
        logger.info("url--->" + url);
        logger.info("最后结束");

        return respMessage;
    }

    private String out30Min(String fromUserName, String respMessage, String msgType, TextMessage textMessage, String content_, int i, String s) {
        String respContent;
        UserText ut = new UserText();
        ut.setContent(content_);
        ut.setCreateTime(new Date());
        ut.setOpenId(fromUserName);
        ut.setType(msgType);
        ut.setStatus(i);
        userTextService.add(ut);
        respContent = s;
        textMessage.setContent(respContent);
        respMessage = MessageUtil.messageToXml(textMessage);
        return respMessage;
    }

    /**
     * 设置范文内容
     *
     * @param articleList
     * @param title
     * @param description
     * @param imgurl
     * @param url
     */
    private void setArticleInfo(List<Article> articleList, String title, String description, String imgurl, String url) {
        //单图文
        Article article = new Article();
        article.setTitle(title);
        article.setDescription(description);
        article.setPicUrl(imgurl);
        article.setUrl(url == null ? "" : url);
        articleList.add(article);
    }

    /**
     * 获取关注公众号之后的消息
     * @param newsMessage
     * @param articleList
     * @param WX_URL
     * @return
     */
    private NewsMessage getFirstArticle(NewsMessage newsMessage, List<Article> articleList, String WX_URL) {

        IndexArticle indexArticle = new IndexArticle();
        indexArticle.setStatus(2);
        PageInfo<IndexArticle> indexArticlePageInfo = indexArticleService.findPage(1,10,indexArticle);

        Article article = new Article();
        for (IndexArticle ia: indexArticlePageInfo.getList()
             ) {
            article.setTitle(ia.getTitle());
            article.setDescription(ia.getDescription());
            //article.setUrl(WX_URL+"wx/index.html");
            article.setPicUrl(ia.getPicUrl());
            article.setUrl(ia.getUrl());
            articleList.add(article);
        }

        newsMessage.setArticleCount(articleList.size());
        newsMessage.setArticles(articleList);
        return newsMessage;
    }
}
