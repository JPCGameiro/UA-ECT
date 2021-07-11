SELECT pub_name, SUM(ytd_sales) AS total_sales
FROM publishers, titles
WHERE publishers.pub_id = titles.pub_id
GROUP BY pub_name;