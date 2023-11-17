-- This file allow to write SQL commands that will be emitted in test and dev.

drop table if exists movie;

create table movie
(
    id          varchar(255) not null,
    title       varchar(255) not null,
    year        int,
    description varchar(10000),
    primary key (id)
);

insert into movie (id, title, year, description)
values ('tt0133093', 'The Matrix', 1999,
        'When a beautiful stranger leads computer hacker Neo to a forbidding underworld, he discovers the shocking truth--the life he knows is the elaborate deception of an evil cyber-intelligence.'),
       ('tt0266697', 'Kill Bill: Vol. 1', 2003,
        'After awakening from a four-year coma, a former assassin wreaks vengeance on the team of assassins who betrayed her.'),
       ('tt0073486', 'One Flew Over the Cuckoo''s Nest', 1975,
        'In the Fall of 1963, a Korean War veteran and criminal pleads insanity and is admitted to a mental institution, where he rallies up the scared patients against the tyrannical nurse.');
