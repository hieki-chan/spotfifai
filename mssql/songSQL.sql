CREATE DATABASE SPOTFIFAI

USE SPOTFIFAI

CREATE TABLE [User]
(
	userId CHAR(10) NOT NULL PRIMARY KEY,
	username NVARCHAR(50) UNIQUE,
	password NCHAR(20),
)


CREATE TABLE SONG
(
	songId NVARCHAR(20) NOT NULL PRIMARY KEY,
    title NVARCHAR(100) NOT NULL UNIQUE,
	description NVARCHAR(500),
	audioData VARBINARY(MAX),
	icon VARBINARY(MAX),
	userId CHAR(10) FOREIGN KEY REFERENCES [User](userId)
)


CREATE TABLE Playlist
(
	playlistId INT PRIMARY KEY IDENTITY (1,1),
    title NVARCHAR(100),
	userId CHAR(10) FOREIGN KEY REFERENCES [User](userId)
)

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

SELECT * FROM SONG
SELECT TOP 1 *  FROM SONG

SELECT * FROM Playlist
SELECT * FROM PlaylistDetail

SELECT * FROM [User]

INSERT INTO [User] VALUES ('admin-123', 'admin', '123')

SELECT *
FROM Playlist p
JOIN PlaylistDetail pd ON p.playlistId = pd.playlistId
WHERE p.userId = 'admin-123'
ORDER BY p.playlistId

SELECT TOP 3 *  FROM PlaylistDetail

SELECT songId, title, description, userId FROM Song 

DELETE FROM SONG
WHERE userId is NULL

DELETE FROM PlaylistDetail
DELETE FROM Playlist