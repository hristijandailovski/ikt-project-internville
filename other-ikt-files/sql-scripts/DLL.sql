--SCHEMA
create schema IKT_PROJECT;
alter schema IKT_PROJECT owner to ikt_user;


-- DOMAINS
create domain email as varchar(255) check ( VALUE ~ '^[\w\-\.]+@([\w\-]+\.)+[\w\-]{2,4}$' ); -- one matching format is: yyyyyy@xxxx.xxx
create domain phone as varchar(9) check ( VALUE ~ '[0-9]{9}'); -- Exactly 9 digits.
create domain address as varchar(255);
create domain job as varchar(20) check ( VALUE ~ '^(EMPLOYMENT|INTERNSHIP)$'); --EMPLOYEMENT or INTERNSHIP
create domain pos_int as integer check ( VALUE > 0);
create domain percent as varchar(10) check ( VALUE ~ '^([0-9]|[1-9][0-9]|100)$'); -- 0-100
create domain lang_level    as varchar(10) check (VALUE ~ '^([ABC][12])$');  -- A1,A2,B1,B2,C1,C2
create domain study as varchar(10) check ( VALUE ~ '^(BACHELOR|MASTER|DOCTORAL)$'); -- BACHELOR or MASTER or DOCTORAL
create domain gpa   as float check ( VALUE >= 6 and VALUE <=10); -- [6,10]
create domain app_status as varchar(20) check ( VALUE ~ '^(ACCEPTED|REJECTED)$' ); --ACCEPTED or REJECTED
create domain grade as integer check ( VALUE >=0 and VALUE <=10);

--Alter domains
alter domain ikt_project.app_status drop constraint app_status_check;
alter domain ikt_project.app_status add constraint app_status_check
    check ((VALUE)::text ~'^(applied|accepted|rejected|ongoing|completed)$'::text);
--WARNING: uncomment following line only when you must to delete and drop the schema.
-- drop schema nbp_project cascade ;
