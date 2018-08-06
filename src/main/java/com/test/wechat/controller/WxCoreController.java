package com.test.wechat.controller;

import com.test.wechat.entity.WeixinTokenConstant;
import com.test.wechat.service.ICoreService;
import com.test.wechat.util.SignUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/WxCoreController")
public class WxCoreController {

    //增加日志
    private static Logger logger = LoggerFactory.getLogger(WxCoreController.class);

    @Autowired
    private ICoreService coreService;

    @Autowired
    private  WeixinTokenConstant weixinTokenConstant;

    @RequestMapping("/wxCore")
    public String checkSignature(@RequestParam(value = "signature" ,required = false) String signature  ,
                                 @RequestParam(value = "nonce",required = false) String  nonce ,
                                 @RequestParam(value = "timestamp",required = false) String  timestamp ,
                                 @RequestParam(value = "echostr",required = false) String  echostr) {

        logger.info("开始");
        logger.info(weixinTokenConstant.getToken());
        // 通过检验signature对请求进行校验，若校验成功则原样返回echostr，表示接入成功，否则接入失败
        if (SignUtil.checkSignature(weixinTokenConstant.getToken(),signature, timestamp, nonce)) {
            logger.info("接入成功");
            return echostr;
        }
        logger.error("接入失败");
        return "";
    }

    // 调用核心业务类接收消息、处理消息跟推送消息
    @RequestMapping(value = "wxCore1",method = RequestMethod.POST)
    public  String post(HttpServletRequest request){
        String respMessage = coreService.processRequest(request);
        return respMessage;
    }

    @RequestMapping("/index")
    @ResponseBody
    public  Object index(Model model) {

        Map map = new HashMap();
        map.put("result",true);
        map.put("msg","成功了");
        map.put("code",200);

        return map;
    }
}
