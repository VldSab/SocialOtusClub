DROP TABLE IF EXISTS public.user;

create table if not exists public.user (
    id bigint,
    name varchar(64),
    surname varchar(64),
    age integer,
    sex varchar(8),
    interests text,
    city varchar(64)
);