package com.citi.training.spring.entity;

/**
 * Created by sxj on 2017/2/23.
 */
public interface Bowling {

    void addPins(int... pins);

    Turn[] getTurns();

    int getBid();

}
