#Books Keeper

###Start database (Postgresql)

```bash
pg_ctl -D "C:\Program Files\PostgreSQL\14\data" start
```

###Config environment application properties:

Run > Edit configurations > VM options > add the next command option "-Dspring.profiles.active=dev"