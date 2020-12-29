CREATE TABLE user (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  password varchar(255) NOT NULL,
  username varchar(255) NOT NULL,
  role varchar(255) NOT NULL,
  email varchar(255) NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY UK_jreodf78a7pl5qidfh43axdfb (username)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;