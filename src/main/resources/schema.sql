drop table if exists public.user_role;
drop table if exists public.user_friend;
drop table if exists public.role;
drop table if exists public.post;
drop table if exists public.user;

create table if not exists public.user (
    id bigint generated by default as identity primary key,
    username varchar(128) not null unique,
    name varchar(64),
    surname varchar(64),
    age integer,
    sex varchar(8),
    interests text,
    city varchar(64),
    password varchar(256)
);

create table if not exists public.role (
    id bigint generated by default as identity primary key,
    role varchar(32)
);

create table if not exists public.user_role (
    user_id bigint,
    role_id bigint,
    constraint user_fk foreign key (user_id) references public.user (id),
    constraint role_fk foreign key (role_id) references public.role (id)
);

create table if not exists public.user_friend (
    user_id bigint,
    friend_id bigint,
    constraint user_fk foreign key (user_id) references public.user (id)
);

create table if not exists public.post (
    id bigint generated by default as identity primary key,
    owner_id bigint,
    payload varchar,
    constraint owner_fk foreign key (owner_id) references public.user (id)
);

create index user_name_surname_idx on public."user" using btree (name varchar_pattern_ops, surname varchar_pattern_ops);

create index user_friend_user_id_idx on public.user_friend using btree (user_id);
