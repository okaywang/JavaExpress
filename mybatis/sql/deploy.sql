create TABLE blog(
	id SERIAL,
	blogname varchar(10) not null,
	blogtype int8 not null,
CONSTRAINT pk_blog_id PRIMARY KEY (id)
)
