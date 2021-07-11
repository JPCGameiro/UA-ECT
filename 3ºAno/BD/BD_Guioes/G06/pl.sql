--l) Para os títulos, obter o preço médio e o número total de vendas agrupado por tipo
--(type) e editora (pub_id);

select type as tipo_livro, pub_name as editora, avg(titles.price) as media_preço, sum(ytd_sales) as total_vendas
from titles join publishers on titles.pub_id = publishers.pub_id
group by type, pub_name
having avg(titles.price) is not null;
