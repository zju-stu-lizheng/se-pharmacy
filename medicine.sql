CREATE TABLE `administrator` (
  `ano` char(10) NOT NULL,
  `aname` char(100) DEFAULT NULL,
  `password` char(100) DEFAULT NULL,
  `phonenumber` char(100) DEFAULT NULL,
  PRIMARY KEY (`ano`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `bill` (
  `bill_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` char(10) DEFAULT NULL,
  `storehouse_id` varchar(100) DEFAULT NULL,
  `order_date` date DEFAULT NULL,
  `paid_date` date DEFAULT NULL,
  `isPaid` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`bill_id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;

CREATE TABLE `db_drugs` (
  `id` char(10) NOT NULL COMMENT '药品id',
  `brand` varchar(255) DEFAULT NULL COMMENT '药品商标',
  `name` varchar(255) DEFAULT NULL COMMENT '药品名称',
  `function` text COMMENT '功能',
  `dosage` text COMMENT '用法用量',
  `banned` text COMMENT '禁用人群',
  `unit` varchar(255) DEFAULT NULL COMMENT '药品单位',
  `prescription` tinyint(1) DEFAULT '0' COMMENT '是否为处方药',
  `picture` varchar(255) DEFAULT NULL COMMENT '图片',
  `price` float DEFAULT '46',
  PRIMARY KEY (`id`) USING BTREE,
  KEY `picture_index` (`picture`)
) ENGINE=MyISAM AUTO_INCREMENT=56978 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='药品表';

CREATE TABLE `se_queue` (
  `bill_id` int(11) DEFAULT NULL,
  `storehouse_id` varchar(100) DEFAULT NULL,
  `qid` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`qid`),
  UNIQUE KEY `bill_id` (`bill_id`),
  CONSTRAINT `se_queue_ibfk_1` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`bill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `se_window` (
  `bill_id` int(11) DEFAULT NULL,
  `storehouse_id` varchar(100) DEFAULT NULL,
  `wid` char(100) DEFAULT NULL,
  UNIQUE KEY `bill_id` (`bill_id`),
  CONSTRAINT `window_ibfk_1` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`bill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `shoppingcart` (
  `user_id` char(10) NOT NULL,
  `medicine_id` char(10) NOT NULL,
  `num` int(11) DEFAULT NULL,
  `storehouse_id` varchar(100) NOT NULL,
  `bill_id` int(11) NOT NULL,
  PRIMARY KEY (`user_id`,`medicine_id`,`storehouse_id`,`bill_id`),
  KEY `bill_id` (`bill_id`),
  CONSTRAINT `shoppingcart_ibfk_1` FOREIGN KEY (`bill_id`) REFERENCES `bill` (`bill_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `medicine` (
  `id` char(10) NOT NULL,
  `effective_date` date NOT NULL,
  `storehouse_id` varchar(100) NOT NULL,
  `stock` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`,`effective_date`,`storehouse_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


insert into administrator VALUES('001','lizheng','yp','123456');
