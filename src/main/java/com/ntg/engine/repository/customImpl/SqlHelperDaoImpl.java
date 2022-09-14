package com.ntg.engine.repository.customImpl;

import java.util.*;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.ColumnMapRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.ntg.engine.entites.SlaMilestones;
import com.ntg.engine.repository.custom.SqlHelperDao;
import com.ntg.engine.util.Utils;

@Repository
public class SqlHelperDaoImpl implements SqlHelperDao {

    private JdbcTemplate jdbcTemplate;

    private NamedParameterJdbcTemplate namedParamJdbcTemplate;

    @Autowired
    public void setDataSource(javax.sql.DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
        this.namedParamJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Map<String, Object>> queryForList(String query) {
        List<Map<String, Object>> objs = jdbcTemplate.query(query, new ColumnMapRowMapper() {

            @Override
            protected String getColumnKey(String columnName) {
                return columnName.toLowerCase();
            }
        });
        return objs;
    }

    @Override
    public List<Map<String, Object>> queryForList(String query, Object[] params) {
        List<Map<String, Object>> objs = jdbcTemplate.query(query, params, new ColumnMapRowMapper() {

            @Override
            protected String getColumnKey(String columnName) {
                return columnName.toLowerCase();
            }
        });
        return objs;
    }

    public String SquanceFetchSql(String SequanceName, long connectionType) {
        String Sql = "";
        if (connectionType == 1) {// oracle
            Sql = "Select " + SequanceName + ".nextVal from dual";
        } else {// postgres syntax
            Sql = "select nextval('" + SequanceName + "') ";
        }
        return Sql;
    }

}
