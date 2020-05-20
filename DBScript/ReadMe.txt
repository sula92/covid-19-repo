use the following table records in order to logged with different access privileges


MySQL [covid19]> select * from user;
+-------+------------+---------------+-------+-------+-------------+------------+------+
| name  | contact    | email         | unm   | pwd   | role        | hospitalid | qcid |
+-------+------------+---------------+-------+-------+-------------+------------+------+
| amal  | 1234567890 | ss@gmail.com  | amal  | amal  | Hospital-IT | NULL       | NULL |
| kamal | 0713430086 | fdf@gmail.com | kamal | xxx   | PSTF        | NULL       | NULL |
| nimal | 2345678567 | sss@ddd.com   | nimla | nimal | Hospital-IT | NULL       | NULL |
| rock  | 940400     | erd@gmail.com | rock  | 123   | Hospital-IT | H001       | NULL |
| sula  | 03030303   | acd@gmail.com | sula  | abcd  | admin       | H001       | NULL |
+-------+------------+---------------+-------+-------+-------------+------------+------+
5 rows in set (0.00 sec)


.......logged as sula if you want to use the system with admin privileges...........