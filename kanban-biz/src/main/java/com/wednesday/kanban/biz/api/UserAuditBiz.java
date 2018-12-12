package com.wednesday.kanban.biz.api;

import com.wednesday.kanban.common.param.UserParam;
import com.wednesday.kanban.domain.UserInstance;
import com.wednesday.kanban.common.Page;

public interface UserAuditBiz {
    public UserInstance findById(Long id);

    public UserInstance findByUser(UserParam userParam);

    public Page<UserInstance> findByPage(UserParam userParam);

    public String updatePwd(UserParam userParam);

    public String delUser(Long userId);

    public  String addUser(UserParam userParam);
}
