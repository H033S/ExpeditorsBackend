-- Postgres SQL to set up Groups and Users (Roles).
-- Actually in Postgres they are the same and everything
-- is also called a Role.
-- Here we are going to create a Group role called "larkugrp",
-- to which we can later attach Users.  The only user we are using
-- here is "larku", but you could have more user assigned to the
-- same group and the control permissions through the group.

-- We have to drop the databases here because we want to drop the
-- possibliy existing users and start from scratch.

DROP database if EXISTS sakila WITH (FORCE);
DROP database if EXISTS northwind WITH (FORCE);
DROP database if EXISTS larku WITH (FORCE);

-- Drop if exists Group turns out to be a bit
-- more involved.  First we have to remove anything
-- owned by the group.

-- do
-- $$
-- begin
  -- if exists (select * from pg_user where usename = 'larkugrp') then
     -- drop owned by larkugrp;
  -- end if;
-- end
-- $$
-- ;

-- DROP role/group larkugrp;
-- DROP role if EXISTS larkugrp;

-- Drop if exists User needs the same procedure as for the
-- Group above.
do
$$
begin
  if exists (select * from pg_user where usename = 'larku') then
     drop owned by larku;
  end if;
end
$$
;

-- drop user larku;
DROP role if EXISTS larku;


-- create role larkugrp CREATEDB CREATEROLE;

-- We are creating a variable with the value of the
-- Environment variable DB_PASSWORD.  This *better*
-- have been set before this script is called.
--\set pw `echo ${DB_PASSWORD}`
\getenv pw DB_PASSWORD

\echo :pw

-- Here is where we use the variable set up above.
create user larku password :'pw' CREATEDB CREATEROLE;

-- Allow our new role to Login and have it Inherit
-- permissions give to it's parent group
ALTER ROLE larku WITH LOGIN;
ALTER ROLE larku INHERIT;

-- Finaly, we associate the "user" larku with the 
-- "group" larkgrp.
-- GRANT larkugrp to larku;


