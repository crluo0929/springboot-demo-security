create table if not exists user(
    id int not null,
    username varchar(20) not null,
    password varchar(60) not null,
    roles varchar(50),
    telnum varchar(10),
    email varchar(30),
    city varchar(15),
    primary key(id)
) ;