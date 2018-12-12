package com.wednesday.kanban.service.api;

import com.wednesday.kanban.common.param.UserParam;

public interface UserService {
    public boolean delUser(Long userId);

    public boolean updatePwd(UserParam param);

    public boolean insertUser(UserParam param);
}
