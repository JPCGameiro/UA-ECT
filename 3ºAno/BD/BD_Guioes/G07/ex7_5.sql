
--i. Nome dos títulos e nome dos respetivos autores;
create view Titulos_autores as
SELECT pubs.dbo.titles.title, pubs.dbo.authors.au_lname+' '+pubs.dbo.authors.au_fname as autor
FROM     pubs.dbo.titleauthor INNER JOIN
                  pubs.dbo.titles ON pubs.dbo.titleauthor.title_id = pubs.dbo.titles.title_id INNER JOIN
                  pubs.dbo.authors ON pubs.dbo.titleauthor.au_id = pubs.dbo.authors.au_id;

--ii. Nome dos editores e nome dos respetivos funcionários;
create view Editora_empregados as
SELECT pubs.dbo.publishers.pub_name, pubs.dbo.employee.fname+' '+pubs.dbo.employee.minit+'. '+pubs.dbo.employee.lname as fullname
FROM     pubs.dbo.publishers INNER JOIN
                  pubs.dbo.employee ON pubs.dbo.publishers.pub_id = pubs.dbo.employee.pub_id
group by pub_name, pubs.dbo.employee.fname+' '+pubs.dbo.employee.minit+'. '+pubs.dbo.employee.lname;

--iii. Nome das lojas e o nome dos títulos vendidos nessa loja;
create view loja_titulos as
SELECT pubs.dbo.stores.stor_name, pubs.dbo.titles.title
FROM     pubs.dbo.sales INNER JOIN
                  pubs.dbo.stores ON pubs.dbo.sales.stor_id = pubs.dbo.stores.stor_id INNER JOIN
                  pubs.dbo.titles ON pubs.dbo.sales.title_id = pubs.dbo.titles.title_id;

--iv. Livros do tipo ‘Business’;
create view livros_tipo_business as
SELECT pubs.dbo.titles.title_id, pubs.dbo.titles.title, pubs.dbo.titles.type, pubs.dbo.titles.pub_id, pubs.dbo.titles.price, pubs.dbo.titles.notes
FROM     pubs.dbo.titles
WHERE  (type = 'Business');

--b)
-- i.todos os autores do livro "Net Etiquette"
select autor from Titulos_autores where title = 'Net Etiquette';
-- ii.nome das editoras com mais de 5 empregados
select pub_name from
(select pub_name, count(fullname) as num_empregados from Editora_empregados group by pub_name having count(fullname) > 5) as T;
-- iii.nome das lojas que contenham "Book" no nome
select stor_name from loja_titulos where title = '%Book%';
-- iv.listar titulo dos livros
select title from livros_tipo_business;

--c)
--i. Nome das lojas e nome dos autores vendidos na loja
create view Titulos_autoresv2 as
SELECT pubs.dbo.titles.title, pubs.dbo.authors.au_fname+' '+pubs.dbo.authors.au_lname as name, pubs.dbo.authors.phone, pubs.dbo.stores.stor_name, pubs.dbo.stores.stor_address, pubs.dbo.stores.city, pubs.dbo.stores.state
FROM     pubs.dbo.authors INNER JOIN
                  pubs.dbo.titleauthor ON pubs.dbo.authors.au_id = pubs.dbo.titleauthor.au_id INNER JOIN
                  pubs.dbo.titles ON pubs.dbo.titleauthor.title_id = pubs.dbo.titles.title_id INNER JOIN
                  pubs.dbo.sales ON pubs.dbo.titles.title_id = pubs.dbo.sales.title_id INNER JOIN
                  pubs.dbo.stores ON pubs.dbo.sales.stor_id = pubs.dbo.stores.stor_id
group by pubs.dbo.titles.title, pubs.dbo.authors.au_fname+' '+pubs.dbo.authors.au_lname, pubs.dbo.authors.phone, pubs.dbo.stores.stor_name, pubs.dbo.stores.stor_address, pubs.dbo.stores.city, pubs.dbo.stores.state;

--iii. Nome das lojas e o nome dos títulos vendidos nessa loja;
create view loja_titulosv2 as
SELECT pubs.dbo.stores.stor_name, pubs.dbo.titles.title, pubs.dbo.authors.au_fname+' '+pubs.dbo.authors.au_lname as name
FROM     pubs.dbo.authors INNER JOIN
                  pubs.dbo.titleauthor ON pubs.dbo.authors.au_id = pubs.dbo.titleauthor.au_id INNER JOIN
                  pubs.dbo.titles ON pubs.dbo.titleauthor.title_id = pubs.dbo.titles.title_id INNER JOIN
                  pubs.dbo.sales ON pubs.dbo.titles.title_id = pubs.dbo.sales.title_id INNER JOIN
                  pubs.dbo.stores ON pubs.dbo.sales.stor_id = pubs.dbo.stores.stor_id
group by pubs.dbo.stores.stor_name, pubs.dbo.titles.title, pubs.dbo.authors.au_fname+' '+pubs.dbo.authors.au_lname;

-- consula
select stor_name, title, name from Titulos_autoresv2;
select stor_name, title, name from loja_titulosv2;

-- d) 
insert into livros_tipo_business (title_id, title, type, pub_id, price, notes)
values('BDTst1', 'New BD Book','popular_comp', '1389', $30.00, 'A must-read for
DB course.');

-- a instrução não resulta por não estar a usar um sgbd próprio
-- no entanto, pela aula teórica:
--a) a instrução iria funcionar,
--b) mas não faz sentido, a view apenas contem livros do type Business
--c) para garantir que não se inserem livros de outros tipos na view, teriamos que acrescentar WITH CHECK OPTION,
-- para garantir que as clasulas Where são verificadas na inserção, aualização da view;

