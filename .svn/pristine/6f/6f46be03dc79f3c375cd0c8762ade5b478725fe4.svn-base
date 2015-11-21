Create database bank;

USE bank;


CREATE TABLE `users` (
  `userid` int(11) DEFAULT NULL,
  `PassWord` varchar(255) DEFAULT NULL,
  `UserName` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

CREATE TABLE `transactions` (
  `contractid` int(11) NOT NULL,
  `balance` varchar(20) DEFAULT NULL,
  `transaction_date` datetime DEFAULT NULL,
  `deposited_from` int(11) DEFAULT NULL,
  `withdraw_to` int(11) DEFAULT NULL,
  `amount` varchar(11) DEFAULT NULL,
  `intrest_rate` float DEFAULT NULL,
  `monthlypayments` varchar(20) DEFAULT NULL,
  `creationdate` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 DELAY_KEY_WRITE=1;

CREATE TABLE `accounts` (
  `userid` int(11) NOT NULL,
  `saving_contract` int(11) DEFAULT NULL,
  `cheqing_contract` int(11) DEFAULT NULL,
  `credit_contract` int(11) DEFAULT NULL,
  `mortgage_contract` int(11) DEFAULT NULL,
  `loan_contract` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into `accounts`(`userid`,`saving_contract`,`cheqing_contract`,`credit_contract`,`mortgage_contract`,`loan_contract`) values (1,1,1,1,1,1);
insert into `accounts`(`userid`,`saving_contract`,`cheqing_contract`,`credit_contract`,`mortgage_contract`,`loan_contract`) values (2,2,2,2,2,2);
insert into `accounts`(`userid`,`saving_contract`,`cheqing_contract`,`credit_contract`,`mortgage_contract`,`loan_contract`) values (3,3,3,3,3,3);
insert into `accounts`(`userid`,`saving_contract`,`cheqing_contract`,`credit_contract`,`mortgage_contract`,`loan_contract`) values (4,4,4,4,4,4);
