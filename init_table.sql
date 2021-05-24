CREATE TABLE IF NOT EXISTS vaccine_center
(
    id              BIGSERIAL PRIMARY KEY,
    center_id       int,
    vaccine_id      int,
    primary_link    character varying(250),
    result_topology int,
    provider        character varying(20),
    lastok          timestamp without time zone default now(),
    lastcheck       timestamp without time zone default now(),
    lastphonecheck  timestamp without time zone default now(),
    date_in         date not null               default CURRENT_DATE,
    status          int                         default 3,
    token           int                         default 0,
    init_token      int                         default 10,
    msinterval      int                         default 120000,
    nbsuccess       int                         default 0,
    total           int                         default 0,
    params          character varying(1000),
    nextrdv         timestamp without time zone default NULL,

    lastok18          timestamp without time zone default now(),
    lastcheck18       timestamp without time zone default now(),
    nbsuccess18       int                         default 0,
    total18           int                         default 0,
    params18          character varying(1000),
    nextrdv18         timestamp without time zone default NULL

);


CREATE TABLE IF NOT EXISTS vaccine_center_infos
(
    id          BIGSERIAL PRIMARY KEY,
    name        character varying(200),
    address     character varying(400),
    city        character varying(200),
    zipcode     int,
    open_date   date not null default CURRENT_DATE,
    close_date  date,
    center_id   int,
    vaccine_id  int,
    platform_id int
);


CREATE TABLE IF NOT EXISTS vaccine_phone
(
    id         BIGSERIAL PRIMARY KEY,
    phonesalt  character varying(50) UNIQUE,
    centerid   int references vaccine_center (id) on delete cascade,
    lastok     timestamp without time zone,
    lastcheck  timestamp without time zone,
    date_in    date not null default CURRENT_DATE,
    msinterval int           default 120000,
    total      int           default 0,
    nbsuccess  int           default 0,
    CONSTRAINT salt_unique UNIQUE (phonesalt)
);

alter table vaccine_center add column lastok18      timestamp without time zone default null;
alter table vaccine_center add column lastcheck18   timestamp without time zone default null;
alter table vaccine_center add column nbsuccess18   int                         default 0;
alter table vaccine_center add column total18       int                         default 0;
alter table vaccine_center add column nextrdv18     timestamp without time zone default NULL;
alter table vaccine_center add column status18      int                         default 3;
alter table vaccine_center add column params18      character varying(1000);



alter table vaccine_center     add constraint uniquecenter_vaccine_center UNIQUE (center_id, vaccine_id);

alter table vaccine_center     alter column params type character varying(1000);

alter table vaccine_center     add column nextRdv timestamp without time zone default NULL;

alter table vaccine_center     alter column primary_link type character varying(250);
-- alter table TABLE_NAME alter column COLUMN_NAME type character varying(120);

drop table vaccine_phone;
drop table vaccine_center;

-- various maintenance operations below





select a.name, a.zipcode, a.center_id from vaccine_center_info a order by zipcode, center_id;
select substr(a.name, 0, 15), a.center_id, b.status, b.params from vaccine_center_infos a,     vaccine_center b
where a.center_id = b.center_id   and a.vaccine_id = b.vaccine_id order by zipcode, center_id;

select substr(a.name, 0, 15), a.center_id, b.status, b.params from vaccine_center_infos a,      vaccine_center b
where a.center_id = b.center_id   and a.vaccine_id = b.vaccine_id   and zipcode > 21000   and zipcode < 23000 order by zipcode, center_id;

select substr(a.name, 0, 15), a.center_id, b.status, b.params from vaccine_center_infos a,   vaccine_center b
where a.center_id = b.center_id   and a.vaccine_id = b.vaccine_id   and zipcode > 34000   and zipcode < 36000
order by zipcode, center_id;

select substr(a.name, 0, 15), a.center_id, b.status, b.params
from vaccine_center_infos a,
     vaccine_center b
where a.center_id = b.center_id
  and a.vaccine_id = b.vaccine_id
  and primary_link like '%keldoc%'
order by zipcode, center_id;

heroku psql -a torr-penn - c "\COPY  vaccine_center(center_id,provider,vaccine_id,primary_link) FROM '~/covid/center/load_center_sql.csv' WITH DELIMITER ';' ;"

\COPY  vaccine_center(center_id,provider,vaccine_id,result_topology,params,primary_link) FROM '/tmp/load_center_sql.csv' WITH DELIMITER ';';
\COPY  vaccine_center_infos (name,address,city,zipcode,open_date,close_date,center_id,vaccine_id,platform_id) FROM '/tmp/bretagne.csv' WITH (DELIMITER ';', NULL '');



\COPY  center_temp(center_id,provider,vaccine_id,result_topology,params) FROM '/tmp/extra_29.txt' WITH DELIMITER ';' ;
\COPY  center_temp(center_id,provider,vaccine_id,result_topology,params) FROM '/tmp/extra_22.txt' WITH DELIMITER ';' ;


\COPY  center_temp(center_id,provider,vaccine_id,result_topology,params) FROM '/tmp/extra_22.txt' WITH DELIMITER ';' ;



update vaccine_center set result_topology=b.result_topology,     params=b.params,     status=2 from center_temp b
where vaccine_center.vaccine_id = b.vaccine_id   and vaccine_center.center_id = b.center_id;



CREATE TABLE IF NOT EXISTS vaccine_center_histo
(
    id              BIGSERIAL PRIMARY KEY,
    center_id       int,
    vaccine_id      int,
    date            date,
    nbsuccess       int,
    total           int,
    nbsuccess18     int,
    total18         int
);

alter table vaccine_center_histo     add constraint unique_vaccine_center_histo UNIQUE (center_id, vaccine_id,date);



CREATE TABLE IF NOT EXISTS vaccine_histo_status
(
    id              BIGSERIAL PRIMARY KEY,
    date            date,
    lastok          timestamp
);
insert into vaccine_histo_status (date, lastok) values ((current_date-2), (now()-INTERVAL '1 day')::Timestamp);

CREATE TABLE IF NOT EXISTS vaccine_center_ref
(
    id              BIGSERIAL PRIMARY KEY,
    center_id       int,
    vaccine_id      int,
    nbsuccess       int,
    total           int,
    nbsuccess18     int,
    total18         int
);

alter table vaccine_center_ref     add constraint unique_vaccine_center_ref UNIQUE (center_id, vaccine_id);


