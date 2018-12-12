package com.wednesday.kanban.dao;

import org.mybatis.spring.support.SqlSessionDaoSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;

import java.util.List;
import java.util.Map;

public abstract class BaseDao extends SqlSessionDaoSupport implements ISuperDAO {

    protected static Logger log = LoggerFactory.getLogger(SuperDAO.class);

    public Integer update(String statementName, Object parameterObject) {
        return this.getSqlSession().update(statementName, parameterObject);
    }

    public Integer delete(String statementName, Object parameterObject) {
        return this.getSqlSession().delete(statementName, parameterObject);
    }

    public int insert(String statementName, Object parameterObject) {
        return this.getSqlSession().insert(statementName, parameterObject);
    }

    public <T> List<T> getList(String statementName, Object parameterObject) throws DataAccessException {
        return (List<T>)this.getSqlSession().selectList(statementName, parameterObject);
    }

    public <T, V> Map<T, V> getMap(String statementName, Object parameterObject, String key) {
        return (Map<T, V>)this.getSqlSession().selectMap(statementName, parameterObject, key);

    }

    public <T> T getObject(String statementName, Object parameterObject) {
        return (T) this.getSqlSession().selectOne(statementName, parameterObject);
    }

}
