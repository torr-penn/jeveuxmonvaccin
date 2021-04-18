CREATE TABLE IF NOT EXISTS vaccine_center
(
    id              BIGSERIAL PRIMARY KEY,
    center_id       int,
    vaccine_id      int,
    primary_link    character varying(200),
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
    params          character varying(500)
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

alter table vaccine_center
    add constraint uniquecenter_vaccine_center UNIQUE (center_id, vaccine_id);

drop table vaccine_phone;
drop table vaccine_center;

-- various maintenance operations below


COPY vaccine_center (center_id, provider, vaccine_id, primary_link)
    FROM '~/covid/center/load_center_sql.csv'
    WITH DELIMITER ';'
;


^
torr-penn::DATABASE=>
insert into vaccine_center (id, center_id)
values (0, 0);
INSERT
    0 1
    heroku psql -a torr-penn - c "\COPY  vaccine_center(center_id,provider,vaccine_id,primary_link)
FROM '~/covid/center/load_center_sql.csv'
WITH DELIMITER ';'
;"

\COPY  vaccine_center(center_id,provider,vaccine_id,result_topology,params,primary_link)
FROM '/tmp/load_center_sql.csv'
WITH DELIMITER ';';

update vaccine_center
set result_topology=2,
    status=1,
    params='2678861:449685:175124'
where center_id = 1009;
update vaccine_center
set result_topology=2,
    status=1,
    vaccine_id=2,
    params='2575758:429756-417446-416771-433205-436456-436455:167193'
where center_id = 1277;

\COPY  center_temp(center_id,provider,vaccine_id,result_topology,params) FROM '/tmp/extra_29.txt' WITH DELIMITER ';' ;
\COPY  center_temp(center_id,provider,vaccine_id,result_topology,params) FROM '/tmp/extra_22.txt' WITH DELIMITER ';' ;

delete
from vaccine_center
where center_id = 925
  and vaccine_id = 1;
update vaccine_center
set vaccine_id=2
where center_id = 1438
  and vaccine_id = 5;
update vaccine_center
set vaccine_id=2
where center_id = 2001
  and vaccine_id = 5;
update vaccine_center
set vaccine_id=2
where center_id = 422
  and vaccine_id = 5;
update vaccine_center
set vaccine_id=2
where center_id = 56
  and vaccine_id = 5;
update vaccine_center
set result_topology=b.result_topology,
    params=b.params,
    status=2
from center_temp b
where vaccine_center.vaccine_id = b.vaccine_id
  and vaccine_center.center_id = b.center_id;
