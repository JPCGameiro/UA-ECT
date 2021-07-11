SELECT pub_name 
FROM publishers,titles
WHERE titles.type='business'
GROUP BY pub_name;