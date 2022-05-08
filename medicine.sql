create table `medicine`( 
    `id` char(10), 
    `effective_date` date,/*YYYY-MM-DD*/
    `storehouse_id` varchar(100),
    `brand` varchar(100),
    `name` varchar(100), 
    `function` varchar(100),
    `price` float,
    `stock` int,
    primary key(id,effective_date,storehouse_id))engine=InnoDB default charset= utf8;

create table `picture`(
    `name` varchar(100),
    `brand` varchar(100),
    `url` varchar(256),
    primary key(name,brand)
)engine=InnoDB default charset= utf8;

create table `administrator`( 
    `ano` char(10),
    `aname` char(100),
    `password` char(100), 
    `phonenumber` char(100),
    primary key(ano))engine=InnoDB default charset= utf8;

create table `log`( 
    `ano` char(10),
    `option` char(100),
    `id` char(10), 
    `effective_date` date,/*YYYY-MM-DD*/
    `storehouse_id` varchar(100),
    `stock` int,
    foreign key (ano) references administrator(ano)
    )engine=InnoDB default charset= utf8;

create table shoppingCart(
	`user_id` char(10),
	`medicine_id` char(10),
	`num` int,
    `storehouse_id` varchar(100),
	primary key(user_id,medicine_id,storehouse_id)
)engine=InnoDB default charset= utf8;