create or replace procedure ikt_project.delete_offer(
    p_member_id integer,
    p_offer_id integer
)
as
$$
begin
    IF EXISTS (
        SELECT 1
        FROM ikt_project.offer
        where id = p_offer_id and member_id=p_member_id
    )
    THEN
        delete from ikt_project.internship as i where i.applies_for_offer_id = p_offer_id;
        delete from ikt_project.applies_for as a where a.offer_id = p_offer_id;
        delete from ikt_project.accommodation as ac where ac.offer_id = p_offer_id;
        delete from ikt_project.offer where id=p_offer_id;
    END IF;
end;
$$ language plpgsql;



create or replace procedure insert_end_user_member(
    p_username varchar,
    p_password varchar,
    p_name varchar,
    p_surname varchar,
    p_date_of_birth date,
    p_address varchar,
    p_phone_number varchar,
    p_email_address varchar,
    p_country_id integer,
    p_organization_id integer,
    p_committee_country_id integer
)
as
$$
declare
    v_returned_member_id integer;
    v_returned_committee_id integer = -1; --assign an invalid value;
begin
    if exists(
        select 1
        from ikt_project.end_user
        where username = p_username
    )
    then
        --member exists with this username, raise exception or ignore ?
    else
        --find the id
        select com.id
        from ikt_project.organization as org
                 join ikt_project.committee as com on org.id = com.org_id
                 join ikt_project.country as cou on com.country_id = cou.id
        where com.country_id =p_committee_country_id and org.id = p_organization_id
        into  v_returned_committee_id;

        if v_returned_committee_id != -1 then
            -- the username is free, and can be used to create new member
            -- there is a committee for the organization in the specified country
            INSERT INTO ikt_project.end_user(username, password, name, surname, date_of_birth, address, phone_number,email_address, country_id)
            VALUES (p_username, p_password, p_name, p_surname, p_date_of_birth, p_address, p_phone_number, p_email_address, p_country_id)
            RETURNING id INTO v_returned_member_id;

            insert into ikt_project.member(id, committee_id) values (v_returned_member_id,v_returned_committee_id);
        end if;
    end if;
    commit;
end
$$ language  plpgsql;


create or replace procedure ikt_project.insert_offer (
    p_requirements varchar,
    p_responsibilities varchar,
    p_benefits varchar,
    p_salary integer,
    p_field varchar,
    p_start_date date,
    p_duration_in_weeks integer,
    p_member_id integer,
    p_company_id integer
)
AS $$
BEGIN
    INSERT INTO ikt_project.offer (requirements, responsibilities, benefits,
                                   salary, field, start_date, duration_in_weeks, member_id, company_id,is_active)
    VALUES (p_requirements, p_responsibilities, p_benefits,
            p_salary, p_field, p_start_date, p_duration_in_weeks, p_member_id, p_company_id,true);
    COMMIT;

END;
$$ language plpgsql;

--update existing offer with offer_id
create or replace procedure ikt_project.update_offer(
    p_member_id integer,
    p_offer_id integer,
    p_requirements varchar,
    p_responsibilities varchar,
    p_benefits varchar,
    p_salary integer,
    p_field varchar,
    p_start_date date,
    p_duration_in_weeks integer
)
as
$$
begin
    -- Update existing offer
    UPDATE ikt_project.offer
    SET requirements = p_requirements,
        responsibilities = p_responsibilities,
        benefits = p_benefits,
        salary = p_salary,
        field = p_field,
        start_date = p_start_date,
        duration_in_weeks = p_duration_in_weeks
    WHERE id = p_offer_id and member_id = p_member_id;
end;

$$ language plpgsql;

--update existing offer with accommodation with offer_id
create or replace procedure ikt_project.update_offer_accommodation(
    p_member_id integer,
    p_offer_id integer,
    p_requirements varchar,
    p_responsibilities varchar,
    p_benefits varchar,
    p_salary integer,
    p_field varchar,
    p_start_date date,
    p_duration_in_weeks integer,
    p_acc_phone varchar,
    p_acc_email varchar,
    p_acc_address varchar,
    p_acc_description varchar
)
as
$$
begin
    UPDATE ikt_project.offer
    SET requirements = p_requirements,
        responsibilities = p_responsibilities,
        benefits = p_benefits,
        salary = p_salary,
        field = p_field,
        start_date = p_start_date,
        duration_in_weeks = p_duration_in_weeks
    WHERE id = p_offer_id and member_id = p_member_id;

    IF EXISTS (
        SELECT 1
        FROM ikt_project.accommodation as a
        where a.offer_id = p_offer_id
    )
    THEN
        --if exists update
        UPDATE ikt_project.accommodation
        SET phone_number = p_acc_phone,
            email_address = p_acc_email,
            address = p_acc_address,
            description = p_acc_description
        WHERE offer_id = p_offer_id;
    ELSE
        --Accommodation doesn't exists for this offer, insert the accommodation
        INSERT INTO  ikt_project.accommodation(phone_number,email_address,address,description,offer_id)
        VALUES (p_acc_phone,p_acc_email,p_acc_address,p_acc_description,p_offer_id);
    end if;
    commit;
end;

$$ language plpgsql;


--insert new Student
create or replace procedure ikt_project.insert_end_user_student (
    p_username varchar,
    p_password varchar,
    p_name varchar,
    p_surname varchar,
    p_date_of_birth date,
    p_address varchar,
    p_phone_number varchar,
    p_email_address varchar,
    p_country_id integer,
    p_study_level varchar,
    p_gpa float,
    p_ects_credits integer,
    p_major_id integer,
    p_educational_institute_id integer,
    p_start_year integer
)
AS $$
declare
    v_returned_student_id integer;
BEGIN
    IF EXISTS (
        SELECT 1
        FROM ikt_project.end_user
        where username = p_username
    )
    THEN
        --student exists with this username, raise exception or ignore ?

    ELSE
        INSERT INTO ikt_project.end_user(username, password, name, surname, date_of_birth, address, phone_number,email_address, country_id)
        VALUES (p_username, p_password, p_name, p_surname, p_date_of_birth, p_address, p_phone_number, p_email_address, p_country_id)
        RETURNING id INTO v_returned_student_id;

        INSERT INTO ikt_project.student(id,study_level, gpa, start_year, ects_credits, major_id, educational_institute_id)
        VALUES (v_returned_student_id,p_study_level, p_gpa, p_start_year, p_ects_credits, p_major_id, p_educational_institute_id);
    END IF;
    COMMIT;
END;
$$ language plpgsql;


--update existing Student
create or replace procedure ikt_project.update_end_user_student (
    p_student_id integer,
    p_password varchar,
    p_name varchar,
    p_surname varchar,
    p_date_of_birth date,
    p_address varchar,
    p_phone_number varchar,
    p_email_address varchar,
    p_country_id integer,
    p_study_level varchar,
    p_gpa float,
    p_ects_credits integer,
    p_major_id integer,
    p_educational_institute_id integer,
    p_start_year integer
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
    WHERE id = p_student_id;

    UPDATE ikt_project.student
    SET study_level = p_study_level,
        gpa = p_gpa,
        start_year = p_start_year,
        ects_credits = p_ects_credits,
        major_id = p_major_id,
        educational_institute_id = p_educational_institute_id
    WHERE id = p_student_id;
    COMMIT;
END;
$$ language plpgsql;

--Procedure for inserting a row into the experience table
CREATE OR REPLACE PROCEDURE ikt_project.insert_experience(
    p_type_of_job VARCHAR,
    p_description VARCHAR,
    p_start_year DATE,
    p_duration_in_weeks INTEGER,
    p_student_id INTEGER
)
AS $$
BEGIN
    -- Check if the experience exists based on the provided type_of_job and student_id
    -- Insert a new experience
    INSERT INTO ikt_project.experience (type_of_job, description, start_year, duration_in_weeks, student_id)
    VALUES (p_type_of_job, p_description, p_start_year, p_duration_in_weeks, p_student_id);


    COMMIT;
END;
$$language plpgsql;
