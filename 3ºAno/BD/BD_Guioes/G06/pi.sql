-- i) Número total de vendas de cada editora agrupado por título;
select pub_name, title, sum(ytd_sales) as total_sales
from publishers, titles
where publishers.pub_id = titles.pub_id
group by pub_name, title
order by pub_name