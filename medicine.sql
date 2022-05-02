create table `medicine`( 
    `id` char(10), 
    `storehouse_id` char(2),
    `name` varchar(100), 
    `function` varchar(100),
    `effective_date` date,
    `price` float,
    `stock` int,
    primary key(id))engine=InnoDB default charset= utf8;