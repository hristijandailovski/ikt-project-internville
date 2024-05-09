create or replace function ikt_project.offers_created_by_member(
    p_member_id integer,
    p_page_number integer
) returns table(offer_id integer,country_name varchar,field varchar, start_date date
    , duration_in_weeks ikt_project.pos_int,company_name varchar)
as
$$
declare
    v_page_number integer = p_page_number;
begin
    if v_page_number < 1 then
        v_page_number = 1;
    end if;
    return query
        select o.id as offer_id, cou.name as country_name, o.field, o.start_date,o.duration_in_weeks, com.name as company_name
        from ikt_project.offer as o
                 join ikt_project.company as com on o.company_id= com.id
                 join ikt_project.country as cou on com.country_id = cou.id
        where o.member_id = p_member_id
        order by o.start_date desc
        limit 20 offset (v_page_number -1)*20;
end;
$$ language plpgsql;

create or replace function ikt_project.find_user_credentials_with_username_and_password(
    p_username varchar,
    p_password varchar
) returns table(id integer,username varchar, password varchar, name varchar, surname varchar,
                type varchar)
as
$$
begin
    if(ikt_project.is_student(p_username) is true) then
        return query
            select eu.id,eu.username,eu.password,eu.name,eu.surname,'student'::varchar
            from ikt_project.end_user as eu
            where eu.username = p_username and eu.password = p_password;
    end if;
    if(ikt_project.is_member(p_username) is true) then
        return query
            select eu.id,eu.username,eu.password,eu.name,eu.surname,'member'::varchar
            from ikt_project.end_user as eu
            where eu.username = p_username and eu.password = p_password;
    end if;
end;
$$ language plpgsql;


create or replace function ikt_project.is_student(
    p_username varchar ) returns boolean
as
$$
begin
    if exists(
        select 1
        from ikt_project.end_user as eu
                 join ikt_project.student as st on eu.id = st.id
        where eu.username = p_username
    )
    then
        return true;
    else
        return false;
    end if;

end;
$$ language plpgsql;

create or replace function ikt_project.is_member(
    p_username varchar ) returns boolean
as
$$
begin
    if exists(
        select 1
        from ikt_project.end_user as eu
                 join ikt_project.member as mem on eu.id = mem.id
        where eu.username = p_username
    )
    then
        return true;
    else
        return false;
    end if;

end;
$$ language plpgsql;


create or replace function ikt_project.member_profile_edit_view(
    p_member_id integer
)returns table(id integer, name varchar,surname varchar, date_of_birth date,username varchar,password varchar,
               address ikt_project.address,phone_number ikt_project.phone,email_address ikt_project.email,country_id integer,
               org_name varchar,comm_phone_number ikt_project.phone,comm_email ikt_project.email,
               comm_address ikt_project.address,comm_country_name varchar)
as
$$
begin
    return query
        select  eu.id, eu.name, eu.surname,eu.date_of_birth,eu.username,eu.password,eu.address,eu.phone_number,eu.email_address,eu.country_id
             ,org.name as org_name, com.phone_number as comm_phone_number, com.email_address as comm_email
             ,com.address as comm_address,com_cou.name as  comm_country_name
        from ikt_project.end_user as eu
                 join ikt_project.member as mem on eu.id = mem.id
                 join ikt_project.committee as com on mem.committee_id = com.id
                 join ikt_project.country as com_cou on com.country_id =com_cou.id
                 join ikt_project.organization as org on com.org_id = org.id
        where mem.id = p_member_id;
end;
$$ language plpgsql;



create or replace function ikt_project.member_profile_view(
    p_member_id integer
)returns table(id integer, name varchar,surname varchar, age integer,country_name varchar,
               org_name varchar,comm_phone_number ikt_project.phone, comm_email ikt_project.email,
               comm_address ikt_project.address,comm_country_name varchar)
as
$$
begin
    return query
        select  eu.id, eu.name, eu.surname,date_part('year',age(NOW(),eu.date_of_birth))::integer as age,eu_cou.name as country_name,
                org.name as org_name,com.phone_number as comm_phone_number, com.email_address as comm_email,
                com.address as comm_address,com_cou.name as comm_country_name
        from ikt_project.end_user as eu
                 join ikt_project.country as eu_cou on eu.country_id = eu_cou.id
                 join ikt_project.member as mem on eu.id = mem.id
                 join ikt_project.committee as com on mem.committee_id = com.id
                 join ikt_project.country as com_cou on com_cou.id = com.country_id
                 join ikt_project.organization as org on com.org_id = org.id
        where mem.id = p_member_id;
end;
$$ language plpgsql;

create or replace  function ikt_project.countries_of_committees_by_organization(
    p_org_id integer
) returns table(id integer,name varchar)
as
$$
begin
    return query
        select cou.id,cou.name
        from ikt_project.organization as org
                 join ikt_project.committee as com on org.id = com.org_id
                 join ikt_project.country as cou on cou.id = com.country_id
        where org.id = p_org_id;
end;
$$ language plpgsql;

create or replace  function ikt_project.all_applicant_by_offer(
    p_offer_id integer
) returns table(offer_id integer,student_id integer,status varchar,
                country varchar, name varchar, surname varchar,age integer,
                faculty varchar, degree ikt_project.study, major varchar)
as
$$
begin
    return query
        select ap.offer_id,ap.student_id,ac.status,cou.name as country,
               eu.name,eu.surname,date_part('year',age(NOW(),eu.date_of_birth))::integer as age
                ,edu.name as faculty,st.study_level as degree,ma.major
        from ikt_project.end_user as eu
                 join ikt_project.country as cou on eu.country_id = cou.id
                 join ikt_project.student as st  on eu.id = st.id
                 join ikt_project.major as ma on st.major_id = ma.id
                 join ikt_project.educational_institute as edu on  edu.id = st.educational_institute_id
                 join ikt_project.applies_for as ap on st.id = ap.student_id
                 join ikt_project.acceptance_status as ac on ap.acceptance_status = ac.id
        where ap.offer_id = p_offer_id;
end;
$$ language plpgsql;


