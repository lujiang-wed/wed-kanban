package com.wednesday.kanban.service;

import com.wednesday.kanban.dao.SuperDAO;
import com.wednesday.kanban.domain.Auth;
import com.wednesday.kanban.service.api.AuthAuditService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by wyyangyang1 on 2015/1/6.
 */
@Service("authAuditServiceImpl")
public class AuthAuditServiceImpl implements AuthAuditService {
    @Resource
    private SuperDAO superDAO;

    public List<Auth> findAll() {
        return superDAO.getList("AuthMapper.selectAll",1L);
    }

    public List<Auth> findByUser(String user){
        return superDAO.getList("AuthMapper.selectByUser",user);
    }

    @Override
    public List<Auth> findBySid(Long sid) {
            return superDAO.getList("AuthMapper.selectBySid",sid);
    }

    @Override
    public int addAuth(Auth auth) {
        return superDAO.insert("AuthMapper.insert",auth);
    }

    @Override
    public int modifyAuth(Auth auth) {
        return superDAO.update("AuthMapper.updateByAuth",auth);
    }

    @Override
    public int delAuth(Auth auth) {
        return superDAO.delete("AuthMapper.delByAuth",auth);
    }
}
