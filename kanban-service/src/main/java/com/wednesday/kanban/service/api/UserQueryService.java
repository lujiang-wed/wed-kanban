package com.wednesday.kanban.service.api;

import com.wednesday.kanban.common.Page;
import com.wednesday.kanban.common.param.UserParam;
import com.wednesday.kanban.domain.UserInstance;

public interface UserQueryService {
    /**
     * 根据ID查找用户
     * @param id
     * @return
     */
    public UserInstance findById(Long id);

    /**
     * 根据用户名密码查找用户
     * @param userParam
     * @return
     */
    public UserInstance findByUser(UserParam userParam);

    /**
     * find All
     * @return
     */
    public Page<UserInstance> finaByPage(UserParam userParam);
}
