drop table if exists movieActor;
drop table if exists actor;
drop table if exists movie;

create table movie
(
    movieId     varchar(255) not null,
    title       varchar(255) not null,
    year        year,
    description varchar(10000),
    primary key (movieId)
);

insert into movie (movieId, title, year, description)
values ('tt0133093', 'The Matrix', 1999,
        'When a beautiful stranger leads computer hacker Neo to a forbidding underworld, he discovers the shocking truth--the life he knows is the elaborate deception of an evil cyber-intelligence.'),
       ('tt0266697', 'Kill Bill: Vol. 1', 2003,
        'After awakening from a four-year coma, a former assassin wreaks vengeance on the team of assassins who betrayed her.'),
       ('tt0073486', 'One Flew Over the Cuckoo''s Nest', 1975,
        'In the Fall of 1963, a Korean War veteran and criminal pleads insanity and is admitted to a mental institution, where he rallies up the scared patients against the tyrannical nurse.'),
       ('tt0241527', 'Harry Potter and the Sorcerer''s Stone', 2001,
        'An orphaned boy enrolls in a school of wizardry, where he learns the truth about himself, his family and the terrible evil that haunts the magical world.'),
       ('tt0068646', 'The Godfather', 1972,
        'Don Vito Corleone, head of a mafia family, decides to hand over his empire to his youngest son Michael. However, his decision unintentionally puts the lives of his loved ones in grave danger.'),
       ('tt0111161', 'The Shawshank Redemption', 1994,
        'Over the course of several years, two convicts form a friendship, seeking consolation and, eventually, redemption through basic compassion.'),
       ('tt0825232', 'The Bucket List', 2007,
        'Two terminally ill men escape from a cancer ward and head off on a road trip with a wish list of to-dos before they die.'),
       ('tt0109686', 'Dumb and Dumber', 1994,
        'After a woman leaves a briefcase at the airport terminal, a dumb limo driver and his dumber friend set out on a hilarious cross-country road trip to Aspen to return it.'),
       ('tt1659337', 'The Perks of Being a Wallflower', 2012,
        'Charlie, a 15-year-old introvert, enters high school and is nervous about his new life. When he befriends his seniors, he learns to cope with his friend''s suicide and his tumultuous past.'),
       ('tt0468569', 'The Dark Knight', 2008,
        'When the menace known as the Joker wreaks havoc and chaos on the people of Gotham, Batman must accept one of the greatest psychological and physical tests of his ability to fight injustice.'),
       ('tt0110912', 'Pulp Fiction', 1994,
        'The lives of two mob hitmen, a boxer, a gangster and his wife, and a pair of diner bandits intertwine in four tales of violence and redemption.'),
       ('tt0120737', 'The Lord of the Rings: The Fellowship of the Ring', 2001,
        'A meek Hobbit from the Shire and eight companions set out on a journey to destroy the powerful One Ring and save Middle-earth from the Dark Lord Sauron.'),
       ('tt0137523', 'Fight Club', 1999,
        'An insomniac office worker and a devil-may-care soap maker form an underground fight club that evolves into much more.'),
       ('tt1375666', 'Inception', 2010,
        'A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O., but his tragic past may doom the project and his team to disaster.'),
       ('tt0816692', 'Interstellar', 2014,
        'When Earth becomes uninhabitable in the future, a farmer and ex-NASA pilot, Joseph Cooper, is tasked to pilot a spacecraft, along with a team of researchers, to find a new planet for humans.'),
       ('tt0103064', 'Terminator 2: Judgment Day', 1991,
        'A cyborg, identical to the one who failed to kill Sarah Connor, must now protect her ten year old son John from an even more advanced and powerful cyborg.'),
       ('tt0120586', 'American History X', 1998,
        'Living a life marked by violence, neo-Nazi Derek finally goes to prison after killing two black youths. Upon his release, Derek vows to change; he hopes to prevent his brother, Danny, who idolizes Derek, from following in his footsteps.'),
       ('tt0482571', 'The Prestige', 2006,
        'After a tragic accident, two stage magicians in 1890s London engage in a battle to create the ultimate illusion while sacrificing everything they have to outwit each other.'),
       ('tt0102926', 'The Silence of the Lambs', 1991,
        'A young F.B.I. cadet must receive the help of an incarcerated and manipulative cannibal killer to help catch another serial killer, a madman who skins his victims.'),
       ('tt0172811', 'Moj ata, socialistični kulak', 1987,
        'Year 1945. The second World War is over and the soldiers from the disbanded army are returning home. Yet there is still no sign of Joze Malek. His wife Mimika and their children Tincek and olga know only that he had deserted the German army and gone over to the Soviet Red Army.');

create table actor
(
    actorId   varchar(255) not null,
    firstName varchar(255) not null,
    lastName  varchar(255) not null,
    bornDate  date,
    primary key (actorId)
);

insert into actor (actorId, firstName, lastName, bornDate)
values ('nm0000206', 'Keanu', 'Reeves', '1964-09-02'),
       ('nm0000401', 'Laurence', 'Fishburne', '1961-07-30'),
       ('nm0005251', 'Carrie-Anne', 'Moss', '1967-08-21'),
       ('nm0000235', 'Uma', 'Thurman', '1970-05-29'),
       ('nm0000197', 'Jack', 'Nicholson', '1937-05-22'),
       ('nm0705356', 'Daniel', 'Radcliffe', '1989-07-23'),
       ('nm0000008', 'Marlon', 'Brando', '1924-07-01'),
       ('nm0000199', 'Al', 'Pacino', '1940-05-25'),
       ('nm0000151', 'Morgan', 'Freeman', '1937-06-01'),
       ('nm0000120', 'Jim', 'Carrey', '1962-01-17'),
       ('nm0914612', 'Emma', 'Watson', '1990-05-15'),
       ('nm0000288', 'Christian', 'Bale', '1974-01-30'),
       ('nm0005132', 'Heath', 'Ledger', '1979-04-04'),
       ('nm0000237', 'John', 'Travolta', '1954-02-18'),
       ('nm0000704', 'Elijah', 'Wood', '1981-01-28'),
       ('nm0000093', 'Brad', 'Pitt', '1963-12-18'),
       ('nm0000138', 'Leonardo', 'DiCaprio', '1974-11-11'),
       ('nm0000190', 'Matthew', 'McConaughey', '1969-11-04'),
       ('nm0000216', 'Arnold', 'Schwarzenegger', '1947-07-30'),
       ('nm0001570', 'Edward', 'Norton', '1969-08-18'),
       ('nm0413168', 'Hugh', 'Jackman', '1968-10-12'),
       ('nm0000164', 'Anthony', 'Hopkins', '1937-12-31'),
       ('nm0000149', 'Jodie', 'Foster', '1962-11-19');

create table movieActor
(
    movieActorId int          not null auto_increment, -- optional, could be useful though
    movieId      varchar(255) not null,
    actorId      varchar(255) not null,
    primary key (movieActorId),                        -- could be a combination of movieId and actorId
    foreign key (movieId) references movie (movieId) on delete cascade on update cascade,
    foreign key (actorId) references actor (actorId) on delete cascade on update cascade
);

insert into movieActor (movieId, actorId)
values ('tt0133093', 'nm0000206'),
       ('tt0133093', 'nm0000401'),
       ('tt0133093', 'nm0005251'),
       ('tt0266697', 'nm0000235'),
       ('tt0073486', 'nm0000197'),
       ('tt0241527', 'nm0705356'),
       ('tt0241527', 'nm0914612'),
       ('tt0068646', 'nm0000008'),
       ('tt0068646', 'nm0000199'),
       ('tt0111161', 'nm0000151'),
       ('tt0825232', 'nm0000151'),
       ('tt0109686', 'nm0000120'),
       ('tt1659337', 'nm0914612'),
       ('tt0468569', 'nm0000288'),
       ('tt0468569', 'nm0005132'),
       ('tt0110912', 'nm0000237'),
       ('tt0120737', 'nm0000704'),
       ('tt0137523', 'nm0000093'),
       ('tt0137523', 'nm0001570'),
       ('tt1375666', 'nm0000138'),
       ('tt0816692', 'nm0000190'),
       ('tt0103064', 'nm0000216'),
       ('tt0120586', 'nm0001570'),
       ('tt0482571', 'nm0413168'),
       ('tt0482571', 'nm0000288'),
       ('tt0102926', 'nm0000164'),
       ('tt0102926', 'nm0000149');
