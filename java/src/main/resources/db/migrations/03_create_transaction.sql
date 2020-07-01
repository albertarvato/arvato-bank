CREATE TABLE transaction(
                            transaction_id serial PRIMARY KEY,
                            created TIMESTAMP NOT NULL,
                            value INTEGER NOT NULL,
                            account_from INTEGER NOT NULL,
                            account_to INTEGER NOT NULL,
                            CONSTRAINT transaction_account_from_fkey FOREIGN KEY (account_from)
                                REFERENCES account (account_id) MATCH SIMPLE
                                ON UPDATE NO ACTION ON DELETE NO ACTION,
                            CONSTRAINT transaction_account_to_fkey FOREIGN KEY (account_to)
                                REFERENCES account (account_id) MATCH SIMPLE
                                ON UPDATE NO ACTION ON DELETE NO ACTION
);