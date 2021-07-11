SELECT title 
FROM titles
EXCEPT
SELECT title
FROM titles, stores, sales
WHERE stores.stor_id = sales.stor_id AND titles.title_id = sales.title_id AND stores.stor_name = 'Bookbeat';