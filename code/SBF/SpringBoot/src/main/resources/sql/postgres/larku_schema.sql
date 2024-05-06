drop table if exists student_scheduledclass;
drop table if exists scheduledclass;
drop table if exists course;
drop table if exists phonenumber;
drop table if exists student;

drop sequence if exists public.student_id_seq;
drop sequence if exists public.course_course_id_seq;
drop sequence if exists public.scheduledclass_class_id_seq;
drop sequence if exists public.phonenumber_phone_id_seq;

create table public.student
(
    id          serial
        primary key,
    name        varchar(150) not null,
--     phonenumber varchar(25),
    status      varchar(20),
    dob         date
);

alter table public.student
    owner to larku;

create table public.course
(
    course_id serial
        constraint course_pk
            primary key,
    code      varchar(20)   not null,
    title     varchar(100)  not null,
    credits   numeric(3, 1) not null
);

alter table public.course
    owner to larku;

create table public.scheduledclass
(
    class_id  serial
        constraint scheduledclss_pk
            primary key,
    startdate date    not null,
    enddate   date    not null,
    course_id integer not null
        constraint scheduledclass_course_course_id_fk
            references public.course
);

alter table public.scheduledclass
    owner to larku;

create table public.phonenumber
(
    phone_id   serial
        constraint phonenumber_pk
            primary key,
    type       varchar(15) not null,
    number     varchar(20) not null,
    student_id integer
        constraint phonenumber_student_id_fk
            references public.student
);

create table public.student_scheduledclass
(
    student_id integer not null
        constraint student_scheduledclass_student_id_fk
            references public.student,
    class_id   integer not null
        constraint student_scheduledclass_scheduledclass_class_id_fk
            references public.scheduledclass,
    constraint student_scheduledclass_pk
        unique (class_id, student_id)
);

alter table public.student_scheduledclass
    owner to larku;



alter table public.phonenumber
    owner to larku;

-- create sequence public.student_id_seq
--     as integer;

alter sequence public.student_id_seq owner to larku;

alter sequence public.student_id_seq owned by public.student.id;

-- create sequence public.course_course_id_seq
--     as integer;

alter sequence public.course_course_id_seq owner to larku;

alter sequence public.course_course_id_seq owned by public.course.course_id;

-- create sequence public.scheduledclass_class_id_seq
--     as integer;

alter sequence public.scheduledclass_class_id_seq owner to larku;

alter sequence public.scheduledclass_class_id_seq owned by public.scheduledclass.class_id;

-- create sequence public.phonenumber_phone_id_seq
--     as integer;

alter sequence public.phonenumber_phone_id_seq owner to larku;

alter sequence public.phonenumber_phone_id_seq owned by public.phonenumber.phone_id;

