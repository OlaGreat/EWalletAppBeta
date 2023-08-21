Set FOREIGN_KEY_CHECKS = 0;
truncate table wallets;
truncate table address;
truncate table transactions;


insert into wallets (wallet_id, email, first_name, last_name, pin, pass_word, gender, phone_number, user_name, balance, account_number, address_id)
values (101, 'oladipupoOlamilekan2@gmail.com', 'Ola', 'Ola', '1111', 'great', 'Male', '08126188203','Great', 0.0, '8126188203',1),
       (102, 'oladipupoOlamilekan1@gmail.com', 'Ola', 'Ola', '1111', 'great', 'Male', '08140802014','Great1', 0.0, '8140802014',2),
       (103, 'oladipupoOlamilekan4@gmail.com', 'Ola', 'Ola', '1111', 'great', 'Male', '08126188206','Great2', 0.0, '8126188206',3),
       (104, 'oladipupoOlamilekan5@gmail.com', 'Ola', 'Ola', '1111', 'great', 'Male', '08126188205','Great3', 0.0, '8126188205',4),
       (105, 'oladipupoOlamilekan3@gmail.com', 'Ola', 'Ola', '1111', 'great', 'Male', '08126188209','Great4', 0.0, '8126188209',5);

insert into address(id, house_number,street, lga, state)
values (1,'6','sabo','yaba','Lagos'),
       (2,'6','sabo','yaba','Lagos'),
       (3,'6','sabo','yaba','Lagos'),
       (4,'6','sabo','yaba','Lagos'),
       (5,'6','sabo','yaba','Lagos');
