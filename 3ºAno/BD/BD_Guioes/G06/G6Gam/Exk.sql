SELECT au_fname, au_lname, COUNT(DISTINCT type) as num_differentTypes
FROM titleauthor, authors, titles
WHERE titleauthor.au_id = authors.au_id AND titles.title_id = titleauthor.title_id
GROUP BY au_fname, au_lname
HAVING COUNT(DISTINCT type) > 1;