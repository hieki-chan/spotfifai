CREATE DATABASE SPOTFIFAI

USE SPOTFIFAI

DROP TABLE SONG
CREATE TABLE SONG
(
	id INT PRIMARY KEY IDENTITY(1,1),
    name NVARCHAR(100),
	description NTEXT,
)


INSERT INTO SONG (name, description) 
VALUES (N'Bohemian Rhapsody', N'Một bản nhạc huyền thoại của Queen.');

INSERT INTO SONG (name, description) 
VALUES (N'Stairway to Heaven', N'Ca khúc nổi tiếng của Led Zeppelin.');

INSERT INTO SONG (name, description) 
VALUES (N'Imagine', N'Bài hát biểu tượng của John Lennon.');

INSERT INTO SONG (name, description) 
VALUES (N'Let It Be', N'Tác phẩm nổi tiếng của The Beatles.');

INSERT INTO SONG (name, description) 
VALUES (N'Shape of You', N'Một hit toàn cầu của Ed Sheeran.');

SELECT * FROM SONG

CREATE TABLE Playlist
(
	id INT PRIMARY KEY,
    name NVARCHAR(100),
)