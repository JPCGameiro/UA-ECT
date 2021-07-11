SELECT DISTINCT titles.title, ytd_sales, 
				ytd_sales*price as facturacao,
				ytd_sales*royalty*price/100 as auths_revenue,
				ytd_sales*price*(100-royalty)/100 as pub_revenue
FROM titles, sales
WHERE titles.title_id = sales.title_id;