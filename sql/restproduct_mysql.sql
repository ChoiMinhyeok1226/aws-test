-- CONNECTION: name=sample-db.cyvkocyw3bgy.eu-west-3.rds.amazonaw




--
CREATE DATABASE scott;
-- --
-- --
CREATE USER scott@'%' IDENTIFIED BY 'tiger'; -- 외부에서 접속가능하다
-- 
-- 
GRANT ALL PRIVILEGES ON *.* TO 'scott'@'%';

use scott;
-- drop table if exists member ;
-- drop table if exists board ;
-- drop table if exists company;
-- drop table if exists product;
-- drop table if exists attachment ;

drop table if exists  category , company, attachment,review, product, board, MEMBER ;
CREATE TABLE member (
	no BIGINT AUTO_INCREMENT PRIMARY KEY,
	id VARCHAR(255) UNIQUE, -- 일반 회원 아이디 (OAuth2 회원은 NULL 가능)
	pw VARCHAR(255), -- 일반 회원 비밀번호 (OAuth2 회원은 NULL 가능)
	name VARCHAR(20) NOT NULL, -- 회원 이름
	tel VARCHAR(11), -- 회원 전화번호
	member_role VARCHAR(100) DEFAULT 'ROLE_USER' NOT NULL, -- 회원 역할
	member_email VARCHAR(100) UNIQUE NOT NULL, -- 회원 이메일 (일반, OAuth2 모두 필요)
	created_date DATETIME DEFAULT CURRENT_TIMESTAMP, -- 생성일
	created_person VARCHAR(255), -- 생성자
	modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP, -- 수정일
	modified_person VARCHAR(255), -- 수정자
	oauth_provider VARCHAR(50), -- OAuth2 제공자 (google, facebook 등)
	oauth_provider_id VARCHAR(255) -- OAuth2 제공자에서 제공하는 사용자 ID
);

-- CATEGORY 테이블 생성
CREATE TABLE category(
  c_code int primary key,
  c_name varchar(30) check(c_name in('공통', '운동', '등산', '게임', '낚시', '요리', '기타'))
);

-- BOARD 테이블 생성
CREATE TABLE board (
  no bigint primary key auto_increment,
  category_code int,
  title varchar(100),
  content text not null,
  writer varchar(255) not null,
  count int default 0 not null,
  created_date datetime default current_timestamp,
  created_person varchar(255),
  modified_date datetime default current_timestamp on update current_timestamp,
  modified_person varchar(255),
  status char(1) default 'y',
  foreign key (category_code) references category(c_code),
  foreign key (writer) references member(id)
);

-- COMPANY 테이블 생성
CREATE TABLE company (
	id varchar(200) primary key,
	name varchar(200),
	addr varchar(200),
	tel int,
	created_date datetime default current_timestamp,
	created_person varchar(255),
	modified_date datetime default current_timestamp on update current_timestamp,
	modified_person varchar(255)
);

-- PRODUCT 테이블 생성
CREATE TABLE product (
    id bigint primary key auto_increment,
    name varchar(100),
    content varchar(500),
    price int,
    company_id varchar(200) not null,
    product_image_url varchar(255),
    created_date datetime default current_timestamp,
    created_person varchar(255),
    modified_date datetime default current_timestamp on update current_timestamp,
    modified_person varchar(255),
    status char(1) default 'y',
    foreign key (company_id) references company(id)
);

-- ATTACHMENT 테이블 생성
CREATE TABLE attachment (
  attachment_no bigint primary key auto_increment,
  ref_product_no bigint not null,
  original_name varchar(255) not null,
  saved_name varchar(255) not null,
  save_path varchar(1000) not null,
  thumbnail_path varchar(255),
  status varchar(1) default 'y',
  created_date datetime default current_timestamp,
  created_person varchar(255),
  modified_date datetime default current_timestamp on update current_timestamp,
  modified_person varchar(255),
  foreign key (ref_product_no) references product(id)
);

-- REVIEW 테이블 생성
CREATE TABLE review (
    review_code BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_code BIGINT NOT NULL,
    member_code varchar(255) ,
    review_title VARCHAR(100) NOT NULL,
    review_content VARCHAR(1000) NOT NULL,
    created_date DATETIME DEFAULT CURRENT_TIMESTAMP,
    created_person VARCHAR(255),
    modified_date DATETIME DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    modified_person VARCHAR(255),
    FOREIGN KEY (product_code) REFERENCES product(id),
    FOREIGN KEY (member_code) REFERENCES member(id)
);

DROP TABLE if exists refresh_token;
CREATE TABLE refresh_token (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id VARCHAR(255),
    refresh_token VARCHAR(500) NOT NULL UNIQUE,
    issued_at DATETIME DEFAULT CURRENT_TIMESTAMP,
    expired_at DATETIME
);
select * from attachment ;
SELECT * FROM product ;
SELECT * FROM company ;
select * from board;
select * from member;
select * from category;
select * from review;
select * from refresh_token;

