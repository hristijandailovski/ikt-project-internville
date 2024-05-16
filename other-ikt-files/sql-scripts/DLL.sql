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


create table major(
                      id  int     primary key ,
                      major varchar(50)
);
create table STUDENT(
                        id      integer     PRIMARY KEY,
                        FOREIGN KEY (id) REFERENCES END_USER(id)
                            ON  DELETE CASCADE ON UPDATE CASCADE
);
create table LANGUAGE(
                         id      serial     PRIMARY KEY,
                         name    varchar(30) NOT NULL,
                         code    varchar(10) NOT NULL
);
create table KNOWS_LANGUAGE(
                               student_id  integer,
                               lang_id     integer,
                               level       lang_level      NOT NULL,
                               PRIMARY KEY (student_id,lang_id),
                               FOREIGN KEY (student_id) REFERENCES STUDENT(id)
                                   ON DELETE CASCADE ON UPDATE CASCADE,
                               FOREIGN KEY (lang_id) REFERENCES LANGUAGE(id)
                                   ON DELETE CASCADE ON UPDATE CASCADE

);
create table EXPERIENCE(
                           id                  serial         PRIMARY KEY,
                           type_of_job         job,
                           description         varchar(1024),
                           start_year          date,
                           duration_in_weeks   pos_int,
                           student_id          integer     NOT NULL,
                           FOREIGN KEY (student_id) REFERENCES STUDENT(id)
                               ON DELETE CASCADE ON UPDATE CASCADE
);
create table CERTIFICATE(
                            id                  serial     PRIMARY KEY,
                            name                varchar(30),
                            description         varchar(1024),
                            date_of_issue       date,
                            publisher           varchar(30),
                            student_id          integer     NOT NULL,
                            FOREIGN KEY (student_id) REFERENCES STUDENT(id)
                                ON DELETE CASCADE ON UPDATE CASCADE
);
create table PROJECT(
                        id                  serial         PRIMARY KEY,
                        name                varchar(50),
                        description         varchar(1024),
                        completeness        percent         DEFAULT '0',
                        student_id          integer         NOT NULL,
                        FOREIGN KEY (student_id) REFERENCES STUDENT(id)
                            ON DELETE CASCADE ON UPDATE CASCADE
);
create table EDUCATIONAL_INSTITUTE(
                                      id              serial     PRIMARY KEY,
                                      name            varchar(50),
                                      phone_number    phone,
                                      email_address   email,
                                      address         address,
                                      superior_id     integer,
                                      country_id      integer,
                                      FOREIGN KEY (superior_id) REFERENCES EDUCATIONAL_INSTITUTE (id)
                                          ON DELETE SET NULL ON UPDATE CASCADE,
                                      FOREIGN KEY (country_id) REFERENCES COUNTRY(id)
                                          ON DELETE SET NULL ON UPDATE CASCADE
);

create table COUNTRY(
                        id      serial      PRIMARY KEY,
                        name    varchar(50) UNIQUE    NOT NULL
);
create table END_USER(
                         id              serial         PRIMARY KEY,
                         username        varchar(20)     UNIQUE,
                         password        varchar(20),
                         name            varchar(20),
                         surname         varchar(20),
                         date_of_birth   date,
                         address         address,
                         phone_number    phone,
                         email_address   email,
                         country_id      integer,
                         FOREIGN KEY (country_id) REFERENCES COUNTRY (id)
                             ON DELETE SET NULL ON UPDATE CASCADE
);
create table ORGANIZATION (
                              id              serial     PRIMARY KEY,
                              name            varchar(50),
                              phone_number    phone,
                              email_address   email,
                              address         address,
                              country_id      integer,
                              FOREIGN KEY (country_id) REFERENCES COUNTRY(id)
                                  ON DELETE SET NULL ON UPDATE CASCADE
);
create table COMMITTEE(
                          id              serial     PRIMARY KEY,
                          phone_number    phone,
                          email_address   email,
                          address         address,
                          country_id      integer,
                          org_id          integer     NOT NULL,
                          FOREIGN KEY (country_id) REFERENCES COUNTRY(id)
                              ON DELETE SET NULL ON UPDATE CASCADE,
                          FOREIGN KEY (org_id) REFERENCES ORGANIZATION(id)

);
create table MEMBER(
                       id      integer     PRIMARY KEY,
                       FOREIGN KEY (id) REFERENCES END_USER(id)
                           ON  DELETE CASCADE ON UPDATE CASCADE
);


create table acceptance_status (
                                   id      int         primary key,
                                   status  varchar(20)
);

create table COMPANY(
                        id                      serial     PRIMARY KEY,
                        name                    varchar(30),
                        phone_number            phone,
                        email_address           email,
                        address                 address,
                        country_id              integer,
                        number_of_employees     pos_int,
                        FOREIGN KEY (country_id) REFERENCES COUNTRY(id)
                            ON DELETE SET NULL ON UPDATE CASCADE

);
create table OFFER(
                      id                  serial     PRIMARY KEY,
                      requirements        varchar(500),
                      responsibilities    varchar(500),
                      benefits            varchar(500),
                      salary              pos_int,
                      field               varchar(50),
                      start_date          date,
                      duration_in_weeks   pos_int,
                      member_id           integer     NOT NULL,
                      company_id          integer,
                      FOREIGN KEY (member_id) REFERENCES MEMBER(id)
                          ON DELETE SET NULL ON UPDATE CASCADE,
                      FOREIGN KEY (company_id) REFERENCES COMPANY(id)
                          ON DELETE SET NULL ON UPDATE CASCADE
);
create table ACCOMMODATION(
                              id          serial     PRIMARY KEY ,
                              phone_number            phone,
                              email_address           email,
                              address                 address,
                              country_id              integer,
                              start_date              date,
                              end_date                date,
                              type                    varchar(30),
                              description             varchar(500),
                              offer_id                integer     NOT NULL,
                              FOREIGN KEY (country_id) REFERENCES COUNTRY(id)
                                  ON DELETE SET NULL ON UPDATE CASCADE,
                              FOREIGN KEY (offer_id) REFERENCES OFFER(id)
                                  ON DELETE SET NULL ON UPDATE CASCADE,
                              CHECK (start_date < end_date)
);

create table APPLIES_FOR(
                            student_id                      integer,
                            offer_id                        integer,
                            acceptance_status               app_status,
                            date_of_app_submission          date,
                            PRIMARY KEY (student_id,offer_id),
                            FOREIGN KEY (student_id) REFERENCES STUDENT(id)
                                ON DELETE SET NULL  ON UPDATE CASCADE,
                            FOREIGN KEY (offer_id) REFERENCES OFFER(id)
                                ON DELETE SET NULL  ON UPDATE CASCADE

);

create table INTERNSHIP(
                           id      serial     PRIMARY KEY ,
                           grade_work  grade,
                           grade_accommodation     grade,
                           grade_student           grade,
                           comment_student         varchar(500),
                           comment_company         varchar(500),
                           duration_in_weeks       pos_int,
                           salary                  pos_int,
                           bonus_pay               pos_int,
                           applies_for_student_id  integer,
                           applies_for_offer_id    integer,
                           FOREIGN KEY (applies_for_student_id,applies_for_offer_id) REFERENCES APPLIES_FOR (student_id,offer_id)
                               ON DELETE SET NULL ON UPDATE CASCADE
);


--Procedure for inserting a row into the knows_language table
CREATE OR REPLACE PROCEDURE ikt_project.insert_or_update_knows_language(
    p_student_id INTEGER,
    p_lang_id INTEGER,
    p_level VARCHAR
)
AS $$
BEGIN
    -- Check if the language knowledge exists based on the provided student_id and lang_id
    IF EXISTS (
        SELECT 1
        FROM ikt_project.knows_language
        WHERE student_id = p_student_id
          AND lang_id = p_lang_id
    ) THEN
        -- Update existing language knowledge
UPDATE ikt_project.knows_language
SET level = p_level
WHERE student_id = p_student_id
  AND lang_id = p_lang_id;
ELSE
        -- Insert a new language knowledge
        INSERT INTO ikt_project.knows_language (student_id, lang_id, level)
        VALUES (p_student_id, p_lang_id, p_level);
END IF;

COMMIT;
END;
$$ language plpgsql;

--Procedure for inserting a row into the project table
CREATE OR REPLACE PROCEDURE ikt_project.insert_or_update_project(
    p_name VARCHAR,
    p_description VARCHAR,
    p_completeness VARCHAR,
    p_student_id INTEGER
)
AS $$
BEGIN
    -- Check if the project exists based on the provided name
    IF EXISTS (
        SELECT 1
        FROM ikt_project.project
        WHERE name = p_name
    ) THEN
        -- Update existing project
UPDATE ikt_project.project
SET description = p_description,
    completeness = p_completeness,
    student_id = p_student_id
WHERE name = p_name;
ELSE
        -- Insert a new project
        INSERT INTO ikt_project.project (name, description, completeness, student_id)
        VALUES (p_name, p_description, p_completeness, p_student_id);
END IF;

COMMIT;
END;
$$ language plpgsql;

--Procedure for inserting a row in the certificate table
CREATE OR REPLACE PROCEDURE ikt_project.insert_or_update_certificate(
    p_name VARCHAR,
    p_description VARCHAR,
    p_date_of_issue DATE,
    p_publisher VARCHAR,
    p_student_id INTEGER
)
AS $$
BEGIN
    -- Check if the certificate exists based on the provided name and student_id
    IF EXISTS (
        SELECT 1
        FROM ikt_project.certificate
        WHERE name = p_name
          AND student_id = p_student_id
    ) THEN
        -- Update existing certificate
UPDATE ikt_project.certificate
SET description = p_description,
    date_of_issue = p_date_of_issue,
    publisher = p_publisher
WHERE name = p_name
  AND student_id = p_student_id;
ELSE
        -- Insert a new certificate
        INSERT INTO ikt_project.certificate (name, description, date_of_issue, publisher, student_id)
        VALUES (p_name, p_description, p_date_of_issue, p_publisher, p_student_id);
END IF;

COMMIT;
END;
$$ language plpgsql;


create or replace procedure ikt_project.student_apply_for_offer(
    p_student_id integer,
    p_offer_id integer
)
AS $$
BEGIN
    if not exists(
        select 1
        from ikt_project.applies_for as ap
        where ap.offer_id = p_offer_id and ap.student_id = p_student_id
    )
    then
        INSERT INTO ikt_project.applies_for (student_id, offer_id, date_of_app_submission, acceptance_status)
        VALUES (p_student_id, p_offer_id, now(), 1);
end if;
COMMIT;

END;
$$ language plpgsql;


create or replace procedure ikt_project.accept_applicant(
    p_student_id integer,
    p_offer_id integer
)
as $$
begin
    -- check if this application exists and if it has status: waiting
    -- change status in applies_for to 'accepted' for applicant
    -- change offer.is_active to false
    -- change status in applies_for to 'rejected' for all other applicants

    if exists(
        select 1
        from ikt_project.applies_for
        where offer_id = p_offer_id and
            student_id = p_student_id and
            applies_for.acceptance_status = 1 -- 1 means 'waiting'
    )
    then
        -- change status of the selected applicant to 'accepted'
update ikt_project.applies_for
set acceptance_status = 2       -- 2 means 'accepted'
where offer_id = p_offer_id and
        student_id = p_student_id;

-- set the offer as not active anymore
update ikt_project.offer as offer
set is_active = false
where offer.id = p_offer_id;

-- reject all other applicants for the offer
update ikt_project.applies_for
set acceptance_status = 3       -- 3 means 'rejected'
where offer_id = p_offer_id and
    student_id != p_student_id;

end if;
end;
$$ language plpgsql;


create or replace procedure ikt_project.update_end_user(
    p_id integer,
    p_password varchar,
    p_name varchar,
    p_surname varchar,
    p_date_of_birth date,
    p_address varchar,
    p_phone_number varchar,
    p_email_address varchar,
    p_country_id integer
)
AS $$
BEGIN
UPDATE ikt_project.end_user
SET password = p_password,
    name = p_name,
    surname = p_surname,
    date_of_birth = p_date_of_birth,
    address = p_address,
    phone_number = p_phone_number,
    email_address = p_email_address,
    country_id = p_country_id
WHERE id = p_id;
END;
$$ language plpgsql;


create or replace procedure ikt_project.give_feedback (
    p_student_id integer,
    p_offer_id integer,
    p_grade_work integer,
    p_grade_accommodation integer,
    p_comment varchar
)
AS $$
BEGIN
    IF NOT EXISTS(
        select 1 from ikt_project.internship
        where applies_for_offer_id = p_offer_id and
            applies_for_student_id = p_student_id
    ) THEN
        INSERT INTO ikt_project.internship (grade_work,
                                            grade_accommodation,
                                            comment_student,
                                            applies_for_student_id,
                                            applies_for_offer_id)
        VALUES (p_grade_work, p_grade_accommodation, p_comment, p_student_id, p_offer_id);
ELSE
UPDATE ikt_project.internship SET  grade_work=p_grade_work,
                                   grade_accommodation = p_grade_accommodation,
                                   comment_student = p_comment
WHERE applies_for_offer_id = p_offer_id and
        applies_for_student_id = p_student_id;
END IF;
COMMIT;

END
$$ language plpgsql;

create or replace procedure ikt_project.delete_student(
    p_user_id integer
)
as
$$
begin
    IF EXISTS (
        SELECT 1
        FROM ikt_project.student
        where id = p_user_id
    )
    THEN
delete from ikt_project.internship where applies_for_student_id = p_user_id;
delete from ikt_project.applies_for where student_id = p_user_id;
delete from ikt_project.student where id = p_user_id;

END IF;
end;
$$ language plpgsql;

-- update status of applicant - from accepted to ongoing, from ongoing to completed
CREATE OR REPLACE PROCEDURE ikt_project.update_applicant_status(
    p_offer_id integer,
    p_student_id integer
)
AS $$
DECLARE
v_status integer;
BEGIN
    -- check if this application exists
    IF EXISTS(
        SELECT 1
        FROM ikt_project.applies_for
        WHERE offer_id = p_offer_id AND
            student_id = p_student_id
    )
    THEN
        -- read the acceptance status for later
SELECT acceptance_status
FROM ikt_project.applies_for
WHERE student_id = p_student_id AND
        offer_id = p_offer_id
    INTO v_status;

-- change status from accepted to ongoing
IF v_status = 2 THEN
UPDATE ikt_project.applies_for
SET acceptance_status = 4
WHERE offer_id = p_offer_id AND
        student_id = p_student_id;

-- change status from ongoing to completed
ELSIF v_status = 4 THEN
UPDATE ikt_project.applies_for
SET acceptance_status = 5
WHERE offer_id = p_offer_id AND
        student_id = p_student_id;
END IF;
END IF;
COMMIT;
END
$$ LANGUAGE plpgsql;



