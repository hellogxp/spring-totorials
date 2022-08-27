## Mysql binary log config
1. Create the MySQL user root    
```mysql
CREATE USER 'root'@'localhost' IDENTIFIED BY 'root';    
```
2. Grant regard permission to the user     
```mysql
GRANT SELECT, SHOW DATABASES, REPLICATION SLAVE, REPLICATION CLIENT ON *.* TO 'root' IDENTIFIED BY 'root';
```

3. Config the bin log    
```xml
# log_bin
max_allowed_packet=1024M
server_id=1
log-bin=master
binlog_format=row
binlog-do-db=gmall 
```