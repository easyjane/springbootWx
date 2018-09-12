package com.test.controller;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.github.pagehelper.PageInfo;
import com.test.entity.Result;
import com.test.entity.UserSign;
import com.test.service.IUserSignService;
import com.test.utils.bean.UserSignEntity;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.mapping.ResultMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.*;

@RestController
@RequestMapping("/sign")
public class UserSignController {

    @Autowired
    private IUserSignService userSignService;

    private Logger logger = LoggerFactory.getLogger(UserSignController.class);

    /**
     * 判断登录用户
     *
     * @param username
     * @param phone
     * @return
     */
    @RequestMapping("/login")
    public Object login(String username, String phone) {

        if (StringUtils.isBlank(username)) {
            return Result.setResult(404, "姓名不能为空", "");
        }
        UserSign us = userSignService.login(username, null);
        if (us == null) {
            return Result.setResult(404, "暂无此数据", "");
        }
        us.setIsSign(1);
        userSignService.add(us);
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

        ImportParams params = new ImportParams();
        params.setTitleRows(0);
        params.setHeadRows(1);

        File file = new File("E:\\workspace\\桌位列表.xls");
        logger.info(file.getName());
        List<UserSignEntity> usnList = ExcelImportUtil.importExcel(file,UserSignEntity.class,params);

        List<UserSignEntity> resultList = new ArrayList();
        UserSignEntity resultUsn;
        UserSign us;
        int count = 0;
        for (UserSignEntity usn : usnList) {
            logger.info(usn.toString());
        }
        for (UserSignEntity usn : usnList) {
            logger.info(usn.toString());
            us = new UserSign();
            us.setTableName(usn.getTablename());
            PageInfo usPage = userSignService.findPage(1, 10, us);
            if (usPage.getTotal() > 0) {
                resultUsn = usn;
                resultUsn.setRemark("桌位号重复了");
                resultList.add(resultUsn);
            } else {
                us = userSignService.login(username, phone);
                if (us != null) {
                    resultUsn = usn;
                    resultUsn.setRemark("用户名和电话重复");
                    resultList.add(resultUsn);
                } else {
                    us = new UserSign();
                    us.setTableName(usn.getTablename());
                    us.setCreateTime(new Date());
                    us.setIsSign(0);
                    us.setPhone(usn.getPhone());
                    us.setUseable(1);
                    us.setUsername(usn.getUsername());
                    count += userSignService.add(us);
                }
            }


        }

        us = new UserSign();
        us.setTableName("中文测试");
        us.setCreateTime(new Date());
        us.setIsSign(0);
        us.setPhone("17772345678");
        us.setUseable(1);
        us.setUsername("中文测试");
        count += userSignService.add(us);

        if (resultList.size()>0) {
            logger.info("出现问题的数据如下--------------------------------");
            for (UserSignEntity useError:
                    resultList) {
                logger.info(useError.toString());
            }

        } else {
            logger.info("导入的数据正确无误----------------------------------");
        }

        Map map = new HashMap();
        map.put("count", count);
        return Result.setResult(200, "提交成功", map);
    }

    /**
     * 分页数据
     * @param pageNo
     * @param pageSize
     * @param searchKey
     * @return
     */
    @RequestMapping("/userList")
    public Object userList(int pageNo,int pageSize,String searchKey,Integer isSign) {

        UserSign us = new UserSign();
        if (StringUtils.isNotBlank(searchKey)) {
            us.setTableName(searchKey);
            us.setUsername(searchKey);
            us.setPhone(searchKey);
        }
        if (isSign != null) {
            us.setIsSign(isSign);
        }


        PageInfo pageInfo = userSignService.findPageLikeItemOr(pageNo,pageSize,us);


        return Result.setResult(200,"成功了",pageInfo);
    }
}
