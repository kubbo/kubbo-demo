package com.kubbo.demo.mybatis;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by zhuwei on 2015/2/28.
 */
public class StringTypeHandler extends BaseTypeHandler<String> {
    private static final Logger logger = LoggerFactory.getLogger(StringTypeHandler.class);
    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, String parameter, JdbcType jdbcType) throws SQLException {

        logger.info("------------------------:setNonNullParameter");
        ps.setString(i, parameter);
    }

    @Override
    public String getNullableResult(ResultSet rs, String columnName) throws SQLException {
        logger.info("------------------------:getNullableResult");
        return rs.getString(columnName);
    }

    @Override
    public String getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        logger.info("------------------------:getNullableResult");
        return rs.getString(columnIndex);
    }

    @Override
    public String getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        logger.info("------------------------:getNullableResult");
        return cs.getString(columnIndex);
    }
}
