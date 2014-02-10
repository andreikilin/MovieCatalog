drop database if exists moviecatalog;
create database moviecatalog;


use moviecatalog;

create table users(
  id smallint unsigned not null unique auto_increment,
  login varchar(100) not null,
  password varchar(10) not null,
  email varchar(100) not null,
  primary key (id)
);
insert into users(login, password, email) values ("admin", "200206", "muzichko_vika@mail.ru");
insert into users(login, password, email) values('paul', '111', 'paul909@gmail3.com');

create table genres(
  id smallint unsigned not null unique auto_increment,
  name varchar(100) not null,
  primary key (id)
);

insert into genres(name) values ("Comedy");
insert into genres(name) values ("Animation");

create table countries(
  id smallint unsigned not null unique auto_increment,
  name varchar(100) not null,
  primary key (id)
);

insert into countries(name) values ("USA");
insert into countries(name) values ("Ukraine");

create table movies(
  id smallint unsigned not null unique auto_increment,
  idgenre smallint unsigned not null,
  idcountry smallint unsigned not null,
  name varchar(200) not null,
  description varchar(200),
  starring varchar(200),
  year smallint(4) unsigned not null,
  primary key (id),
  foreign key (idgenre) references genres(id),
  foreign key (idcountry) references countries(id)
);

insert into movies(idgenre, idcountry, name, year) values (1, 1, "Film1", 2012);
insert into movies(idgenre, idcountry, name, year) values (1, 2, "Film2", 2010);

create table reviews(
  id smallint unsigned not null unique auto_increment,
  idmovie smallint unsigned not null,
  iduser smallint unsigned not null,
  ts timestamp default current_timestamp on update current_timestamp,
  viewed bit(1) default 1,
  rating tinyint unsigned default 0,
  comment varchar(200),
  primary key (id),
  foreign key (idmovie) references movies(id),
  foreign key (iduser) references users(id)
);

insert into reviews(idmovie, iduser, rating, comment) values (1, 1, 80, "not bad");
insert into reviews(idmovie, iduser, rating, comment) values (2, 1, 100, "excellent");
insert into reviews(idmovie, iduser, rating, comment) values (2, 2, 50, "boring");
