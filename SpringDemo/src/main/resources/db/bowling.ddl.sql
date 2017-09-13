DROP TABLE IF EXISTS bowling;
DROP TABLE IF EXISTS turn;
CREATE TABLE bowling(bid int PRIMARY KEY, max_turns int, score int);
CREATE TABLE turn(bid int, tid int, x int, y int, z int, PRIMARY KEY(bid, tid), FOREIGN KEY (bid) REFERENCES bowling(bid));

INSERT INTO bowling(bid, max_turns, score) VALUES (1, 3, 37);
INSERT INTO turn(bid, tid, x, y, z) VALUES (1, 0, 10, 0, 0);
INSERT INTO turn(bid, tid, x, y, z) VALUES (1, 1, 2, 7, 0);
INSERT INTO turn(bid, tid, x, y, z) VALUES (1, 2, 8, 1, 0);

INSERT INTO bowling(bid, max_turns, score) VALUES (2, 3, 27);
INSERT INTO turn(bid, tid, x, y, z) VALUES (2, 0, 8, 1, 0);
INSERT INTO turn(bid, tid, x, y, z) VALUES (2, 1, 2, 7, 0);
INSERT INTO turn(bid, tid, x, y, z) VALUES (2, 2, 8, 1, 0);
