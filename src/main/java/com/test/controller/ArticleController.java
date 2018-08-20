package com.test.controller;

import cn.xsshome.taip.ptu.TAipPtu;
import com.github.pagehelper.PageInfo;
import com.test.entity.Article;
import com.test.entity.Result;
import com.test.service.IArticleService;
import com.test.utils.DateStyle;
import com.test.utils.DateUtil;
import com.test.utils.JsonUtils;
import com.test.wechat.common.TokenTimer;
import com.test.wechat.entity.WeixinTokenConstant;
import com.test.wechat.util.HttpUtils;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
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
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.*;

@Controller
@RequestMapping("/article")
public class ArticleController {

    private static Logger logger = LoggerFactory.getLogger(ArticleController.class);

    private static String url = "http://v.juhe.cn/todayOnhistory/queryEvent.php?key=yourkey&date=setdate";
    private static String key = "8f87d16fdd8904e63f1ff840f90b3041";


    private static final String APP_ID = "2107744650";
    private static final String APP_KEY = "Ex9D2TOvJZNGVdsG";

    @Autowired
    private IArticleService articleService;

    @RequestMapping("/index")
    @ResponseBody
    public Object index(Model model) {
        Map mapResult = new HashMap();

        int count = 0;
        Date now = new Date();
        Article article = new Article();
        for (int i = 1; i < 33; i++) {
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
            count = setArticleInfo(count, now, list);

            logger.info(mm + "/" + dd);
        }

        for (int i = 0; i < 63; i++) {
            Date otherDate = DateUtil.addDay(now, i);
            int mm = otherDate.getMonth() + 1;
            int dd = otherDate.getDate();
            String thisUrl = url.replace("yourkey", key).replace("setdate", mm + "/" + dd);

            String result = HttpUtils.getHttpData(thisUrl);

            Map paraMap = new HashMap();
            paraMap.put("result", Map.class);
            Map map = (Map) JsonUtils.str2Obj(result, Map.class, paraMap);

            logger.info(map.get("reason").toString());
            List<Map> list = (List<Map>) map.get("result");
            count = setArticleInfo(count, now, list);

            logger.info(mm + "/" + dd);
        }

        mapResult.put("result",true);
        mapResult.put("count",count);

        return mapResult;
    }

    private int setArticleInfo(int count, Date now, List<Map> list) {
        Article article;
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
        return count;
    }

    /**
     * 分页加载数据
     * @param pageNo
     * @param pageSize
     * @param date
     * @param addDay
     * @return
     */
    @RequestMapping("/query")
    @ResponseBody
    public Object query(int pageNo,int pageSize,String date,Integer addDay) {

        if (!StringUtils.contains(date,"/")) {
            return Result.setResult(500,"时间参数不正确",null);
        }

        String[] mmdd = date.split("/");
        try {
            int mm = Integer.parseInt(mmdd[0]);
            int dd = Integer.parseInt(mmdd[1]);
            Date now = new Date();
            int year = DateUtil.getYear(now);

            String theDate = DateUtil.addDay(year+"-"+mm+"-"+dd ,addDay);
            mm = DateUtil.getMonth(theDate);
            dd = DateUtil.getDay(theDate);
            Article article = new Article();
            article.setDate(mm+"/"+dd);
            PageInfo arPi = articleService.findPage(pageNo,pageSize,article);
            return Result.setResult(200,"成功了",arPi);


        } catch (Exception e) {
            logger.error(e.getMessage());
            return Result.setResult(500,"时间参数不正确",null);
        }




    }

    @RequestMapping("/uploadImg")
    @ResponseBody
    public Object uploadImg(Model model, MultipartFile img, HttpServletRequest request) {

        // 获取图片原始文件名
        String originalFilename = img.getOriginalFilename();
        logger.info(originalFilename);

        String fileName = DateUtil.DateToString(new Date(),DateStyle.YYYY_MM_DD)+new Random().nextInt(100);

        // 获取上传图片的扩展名(jpg/png/...)
        String suffix = img.getOriginalFilename().split(
                "\\.")[1];

        // 图片上传的相对路径（因为相对路径放到页面上就可以显示图片）
        String path = "/upload/" ;

        // 图片上传的绝对路径
        String url = "d://" + path;

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

//        // 将相对路径写回（json格式）
//        JSONObject jsonObject = new JSONObject();
//        // 将图片上传到本地
//        jsonObject.put("path", path);

        TAipPtu aipPtu = new TAipPtu(APP_ID, APP_KEY);
        String result = "";
        try {
            result = aipPtu.faceAge(path+ fileName + "." + suffix);

        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info(result);

//        Map map = new HashMap();
//        map.put("data",Map.class);
//        Map imgResult = (Map) JsonUtils.str2Obj(result,Map.class,map);


        return result;
    }
}
