package com.citi.training.spring.jdbc;

import org.springframework.jdbc.core.RowMapper;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sxj on 2017/3/1.
 */
public class TestUser implements RowMapper<TestUser>, Serializable {

    private int id;
    private String name;

    public TestUser() {
    }

    public TestUser(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public TestUser mapRow(ResultSet resultSet, int i) throws SQLException {
        TestUser user = new TestUser();
        user.setId(resultSet.getInt("id"));
        user.setName(resultSet.getString("name"));
        return user;
    }
}
