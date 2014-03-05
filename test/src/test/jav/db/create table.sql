create table t_actress(name varchar(32) not null, rank char(1),uncensored int,alias1 varchar(32),
alias2 varchar(32),alias3 varchar(32),alias4 varchar(32),alias5 varchar(32),alias6 varchar(32));

alter table t_actress add primary key(name);

create table t_video(designation varchar(60) not null, video_name varchar(250), uncensored int,
	actress varchar(32),theme varchar(32),size double(5,2),format varchar(10), comment varchar(250));

alter table t_video add primary key(designation);

create index t_video_idx on t_video(actress);
