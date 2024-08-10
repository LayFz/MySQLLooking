CREATE TABLE IF NOT EXISTS database (
    id INTEGER PRIMARY KEY AUTOINCREMENT,
    dbName TEXT NOT NULL,
    dbUser TEXT NOT NULL,
    dbPassword TEXT NOT NULL,
    dbUrl TEXT NOT NULL,
    dbport Integer NOT NULL
);
INSERT INTO database (dbName, dbUser, dbPassword, dbUrl, dbport) VALUES ('test', 'root','20@@GYgy','localhost', 3306);


