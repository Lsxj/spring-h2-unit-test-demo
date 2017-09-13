package com.citi.training.spring.dao;


import com.citi.training.spring.entity.Bowling;

/**
 * Created by sxj on 2017/2/23.
 */
public interface BowlingDao {
    Bowling query(int id) throws Exception;
    int save(Bowling bowling) throws Exception;
    int delete(int id) throws Exception;
}
