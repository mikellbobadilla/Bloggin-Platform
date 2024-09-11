CREATE TABLE IF NOT EXISTS posts (
    id int primary key generated always as identity,
    title varchar(100) not null,
    content text not null,
    category varchar(100) not null,
    tags text[],
    create_at timestamp with time zone default now(),
    update_at timestamp with time zone
);