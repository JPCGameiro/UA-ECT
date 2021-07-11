-- h) Número total de vendas de cada editora;
select pub_name, sum(ytd_sales) as total_sales
from publishers, titles
where titles.pub_id = publishers.pub_id
group by pub_name