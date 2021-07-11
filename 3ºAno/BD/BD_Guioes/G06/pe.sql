select au_fname as first_name, au_lname as last_name, phone as telephone 
from authors 
where state = 'CA' and au_lname != 'Ringer'
order by au_fname, au_lname