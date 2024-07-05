/*CREATE DATABASE CN_Electronic_Shop;
       USE CN_Electronic_Shop;

CREATE TABLE user(
                     user_ID int auto_increment primary key ,
                     contact varchar(10) not null ,
                     email varchar(30) not null ,
                     user_Name varchar(10)  not null unique ,
                     password varchar(8) not null,
                     fixed_Code varchar(8) not null
);

CREATE TABLE tool_kit(
                         code int primary key ,
                         name text
);

CREATE TABLE item(
                         code int primary key ,
                         name text not null ,
                         status text not null ,
                         unit_price decimal(5,2)  not null ,
                         qty int  not null ,
                         user_ID int ,
                         constraint foreign key (user_ID) references user(user_ID) on DELETE cascade on UPDATE cascade

);


CREATE TABLE technisiyan
(
                            tech_ID int  primary key ,
                            user_ID int  ,
                            code int  ,
                            name varchar(155) not null ,
                            nic varchar(12) not null ,
                            bank_name varchar(50),
                            account_num int,
                            address text not null ,
                            con_number varchar(10) not null ,
                            attend varchar(3)  ,
                            constraint foreign key (user_ID) references user(user_ID) on DELETE cascade on UPDATE cascade ,
                            constraint foreign key (code) references tool_kit(code) on DELETE cascade on UPDATE cascade
);



CREATE TABLE salary (
                        salary_ID int  primary key,
                        tech_ID int,
                        basic_salary decimal(6,2) not null,
                        OT_pay_hour decimal(5,2) not null,
                        ot_hours double not null,
                        full_salary decimal(10,2) not null,
                        constraint foreign key (tech_ID) references technisiyan(tech_ID) on UPDATE cascade on DELETE cascade
);

CREATE TABLE machine(
                        machine_ID  int  primary key,
                        user_ID int,
                        m_name varchar(200) ,
                        description text not null ,
                        constraint foreign key (user_ID) references user(user_ID) on DELETE cascade on UPDATE cascade
);

CREATE TABLE machine_detail(
                               machine_ID int ,
                               tech_ID int ,
                               constraint foreign key (machine_ID) references machine(machine_ID) on UPDATE cascade on DELETE cascade ,
                               constraint foreign key (tech_ID) references technisiyan(tech_ID) on DELETE cascade on UPDATE cascade
);



CREATE TABLE supplier (
                          supp_ID int  primary key,
                          code int ,
                          user_ID int,
                          s_name varchar(50) not null,
                          contact_num varchar(10) not null,
                          email varchar(30) not null,
                          company_name text not null,
                          description text not null,
                          qty int not null,
                          constraint fk_item_code foreign key (code) references item(code) on DELETE cascade on UPDATE cascade,
                          constraint fk_user_ID foreign key (user_ID) references user(user_ID) on DELETE cascade on UPDATE cascade
);

CREATE TABLE ordinary_cus (
                              cus_ID int  primary key,
                              date date
);

CREATE TABLE orders (
                        order_id int  primary key,
                        cus_ID int,
                        constraint foreign key (cus_ID) references ordinary_cus(cus_ID) on UPDATE cascade on DELETE cascade

);

CREATE TABLE order_detail(
                             order_id int  ,
                             item_Code int ,
                             qty int ,
                             unit_price decimal(5,2) ,
                             constraint foreign key (item_Code) references item(code) on DELETE cascade on UPDATE cascade ,
                             constraint foreign key (order_id) references orders(order_ID) on DELETE cascade on UPDATE cascade
);

CREATE TABLE permenent_cus (
                               reg_id int  primary key,
                               cus_ID int,
                               code int,
                               order_ID int,
                               date date not null ,
                               name varchar(200) not null,
                               address text not null,
                               contact varchar(10) not null,


                               status text ,
                               qty int,
                               constraint foreign key (order_ID) references orders(order_ID) on DELETE cascade on UPDATE cascade,
                               constraint foreign key (code) references item(code) on DELETE cascade on UPDATE cascade,
                               constraint foreign key (cus_ID) references ordinary_cus(cus_ID) on UPDATE cascade on DELETE cascade
);

CREATE TABLE construction_order(
                                   con_order_ID int primary key ,
                                   cus_ID int  ,
                                   stater text  ,
                                   constraint foreign key (cus_ID) references ordinary_cus(cus_ID) on UPDATE cascade on DELETE cascade
);

CREATE TABLE construction_payment(
                                     con_payment_ID int  primary key ,
                                     con_order_ID int not null ,
                                     cus_ID int not null ,
                                     full_payment decimal(10,2) not null ,
                                     pay_advance decimal(10,2) not null ,
                                     remaining_payment decimal(10,2) not null ,
                                     constraint foreign key (con_order_ID) references construction_order(con_order_ID) on UPDATE cascade on DELETE cascade ,
                                     constraint foreign key (cus_ID) references ordinary_cus(cus_ID) on UPDATE cascade on DELETE cascade
);



CREATE TABLE construction_technisiyan_detail(
                                             con_order_ID int not null ,
                                             tech_ID int  ,
                                             tech_count int ,
                                             constraint foreign key (con_order_ID) references construction_order(con_order_ID) on DELETE cascade on UPDATE cascade ,
                                             constraint foreign key (tech_ID) references technisiyan(tech_ID) on UPDATE cascade on DELETE cascade
);


insert into user(contact, email, user_Name, password, fixed_Code) values ('0772032675', 'cnelectricalcompany@gmail.com', 'thathsara', 'CN123456', '20070113');

INSERT INTO tool_kit (code, name) VALUES
                                       (1, 'Hammer'),
                                       (2,'Screwdriver'),
                                       (3,'Wrench'),
                                       (4,'Pliers'),
                                       (5,'Drill'),
                                       (6,'Saw'),
                                       (7,'Tape Measure');

INSERT INTO item (code, name, status, unit_price, qty, user_ID) VALUES
                                                                    (1001, 'Item A', 'available', 10.99, 50, 1),
                                                                    (1002, 'Item B', 'out of stock', 15.49, 0, 1);

INSERT INTO technisiyan (tech_ID, user_ID, code, name, nic, bank_name, account_num, address, con_number, attend)
VALUES
    (1, 1, 1, 'John Doe', '123456789V', 'Bank A', 123456789, '123 Main St', '0123456789', 'Y'),
    (2, 1, 2, 'Jane Smith', '987654321V', 'Bank B', 125697653, '456 Elm St', '0987654321', 'N'),
    (3, 1, 3, 'Mike Johnson', '192837465V', 'Bank C', 251976359, '789 Oak St', '0192837465', 'Y'),
    (4, 1, 4, 'Emily Davis', '564738291V', 'Bank D', 789564286, '101 Pine St', '0654738291', 'N'),
    (5, 1, 5, 'Robert Brown', '847362915V', 'Bank E', 498356759, '202 Maple St', '0847362915', 'Y');

INSERT INTO orders (order_id, cus_ID)
VALUES
    (1, 1),
    (2, 2),
    (3, 3),
    (4, 5),
    (5, 4);

INSERT INTO ordinary_cus (cus_id, order_ID, code, qty, date)
VALUES
    (1, 1, 1002, 10, '2024-05-18'),
    (2, 2, 1003, 15, '2024-05-19'),
    (3, 3, 1004, 20, '2024-05-20'),
    (4, 4, 1005, 25, '2024-05-21'),
    (5, 5, 1002, 30, '2024-05-22');

INSERT INTO order_detail (code, order_ID)
VALUES
    (1001, 1),
    (1002, 2),
    (1003, 3),
    (1004, 4),
    (1005, 5);

select oc.cus_id, oc.order_id, od.code, oc.date, i.name, oc.qty, i.qty, i.unit_price
from ordinary_cus as oc
    join order_detail od on oc.order_ID = od.order_ID
    join item i on od.code = i.code;
*/


create database shop;

use shop;

`DROP TABLE IF EXISTS `Item`;
CREATE TABLE `Item` (
                        `code` varchar(255) NOT NULL,
                        `description` varchar(255) DEFAULT NULL,
                        `qtyOnHand` int(10) DEFAULT NULL,
                        `unitPrice` decimal(10,2) DEFAULT NULL,
                        PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;`

DROP TABLE IF EXISTS `Company`;
CREATE TABLE `Company` (
                            `code` varchar(30) NOT NULL,
                            `name` varchar(255) DEFAULT NULL,
                            `contact` varchar(10) NOT NULL,
                            `email` varchar(255) NOT NULL,
                            `description` varchar(255) NOT NULL,
                            PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `Orders`;
CREATE TABLE `Orders` (
                          `oid` varchar(255) NOT NULL,
                          `date` date DEFAULT NULL,
                          `customerID` varchar(255) DEFAULT NULL,
                          PRIMARY KEY (`oid`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `OrderDetails`;
CREATE TABLE `OrderDetails` (
                                `oid` varchar(255) NOT NULL,
                                `itemCode` varchar(255) NOT NULL,
                                `qty` int(10) DEFAULT NULL,
                                `unitPrice` decimal(10,2) DEFAULT NULL,
                                PRIMARY KEY (`oid`,`itemCode`),
                                KEY `itemCode` (`itemCode`),
                                CONSTRAINT `OrderDetails_ibfk_1` FOREIGN KEY (`oid`) REFERENCES `Orders` (`oid`) ON DELETE CASCADE ON UPDATE CASCADE,
                                CONSTRAINT `OrderDetails_ibfk_2` FOREIGN KEY (`itemCode`) REFERENCES `Item` (`code`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `techDetail`;
CREATE TABLE `techDetail` (
                                `code` varchar(255) NOT NULL,
                                `NIC` varchar(20) NOT NULL,
                                `name` varchar(255) NOT NULL,
                                `address` varchar(255) NOT NULL,
                                `contact` varchar(10) NOT NULL,
                                `bankName` varchar(255) NOT NULL,
                                `accountNum` varchar(30) NOT NULL,
                                `toolCode` varchar(255) NOT NULL,
                                `description` varchar(255) NOT NULL,
                                PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
