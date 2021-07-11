--n) Obter, para cada título, nome dos autores e valor arrecadado por estes com a sua venda;
select title, au_fname+' '+au_lname as name, sum(ytd_sales) as money_made
from authors join titleauthor on authors.au_id = titleauthor.au_id join titles on titles.title_id = titleauthor.title_id join sales on titles.title_id = sales.title_id
group by title, au_fname+' '+au_lname
order by title, au_fname+' '+au_lname
