SELECT title 
FROM titles, sales, stores 
WHERE stor_name = 'Bookbeat' AND sales.stor_id = stores.stor_id AND titles.title_id = sales.title_id
GROUP BY title;