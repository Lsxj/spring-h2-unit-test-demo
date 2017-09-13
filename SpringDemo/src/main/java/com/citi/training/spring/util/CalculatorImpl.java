package com.citi.training.spring.util;


import com.citi.training.spring.entity.Bowling;
import com.citi.training.spring.entity.Turn;

import static com.citi.training.spring.entity.TurnImpl.FULLMARK;

/**
 * Created by sxj on 2017/2/23.
 */
public class CalculatorImpl implements Calculator {

    private static Calculator singleton;

    private CalculatorImpl() {
    }

    public static Calculator getInstance() {
        if (singleton == null) {
            singleton = new CalculatorImpl();
        }
        return singleton;
    }


    @Override
    public int calculateTotalScore(Bowling bowling) {
        int sum = 0;
        int[] score = calculateScore(bowling);
        for (int i: score) {
            sum += i;
        }
        return sum;
    }

    @Override
    public int[] calculateScore(Bowling bowling) {
        int[] score = new int[bowling.getTurns().length];

        for (int i = 0; i < score.length; i++) {
            int point = turnCalculate(i, bowling);
            score[i] = point;
        }

        return score;
    }

    private int turnCalculate(int index, Bowling bowling) {
        int score = 0;
        int maxTurns = bowling.getTurns().length;

        Turn currentTurn = bowling.getTurns()[index];

        if(currentTurn == null)
            return 0;

        int x = currentTurn.getX();
        int y = currentTurn.getY();
        int z = currentTurn.getZ();

        if (x + y < FULLMARK) //miss
            return x + y;

        //the last turn
        //no matter strike or spare
        //score = x + y + z;
        if (index == maxTurns - 1) {
            score = x + y + z;
            return score;
        }

        //in other turn
        //need to get the next turn's point
        Turn nextTurnFirstTurn = bowling.getTurns()[index+1];
        int nextX = nextTurnFirstTurn == null? 0 : nextTurnFirstTurn.getX();
        int nextY = nextTurnFirstTurn == null? 0 : nextTurnFirstTurn.getY();

        if (x == FULLMARK) {
            // need to add next turn first point
            score = x + nextX;

            // must to check currentTurn.id is not maxTurns-1.
            // or it will cause IndexOutOfBoundsException
            if (nextX == FULLMARK && index != maxTurns - 2) {
                //need to add index+2 turn first point
                int point = bowling.getTurns()[index + 2] == null?
                        0:bowling.getTurns()[index+ 2].getX();
                score += point;
            } else { // add index + 1 round 2nd score
                score += nextY;
            }
            return score;
        }

        if (x + y == 10) {
            score = x + y + nextX;
            return score;
        }

        return score;
    }
}
