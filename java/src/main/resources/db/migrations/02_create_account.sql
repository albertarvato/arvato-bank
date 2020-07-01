CREATE TABLE account(
                       account_id serial PRIMARY KEY,
                       username VARCHAR (50) UNIQUE NOT NULL,
                       password VARCHAR (50) NOT NULL,
                       fullname VARCHAR (50) NOT NULL,
                       mobile VARCHAR (355) UNIQUE NOT NULL,
                       email VARCHAR (355) UNIQUE,
                       iban VARCHAR (34) UNIQUE NOT NULL
);