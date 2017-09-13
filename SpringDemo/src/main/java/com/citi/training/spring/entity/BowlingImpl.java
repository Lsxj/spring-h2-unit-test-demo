package com.citi.training.spring.entity;


import java.util.ArrayList;
import java.util.List;

import static com.citi.training.spring.entity.TurnImpl.FULLMARK;


/**
 * Created by sxj on 2017/2/23.
 */
public class BowlingImpl implements Bowling {
    private int bid;
    private final int maxTurns;
    private int[] pins;
    private int pointer = 0; //record the index of pins which not in turns
    private int pinCount = 0; //record the number of valid pin in pins[]
    private List<Turn> turnList;
    private int score = 0;

    public BowlingImpl() {
        this.bid = 0;
        this.maxTurns = 10;
        this.pins = new int[2 * maxTurns + 1];
        this.turnList = new ArrayList<Turn>();
    }

    public BowlingImpl(int maxTurns) {
        this.maxTurns = maxTurns;
        this.bid = 0;
        this.pins = new int[2 * maxTurns + 1];
        this.turnList = new ArrayList<Turn>();
    }

    public BowlingImpl(int bid, int maxTurns) {
        this.bid = bid;
        this.maxTurns = maxTurns;
        this.pins = new int[2 * maxTurns + 1];
        this.turnList = new ArrayList<Turn>();
    }

    public BowlingImpl(int bid, int maxTurns, Turn[] turns, int score) {
        this.bid = bid;
        this.maxTurns = maxTurns;
        this.pins = new int[2 * maxTurns + 1];
        this.turnList = new ArrayList<Turn>();
        for (Turn t: turns) {
            this.turnList.add(t);
        }
        this.score = score;
    }

    @Override
    public int getBid() {
        return bid;
    }

    public void setBid(int bid) {
        this.bid = bid;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("BowlingImpl{");
        sb.append("score=").append(score);
        sb.append(", bid=").append(bid);
        sb.append(", maxTurns=").append(maxTurns);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Turn[] getTurns() {
        return turnList.toArray(new Turn[turnList.size()]);
    }

    @Override
    public void addPins(int... pins) {
        int validCount = 0; //record the number of valid pin

        if (!checkPinsOnBound(pins))
            return;

        for (int i = 0; i < pins.length; i++) {
            if (!isWithinPinSize(validCount)) {
                rollBackPins(validCount);
                return;
            }
            this.pins[this.pinCount + validCount] = pins[i];
            validCount++;
        }
        this.pinCount += validCount;

        //turn into turn
        List<Turn> turns = pinsIntoTurn();

        if (turns == null) { //invalid pins need to rollBack pins
            rollBackPins(validCount);
            return;
        }

        concatTurns(turns);

    }

    /**
     * delete invalid addPins in pins[]
     * @param validCount
     */
    private void rollBackPins(int validCount) {
        this.pinCount -= validCount;
        for (int i = this.pinCount; i < this.pins.length; i++) {
            this.pins[i] = 0;
        }

    }

    /**
     * check addPins whether can add
     * @param validCount
     * @return
     */
    private boolean isWithinPinSize(int validCount) {
        if (this.turnList.size() * 2 + validCount >= this.pins.length) {
            System.out.println("Sorry. the pins has over the maxTurns. Can't add pins.");
            return false;
        }
        return true;

    }

    /**
     * check each pin weather is on bound.
     * if valid return true. else return false
     *
     * @param pin
     * @return
     */
    private boolean eachPinIsOnBound(int pin) {
        return pin <= FULLMARK && pin >= 0;
    }


    /**
     * check pin within in 0 ~ 10
     * @param pins
     * @return
     */
    private boolean checkPinsOnBound(int... pins) {
        for (int i = 0; i < pins.length; i++) {
            if (!eachPinIsOnBound(pins[i])) {
                System.out.println("Sorry. the pins has out of bound.");
                return false;
            }
        }
        return true;
    }

    /**
     * concat pinsIntoTurn() result and this.turns
     *
     * @param turns
     */
    private void concatTurns(List<Turn> turns) {
        this.turnList.addAll(turns);
    }


    /**
     * translate pins into turns
     */
    private List<Turn> pinsIntoTurn() {
        List<Turn> tempTurnTest = new ArrayList<>();
        int tid = this.turnList.size();
        int remainPinCount = 0; // remain in pins which has not turn into turns
        int tempPointer = this.pointer; //in case invalid pins destroy pointer

        for (int i = tempPointer; i < pinCount; i++) {
            switch (remainPinCount) {
                case 0: //only in Full mark && not in last turn, we can new a turn
                {
                    if (isFullMark(this.pins[i]) && tid != this.maxTurns - 1) { //FULL MARK,
                        tempTurnTest.add(new TurnImpl(tid++, this.pins[i]));
                        this.pointer = i + 1;
                    } else {
                        remainPinCount++;
                    }
                    break;
                }
                case 1: {
                    //check pin
                    if (this.pins[i - 1] + this.pins[i] > FULLMARK && !isFullMark(this.pins[i - 1])) {
                        System.out.println("Sorry. the pins has out of bound.");
                        this.pointer = tempPointer;
                        return null;
                    }
                    //last turn only spare or full mark has third pin
                    if (tid == this.maxTurns - 1 &&
                            (isSpare(this.pins[i - 1], this.pins[i]) || isFullMark(this.pins[i - 1]))) {
                        remainPinCount++;
                    } else {
                        // in other turn, 2 pins(plus < 10) can turn into 1 turn.
                        tempTurnTest.add(new TurnImpl(tid++, this.pins[i - 1], this.pins[i]));
                        //update pointer and remainPinCount
                        this.pointer = i + 1;
                        remainPinCount = 0;
                    }
                    break;
                }
                case 2: //only in last turn has two remainPinCount
                {
                    //check pin. only Spare or Full mark is ok.
                    if (!isSpare(this.pins[i - 2], this.pins[i - 1]) && !isFullMark(this.pins[i - 2])) {
                        System.out.println("Sorry. the pins is invalid. " +
                                "Because " + this.pins[i - 2] + " + " + this.pins[i - 1] + " doesn't equal Fullmark.");
                        //invalid pins. recover pointer.
                        this.pointer = tempPointer;
                        return null;
                    }
                    //valid pin. new a last turn
                    tempTurnTest.add(new TurnImpl(tid++, this.pins[i - 2], this.pins[i - 1], this.pins[i]));
                    this.pointer = i + 1;
                    remainPinCount = 0;
                    break;
                }
                default:
                    return null;
            }
        }

        return tempTurnTest;

    }

    private boolean isSpare(int x, int y) {
        return x + y == FULLMARK;
    }

    private boolean isFullMark(int x) {
        return x == FULLMARK;
    }
}
