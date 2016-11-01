package com.bailian.utils.orm.dialect;

/**
 * @ClassName: Dialect 
 * @Description: 数据库方言抽象类
 * @author zhangwenming
 * @date 2015年11月23日 上午11:34:43
 */
public abstract class Dialect {

    /**
     * 得到分页sql
     * 
     * @param sql
     * @param offset
     * @param limit
     * @return
     */
    public abstract String getLimitString(String sql, int offset, int limit);

    /**
     * 得到总数量 sql
     * 
     * @param sql
     * @return
     */
    public abstract String getCountString(String sql);

}
