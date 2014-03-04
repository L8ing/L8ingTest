create table t_actress(name varchar(32) not null, rank char(1),alias1 varchar(32),alias2 varchar(32),
alias3 varchar(32),alias4 varchar(32),alias5 varchar(32),alias6 varchar(32));

alter table t_actress add primary key(name);

create table t_video(designation varchar(60) not null, video_name varchar(250), uncensored int,
	actress varchar(32),theme varchar(32),size double(5,2),format varchar(10), comment varchar(250));

alter table t_video add primary key(designation);

create index t_video_idx on t_video(actress);

insert into t_video(designation,
video_name,
uncensored,
actress,
size,
format
)values('n0738','超美形小恶魔本最强治疗效果 本多成实之初裏绝顶中出',true,'本多成实',1.03,'MP4');
