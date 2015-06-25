SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS  `activityinfo`;
CREATE TABLE `activityinfo` (
  `ActivityInfo` varchar(32) NOT NULL,
  `UserID` varchar(32) NOT NULL,
  `Title` varchar(50) NOT NULL,
  `Content` varchar(255) NOT NULL,
  `StartTime` datetime NOT NULL,
  `EndTime` datetime NOT NULL,
  `Place` varchar(50) NOT NULL,
  `UserCount` int(11) NOT NULL DEFAULT '1',
  `IsChecked` int(6) NOT NULL,
  PRIMARY KEY (`ActivityInfo`),
  KEY `Users` (`UserID`),
  CONSTRAINT `Users` FOREIGN KEY (`UserID`) REFERENCES `usersinfo` (`UserID`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `application`;
CREATE TABLE `application` (
  `UserID` varchar(32) NOT NULL,
  `ActivityID` varchar(32) NOT NULL,
  `Time` datetime NOT NULL,
  PRIMARY KEY (`UserID`,`ActivityID`),
  KEY `Activityss` (`ActivityID`),
  CONSTRAINT `Activityss` FOREIGN KEY (`ActivityID`) REFERENCES `activityinfo` (`ActivityInfo`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `userss` FOREIGN KEY (`UserID`) REFERENCES `usersinfo` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `commentinfo`;
CREATE TABLE `commentinfo` (
  `CommentID` varchar(32) NOT NULL,
  `UserID` varchar(32) NOT NULL,
  `ActivityID` varchar(32) NOT NULL,
  `Content` varchar(255) NOT NULL,
  `Time` datetime NOT NULL,
  PRIMARY KEY (`CommentID`),
  KEY `usersss` (`UserID`),
  KEY `Activitysss` (`ActivityID`),
  CONSTRAINT `usersss` FOREIGN KEY (`UserID`) REFERENCES `usersinfo` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Activitysss` FOREIGN KEY (`ActivityID`) REFERENCES `activityinfo` (`ActivityInfo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `good`;
CREATE TABLE `good` (
  `ToID` varchar(32) NOT NULL,
  `FromID` varchar(32) NOT NULL,
  `Time` datetime NOT NULL,
  PRIMARY KEY (`ToID`,`FromID`),
  KEY `From` (`FromID`),
  CONSTRAINT `To` FOREIGN KEY (`ToID`) REFERENCES `usersinfo` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `From` FOREIGN KEY (`FromID`) REFERENCES `usersinfo` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `informinfo`;
CREATE TABLE `informinfo` (
  `InformID` varchar(32) NOT NULL,
  `TargetID` varchar(32) NOT NULL,
  `Form` varchar(32) NOT NULL,
  `Time` datetime NOT NULL,
  `Content` varchar(255) NOT NULL,
  `IsRead` int(5) NOT NULL,
  PRIMARY KEY (`InformID`),
  KEY `ToID` (`TargetID`),
  CONSTRAINT `ToID` FOREIGN KEY (`TargetID`) REFERENCES `usersinfo` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `participation`;
CREATE TABLE `participation` (
  `UserID` varchar(32) NOT NULL,
  `ActivityID` varchar(32) NOT NULL,
  `Time` datetime NOT NULL,
  PRIMARY KEY (`UserID`,`ActivityID`),
  KEY `Activity` (`ActivityID`),
  CONSTRAINT `User` FOREIGN KEY (`UserID`) REFERENCES `usersinfo` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Activity` FOREIGN KEY (`ActivityID`) REFERENCES `activityinfo` (`ActivityInfo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `permissioninfo`;
CREATE TABLE `permissioninfo` (
  `PermissionID` int(11) NOT NULL AUTO_INCREMENT,
  `Name` varchar(40) NOT NULL,
  `CreateTime` datetime NOT NULL,
  PRIMARY KEY (`PermissionID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `photoinfo`;
CREATE TABLE `photoinfo` (
  `PhotoID` varchar(32) NOT NULL,
  `UserID` varchar(32) NOT NULL,
  `ActivityID` varchar(32) NOT NULL,
  `Address` varchar(40) NOT NULL,
  `Title` varchar(40) NOT NULL,
  `Describe` varchar(255) NOT NULL,
  PRIMARY KEY (`PhotoID`),
  KEY `userssss` (`UserID`),
  KEY `Activityssss` (`ActivityID`),
  CONSTRAINT `userssss` FOREIGN KEY (`UserID`) REFERENCES `usersinfo` (`UserID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Activityssss` FOREIGN KEY (`ActivityID`) REFERENCES `activityinfo` (`ActivityInfo`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `roleinfo`;
CREATE TABLE `roleinfo` (
  `RoleID` int(32) NOT NULL AUTO_INCREMENT,
  `Name` varchar(32) NOT NULL,
  `CreateTime` datetime NOT NULL,
  `RoleCount` int(11) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`RoleID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `rolepermission`;
CREATE TABLE `rolepermission` (
  `RoleID` int(11) NOT NULL,
  `PermissionID` int(11) NOT NULL,
  `Time` datetime NOT NULL,
  PRIMARY KEY (`RoleID`,`PermissionID`),
  KEY `Permission` (`PermissionID`),
  CONSTRAINT `Role` FOREIGN KEY (`RoleID`) REFERENCES `roleinfo` (`RoleID`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Permission` FOREIGN KEY (`PermissionID`) REFERENCES `permissioninfo` (`PermissionID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS  `usersinfo`;
CREATE TABLE `usersinfo` (
  `UserID` varchar(32) NOT NULL,
  `Account` varchar(50) NOT NULL,
  `Password` varchar(50) NOT NULL,
  `Phone` varchar(50) DEFAULT NULL,
  `Mailbox` varchar(50) DEFAULT NULL,
  `QQ` varchar(32) DEFAULT NULL,
  `WeiBo` varchar(50) DEFAULT NULL,
  `IsDisable` int(5) NOT NULL,
  `LastLoginIP` varchar(32) NOT NULL,
  `LastLoginTime` datetime NOT NULL,
  `IsOnline` int(11) NOT NULL,
  `RoleID` int(11) NOT NULL,
  PRIMARY KEY (`UserID`),
  KEY `roles` (`RoleID`),
  CONSTRAINT `roles` FOREIGN KEY (`RoleID`) REFERENCES `roleinfo` (`RoleID`) ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

SET FOREIGN_KEY_CHECKS = 1;

