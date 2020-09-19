-->>>> psql --echo-all --host=localhost --port=5432 --dbname=postgres -f drop_tables.sql

drop table if exists books_transactions;
drop table if exists users_transactions;
drop table if exists books;
drop table if exists users;
drop table if exists metadata;