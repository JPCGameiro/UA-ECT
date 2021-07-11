SELECT title, advance, average
FROM titles JOIN (SELECT titles.type, AVG(advance) as average
				  FROM titles
				  WHERE advance IS NOT NULL
				  GROUP BY titles.type) AS grp
	ON titles.type = grp.type AND advance > 1.5*average;