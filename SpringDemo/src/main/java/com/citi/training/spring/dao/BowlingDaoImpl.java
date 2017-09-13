package com.citi.training.spring.dao;


import com.citi.training.spring.entity.Bowling;
import com.citi.training.spring.entity.BowlingImpl;
import com.citi.training.spring.entity.Turn;
import com.citi.training.spring.entity.TurnImpl;
import com.citi.training.spring.util.CalculatorImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by sxj on 2017/2/23.
 */
public class BowlingDaoImpl implements BowlingDao {

    private Connection connection;

    public BowlingDaoImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public Bowling query(int id) throws Exception {
        try (PreparedStatement selectBowlingStatement = connection.prepareStatement("select * from bowling where bid = ?");
             PreparedStatement selectTurnsStatement = connection.prepareStatement("select * from turn where bid = ?");) {
            selectBowlingStatement.setInt(1, id);
            selectTurnsStatement.setInt(1, id);

            ResultSet bowlingResult = selectBowlingStatement.executeQuery();
            ResultSet turnsResult = selectTurnsStatement.executeQuery();


            while (bowlingResult.next()) {
                int bid = bowlingResult.getInt("bid");
                int maxTurns = bowlingResult.getInt("max_turns");
                int score = bowlingResult.getInt("score");
                Turn[] turns = new Turn[maxTurns];

                int i = 0;
                while (turnsResult.next()) {
                    Turn t = new TurnImpl(turnsResult.getInt("tid"),
                            turnsResult.getInt("x"), turnsResult.getInt("y"), turnsResult.getInt("z"));
                    turns[i++] = t;

                }
                return new BowlingImpl(bid, maxTurns, turns, score);
            }
            connection.commit();

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public int save(Bowling bowling) throws Exception {
        try (PreparedStatement insertBowling = connection.prepareStatement(
                "INSERT INTO bowling(bid,max_turns,score) VALUES (?, ?, ?);")) {
            insertBowling.setInt(1, bowling.getBid());
            insertBowling.setInt(2, bowling.getTurns().length);
            insertBowling.setInt(3, CalculatorImpl.getInstance().calculateTotalScore(bowling));

            int result = insertBowling.executeUpdate();

            for (Turn t : bowling.getTurns()) {
                PreparedStatement insertTurns = connection.prepareStatement(
                        "INSERT INTO turn(bid, tid, x, y, z) VALUES (?, ?, ?, ?, ?)");
                insertTurns.setInt(1, bowling.getBid());
                insertTurns.setInt(2, t.getTid());
                insertTurns.setInt(3, t.getX());
                insertTurns.setInt(4, t.getY());
                insertTurns.setInt(5, t.getZ());
                int turnsResult = insertTurns.executeUpdate();
            }

            connection.commit();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }

    @Override
    public int delete(int id) throws Exception {
        try (PreparedStatement bps = connection.prepareStatement("delete from bowling where bid = ?");
        PreparedStatement tps = connection.prepareStatement("DELETE FROM turn WHERE bid = ?");) {
            bps.setInt(1, id);
            tps.setInt(1, id);
            //must need to delete turns at first
            int result2 = tps.executeUpdate();
            int result = bps.executeUpdate();
            connection.commit();
            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
