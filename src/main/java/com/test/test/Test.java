package com.test.test;

import com.test.utils.JsonUtils;
import com.test.utils.TextSplitUtils;
import com.test.utils.bean.JsonResult;
import com.test.utils.bean.Words;
import com.test.wechat.util.HttpUtils;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

public class Test {

    private static Logger logger = LoggerFactory.getLogger(Test.class);

    public static void main(String[] args) {
        String url = "http://www.xunsearch.com/scws/api.php";
        Map map = new HashMap();
        map.put("data","springboot开发集锦");
        map.put("respond","json");
        String result = HttpUtils.postHttpData(url,map);
        Map wordsMap = new HashMap();
        wordsMap.put("words",Words.class);
        JsonResult jr = (JsonResult) JSONObject.toBean(JSONObject.fromObject(result),JsonResult.class,wordsMap);
        List works = jr.getWords();
        TextSplitUtils.listSort(works);
        logger.info(works+"---");
        logger.info(jr+"");
    }
}
