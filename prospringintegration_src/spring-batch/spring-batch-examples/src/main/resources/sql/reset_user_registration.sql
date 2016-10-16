drop table  USER_REGISTRATION;

create table USER_REGISTRATION
(
  ID bigserial  not null,
  FIRST_NAME character varying(255) not null,
  LAST_NAME character varying(255) not null,
  COMPANY character varying(255) not null,
  ADDRESS character varying(255) not null,
  CITY character varying(255) not null,
  STATE character varying(255) not null,
  ZIP character varying(255) not null,
  COUNTY character varying(255) not null,
  URL character varying(255) not null,
  PHONE_NUMBER character varying(255) not null,
  FAX character varying(255) not null,
  constraint USER_REGISTRATION_PKEY primary key (id)
);
