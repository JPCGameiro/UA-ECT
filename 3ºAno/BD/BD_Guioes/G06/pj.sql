-- j) Nome dos títulos vendidos pela loja ‘Bookbeat’;
select title as titles_on_bookbeat
from titles, sales, stores
where titles.title_id = sales.title_id and sales.stor_id = stores.stor_id and stor_name = 'Bookbeat'
order by title