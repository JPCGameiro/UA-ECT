SELECT DISTINCT titles.title, au_fname+' '+au_lname as name, ytd_sales, 
				ytd_sales*price as facturacao,
				ytd_sales*royalty*price/100 as auths_revenue,
				ytd_sales*price*(100-royalty)/100 as pub_revenue
FROM titles, sales, titleauthor, authors
WHERE titles.title_id = sales.title_id and titles.title_id = titleauthor.title_id and titleauthor.au_id = authors.au_id;
 
