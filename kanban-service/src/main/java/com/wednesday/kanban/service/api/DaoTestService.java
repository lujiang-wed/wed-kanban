package com.wednesday.kanban.service.api;

import com.wednesday.kanban.domain.Space;

/**
 * 此处填写功能、用途等，如果多行，用<br>换行
 * <p>创建日期：2014/12/5 </p>
 *
 * @author wypengbo
 * @version V1.0
 * @see
 */
public interface DaoTestService {

    public void insert(Space space);

    public Space select(Long id);
}
