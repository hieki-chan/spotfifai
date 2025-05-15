CREATE DATABASE SPOTFIFAI

USE SPOTFIFAI

DROP TABLE [User]
CREATE TABLE [User]
(
	userId CHAR(10) NOT NULL PRIMARY KEY,
	username NVARCHAR(50),
	password NCHAR(20),
)


DROP TABLE SONG
CREATE TABLE SONG
(
	songId NVARCHAR(20) NOT NULL PRIMARY KEY,
    name NVARCHAR(100) NOT NULL,
	description NVARCHAR,
	userId CHAR(10) FOREIGN KEY REFERENCES [User](userId)
)


INSERT INTO SONG (name, description) 
VALUES (N'Bohemian Rhapsody', N'Một bản nhạc huyền thoại của Queen.');


SELECT * FROM SONG

DROP TABLE Playlist
CREATE TABLE Playlist
(
	playlistId INT NOT NULL PRIMARY KEY,
    name NVARCHAR(100),
	userId CHAR(10) FOREIGN KEY REFERENCES [User](userId)
)

DROP TABLE PlaylistDetail
CREATE TABLE PlaylistDetail
(
	playlistId INT NOT NULL,
	songId NVARCHAR(20),
	PRIMARY KEY (playlistId, songId),
	FOREIGN KEY (playlistId) REFERENCES Playlist(playlistId),
	FOREIGN KEY (songId) REFERENCES Song(songId)
)


DROP TABLE PlaylistDetail
DROP TABLE Playlist
DROP TABLE Song
DROP TABLE [User]