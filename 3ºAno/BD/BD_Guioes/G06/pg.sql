-- g) Nome das editoras que têm pelo menos uma publicação do tipo ‘Business’;
select distinct pub_name as nome_editora 
from publishers, titles
where type = 'Business'