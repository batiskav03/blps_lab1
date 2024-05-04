drop table if exists users cascade;
drop table if exists transaction cascade;
drop table if exists blps.album cascade;
drop table if exists blps.author cascade;
drop table if exists blps.playlist cascade;
drop table if exists blps.music cascade;
drop table if exists blps.users_playlists cascade;
drop table if exists blps.authors_music cascade;
drop table if exists blps.playlist_to_music cascade;
drop table if exists blps.authorities cascade;
drop table if exists blps.users cascade;
drop table if exists authorities cascade;
drop table if exists users cascade;
drop table if exists blps.subscribe cascade;
drop table if exists transaction cascade;
drop schema if exists blps cascade;

create schema blps;

create table transaction(
    id uuid primary key,
    date date,
    url text,
    status boolean
);

create table users(
                      id serial primary key,
                      username varchar(50),
                      password varchar(500) not null,
                      enabled boolean not null
);

create table authorities (
                             u_id integer,
                             username varchar(50) not null,
                             authority varchar(50) not null,
                             constraint fk_authorities_users foreign key(u_id) references users(id)
);
create unique index ix_auth_username on authorities (username,authority);

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
    u_id  integer references users,
    primary key (pl_id, u_id)
);

create table blps.playlist_to_music
(
    pl_id integer references blps.playlist,
    music_id integer references  blps.music,
    primary key (pl_id, music_id)
);

create table blps.subscribe
(
    user_id integer unique references users,
    subscription boolean,
    start_date date,
    end_date date,
    primary key (user_id)
);

insert into blps.author(name)
values ('Megadeth');
insert into blps.author(name)
values ('Хаски');
insert into blps.album (name, author_id)
values ('Любимые песни (воображаемых) людей', 2);
insert into blps.album (name, author_id)
values ('Rust In Peace... Polaris', 1);
insert into blps.playlist (name, description) values ('admin playlist', 'liked');


create or replace function update_subscribe()
    returns trigger
    language plpgsql
as $$
    declare
        s_date date;
        e_date date;
    begin
        if OLD.subscription  is false THEN
            s_date := current_date;
            e_date := s_date::date + 30;
        ELSE
            s_date := OLD.start_date;
            e_date := OLD.end_date + 30;
        end if;
        NEW.start_date = s_date;
        NEW.end_date = e_date;

        return NEW;
    end;
    $$;

create or replace trigger update_subscribe before update on blps.subscribe
    for each row when (NEW.subscription = true)
    execute procedure update_subscribe();

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

create or replace procedure add_music_to_playlist(playlist_id integer, music_id integer)
    language plpgsql
as
$$
begin
    insert into blps.playlist_to_music (pl_id, music_id) values (playlist_id, music_id);
end;
$$;

call add_music(1, 1, 'Tornado of Souls', 'Megadeth - Tornado Of Souls.mp3');
call add_music_by_text('Rust In Peace... Polaris', 'Poison was the cure',  'Megadeth - Poison Was The Cure.mp3');
call add_music_by_text('Rust In Peace... Polaris', 'Five Magics',  'Megadeth - Five Magics.mp3');
call add_music_by_text('Rust In Peace... Polaris', 'Hangar 18',  'Megadeth - Hangar 18.mp3');
call add_music_by_text('Rust In Peace... Polaris', 'Lucretia',  'Megadeth - Lucretia.mp3');
call add_music_by_text('Rust In Peace... Polaris', 'Holy Wars.The Punishment Due',  'Megadeth - Holy Wars.The Punishment Due.mp3');
call add_music_by_text('Rust In Peace... Polaris', 'Rust In Peace... Polaris',  'Megadeth - Rust In Peace.Polaris.mp3');
call add_music_by_text('Rust In Peace... Polaris', 'Take No Prisoners',  'Megadeth - Take No Prisoners.mp3');
call add_music_by_text('Rust In Peace... Polaris', 'Dawn Patrol',  'Megadeth - Dawn Patrol.mp3');

call add_music_by_text('Любимые песни (воображаемых) людей', 'Ай',  'Хаски - Ай.mp3');
call add_music_by_text('Любимые песни (воображаемых) людей', 'Бит шатает голову',  'Хаски - Бит шатает голову.mp3');
call add_music_by_text('Любимые песни (воображаемых) людей', 'Панелька',  'Хаски - Панелька.mp3');
call add_music_by_text('Любимые песни (воображаемых) людей', 'Заново',  'Хаски - Заново.mp3');
call add_music_by_text('Любимые песни (воображаемых) людей', 'Пуля-дура',  'Хаски - Пуля-дура.mp3');
call add_music_by_text('Любимые песни (воображаемых) людей', 'Пироман 17',  'Хаски - Пироман 17.mp3');
call add_music_by_text('Любимые песни (воображаемых) людей', 'Аллилуйя',  'Хаски feat. bollywoodFM - Аллилуйя.mp3');
call add_music_by_text('Любимые песни (воображаемых) людей', 'Мармелад',  'Хаски - Мармелад.mp3');
call add_music_by_text('Любимые песни (воображаемых) людей', 'Фюрер',  'Хаски - Фюрер.mp3');
call add_music_by_text('Любимые песни (воображаемых) людей', 'Черным-черно',  'Хаски - Черным-черно.mp3');
call add_music_by_text('Любимые песни (воображаемых) людей', 'Хозяйка',  'Хаски - Хозяйка.mp3');
call add_music_by_text('Любимые песни (воображаемых) людей', 'Детка-голливуд',  'Хаски - Детка-голливуд.mp3');
call add_music_by_text('Любимые песни (воображаемых) людей', 'Мультики',  'Хаски - Мультики.mp3');


call add_music_to_playlist(1, 1);
call add_music_to_playlist(1, 2);
call add_music_to_playlist(1, 5);
call add_music_to_playlist(1, 10);
call add_music_to_playlist(1, 14);

insert into users(username, password, enabled) values ('user', 1, true), ('admin', 2, true);
insert into authorities(username, authority) values ('user', 'ROLE_USER'), ('admin', 'ROLE_ADMIN');

insert into blps.subscribe (user_id, subscription) values (1, false);
insert into blps.subscribe (user_id, subscription) values (2, false);

update blps.subscribe set subscription = true where user_id = 2;


select * from blps.music
                  inner join blps.album on blps.music.album_id = blps.album.album_id
                  inner join blps.author on blps.author.author_id = blps.album.author_id;