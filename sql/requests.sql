-- Inline comment

/* Multiline
comment */ 


-- USER RESQUEST

--Create a new user
INSERT INTO user (`nickname`, `email`, `password`, `account_creation`) VALUES ('user3', 'email3', 'qwerty3', '2024-10-28 15:03:05.000000');

-- Return all user
SELECT * FROM user;

-- Return user id with the email "user1" -> Used to check if a account already have that email
SELECT * FROM user WHERE email="user1";


-- Return user id with the nickname "user1" -> Used to check if a account already have that nickname
SELECT * FROM user WHERE nickname="user1";


-- Used to check credential. If correct, return the used id, otherwise return nothing
SELECT id FROM user WHERE nickname="user1" AND `password` = "qwerty";





-- STATISTICS REQUEST

-- Find number of game win by player with id 2
SELECT COUNT(*) FROM `match` WHERE winner_id = 2 AND (`status` = "FINISHED" OR `status` = "ABANDONED");

-- Find number of game lost by player with id 2
SELECT COUNT(*) FROM `match` WHERE looser_id = 2 AND `status` = "FINISHED";

-- Find number of game abandoned by player with id 2
SELECT COUNT(*) FROM `match` WHERE looser_id = 2 AND  `status` = "ABANDONED";

--Find number of tie for player with id 2
SELECT COUNT(*) FROM `match` WHERE (player_1 = 2 OR player_2 = 2) AND `status` = "TIE" ;

-- Find the number of match we play in or have played
SELECT COUNT(*) FROM `match` WHERE player_1 = 2 OR player_2 = 2;


--Find number of match player 2 have played  (match is completed)
SELECT COUNT(*) FROM `match` WHERE (player_1 = 2 OR player_2 = 2) AND (`status` = "FINISHED" OR `status` = "ABANDONED" OR`status` = "ABANDONED");

--Find number ot match player 2 is currently playing
SELECT COUNT(*) FROM `match` WHERE (player_1 = 2 OR player_2 = 2) AND `status` = "PLAYING" ;





-- COORDINATES RESQUEST

-- You do not need to create any coordinates, they are all created

-- To find the id of a coordianates
SELECT id FROM `coordinates` WHERE (x = 2 AND y = 2);





-- INVITATION REQUEST

-- Create a invitation (will be automaticaly set to PENDING
INSERT INTO `invitation` (`sender_id`, `receiver_id`, `date_created`) VALUES (5, 2, '2024-10-28 15:26:11');

-- Update a invitation (DECLINED/ACCEPTED/CANCELLED)
UPDATE `invitation` SET `status`='DECLINED' WHERE  `id`=1;

-- Find all inviation for a player still pending
SELECT * FROM `invitation` WHERE receiver_id = 2 AND `status`='PENDING'

-- Find all inviation that a player send and are still prending
SELECT * FROM `invitation` WHERE sender_id = 2 AND `status`='PENDING'




-- NOTIFICATION REQUEST

-- Creation
INSERT INTO `notification` (`player_id`, `message`, `isRead`, `date_created`) VALUES (3, 'A notification !', 0, '2024-10-27 15:33:06');

-- Find all notification that are not read
SELECT * FROM notification WHERE isRead = 0;

--Update a notification
UPDATE `notification` SET `isRead`=1 WHERE  `id`=2;




-- MATCH / PIECE / MOVE RESQUESTS

/* 
Note: you can not have to piece at the same location for the same game. The database will not allow it
Also, when a piece was killed, is position will be null
 */

-- Create a match (does not create the piece)
INSERT INTO `match` (`player_1`, `player_2`, `start_time`, `status`) VALUES (5, 3, '2024-10-28 15:37:40', 'PLAYING');

--Create a piece
INSERT INTO`piece` ( `location`, `type`, `match_id`, `color`) VALUES ( 3, 'QUEEN', 1, 'BLACK');

--Create move
INSERT INTO `move` (`match_id`, `time`, `start_location`, `end_location`, `piece_id`) VALUES (3, '2024-10-28 16:00:51.000000', 3, 3, 4);

-- Complete match
UPDATE `match` SET `end_time`='2024-11-06 15:40:21', `status`='FINISHED', `winner_id`=3, `looser_id`=5 WHERE `id`=5;

-- Find all piece for a match
SELECT * FROM piece WHERE match_id = 3

-- Find all piece for a match that was killed
SELECT * FROM piece WHERE match_id = 4 AND location IS NULL;


-- Find a piece at a location 
SELECT piece.* FROM piece INNER JOIN coordinates ON piece.location = coordinates.id WHERE piece.match_id = 4 AND coordinates.x = 0 AND coordinates.y = 4;

-- update a piece
UPDATE `piece` SET `location`=9 WHERE  `id`=7;
UPDATE `piece` SET `type`='KNIGHT' WHERE  `id`=7;

-- find history of move
SELECT * FROM `move` WHERE match_id = 3 ORDER BY `time` DESC;




