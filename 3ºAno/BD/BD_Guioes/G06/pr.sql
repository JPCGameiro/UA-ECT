-- r)
SELECT stor_name, sum(qty) AS sum_qty
FROM sales JOIN stores
ON sales.stor_id=stores.stor_id
GROUP BY stor_name
HAVING sum(qty) > (	SELECT avg(sum_qty)
						FROM (	SELECT sum(qty) AS sum_qty, stor_id AS stid
								FROM sales
								GROUP BY stor_id) as T
					);