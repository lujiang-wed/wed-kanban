package com.wednesday.kanban.dao;


import com.wednesday.kanban.common.BasePageParam;
import com.wednesday.kanban.common.Page;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SuperDAO extends BaseDao {

    /**
     * 分页查询
     *
     * @param statementName
     * @param param
     * @return
     */
    public Page queryPagination(String statementName, BasePageParam param) {
        // 查询数据总数
        Integer totalCount = (Integer) this.getSqlSession().selectOne(statementName + "-count", param);

        if (param.getPageSize() < 1) {
            //小于1时直接查询全部记录
            param.setStartIdx(0);
            param.setEndIdx(totalCount);
            param.setStart(0);
            param.setLimit(totalCount);
        } else {
            // 计算记录起始值和结束值
            param.setEndIdx(param.getPageSize() * param.getPageNo());
            param.setStartIdx(param.getPageSize() * (param.getPageNo() - 1));
        }

        List resultList = this.getSqlSession().selectList(statementName, param);
        ArrayList data = null;
        if (resultList != null) {
            data = new ArrayList();
            data.addAll(resultList);
        }

        Page page = new Page();
        page.setPageNo(param.getPageNo());
        page.setPageSize(param.getPageSize());
        page.setTotalCount(totalCount);
        page.setData(data);

        return page;
    }

    /**
     * 分页查询
     *
     * @param statementName
     * @param param
     * @return
     */
    public Page queryPagination(String statementName, Map<String, String> param) {
        String pageSize = param.get("pageSize");
        String pageNo = param.get("pageNo");
        Integer ps = 20;
        if (StringUtils.isNotBlank(pageSize) && StringUtils.isNumeric(pageSize)) {
            ps = Integer.parseInt(pageSize);
        }
        Integer pn = 1;
        if (StringUtils.isNotBlank(pageNo) && StringUtils.isNumeric(pageNo)) {
            pn = Integer.parseInt(pageNo);
            if (pn < 1) {
                pn = 1;
            }
        }
        Map<String,Object> map = new HashMap<String, Object>();

        //先把原来map数据放进去
        map.putAll(param);

        map.put("pageSize",ps);
        map.put("pageNo",pn);

        return this.queryPaginations(statementName,map);
    }

    /**
     * 分页查询
     *
     * @param statementName
     * @param param
     * @return
     */
    public Page queryPaginations(String statementName, Map<String, Object> param) {
        // 查询数据总数
        Integer totalCount = (Integer) this.getSqlSession().selectOne(statementName + "-count", param);

        Integer pageSize = 20;
        if (param.get("pageSize") != null) {
            pageSize = (Integer) param.get("pageSize");
        }
        Integer pageNo = 1;
        if (param.get("pageNo") != null) {
            pageNo = (Integer) param.get("pageNo");
            if (pageNo < 1) {
                pageNo = 1;
            }
        }
        if (pageSize < 1) {
            //小于1时直接查询全部记录
            param.put("startIdx", 0);
            param.put("endIdx", totalCount );
            param.put("start", 0);
            param.put("limit", totalCount);
        } else {
            // 计算记录起始值和结束值
            //默认第页20条

            param.put("endIdx", pageSize * pageNo);
            param.put("startIdx", pageSize * (pageNo - 1));
        }

        List resultList = this.getSqlSession().selectList(statementName, param);
        ArrayList data = null;
        if (resultList != null) {
            data = new ArrayList();
            data.addAll(resultList);
        }
        Page page = new Page();
        page.setPageNo(pageNo);
        page.setPageSize(pageSize);
        page.setTotalCount(totalCount);
        page.setData(data);
        return page;
    }
}

