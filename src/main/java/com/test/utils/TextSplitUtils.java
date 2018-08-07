package com.test.utils;

import com.test.utils.bean.JsonResult;
import com.test.utils.bean.Words;
import com.test.wechat.util.HttpUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class TextSplitUtils {

    private static Logger logger = LoggerFactory.getLogger(TextSplitUtils.class);

    /**
     * 返回分词结果中的得分最高的一个词
     * @param str
     * @return
     */
    public static String getMaxText(String str) {

        List<Words> works = getWords(str);

        return works.get(0).getWord();
    }

    /**
     * 返回已经分好词的所有信息
     * @param str
     * @return
     */
    public static List<Words> getWordsList(String str) {
        List<Words> works = getWords(str);

        return works;
    }

    /**
     * 返回已经分词后的字符串
     * @param str
     * @return
     */
    public static List<String> getStrList(String str) {
        List<Words> works = getWords(str);

        List list = new ArrayList();
        for (Words word:
             works) {
            list.add(word.getWord());
        }

        return list;
    }

    /**
     * 将str进行分词操作
     * @param str 需要进行分词的字符串
     * @return
     */
    private static List<Words> getWords(String str) {
        String url = "http://www.xunsearch.com/scws/api.php";
        Map map = new HashMap();
        map.put("data",str);
        map.put("respond","json");
        String result = HttpUtils.postHttpData(url,map);
        Map wordsMap = new HashMap();
        wordsMap.put("words", Words.class);
        JsonResult jr = (JsonResult) JSONObject.toBean(JSONObject.fromObject(result),JsonResult.class,wordsMap);
        List<Words> works = jr.getWords();
        listSort(works);
        logger.info("words:"+works);
        return works;
    }

    /**
     * 讲字符串中已经分好的词进行排序
     * @param works
     */
    public static void listSort(List<Words> works) {
        Collections.sort(works, new Comparator<Object>() {
            @Override
            public int compare(Object o1, Object o2) {
                Words w1 = (Words) o1;
                Words w2 = (Words) o2;

                if (w1.getIdf()>w2.getIdf()) {
                    return -1;
                }
                return 1;
            }
        });
    }
}
