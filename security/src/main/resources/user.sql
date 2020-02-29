DROP TABLE IF EXISTS t_user;
CREATE TABLE t_user (
  id character varying(100) primary key not null,
  username varchar(255) DEFAULT NULL,
  password varchar(255) DEFAULT NULL,
  nickname varchar(255) DEFAULT ''
)