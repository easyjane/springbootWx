package com.test.controller;

import com.test.entity.Article;
import com.test.service.IArticleService;
import com.test.utils.DateUtil;
import com.test.utils.JsonUtils;
import com.test.wechat.common.TokenTimer;
import com.test.wechat.entity.WeixinTokenConstant;
import com.test.wechat.util.HttpUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private static Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private static String url = "http://v.juhe.cn/todayOnhistory/queryEvent.php?key=yourkey&date=setdate";
    private static String key = "8f87d16fdd8904e63f1ff840f90b3041";

    @Autowired
    private IArticleService articleService;

    @RequestMapping("/index")
    @ResponseBody
    public Object index(Model model) {
        Map mapResult = new HashMap();

        int count = 0;
        Date now = new Date();
        Article article = new Article();
        for (int i = 1; i < 93; i++) {
            Date otherDate = DateUtil.addDay(now, -i);
            int mm = otherDate.getMonth() + 1;
            int dd = otherDate.getDate();
            String thisUrl = url.replace("yourkey", key).replace("setdate", mm + "/" + dd);

            String result = HttpUtils.getHttpData(thisUrl);

            Map paraMap = new HashMap();
            paraMap.put("result", Map.class);
            Map map = (Map) JsonUtils.str2Obj(result, Map.class, paraMap);

            logger.info(map.get("reason").toString());
            List<Map> list = (List<Map>) map.get("result");
            for (Map mmap :
                    list) {
                count++;
                article = new Article();
                article.setDate(mmap.get("day").toString());
                article.setCreateTime(now);
                article.setContent(mmap.get("title").toString());
                article.setDateChina("");
                article.setIntro(mmap.get("date").toString());
                article.setStatus(1);
                articleService.add(article);
                logger.info(mmap.get("title").toString());
            }

            logger.info(mm + "/" + dd);
        }

        mapResult.put("result",true);
        mapResult.put("count",count);

        return mapResult;
    }
}
