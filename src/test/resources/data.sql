INSERT INTO person (iban, name, cnp) values (52345334634, 'Narcis', 1920322385574)
INSERT INTO person (iban, name, cnp) values (76457265345, 'Vasile', 1910103385576)
INSERT INTO person (iban, name, cnp) values (43512583465, 'Andrei', 1900205385534)
INSERT INTO person (iban, name, cnp) values (43872946531, 'Marius', 1930709385573)

INSERT INTO transactions (amount, description, payee_cnp, payer_cnp, transaction_type, id) values (10, 'tranz1', 1910103385576, 1920322385574, 'IBAN_TO_IBAN', 1)
INSERT INTO transactions (amount, description, payee_cnp, payer_cnp, transaction_type, id) values (20, 'tranz2', 1930709385573, 1900205385534, 'IBAN_TO_WALLET', 2)
INSERT INTO transactions (amount, description, payee_cnp, payer_cnp, transaction_type, id) values (40, 'tranz3', 1900205385534, 1920322385574, 'IBAN_TO_IBAN', 3)
INSERT INTO transactions (amount, description, payee_cnp, payer_cnp, transaction_type, id) values (50, 'tranz4', 1910103385576, 1920322385574, 'IBAN_TO_WALLET', 4)