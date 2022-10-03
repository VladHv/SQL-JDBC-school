CREATE USER myuser WITH PASSWORD 'password';
CREATE DATABASE "school" WITH OWNER "myuser" ENCODING 'UTF8' LC_COLLATE = 'en-US' LC_CTYPE = 'en-US' TEMPLATE template0;
GRANT ALL PRIVILEGES ON DATABASE school TO myuser;