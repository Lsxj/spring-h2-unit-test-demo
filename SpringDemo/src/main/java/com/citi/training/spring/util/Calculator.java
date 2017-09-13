package com.citi.training.spring.util;


import com.citi.training.spring.entity.Bowling;

/**
 * Created by sxj on 2017/2/23.
 */
public interface Calculator {

    int calculateTotalScore(Bowling bowling);

    int[] calculateScore(Bowling bowling);
}