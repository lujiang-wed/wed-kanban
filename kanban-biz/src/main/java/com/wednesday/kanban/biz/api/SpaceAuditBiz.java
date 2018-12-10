package com.wednesday.kanban.biz.api;

import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.domain.Space;
import com.wednesday.kanban.common.param.SpaceQueryParam;
import com.wednesday.kanban.common.result.SpaceResult;

import java.util.List;

public interface SpaceAuditBiz {
    /**
     * 添加空间
     * @param space
     * @return
     */
    public String addSpace(Space space,String user);

    /**
     * 修改空间属性
     * @param space
     * @return
     */
    public String modifySpace(Space space);

    /**
     * 删除空间（先检索是否有子空间）
     * @param id
     * @return
     */
    public String deleteSpace(Long id);

    /**
     * 查询单个空间
     * @param id
     * @return
     */
    public SpaceResult findOne(Long id);

    /**
     * 查询子空间ID列表
     * @param parentSpace
     * @return
     */
    public List<Long> findSonSpace(Long parentSpace);

    /**
     * 按条件查询空间
     * @param spaceQueryParam
     * @return
     */
    public Page<SpaceResult> findByPage(SpaceQueryParam spaceQueryParam);

    /**
     * 获取空间名
     * @param spaceId
     * @return
     */
    public String findSpaceName(Long spaceId);
}
