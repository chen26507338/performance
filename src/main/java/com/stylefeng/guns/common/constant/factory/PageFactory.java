package com.stylefeng.guns.common.constant.factory;

import com.baomidou.mybatisplus.plugins.Page;
import com.stylefeng.guns.common.constant.state.Order;
import com.stylefeng.guns.core.support.HttpKit;
import com.stylefeng.guns.core.util.ToolUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * BootStrap Table默认的分页参数创建
 *
 * @author fengshuonan
 * @date 2017-04-05 22:25
 */
public class PageFactory<T> {

    public Page<T> defaultPage() {
        HttpServletRequest request = HttpKit.getRequest();
        //每页多少条数据
        int limit = Integer.parseInt(request.getParameter("limit"));
        //每页的偏移量(本页当前有多少条)
        int offset = Integer.parseInt(request.getParameter("offset"));
        //排序字段名称
        String sort = request.getParameter("sort");
        //asc或desc(升序或降序)
        String order = request.getParameter("order");
        if (ToolUtil.isEmpty(sort)) {
            Page<T> page = new Page<>((offset / limit + 1), limit);
            page.setOpenSort(false);
            return page;
        } else {
            Page<T> page = new Page<>((offset / limit + 1), limit, sort);
            if (Order.ASC.getDes().equals(order)) {
                page.setAsc(true);
            } else {
                page.setAsc(false);
            }
            return page;
        }
    }

    public Page<T> apiPage() {
        HttpServletRequest request = HttpKit.getRequest();
        //每页多少条数据
        int pageNo = Integer.parseInt(request.getParameter("pageNo"));
        //每页的偏移量(本页当前有多少条)
        int pageSize = Integer.parseInt(request.getParameter("pageSize"));
        Page<T> page = new Page<>(pageNo,pageSize);
        page.setSearchCount(false);
        return page;
    }
}
