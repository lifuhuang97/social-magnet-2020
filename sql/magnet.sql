SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+08:00";
CREATE DATABASE IF NOT EXISTS `magnet` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
USE `magnet`;

# MAIN
CREATE TABLE `USERPROFILE` (
  `userID` int(3) NOT NULL AUTO_INCREMENT,
  `fullname` varchar(50) NOT NULL,
  `username` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  `rank` int(3) NOT NULL DEFAULT 1,
  `xp` int(10) NOT NULL DEFAULT 0, 
  `gold` int(15) NOT NULL DEFAULT 0,
    CONSTRAINT userprofile_pk primary key(userID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# when creating account, use INSERT INTO USERPROFILE (fullname, username, password) VALUES (fullname, username, password)

# SOCIAL MAGNET
CREATE TABLE `POST` (
  `postID` int(3) NOT NULL AUTO_INCREMENT,
  `content` varchar(300) NOT NULL,
  `datetime` varchar(16) NOT NULL,
  CONSTRAINT post_pk primary key(postID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# SOCIAL MAGNET

CREATE TABLE `USER_POST` (
  `userID` int(3) NOT NULL,
  `postID` int(3) NOT NULL,
  CONSTRAINT user_post_pk primary key (userID, postID),
  CONSTRAINT user_post_fk1 foreign key(userID) references USERPROFILE(userID),
  CONSTRAINT user_post_fk2 foreign key(postID) references POST(postID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `USER_WALL` (
  `userID` int(3) NOT NULL,
  `postID` int(3) NOT NULL,
  CONSTRAINT user_wall_pk primary key (userID, postID),
  CONSTRAINT user_wall_fk1 foreign key(userID) references USERPROFILE(userID),
  CONSTRAINT user_wall_fk2 foreign key(postID) references POST(postID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# POST CLASS - need a function to remove tag

CREATE TABLE `POST_TAGS` (
  `postID` int(3) NOT NULL,
  `tagID` int(3) NOT NULL,
  CONSTRAINT post_tags_pk primary key (postID, tagID),
  CONSTRAINT post_tags_fk1 foreign key(postID) references POST(postID),
  CONSTRAINT post_tags_fk2 foreign key(tagID) references USERPROFILE(userID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


CREATE TABLE `POST_LIKE` (
	`postID` int(3) NOT NULL,
    `userID` int(3) NOT NULL,
    CONSTRAINT post_like_pk primary key (postID, userID),
    CONSTRAINT post_like_fk1 foreign key(userID) references USERPROFILE(userID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `POST_DISLIKE` (
	`postID` int(3) NOT NULL,
    `userID` int(3) NOT NULL,
    CONSTRAINT post_dislike_pk primary key (postID, userID),
    CONSTRAINT post_dislike_fk1 foreign key(userID) references USERPROFILE(userID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# SOCIAL MAGNET
CREATE TABLE `COMMENT` (
  `commentID` int(3) NOT NULL AUTO_INCREMENT,
  `content` varchar(300) NOT NULL,
  `datetime` varchar(16) NOT NULL,
  CONSTRAINT comment_pk primary key (commentID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `USER_COMMENT` (
	`userID` int(3) NOT NULL,
    `commentID` int(3) NOT NULL,
    CONSTRAINT user_comment_pk primary key (userID, commentID),
    CONSTRAINT user_comment_fk1 foreign key(userID) references USERPROFILE(userID),
    CONSTRAINT user_comment_fk2 foreign key(commentID) references COMMENT(commentID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `POST_COMMENT` (
  `postID` int(3) NOT NULL,
  `commentID` int(3) NOT NULL,
  CONSTRAINT post_comment_pk primary key (postID, commentID),
  CONSTRAINT post_comment_fk1 foreign key(postID) references POST(postID),
  CONSTRAINT post_comment_fk2 foreign key(commentID) references COMMENT(commentID) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#SOCIAL MAGNET
CREATE TABLE `FRIENDS` (
  `userID` int(3) NOT NULL,
  `friendID` int(3) NOT NULL,
  CONSTRAINT friends_pk primary key (userID, friendID),
  CONSTRAINT friends_fk1 foreign key(userID) references USERPROFILE(userID),
  CONSTRAINT friends_fk2 foreign key(friendID) references USERPROFILE(userID)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `FRIEND_REQUESTS` (
  `requesterID` int(3) NOT NULL,
  `requestedID` int(3) NOT NULL,
  CONSTRAINT friend_request_pk primary key (requesterID, requestedID),
  CONSTRAINT friend_request_fk1 foreign key(requesterID) references USERPROFILE(userID),
  CONSTRAINT friend_request_fk2 foreign key(requestedID) references USERPROFILE(userID)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

#--------------------------------------------------------------------------------------------
-- # FARM
CREATE TABLE `CROP` ( # ABSTRACT -- need a super name
  `cropID` int(3) NOT NULL AUTO_INCREMENT, # common
  `name` varchar(30) NOT NULL, # common
  `cost` int(4) NOT NULL,  # seed
  `harvesttime` int(5) NOT NULL, #seed
  `minyield` int(5) NOT NULL, # seed
  `maxyield` int(5) NOT NULL, # seed
  `saleprice` int(5) NOT NULL, #crop
  `xp` int(10) NOT NULL, #crop
  CONSTRAINT crop_pk primary key (cropID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- # FARM
CREATE TABLE `GIFT` (
  `userID` int(3) NOT NULL,
  `friendID` int(3) NOT NULL,
  `timesent` varchar(16) NOT NULL,
  `cropID` int(3) NOT NULL,
  CONSTRAINT gift_pk primary key(userID, friendID, timesent)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


# FARM
CREATE TABLE `INVENTORY` (
  `userID` int(3) NOT NULL,
  `cropID` int(3) NOT NULL,
  `quantity` int(10) NOT NULL,
  CONSTRAINT crop_pk primary key (userID, cropID)
  # + FK1,2 for userID, crop ID
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# FARM
CREATE TABLE `PLOT` (
  `plotID` int(11) NOT NULL,
  `cropID` int(11) DEFAULT NULL,
  `plantTime` VARCHAR(16) DEFAULT NULL,
  CONSTRAINT plot_pk primary key (plotID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# PLOT SUPPORT TABLE
CREATE TABLE `USER_PLOT` (
  `userID` int(11) NOT NULL,
  `plotID` int(11) NOT NULL,
  CONSTRAINT user_plot_pk primary key (userID, plotID),
  CONSTRAINT user_plot_fk1 foreign key (plotID) references PLOT(plotID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

# FARM 
#d
CREATE TABLE `RANK` (
`rankID` int(2) NOT NULL AUTO_INCREMENT,
`rankname` VARCHAR(15) NOT NULL,
`xp` int(10) NOT NULL,
`plots` int(2) NOT NULL,
CONSTRAINT rank_pk primary key (rankID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `userprofile` (`fullname`, `username`, `password`, `gold`) VALUES ('Harvey Specter', 'harveyspecter999', 'iamthebest', 50);
INSERT INTO `userprofile` (`fullname`, `username`, `password`, `gold`) VALUES ('Louis Litt', 'louislitt1337', 'managingpartner', 50);
INSERT INTO `userprofile` (`fullname`, `username`, `password`, `gold`) VALUES ('Mike Ross', 'mikeross', 'bigbrainmike', 50);
INSERT INTO `userprofile` (`fullname`, `username`, `password`, `gold`) VALUES ('Robert Zane', 'robert_zane', 'nuggets', 50);
INSERT INTO `userprofile` (`fullname`, `username`, `password`, `gold`) VALUES ('Frank Gallo', 'frankgalloftw', 'pogchamp', 50);
INSERT INTO `userprofile` (`fullname`, `username`, `password`, `gold`) VALUES ('Jessica Pearson', 'jessicapearson', 'securepassword', 50);
INSERT INTO `userprofile` (`fullname`, `username`, `password`, `gold`) VALUES ('Jeff Malone', 'jeffmalone', 'schmeff', 50);
INSERT INTO `userprofile` (`fullname`, `username`, `password`, `gold`) VALUES ('Elon Musk', 'elonmusketeer', 'marovers', 50);


INSERT INTO `post` (`postID`, `content`, `datetime`) VALUES (NULL, 'Give me a mountain – I\'ll climb it. Give me a Katy Perry song – I\'ll sing it', '10/03/2020 12:50');
INSERT INTO `post` VALUES (NULL, 'Looking at Mars as a backup plan seems more important by the day.', '28/02/2020 06:27');
INSERT INTO `post` VALUES (NULL, 'For $52k I’ll sell you a much better computer. And it comes attached to a car.', '22/05/2019 06:27');
INSERT INTO `post` VALUES (NULL, 'Blanket that absorbs and transfers heat from one person to another based on temperature preferences.', '17/12/2019 06:27');
INSERT INTO `post` VALUES (NULL, 'All we can do is our best, and sometimes the best that we can do is to start over.', '15/11/2019 18:55');
INSERT INTO `post` VALUES (NULL, 'YOU COULD NOT LIVE WITH YOUR OWN FAILURE, AND WHERE DID THAT BRING YOU? BACK TO ME.', '14/07/2019 20:16');

# all the posts and who owns them
INSERT INTO `user_post` VALUES (2, 1);
INSERT INTO `user_post` VALUES (8, 2);
INSERT INTO `user_post` VALUES (5, 3);
INSERT INTO `user_post` VALUES (4, 4);
INSERT INTO `user_post` VALUES (5, 5);
INSERT INTO `user_post` VALUES (7, 6);

# post 1 likes and dislikes
INSERT INTO `post_like` VALUES (1, 5);
INSERT INTO `post_like` VALUES (1, 7);
INSERT INTO `post_like` VALUES (1, 8);
INSERT INTO `post_dislike` VALUES (1, 1);
INSERT INTO `post_dislike` VALUES (1, 8);

# who is tagged in the post?
INSERT INTO `post_tags` VALUES (1, 4);
INSERT INTO `post_tags` VALUES (1, 5);

#sample comments (all on post 1)
INSERT INTO `comment` (`content`, `datetime`) VALUES ("OMEGALUL", '12/03/2020 07:15');
INSERT INTO `comment` (`content`, `datetime`) VALUES ("hahahahahaha", '13/04/2020 05:19');
INSERT INTO `comment` (`content`, `datetime`) VALUES ("what the hell louis", '13/04/2020 15:57');
INSERT INTO `comment` (`content`, `datetime`) VALUES ("ure amazing", '13/04/2020 20:14');

# which post does the comment belong to
INSERT INTO `post_comment` VALUES (1, 1);
INSERT INTO `post_comment` VALUES (1, 2);
INSERT INTO `post_comment` VALUES (1, 3);
INSERT INTO `post_comment` VALUES (1, 4);

# which user owns which comment
INSERT INTO `user_comment` VALUES (1, 1);
INSERT INTO `user_comment` VALUES (8, 2);
INSERT INTO `user_comment` VALUES (6, 3);
INSERT INTO `user_comment` VALUES (3, 4);

# Everyones friends with everyone
INSERT INTO `friends` VALUES (1,2);
INSERT INTO `friends` VALUES (1,3);
INSERT INTO `friends` VALUES (1,4);
INSERT INTO `friends` VALUES (1,5);
INSERT INTO `friends` VALUES (1,6);
INSERT INTO `friends` VALUES (1,7);
INSERT INTO `friends` VALUES (1,8);
INSERT INTO `friends` VALUES (2,1);
INSERT INTO `friends` VALUES (2,3);
INSERT INTO `friends` VALUES (2,4);
INSERT INTO `friends` VALUES (2,5);
INSERT INTO `friends` VALUES (2,6);
INSERT INTO `friends` VALUES (2,7);
INSERT INTO `friends` VALUES (2,8);
INSERT INTO `friends` VALUES (3,1);
INSERT INTO `friends` VALUES (3,2);
INSERT INTO `friends` VALUES (3,4);
INSERT INTO `friends` VALUES (3,5);
INSERT INTO `friends` VALUES (3,6);
INSERT INTO `friends` VALUES (3,7);
INSERT INTO `friends` VALUES (3,8);
INSERT INTO `friends` VALUES (4,1);
INSERT INTO `friends` VALUES (4,2);
INSERT INTO `friends` VALUES (4,3);
INSERT INTO `friends` VALUES (4,5);
INSERT INTO `friends` VALUES (4,6);
INSERT INTO `friends` VALUES (4,7);
INSERT INTO `friends` VALUES (4,8);
INSERT INTO `friends` VALUES (5,1);
INSERT INTO `friends` VALUES (5,2);
INSERT INTO `friends` VALUES (5,4);
INSERT INTO `friends` VALUES (5,3);
INSERT INTO `friends` VALUES (5,6);
INSERT INTO `friends` VALUES (5,7);
INSERT INTO `friends` VALUES (5,8);
INSERT INTO `friends` VALUES (6,1);
INSERT INTO `friends` VALUES (6,2);
INSERT INTO `friends` VALUES (6,4);
INSERT INTO `friends` VALUES (6,5);
INSERT INTO `friends` VALUES (6,3);
INSERT INTO `friends` VALUES (6,7);
INSERT INTO `friends` VALUES (6,8);
INSERT INTO `friends` VALUES (7,1);
INSERT INTO `friends` VALUES (7,2);
INSERT INTO `friends` VALUES (7,4);
INSERT INTO `friends` VALUES (7,5);
INSERT INTO `friends` VALUES (7,6);
INSERT INTO `friends` VALUES (7,3);
INSERT INTO `friends` VALUES (7,8);
INSERT INTO `friends` VALUES (8,1);
INSERT INTO `friends` VALUES (8,2);
INSERT INTO `friends` VALUES (8,4);
INSERT INTO `friends` VALUES (8,5);
INSERT INTO `friends` VALUES (8,6);
INSERT INTO `friends` VALUES (8,7);
INSERT INTO `friends` VALUES (8,3);

INSERT INTO `rank` (rankname, xp, plots) VALUES ("Novice", 0, 5);
INSERT INTO `rank` (rankname, xp, plots) VALUES ("Journeyman", 2500, 7);
INSERT INTO `rank` (rankname, xp, plots) VALUES ("Apprentice", 1000, 6);
INSERT INTO `rank` (rankname, xp, plots) VALUES ("Grandmaster", 5000, 8);
INSERT INTO `rank` (rankname, xp, plots) VALUES ("Legendary", 12000, 9);

INSERT INTO `crop` (name, cost, harvesttime, xp, minyield, maxyield, saleprice) VALUES ("Papaya", 20, 30, 8, 75, 100, 15);
INSERT INTO `crop` (name, cost, harvesttime, xp, minyield, maxyield, saleprice) VALUES ("Pumpkin", 30, 60, 5, 5, 200, 20);
INSERT INTO `crop` (name, cost, harvesttime, xp, minyield, maxyield, saleprice) VALUES ("Sunflower", 40, 120, 20, 15, 20, 40);
INSERT INTO `crop` (name, cost, harvesttime, xp, minyield, maxyield, saleprice) VALUES ("Watermelon", 50, 240, 1, 5, 800, 10);

INSERT INTO `plot` (plotID, cropID, planttime) VALUES (1, 1, 1000);
INSERT INTO `plot` (plotID, cropID, planttime) VALUES (2, 2, 1100);
INSERT INTO `plot` (plotID, cropID, planttime) VALUES (3, 3, 1100);

INSERT INTO `user_plot` (userID, plotID) VALUES (3,1);
INSERT INTO `user_plot` (userID, plotID) VALUES (3,2);
INSERT INTO `user_plot` (userID, plotID) VALUES (3,3);
