package com.citi.training.spring.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

/**
 * Created by sxj on 2017/3/2.
 */
public class TestDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;


    public int selectAllCount(){
        String sql = "SELECT count(*) FROM test_users";
        int count = this.jdbcTemplate.queryForObject(sql, Integer.class);
        System.out.println("[selectAllCount]--row count: [" + count + "]");
        return count;
//        int count = this.jdbcTemplate
    }

    public void insertOneRow() {
        String sql = "INSERT INTO test_users(id, name) VALUES (3, 'lily');";
        int result = this.jdbcTemplate.update(sql);
        System.out.println("[insertOneRow] -- result [" + result + "]");
    }


    public <T> List<T> selectAllRows() {
        String sql = "SELECT * FROM test_users ORDER BY id";

        return (List<T>) this.jdbcTemplate.query(sql, new TestUser());


    }


    public void deleteOneRow() {
        String sql = "DELETE FROM test_users where id = 2";
        int result = this.jdbcTemplate.update(sql);
        System.out.println("[deleteOneRow] -- result [" + result + "]");
    }

    public void updateOneRow() {
        String sql = "UPDATE test_users SET name='she' where id = 1";
        int result = this.jdbcTemplate.update(sql);
        System.out.println("[updateOneRow] -- result [" + result + "]");
    }

    public void deleteAllRows() {
        String sql = "DELETE FROM test_users";
        int result = this.jdbcTemplate.update(sql);
        System.out.println("[deleteAllRows] -- result [" + result + "]");
    }

}
