create or replace function ikt_project.lang_known_by_student(
    p_student_id integer
) returns table(id integer,name varchar, level ikt_project.lang_level)
as
$$
begin
return query
select l.id, l.name,kw.level
from ikt_project.knows_language as kw
         join ikt_project.language as l
              on kw.lang_id = l.id
where  kw.student_id = p_student_id;
end;
$$ language plpgsql;


--function that returns a view for offer
create or replace function ikt_project.offer_detail_view(p_offer_id integer)
    returns table(offer_id integer,country_name varchar,company_name varchar,company_address varchar,
                  requirements varchar, responsibilities varchar, benefits varchar, salary ikt_project.pos_int,
                  field varchar, start_date date, duration_in_weeks ikt_project.pos_int, acc_phone ikt_project.phone,
                  acc_email ikt_project.email, acc_address ikt_project.address, acc_description varchar)
as
$$
begin
return query
select o.id, cn.name, c.name, c.address, o.requirements, o.responsibilities,
       o.benefits, o.salary, o.field, o.start_date, o.duration_in_weeks, c.phone_number,
       c.email_address, c.address, a.description
from ikt_project.offer o join ikt_project.member m on o.member_id = m.id
                         join ikt_project.company c on o.company_id = c.id
                         join ikt_project.country cn on c.country_id = cn.id
                         join ikt_project.accommodation a on a.offer_id = o.id
where o.id = p_offer_id;
end;
$$ language plpgsql;


--function that returns a view for a student's profile
create or replace function ikt_project.student_profile_view(
    p_student_id integer
)returns table(id integer, name varchar,surname varchar, age integer,country_name varchar
    ,study_level ikt_project.study, fac_name varchar, major varchar, start_year integer)
as
$$
begin
return query
select  eu.id, eu.name, eu.surname,date_part('year',age(NOW(),eu.date_of_birth))::integer as age,cou.name as country_name,st.study_level,ed.name as fac_name,ma.major as major, st.start_year
from ikt_project.end_user as eu
         join ikt_project.country as cou on eu.country_id = cou.id
         join ikt_project.student as st on eu.id = st.id
         join ikt_project.educational_institute as ed on st.educational_institute_id = ed.id
         join ikt_project.major as ma on ma.id = st.major_id
where p_student_id = eu.id;
end;
$$ language plpgsql;


--view that will be called for populating the edit-profile.html
create or replace function ikt_project.student_profile_edit_view(
    p_student_id integer
)returns table(id integer, name varchar,surname varchar, date_of_birth date,username varchar,password varchar,
               address ikt_project.address,phone_number ikt_project.phone,email_address ikt_project.email,country_id integer,
               study_level ikt_project.study,major_id integer, fac_id integer, start_year integer, gpa ikt_project.gpa, ects_credits integer)
as
$$
begin
return query
select  eu.id, eu.name, eu.surname,eu.date_of_birth,eu.username,eu.password,eu.address,eu.phone_number,eu.email_address,eu.country_id
     ,st.study_level,st.major_id,st.educational_institute_id as fac_id, st.start_year,st.gpa,st.ects_credits
from ikt_project.end_user as eu
         join ikt_project.student as st on eu.id = st.id
where p_student_id = eu.id;
end;
$$ language plpgsql;


--active offers
create or replace function ikt_project.active_offers(
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
where o.is_active = true
order by o.start_date desc
    limit 20 offset (p_page_number -1)*20;
end;
$$ language plpgsql;


--function that returns view for path: "/companies"
create or replace function ikt_project.companies_view_on_page(
    p_page_number integer
)returns table(id integer, name varchar, address ikt_project.address, country_name varchar
    , number_of_employees ikt_project.pos_int,offers_count bigint)
as
$$
declare
v_page_number integer = p_page_number;
begin
    if v_page_number < 1 then
        v_page_number = 1;
end if;
return query
select com.id, com.name, com.address,cou.name,com.number_of_employees,count(*) as offers_count
from ikt_project.company as com
         join ikt_project.country as cou on com.country_id = cou.id
         join ikt_project.offer as of on of.company_id = com.id
group by com.id, com.name, com.address,cou.name,com.number_of_employees
order by count(*) desc
    limit 20 offset (p_page_number -1)*20;
end;
$$ language plpgsql;

create or replace function ikt_project.offer_edit_view(
    p_offer_id integer
) returns table(offer_id integer,country_name varchar,company_name varchar,company_address ikt_project.address,
                requirements varchar, responsibilities varchar, benefits varchar, salary ikt_project.pos_int,
                field varchar, start_date date, duration_in_weeks ikt_project.pos_int, acc_phone ikt_project.phone,
                acc_email ikt_project.email, acc_address ikt_project.address, acc_description varchar)
as
$$
begin
return query
select o.id as offer_id, cn.name as country_name, c.name as company_name, c.address as company_address, o.requirements, o.responsibilities,
       o.benefits, o.salary, o.field, o.start_date, o.duration_in_weeks, a.phone_number as acc_phone,
       a.email_address as acc_email, a.address as acc_address, a.description as acc_description
from ikt_project.offer o join ikt_project.member m on o.member_id = m.id
                         join ikt_project.company c on o.company_id = c.id
                         join ikt_project.country cn on c.country_id = cn.id
                         left outer join ikt_project.accommodation a on a.offer_id = o.id
where o.id = p_offer_id;
end;
$$ language plpgsql;


-- create  view nbp_project.active_offers_view as
-- select o.id as offer_id, cou.name as country_name, o.field, o.start_date,o.duration_in_weeks, com.name as company_name
-- from offer as o
--          join company as com on o.company_id= com.id
--          join country as cou on com.country_id = cou.id
--      --join applies_for as app on app.offer_id = o.id
--      --join acceptance_status as acc on acc.id = app.acceptance_status
-- order by o.start_date desc

create or replace function ikt_project.student_application(
    p_student_id integer,
    p_page_number integer
)
    returns table(offer_id integer, country_name varchar, field varchar, start_date date,
                  duration_in_weeks ikt_project.pos_int , company_name varchar, acceptance_status varchar)
as
$$
declare
v_page_number integer = p_page_number;
begin
    if v_page_number < 1 then
        v_page_number = 1;
end if;
return query
select af.offer_id as offer_id, c2.name as country_name, o.field as field,
       o.start_date as start_date, o.duration_in_weeks as duration_in_weeks,
       c.name as company_name, aas.status as acceptance_status
from ikt_project.applies_for af
         join ikt_project.acceptance_status aas on aas.id = af.acceptance_status
         join ikt_project.offer o on af.offer_id = o.id
         join ikt_project.company c on o.company_id = c.id
         join ikt_project.country c2 on c.country_id = c2.id
where af.student_id = p_student_id
order by o.start_date
    limit 20 offset (v_page_number -1)*20;

end;
$$ language plpgsql;

