package com.wednesday.kanban.service;

import com.wednesday.kanban.dao.SuperDAO;
import com.wednesday.kanban.domain.AuthorityContent;
import com.wednesday.kanban.service.api.AuthorityContentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service("authorityContentService")
public class AuthorityContentServiceImpl implements AuthorityContentService {
    @Resource
    private SuperDAO superDAO;

    @Override
    public void addContent(AuthorityContent authorityContent) {
        superDAO.insert("AuthorityContentMapper.insert",authorityContent);
    }

    @Override
    public void deleteRecord(String user) {
        superDAO.delete("AuthorityContentMapper.delete",user);
    }
}
