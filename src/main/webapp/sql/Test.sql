CREATE TABLE MEMBER(
	MID VARCHAR(10) PRIMARY KEY,
	MPW VARCHAR(10) NOT NULL,
	MNAME VARCHAR(20) NOT NULL
);
CREATE TABLE BOARD(
	BID INT PRIMARY KEY ,
	MID VARCHAR(10) NOT NULL, -- FK는 반드시 상대 테이블의 PK를 사용
	MSG VARCHAR(50) NOT NULL,
	FAVCNT INT DEFAULT 0,
	RCNT INT DEFAULT 0
);
CREATE TABLE REPLY(
	RID INT PRIMARY KEY ,
	MID VARCHAR(10) NOT NULL,
	BID INT NOT NULL,
	RMSG VARCHAR(30) NOT NULL,
	CONSTRAINT BOARD_REPLY FOREIGN KEY (BID) REFERENCES BOARD (BID) ON DELETE CASCADE
);

INSERT INTO BOARD VALUES((SELECT NVL(MAX(BID), 0) + 1 FROM BOARD), '1', '11', 0, 0);
INSERT INTO BOARD VALUES((SELECT NVL(MAX(BID), 0) + 1 FROM BOARD), '2', '22', 0, 0);
INSERT INTO BOARD VALUES((SELECT NVL(MAX(BID), 0) + 1 FROM BOARD), '3', '33', 0, 0);
INSERT INTO BOARD VALUES((SELECT NVL(MAX(BID), 0) + 1 FROM BOARD), '4', '44', 0, 0);
INSERT INTO BOARD VALUES((SELECT NVL(MAX(BID), 0) + 1 FROM BOARD), '5', '55', 0, 0);
INSERT INTO BOARD VALUES((SELECT NVL(MAX(BID), 0) + 1 FROM BOARD), '6', '66', 0, 0);
INSERT INTO BOARD (BID, MID, MSG) VALUES((SELECT NVL(MAX(BID), 0) + 1 FROM BOARD), '7', '77');

SELECT * FROM MEMBER ORDER BY ROWNUM DESC;
SELECT * FROM USER_TABLES;
SELECT * FROM MEMBER;
SELECT * FROM BOARD;
SELECT * FROM REPLY;

DROP TABLE BOARD;
DROP TABLE MEMBER;
DROP TABLE REPLY;