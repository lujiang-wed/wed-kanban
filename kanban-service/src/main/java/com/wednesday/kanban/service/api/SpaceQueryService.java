package com.wednesday.kanban.service.api;

import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.domain.Space;
import com.wednesday.kanban.common.param.SpaceQueryParam;

import java.util.List;


/**
 * 空间查询操作
 * <p>创建日期：2014/12/9 </p>
 *
 * @author wypengbo
 * @version V1.0
 * @see
 */
public interface SpaceQueryService {

    /**
     * 主键查询
     * @param id
     * @return
     */
    public Space findOne(Long id);

    public List<Long> findSonSpace(Long parentSpace);

    /**
     * 按条件查询
     * @param spaceQueryParam
     * @return
     */
    public Page<Space> findByPage(SpaceQueryParam spaceQueryParam);

}
