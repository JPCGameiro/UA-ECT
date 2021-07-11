SELECT stor_name, COUNT(DISTINCT title) as different
FROM stores, sales, titles
WHERE stores.stor_id = sales.stor_id AND sales.title_id = titles.title_id
GROUP BY stor_name
HAVING COUNT(DISTINCT title) = (SELECT COUNT(title) FROM titles) ;
