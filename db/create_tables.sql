-->>>> psql --echo-all --host=localhost --port=5432 --dbname=postgres -f create_tables.sql

create table users
(
	id serial,
	user_name varchar  not null,
	user_type varchar  not null,
	pwd varchar  not null,
	primary key(id)
);

create table books
(
	id serial,
	book_name varchar unique,
	author varchar,
	description varchar,
	quantity int not null,
	availability int,
	primary key(id)
);

create table users_transactions
(
	id serial,
	tran_date date not null,
	user_id int,
	checkout_qty int,
	due_date date,
	status varchar,
	primary key(id),
	constraint users_transactions_users_id_fk
	    foreign key(user_id)
	        references users(id)
);

create table books_transactions
(
    id serial,
	user_transaction_id int,
	book_id int,
	returned bool,
	overdue bool,
	primary key(id),
	constraint books_transactions_users_transactions_id_fk
	    foreign key(user_transaction_id)
	        references users_transactions(id),
	constraint books_transactions_books_id_fk
	    foreign key(book_id)
	        references books(id)
);

create table metadata
(
	meta_key varchar,
	meta_value varchar not null,
	primary key(meta_key)
);

