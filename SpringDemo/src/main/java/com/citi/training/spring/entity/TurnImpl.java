package com.citi.training.spring.entity;


/**
 * Created by sxj on 2017/2/23.
 */
public class TurnImpl implements Turn {
    private int tid;
    private int x, y, z;
    public static final int FULLMARK = 10;

    public TurnImpl() {
        tid = 0;
        x = 0;
        y = 0;
        z = 0;
    }

    public TurnImpl(int tid, int x) {
        this.tid = tid;
        this.x = x;
    }

    public TurnImpl(int tid, int x, int y) {
        this.tid = tid;
        this.x = x;
        this.y = y;
    }

    public TurnImpl(int tid, int x, int y, int z) {
        this.tid = tid;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public int getTid() {
        //default start from 0
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getZ() {
        return z;
    }

    public void setZ(int z) {
        this.z = z;
    }

    @Override
    public boolean isValid() {
        if (x < 0 || x > FULLMARK
                || y < 0 || y > FULLMARK
                || z < 0 || z > FULLMARK)
            return false;

        return true;
    }


    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("TurnImpl{");
        sb.append("tid=").append(tid);
        sb.append(", x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", z=").append(z);
        sb.append('}');
        return sb.toString();
    }
}
