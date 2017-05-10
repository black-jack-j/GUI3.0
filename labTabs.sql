CREATE TABLE USERS (
  ID serial PRIMARY KEY,
  LOGIN text UNIQUE,
  PASSWORD text,
  MAP_NUM integer
);

CREATE TABLE MAPS (
  ID serial PRIMARY KEY,
  OWNER_ID integer REFERENCES USERS (ID),
  NAME text,
  SIZE int,
);

CREATE TABLE TERRITORIES (
  ID text,
  MAP_ID integer REFERENCES MAPS (ID),
  NAME text,
  SIZE integer,
  PRIMARY KEY (ID,MAP_ID)
);