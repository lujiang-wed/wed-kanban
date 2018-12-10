package com.wednesday.kanban.service.api;

import com.wednesday.kanban.domain.Space;

/**
 * 空间的增加和删除操作
 * <p>创建日期：2014/12/9 </p>
 *
 * @author wypengbo
 * @version V1.0
 * @see
 */
public interface SpaceService {

    /**
     * 添加空间
     * @param space
     * @return 空间ID
     */
    public Long addSpace(Space space);

    /**
     * 修改空间
     * @param space
     */
    public void modifySpace(Space space);

    /**
     * 按主键删除主键
     * @param id
     */
    public void deleteSpace(Long id);
}
