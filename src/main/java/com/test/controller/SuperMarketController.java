package com.test.controller;

import com.github.pagehelper.PageInfo;
import com.test.entity.Supermarket;
import com.test.service.ISuperMarketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 超市controller
 */
@Controller
public class SuperMarketController {

    @Autowired
    private ISuperMarketService isuperMarketService;

    @RequestMapping("/smk/list/{pageNo}")
    @ResponseBody
    public Object list(Model model, @PathVariable("pageNo")int pageNo) {

        PageInfo<Supermarket> smkList = isuperMarketService.findPage(pageNo,10,new Supermarket());

        return smkList;
    }

    @RequestMapping("/smk/{sid}")
    @ResponseBody
    public Object findBySid(@PathVariable("sid")String sid) {
        Supermarket smk = isuperMarketService.selectByPrimaryKey(sid);

        return smk;
    }

    @RequestMapping("/smk/index")
    public  String index(Model model) {

        model.addAttribute("msg","欢迎使用");

        return "/th/index";
    }
}
