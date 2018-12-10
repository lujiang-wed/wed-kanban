package com.wednesday.kanban.common.result;

import java.math.BigDecimal;

public class StatisticsResult {

    /**
     * 统计的分类
     */
    private String name;
    /**
     * 分类对应的数量
     */
    private BigDecimal value;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "StatisticsResult{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
