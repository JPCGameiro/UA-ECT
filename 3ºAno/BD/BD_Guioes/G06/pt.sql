-- t)
(SELECT pub_name, stor_name
FROM stores, publishers )
EXCEPT
(SELECT pub_name, stor_name
FROM publishers JOIN (	SELECT pub_id AS ppid, sales.stor_id, stor_name
						FROM titles JOIN sales
						ON titles.title_id=sales.title_id
						JOIN stores
						ON sales.stor_id=stores.stor_id) AS T			
ON pub_id=ppid);