create TABLE blog(
	id SERIAL,
	blogname varchar(10) not null,
	blogtype int8 not null,
CONSTRAINT pk_blog_id PRIMARY KEY (id)
)
create TABLE bloghist(
	id SERIAL,
	bid int8 not null,
CONSTRAINT pk_bloghist_id PRIMARY KEY (id)
)