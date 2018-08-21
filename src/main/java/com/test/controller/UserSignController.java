package com.test.controller;

import com.github.pagehelper.PageInfo;
import com.test.entity.Result;
import com.test.entity.UserSign;
import com.test.service.IUserSignService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.mapping.ResultMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/sign")
public class UserSignController {

    @Autowired
    private IUserSignService userSignService;

    /**
     * 判断登录用户
     *
     * @param username
     * @param phone
     * @return
     */
    @RequestMapping("/login")
    public Object login(String username, String phone) {

        if (StringUtils.isBlank(username) && StringUtils.isBlank(phone)) {
            return Result.setResult(404, "姓名或电话不能为空", "");
        }
        UserSign us = userSignService.login(username, phone);
        if (us == null) {
            return Result.setResult(404, "姓名或者电话不正确", "");
        }
        Map map = new HashMap();
        map.put("id", us.getUsid());

        return Result.setResult(200, "正确", map);
    }

    /**
     * 根据主键查询用户
     * @param id
     * @return
     */
    @RequestMapping("/selectByPrimaryKey")
    public Object selectByPrimaryKey(String id) {
        if (StringUtils.isBlank(id)) {
            return Result.setResult(404,"主键不能为空","");
        }

        UserSign us = userSignService.selectByPrimaryKey(id);
        if (us == null) {
            return Result.setResult(404,"主键不不正确","");
        }

        Map map = new HashMap();
        map.put("us",us);
        return Result.setResult(200,"成功",map);

    }

    /**
     * 模拟添加用户
     *
     * @param username
     * @param phone
     * @param tableName
     * @return
     */
    @RequestMapping("/insert")
    public Object insert(String username, String phone, String tableName) {
        if (StringUtils.isBlank(username) && StringUtils.isBlank(phone)) {
            return Result.setResult(404, "姓名或电话不能为空", "");
        }
        UserSign us = new UserSign();
        us.setTableName(tableName);
        PageInfo usPage = userSignService.findPage(1, 10, us);
        if (usPage.getTotal() > 0) {
            return Result.setResult(404, "座位号重复", "");
        }

        us = userSignService.login(username, phone);
        if (us != null) {
            return Result.setResult(404, "姓名或者电话重复", "");
        }
        UserSign usInsert = new UserSign();
        usInsert.setCreateTime(new Date());
        usInsert.setIsSign(0);
        usInsert.setPhone(phone);
        usInsert.setTableName(tableName);
        usInsert.setUseable(1);
        usInsert.setUsername(username);
        int count = userSignService.add(usInsert);
        Map map = new HashMap();
        map.put("count", count);
        return Result.setResult(200, "提交成功", map);
    }
}
