package com.api.starter.admin.controller;

import com.api.starter.admin.controller.bean.request.ApiAdminResourceListRequest;
import com.api.starter.dao.model.ApiAdminResourceModel;
import com.api.starter.service.bo.ApiAdminResourceBo;
import com.api.starter.service.interfaces.ApiAdminResourceService;
import com.api.starter.util.enums.ApiAdminResourceType;
import com.api.starter.util.utils.CalendarUtil;
import com.api.starter.util.utils.ResponseEntity;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;

/**
 * 资源接口
 * 目前资源是提前在数据库配制的,比如新增一个页面,有一个按钮要控制权限,
 * 这些开发时就是确定的,所以需要提前在resource表中配制好
 */
@RestController
@RequestMapping("resource")
public class ApiAdminResourceController {

    @Autowired
    private ApiAdminResourceService apiAdminResourceService;

    /**
     * 查询所有资源列表
     * @return  {@link ResponseEntity}
     */
    @RequestMapping(value = "allResourceList",method = RequestMethod.GET)
    public ResponseEntity allResourceList() {
        Map<String,List<ApiAdminResourceBo>> result = new HashMap<>();
        Arrays.asList(ApiAdminResourceType.values()).forEach(item -> result.put(item.name(),apiAdminResourceService.listTree(item.name())));
        return ResponseEntity.SUCCESS(result);
    }

    /**
     * 分页查询资源列表
     * @param request  {@link ApiAdminResourceListRequest}
     * @return  {@link ResponseEntity}
     */
    @RequestMapping(value = "list",method = RequestMethod.GET)
    public ResponseEntity list(ApiAdminResourceListRequest request) {
        ApiAdminResourceBo bo = new ApiAdminResourceBo();
        BeanUtils.copyProperties(request,bo);
        PageHelper.startPage(request.getCurrent(), request.getPageSize());
        Date startDate = CalendarUtil.str2date(request.getStartDateStr());
        Date endDate = CalendarUtil.str2date(request.getEndDateStr());
        List<ApiAdminResourceModel> list = apiAdminResourceService.list(bo, startDate, endDate);
        return ResponseEntity.PAGE_SUCCESS(list);
    }
}
