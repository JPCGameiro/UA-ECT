SELECT au_fname as firstName, au_lname as lastName, phone as telephone FROM authors
WHERE state = 'CA' AND authors.au_lname != 'Ringer'
ORDER BY au_fname ASC, au_lname ASC;