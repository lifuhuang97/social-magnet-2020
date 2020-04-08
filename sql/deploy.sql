-- configuration to set SQL to optimal timezone

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+08:00";
-- Drop database if exists and recreate it
DROP DATABASE IF EXISTS MAGNET;
CREATE DATABASE `magnet` DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

-- Use this database to create and populate tables
USE `magnet`;

-- Creates USERPROFILE table to store users' data
CREATE TABLE `USERPROFILE` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `fullname` varchar(50) NOT NULL,
  `username` varchar(25) NOT NULL,
  `password` varchar(25) NOT NULL,
  `rank` int(11) NOT NULL DEFAULT 1,
  `xp` int(10) NOT NULL DEFAULT 0, 
  `gold` int(15) NOT NULL DEFAULT 50,
    CONSTRAINT userprofile_pk primary key(userID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Creates POST table to store all posts (aka threads)
DROP TABLE IF EXISTS `POST`;
CREATE TABLE `POST` (
  `postID` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(300) NOT NULL,
  `datetime` varchar(16) NOT NULL,
  CONSTRAINT post_pk primary key(postID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Creates USER_POST table to store association between users and posts
CREATE TABLE `USER_POST` (
  `userID` int(11) NOT NULL,
  `postID` int(11) NOT NULL,
  CONSTRAINT user_post_pk primary key (userID, postID),
  CONSTRAINT user_post_fk1 foreign key(userID) references USERPROFILE(userID),
  CONSTRAINT user_post_fk2 foreign key(postID) references POST(postID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Creates USER_WALL table to help distinguish where the post should be displayed
CREATE TABLE `USER_WALL` (
  `userID` int(11) NOT NULL,
  `postID` int(11) NOT NULL,
  CONSTRAINT user_wall_pk primary key (userID, postID),
  CONSTRAINT user_wall_fk1 foreign key(userID) references USERPROFILE(userID),
  CONSTRAINT user_wall_fk2 foreign key(postID) references POST(postID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Creates POST_TAGS table to store user tags in any posts
CREATE TABLE `POST_TAGS` (
  `postID` int(11) NOT NULL,
  `tagID` int(11) NOT NULL,
  CONSTRAINT post_tags_pk primary key (postID, tagID),
  CONSTRAINT post_tags_fk1 foreign key(postID) references POST(postID),
  CONSTRAINT post_tags_fk2 foreign key(tagID) references USERPROFILE(userID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Creates POST_LIKE table to store all the likes that belong to a post
CREATE TABLE `POST_LIKE` (
	`postID` int(11) NOT NULL,
    `userID` int(11) NOT NULL,
    CONSTRAINT post_like_pk primary key (postID, userID),
    CONSTRAINT post_like_fk1 foreign key(userID) references USERPROFILE(userID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Creates POST_DISLIKE table to store all the dislikes that belong to a post
CREATE TABLE `POST_DISLIKE` (
	`postID` int(11) NOT NULL,
    `userID` int(11) NOT NULL,
    CONSTRAINT post_dislike_pk primary key (postID, userID),
    CONSTRAINT post_dislike_fk1 foreign key(userID) references USERPROFILE(userID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Creates COMMENT table to store all the comments and their datetime
CREATE TABLE `COMMENT` (
  `commentID` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(300) NOT NULL,
  `datetime` varchar(16) NOT NULL,
  CONSTRAINT comment_pk primary key (commentID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Creates USER_COMMENT table to store association between users and comments
CREATE TABLE `USER_COMMENT` (
	`userID` int(11) NOT NULL,
    `commentID` int(11) NOT NULL,
    CONSTRAINT user_comment_pk primary key (userID, commentID),
    CONSTRAINT user_comment_fk1 foreign key(userID) references USERPROFILE(userID),
    CONSTRAINT user_comment_fk2 foreign key(commentID) references COMMENT(commentID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Creates POST_COMMENT table to store association between comments and posts
CREATE TABLE `POST_COMMENT` (
  `postID` int(11) NOT NULL,
  `commentID` int(11) NOT NULL,
  CONSTRAINT post_comment_pk primary key (postID, commentID),
  CONSTRAINT post_comment_fk1 foreign key(postID) references POST(postID),
  CONSTRAINT post_comment_fk2 foreign key(commentID) references COMMENT(commentID) 
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Creates FRIENDS table to store who users' have as friends
CREATE TABLE `FRIENDS` (
  `userID` int(11) NOT NULL,
  `friendID` int(11) NOT NULL,
  CONSTRAINT friends_pk primary key (userID, friendID),
  CONSTRAINT friends_fk1 foreign key(userID) references USERPROFILE(userID),
  CONSTRAINT friends_fk2 foreign key(friendID) references USERPROFILE(userID)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Creates FRIEND_REQUESTS table to store friend requests from users to users
CREATE TABLE `FRIEND_REQUESTS` (
  `requesterID` int(11) NOT NULL,
  `requestedID` int(11) NOT NULL,
  CONSTRAINT friend_request_pk primary key (requesterID, requestedID),
  CONSTRAINT friend_request_fk1 foreign key(requesterID) references USERPROFILE(userID),
  CONSTRAINT friend_request_fk2 foreign key(requestedID) references USERPROFILE(userID)  
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


-- Creates CROP table to store the details of given crops (aka data given in .csv)
CREATE TABLE `CROP` ( 
  `cropID` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(30) NOT NULL,
  `cost` int(4) NOT NULL, 
  `harvesttime` int(5) NOT NULL, 
  `minyield` int(5) NOT NULL, 
  `maxyield` int(5) NOT NULL, 
  `saleprice` int(5) NOT NULL,
  `xp` int(10) NOT NULL, 
  CONSTRAINT crop_pk primary key (cropID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- insert CROP data given from CSV
INSERT INTO `crop` (name, cost, harvesttime, xp, minyield, maxyield, saleprice) VALUES ("Papaya", 20, 30, 8, 75, 100, 15);
INSERT INTO `crop` (name, cost, harvesttime, xp, minyield, maxyield, saleprice) VALUES ("Pumpkin", 30, 60, 5, 5, 200, 20);
INSERT INTO `crop` (name, cost, harvesttime, xp, minyield, maxyield, saleprice) VALUES ("Sunflower", 40, 120, 20, 15, 20, 40);
INSERT INTO `crop` (name, cost, harvesttime, xp, minyield, maxyield, saleprice) VALUES ("Watermelon", 50, 240, 1, 5, 800, 10);

-- Create GIFT table to store gifts that are sent to friends
CREATE TABLE `GIFT` (
  `userID` int(11) NOT NULL,
  `friendID` int(11) NOT NULL,
  `postID` int(11) NOT NULL,
  `cropID` int(11) NOT NULL,
  CONSTRAINT gift_pk primary key(userID, friendID, postID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create INVENTORY table to store seeds that a user has in City Farmers
CREATE TABLE `INVENTORY` (
  `userID` int(11) NOT NULL,
  `cropID` int(11) NOT NULL,
  `quantity` int(10) NOT NULL,
  CONSTRAINT crop_pk primary key (userID, cropID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create PLOT table to store plots and what's planted on them / planted time / yield / stolen quantity
CREATE TABLE `PLOT` (
  `plotID` int(11) NOT NULL,
  `cropID` int(11) DEFAULT 0,
  `plantTime` VARCHAR(16) DEFAULT NULL,
  `produceAmt` int(11) DEFAULT 0,
  `stolen` int(2) DEFAULT 0,
  CONSTRAINT plot_pk primary key (plotID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create USER_PLOT table to store association between users and plots. This is the table where plotIDs are generated from when a new plot is created.
DROP TABLE IF EXISTS `USER_PLOT`;
CREATE TABLE `USER_PLOT` (
  `userID` int(11) NOT NULL,
  `plotID` int(11) NOT NULL AUTO_INCREMENT,
  CONSTRAINT user_plot_pk primary key (plotID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Create RANK table which stores the ranks given in the .csv file
CREATE TABLE `RANK` (
`rankID` int(3) NOT NULL AUTO_INCREMENT,
`rankname` VARCHAR(15) NOT NULL,
`xp` int(10) NOT NULL,
`plots` int(5) NOT NULL,
CONSTRAINT rank_pk primary key (rankID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- populate RANK table with given data
INSERT INTO `rank` (rankname, xp, plots) VALUES ("Novice", 0, 5);
INSERT INTO `rank` (rankname, xp, plots) VALUES ("Apprentice", 1000, 6);
INSERT INTO `rank` (rankname, xp, plots) VALUES ("Journeyman", 2500, 7);
INSERT INTO `rank` (rankname, xp, plots) VALUES ("Grandmaster", 5000, 8);
INSERT INTO `rank` (rankname, xp, plots) VALUES ("Legendary", 12000, 9);

-- Creates USER_STEAL table to store when a user has already stolen from a plot
CREATE TABLE `USER_STEAL` (
`userID` int(11) NOT NULL,
`plotID` int(11) NOT NULL,
CONSTRAINT user_steal_pk primary key (userID,plotID)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- Test Data for UserProfile
INSERT INTO `userprofile` (`fullname`, `username`, `password`, `rank`, `xp`, `gold`) VALUES ('Harvey Specter', 'harveyspecter999', 'iamthebest', 5, 15000, 10500);
INSERT INTO `userprofile` (`fullname`, `username`, `password`, `rank`, `xp`, `gold`) VALUES ('Louis Litt', 'louislitt1337', 'managingpartner', 4, 14000, 90000);
INSERT INTO `userprofile` (`fullname`, `username`, `password`, `rank`, `xp`, `gold`) VALUES ('Mike Ross', 'mikeross', 'bigbrainmike', 3, 4900, 15750);
INSERT INTO `userprofile` (`fullname`, `username`, `password`, `rank`, `xp`, `gold`) VALUES ('Robert Zane', 'robert_zane', 'nuggets', 2, 1500, 1700);
INSERT INTO `userprofile` (`fullname`, `username`, `password`, `rank`, `xp`, `gold`) VALUES ('Frank Gallo', 'frankgalloftw', 'pogchamp', 1, 800, 500);
INSERT INTO `userprofile` (`fullname`, `username`, `password`, `rank`, `xp`, `gold`) VALUES ('Jessica Pearson', 'jessicapearson', 'securepassword', 1, 500, 150);
INSERT INTO `userprofile` (`fullname`, `username`, `password`, `rank`, `xp`, `gold`) VALUES ('Jeff Malone', 'jeffmalone', 'schmeff', 2, 1700, 2000);
INSERT INTO `userprofile` (`fullname`, `username`, `password`, `rank`, `xp`, `gold`) VALUES ('Elon Musk', 'elonmusketeer', 'marovers', 3, 3600, 13750);

