package com.perfectproject.app.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface RequirementService {
    /**
     * 发布
     */
    Map<String,Object> release(HttpServletRequest request);

    /**
     * 查询发布的列表
     * @return
     */
    Map<String,Object> queryRequirementList(HttpServletRequest request);
}
