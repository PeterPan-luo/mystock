CREATE DATABASE `mystock`
    CHARACTER SET 'gbk'
    COLLATE 'gbk_chinese_ci';
    
CREATE TABLE `fundstockdetail` (
  `fundid` varchar(20) NOT NULL,
  `quarter` int(11) NOT NULL,
  `serial` int(11) DEFAULT NULL,
  `stockid` varchar(20) NOT NULL,
  `stockname` varchar(20) DEFAULT NULL,
  `stocknumber` int(11) DEFAULT NULL,
  `stockvalue` double(15,2) DEFAULT NULL,
  `ratio` float(9,2) DEFAULT NULL,
  PRIMARY KEY (`fundid`,`quarter`,`stockid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE `fund` (
  `fundid` varchar(10) NOT NULL,
  `name` varchar(40) default NULL,
  `company` varchar(40) default NULL,
  `type` varchar(20) default NULL,
  PRIMARY KEY  (`fundid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE `fundcompany` (
  `id` int(11) NOT NULL,
  `name` varchar(40) default NULL,
  `city` varchar(20) default NULL,
  `email` varchar(40) default NULL,
  `phone` varchar(60) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE `stock` (
  `stockid` varchar(10) NOT NULL,
  `name` varchar(20) default NULL,
  `circlecapital` bigint(20) default NULL,
  `totalcapital` bigint(20) default NULL,
  `listdate` int(11) default NULL,
  PRIMARY KEY  (`stockid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE `stocklist` (
  `quarter` int(11) NOT NULL,
  `stockid` varchar(10) NOT NULL,
  `stockname` varchar(20) default NULL,
  `total` bigint(20) default NULL,
  `circleratio` float(9,3) default NULL,
  PRIMARY KEY  (`quarter`,`stockid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;

CREATE TABLE `fcstocklist` (
  `quarter` int(11) NOT NULL,
  `companyid` int(11) default NULL,
  `companyname` varchar(40) default NULL,
  `stockid` varchar(10) default NULL,
  `stockname` varchar(20) default NULL,
  `stocknumber` bigint(20) default NULL,
  `circleratio` float(9,3) default NULL,
  PRIMARY KEY  (`quarter`,`companyid`,`stockid`)
) ENGINE=InnoDB DEFAULT CHARSET=gbk;