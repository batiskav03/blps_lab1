drop table if exists blps.user cascade;
drop table if exists blps.album cascade;
drop table if exists blps.author cascade;
drop table if exists blps.playlist cascade;
drop table if exists blps.music cascade;
drop table if exists blps.users_playlists cascade;
drop table if exists blps.authors_music cascade;


CREATE table blps.user
(
    u_id     serial primary key,
    username text not null,
    reg_data timestamp
);

create table blps.playlist
(
    pl_id       serial primary key,
    name        text not null,
    description text
);


create table blps.author
(
    author_id serial primary key,
    name      text not null
);


create table blps.album
(
    album_id  serial primary key,
    name      text not null,
    author_id integer references blps.author

);

create table blps.music
(
    music_id serial primary key,
    name     text not null,
    year     timestamp,
    aud_num  integer,
    album_id integer references blps.album,
    url      text not null
);

create table blps.authors_music
(
    author_id integer references blps.author,
    music_id  integer references blps.music,
    primary key (author_id, music_id)
);

create table blps.users_playlists
(
    pl_id integer references blps.playlist,
    u_id  integer references blps.user,
    primary key (pl_id, u_id)
);

insert into blps.author(name)
values ('Megadeth');
insert into blps.author(name)
values ('Хаски');
insert into blps.album (name, author_id)
values ('Rust In Peace... Polaris', 1);


create
    or replace procedure add_music(al_id integer, au_id integer, song_name text, song_url text)
    language plpgsql
as
$$
declare
    mu_id integer;
begin
    insert into blps.music (name, year, aud_num, album_id, url)
    values (song_name, current_timestamp, 0, al_id, song_url);
    mu_id
        := (select blps.music.music_id
            from blps.music
            where song_name = blps.music.name
              and blps.music.album_id = al_id);
    insert into blps.authors_music(author_id, music_id)
    values (au_id, mu_id);

end;
$$;


create
    or replace procedure add_music_by_text(album_name text, song_name text, song_url text)
    language plpgsql
as
$$
declare
    al_id integer := (select album_id
                      from blps.album
                      where blps.album.name = album_name);
    au_id integer := (select author_id
                      from blps.album
                      where al_id = album_id);
begin
    call add_music(al_id, au_id, song_name, song_url);
end;
$$;



call add_music(1, 1, 'Tornado of Souls', 'tornadoofsouls.mp3');
call add_music_by_text('Rust In Peace... Polaris', 'Poison was the cure',  '');
