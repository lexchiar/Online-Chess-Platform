-- YOU NEED TO ADD THE TABLE IN A DIFFERENT OHTER. Othewise you will have problem with foreing keys.


-- Dumping structure for table coordinates
DROP TABLE IF EXISTS `coordinates`;
CREATE TABLE IF NOT EXISTS `coordinates` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `x` int(11) NOT NULL,
  `y` int(11) NOT NULL,
  UNIQUE KEY `Index 2` (`x`,`y`),
  KEY `Index 1` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=66 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table coordinates: ~64 rows (approximately)
DELETE FROM `coordinates`;
INSERT INTO `coordinates` (`id`, `x`, `y`) VALUES
	(1, 0, 0),
	(2, 0, 1),
	(3, 0, 2),
	(4, 0, 3),
	(5, 0, 4),
	(6, 0, 5),
	(7, 0, 6),
	(8, 0, 7),
	(9, 1, 0),
	(10, 1, 1),
	(11, 1, 2),
	(12, 1, 3),
	(13, 1, 4),
	(14, 1, 5),
	(15, 1, 6),
	(16, 1, 7),
	(17, 2, 0),
	(18, 2, 1),
	(19, 2, 2),
	(20, 2, 3),
	(21, 2, 4),
	(22, 2, 5),
	(23, 2, 6),
	(24, 2, 7),
	(25, 3, 0),
	(26, 3, 1),
	(27, 3, 2),
	(29, 3, 3),
	(30, 3, 4),
	(31, 3, 5),
	(32, 3, 6),
	(33, 3, 7),
	(34, 4, 0),
	(35, 4, 1),
	(36, 4, 2),
	(37, 4, 3),
	(38, 4, 4),
	(39, 4, 5),
	(40, 4, 6),
	(41, 4, 7),
	(42, 5, 0),
	(43, 5, 1),
	(44, 5, 2),
	(45, 5, 3),
	(46, 5, 4),
	(47, 5, 5),
	(48, 5, 6),
	(49, 5, 7),
	(50, 6, 0),
	(51, 6, 1),
	(52, 6, 2),
	(53, 6, 3),
	(54, 6, 4),
	(55, 6, 5),
	(56, 6, 6),
	(57, 6, 7),
	(58, 7, 0),
	(59, 7, 1),
	(60, 7, 2),
	(61, 7, 3),
	(62, 7, 4),
	(63, 7, 5),
	(64, 7, 6),
	(65, 7, 7);

-- Dumping structure for table invitation
DROP TABLE IF EXISTS `invitation`;
CREATE TABLE IF NOT EXISTS `invitation` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `sender_id` int(11) NOT NULL,
  `receiver_id` int(11) NOT NULL,
  `date_created` datetime NOT NULL DEFAULT current_timestamp(),
  `status` enum('PENDING','ACCEPTED','DECLINED','CANCELLED') NOT NULL DEFAULT 'PENDING',
  KEY `Index 1` (`id`),
  KEY `invitation_to_user_for_sender_id` (`sender_id`),
  KEY `invitation_to_user_for_receiver_id` (`receiver_id`),
  CONSTRAINT `invitation_to_user_for_receiver_id` FOREIGN KEY (`receiver_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `invitation_to_user_for_sender_id` FOREIGN KEY (`sender_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table
DELETE FROM `invitation`;


-- Dumping structure for table match
DROP TABLE IF EXISTS `match`;
CREATE TABLE IF NOT EXISTS `match` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_1` int(11) NOT NULL,
  `player_2` int(11) NOT NULL,
  `turn_count` int(11) NOT NULL DEFAULT 0,
  `start_time` datetime NOT NULL,
  `end_time` datetime DEFAULT NULL,
  `status` enum('NOT_STARTED','PLAYING','ABANDONED','FINISHED','TIE') NOT NULL DEFAULT 'NOT_STARTED',
  `winner_id` int(11) DEFAULT NULL,
  `looser_id` int(11) DEFAULT NULL,
  KEY `Index 1` (`id`),
  KEY `match_to_user_for_player1` (`player_1`),
  KEY `match_to_user_for_player2` (`player_2`),
  KEY `match_to_user_for_winner_id` (`winner_id`),
  KEY `match_to_user_for_looser_id` (`looser_id`),
  CONSTRAINT `match_to_user_for_looser_id` FOREIGN KEY (`looser_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `match_to_user_for_player1` FOREIGN KEY (`player_1`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `match_to_user_for_player2` FOREIGN KEY (`player_2`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `match_to_user_for_winner_id` FOREIGN KEY (`winner_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table match
DELETE FROM `match`;


-- Dumping structure for table move
DROP TABLE IF EXISTS `move`;
CREATE TABLE IF NOT EXISTS `move` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `match_id` int(11) NOT NULL,
  `time` datetime(6) NOT NULL,
  `start_location` int(11) NOT NULL COMMENT '	',
  `end_location` int(11) NOT NULL,
  `piece_id` int(11) NOT NULL,
  KEY `Index 1` (`id`),
  KEY `move_to_match` (`match_id`),
  KEY `move_to_coordinates_for_start_location` (`start_location`),
  KEY `move_to_coordinates_for_end_location` (`end_location`),
  CONSTRAINT `move_to_coordinates_for_end_location` FOREIGN KEY (`end_location`) REFERENCES `coordinates` (`id`),
  CONSTRAINT `move_to_coordinates_for_start_location` FOREIGN KEY (`start_location`) REFERENCES `coordinates` (`id`),
  CONSTRAINT `move_to_match` FOREIGN KEY (`match_id`) REFERENCES `match` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table move: 
DELETE FROM `move`;


-- Dumping structure for table notification
DROP TABLE IF EXISTS `notification`;
CREATE TABLE IF NOT EXISTS `notification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `player_id` int(11) NOT NULL,
  `message` varchar(250) NOT NULL DEFAULT '',
  `isRead` int(11) NOT NULL DEFAULT 0,
  `date_created` datetime NOT NULL,
  KEY `Index 1` (`id`),
  KEY `notification_to_player` (`player_id`),
  CONSTRAINT `notification_to_player` FOREIGN KEY (`player_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table notification: 
DELETE FROM `notification`;


-- Dumping structure for table piece
DROP TABLE IF EXISTS `piece`;
CREATE TABLE IF NOT EXISTS `piece` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `location` int(11) DEFAULT NULL,
  `type` enum('PAWN','KNIGHT','ROOK','BISHOP','KING','QUEEN') NOT NULL,
  `match_id` int(11) NOT NULL,
  `color` enum('BLACK','WHITE') NOT NULL,
  UNIQUE KEY `Index 4` (`match_id`,`location`),
  KEY `Index 1` (`id`),
  KEY `piece_to_coordinates` (`location`),
  CONSTRAINT `piece_to_coordinates` FOREIGN KEY (`location`) REFERENCES `coordinates` (`id`),
  CONSTRAINT `piece_to_match` FOREIGN KEY (`match_id`) REFERENCES `match` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table piece: 
DELETE FROM `piece`;

-- Dumping structure for table user
DROP TABLE IF EXISTS `user`;
CREATE TABLE IF NOT EXISTS `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nickname` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(100) NOT NULL,
  `account_creation` datetime(6) NOT NULL,
  UNIQUE KEY `Index 2` (`nickname`),
  UNIQUE KEY `Index 3` (`email`),
  KEY `Index 1` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- Dumping data for table user: 
DELETE FROM `user`;

