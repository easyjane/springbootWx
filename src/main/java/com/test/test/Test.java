package com.test.test;

import cn.xsshome.taip.http.TAipRequest;
import cn.xsshome.taip.nlp.TAipNlp;
import cn.xsshome.taip.ptu.TAipPtu;
import cn.xsshome.taip.sign.TencentAISignSort;
import cn.xsshome.taip.util.Base64Util;
import cn.xsshome.taip.util.RandomNonceStrUtil;
import com.test.entity.Result;
import com.test.utils.DateUtil;
import com.test.utils.JsonUtils;
import com.test.utils.TextSplitUtils;
import com.test.utils.bean.JsonResult;
import com.test.utils.bean.Words;
import com.test.wechat.util.HttpUtils;
import com.test.wechat.util.SignUtil;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.*;

public class Test {

    private static Logger logger = LoggerFactory.getLogger(Test.class);

    private static String url = "http://v.juhe.cn/todayOnhistory/queryEvent.php?key=yourkey&date=setdate";
    private static String key = "8f87d16fdd8904e63f1ff840f90b3041";

    private static final String APP_ID = "2107744650";
    private static final String APP_KEY = "Ex9D2TOvJZNGVdsG";

    public static void main(String[] args) {

        tencentAiTestByMyself("D:\\workspace\\lanbitou\\img\\0b55b319ebc4b74560d94cd3c3fc1e178b8215a1.jpg");


    }

    public static void testDate() {
        String[] mmdd = "2/28".split("/");
        try {
            int mm = Integer.parseInt(mmdd[0]);
            int dd = Integer.parseInt(mmdd[1]);
            Date now = new Date();
            int year = DateUtil.getYear(now);

            String theDate = DateUtil.addDay(year+"-"+mm+"-"+dd ,1);

            logger.info(theDate);
            logger.info(DateUtil.getMonth(theDate)+"");


        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    public static void tencentAiTestByMyself(String imgUrl) {

        String apiUrl = "https://api.ai.qq.com/fcgi-bin/ptu/ptu_faceage";

        TAipPtu aipPtu = new TAipPtu(APP_ID, APP_KEY);
        String result = "";
        try {
            result = aipPtu.faceAge(imgUrl);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info(result);
    }

    /**
     * 腾讯AI测试
     */
    public static void tencentAiTest() {
        TAipNlp aipNlp = new TAipNlp(APP_ID, APP_KEY);
        String session = new Date().getTime() / 1000 + "";
        String filePath = "G:/tt.jpg";
        String filePath2 = "G:/16.pcm";

        try {
//        String result = aipNlp.nlpWordseg("讲一个笑话吧");//分词
//        String result = aipNlp.nlpWordpos("讲一个笑话吧");//词性标注
//        String result = aipNlp.nlpWordner("最近张学友在深圳开了一场演唱会");//专有名词
//        String result = aipNlp.nlpWordsyn("今天的天气怎么样");//同义词
//        String result = aipNlp.nlpWordcom("今天深圳的天气怎么样？明天呢");//意图成分
//        String result = aipNlp.nlpTextpolar("小帅很帅");//情感分析
        String result = aipNlp.nlpTextchat(session,"开始聊天吧");//基础闲聊

//        String result = aipNlp.nlpTextTrans(0, "小帅开发者");//文本翻译（AI Lab）
//        String result = aipNlp.nlpTextTranslate("小帅开发者", "zh", "en");//文本翻译（翻译君）
//        String result = aipNlp.nlpImageTranslate(filePath, session, "doc","zh", "en");//图片翻译
//        String result = aipNlp.nlpSpeechTranslate(6, 0, 1, session, filePath2,"zh", "en");//语音翻译

//            String result = aipNlp.nlpTextDetect("こんにちは", 0);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 需要爬取的那年今日数据
     */
    public static void thisDayData() {
        Date now = new Date();
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
            logger.info("==============================");
            for (Map mmap :
                    list) {
                logger.info("");
                logger.info(mmap.get("date").toString());
                logger.info(mmap.get("title").toString());
                logger.info(mmap.get("e_id").toString());
                logger.info(mmap.get("day").toString());
                logger.info("");
            }
            logger.info("==============================");

            logger.info(mm + "/" + dd);
        }
    }

    /**
     * 测试分词
     */
    public static void wordsSplit() {
        String url = "http://www.xunsearch.com/scws/api.php";
        Map map = new HashMap();
        map.put("data", "springboot开发集锦");
        map.put("respond", "json");
        String result = HttpUtils.postHttpData(url, map);
        Map wordsMap = new HashMap();
        wordsMap.put("words", Words.class);
        JsonResult jr = (JsonResult) JSONObject.toBean(JSONObject.fromObject(result), JsonResult.class, wordsMap);
        List works = jr.getWords();
        TextSplitUtils.listSort(works);
        logger.info(works + "---");
        logger.info(jr + "");
    }
}
