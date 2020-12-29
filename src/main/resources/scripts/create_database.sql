create database db_bootcamp;
grant all on db_bootcamp.* to bootcamp_user@localhost identified by 'bootcamp_pass';
FLUSH PRIVILEGES;