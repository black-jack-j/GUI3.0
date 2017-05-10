CREATE EXTENSION pgcrypto;

CREATE OR REPLACE FUNCTION change_password() RETURNS trigger AS 
$BODY$
	BEGIN	
		IF (TG_OP = 'INSERT') THEN
			NEW.PASSWORD = crypt(NEW.PASSWORD, gen_salt('bf',8));
			return NEW;
		END IF;
		 IF (TG_OP = 'UPDATE') AND (OLD.hash != NEW.PASSWORD) THEN
			NEW.PASSWORD = crypt(NEW.PASSWORD, gen_salt('bf',8));
			return NEW;
		END IF;
		return NEW;
	END; 
$BODY$
language 'plpgsql';

DROP TRIGGER IF EXISTS users_password ON USERS;
CREATE TRIGGER users_password 
   BEFORE INSERT OR UPDATE
   ON USERS FOR EACH ROW 
   EXECUTE PROCEDURE change_password();

CREATE OR REPLACE FUNCTION isLoged(text, text) RETURNS bool AS 
$BODY$ 
	DECLARE res bool;
	BEGIN
		SELECT 1 INTO res FROM users WHERE LOGIN = $1 AND PASSWORD = crypt($2,PASSWORD);
   		IF FOUND THEN    
   			return true;
		ELSE 
   			return false;
		END IF;
	END; 
$BODY$
language 'plpgsql';