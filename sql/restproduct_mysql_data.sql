-- CONNECTION: name=localhost 6

use scott;
-- drop table if exists member ;
-- drop table if exists board cascade;
-- drop table if exists company;
-- drop table if exists product;
-- drop table if exists attachment ;

-- MEMBER 데이터 삽입
-- DELETE FROM member ;
INSERT INTO member (id, pw, name, tel, member_role, member_email, created_date, created_person, modified_date, modified_person)
VALUES 
('m01', 'm01', '리사', '010', 'ROLE_USER', 'm01@example.com', NOW(), 'm01', NOW(), 'm01'),
('m02', 'm02', '주혁', '010', 'ROLE_USER', 'm02@example.com', NOW(), 'm02', NOW(), 'm02'),
('m03', 'm03', '오둥', '010', 'ROLE_USER', 'm03@example.com', NOW(), 'm03', NOW(), 'm03'),
('m04', 'm04', '제니', '010', 'ROLE_USER', 'm04@example.com', NOW(), 'm04', NOW(), 'm04'),
('m05', 'm05', '정국', '010', 'ROLE_USER', 'm05@example.com', NOW(), 'm05', NOW(), 'm05');

-- CATEGORY 데이터 삽입
INSERT INTO category (c_code, c_name) 
VALUES 
(10, '공통'),
(20, '운동'),
(30, '요리'),
(70, '기타');

-- BOARD 데이터 삽입
INSERT INTO board (category_code, title, content, writer) 
VALUES 
(10, '게시글 1', '게시글1 content 입니다', 'm01'),
(10, '게시글 2', '게시글2 content 입니다', 'm01'),
(10, '게시글 3', '게시글3 content 입니다', 'm02'),
(10, '게시글 4', '게시글4 content 입니다', 'm02'),
(10, '게시글 5', '게시글5 content 입니다', 'm03');

-- COMPANY 데이터 삽입
INSERT INTO company (id, name, addr, tel, created_date, created_person, modified_date, modified_person) 
VALUES 
('c100', 'good', 'seoul', 11, NOW(), 'admin', NOW(), 'admin'),
('c200', 'joa', 'busan', 12, NOW(), 'admin', NOW(), 'admin'),
('c300', 'maria', 'ulsan', 13, NOW(), 'admin', NOW(), 'admin'),
('c400', 'my', 'kwangju', 14, NOW(), 'admin', NOW(), 'admin');



-- PRODUCT 데이터 삽입
INSERT INTO product (name, content, price, company_id, product_image_url, created_date, created_person, modified_date, modified_person)
VALUES 
('Product 1', 'Product 1 description', 1000, 'c100', 'product1.png', NOW(), 'admin', NOW(), 'admin'),
('Product 2', 'Product 2 description', 2000, 'c200', 'product2.png', NOW(), 'admin', NOW(), 'admin'),
('Product 3', 'Product 3 description', 3000, 'c300', 'product3.png', NOW(), 'admin', NOW(), 'admin'),
('Product 4', 'Product 4 description', 4000, 'c400', 'product4.png', NOW(), 'admin', NOW(), 'admin'),
('Product 5', 'Product 5 description', 5000, 'c100', 'product5.png', NOW(), 'admin', NOW(), 'admin'),
('Product 6', 'Product 6 description', 6000, 'c200', 'product6.png', NOW(), 'admin', NOW(), 'admin'),
('Product 7', 'Product 7 description', 7000, 'c300', 'product7.png', NOW(), 'admin', NOW(), 'admin'),
('Product 8', 'Product 8 description', 8000, 'c400', 'product8.png', NOW(), 'admin', NOW(), 'admin'),
('Product 9', 'Product 9 description', 9000, 'c100', 'product9.png', NOW(), 'admin', NOW(), 'admin'),
('Product 10', 'Product 10 description', 10000, 'c200', 'product10.png', NOW(), 'admin', NOW(), 'admin'),
('Product 11', 'Product 11 description', 11000, 'c300', 'product11.png', NOW(), 'admin', NOW(), 'admin'),
('Product 12', 'Product 12 description', 12000, 'c400', 'product12.png', NOW(), 'admin', NOW(), 'admin'),
('Product 13', 'Product 13 description', 13000, 'c100', 'product13.png', NOW(), 'admin', NOW(), 'admin'),
('Product 14', 'Product 14 description', 14000, 'c200', 'product14.png', NOW(), 'admin', NOW(), 'admin'),
('Product 15', 'Product 15 description', 15000, 'c300', 'product15.png', NOW(), 'admin', NOW(), 'admin'),
('Product 16', 'Product 16 description', 16000, 'c400', 'product16.png', NOW(), 'admin', NOW(), 'admin'),
('Product 17', 'Product 17 description', 17000, 'c100', 'product17.png', NOW(), 'admin', NOW(), 'admin'),
('Product 18', 'Product 18 description', 18000, 'c200', 'product18.png', NOW(), 'admin', NOW(), 'admin'),
('Product 19', 'Product 19 description', 19000, 'c300', 'product19.png', NOW(), 'admin', NOW(), 'admin'),
('Product 20', 'Product 20 description', 20000, 'c400', 'product20.png', NOW(), 'admin', NOW(), 'admin');
-- ATTACHMENT 데이터 삽입
INSERT INTO attachment (ref_product_no, original_name, saved_name, save_path, thumbnail_path, created_date, created_person, modified_date, modified_person)
VALUES 
(1, 'original1.jpg', 'saved1.jpg', 'saved1', 'thumbnail1', NOW(), 'admin', NOW(), 'admin'),
(2, 'original2.jpg', 'saved2.jpg', 'saved2', 'thumbnail2', NOW(), 'admin', NOW(), 'admin'),
(3, 'original3.jpg', 'saved3.jpg', 'saved3', 'thumbnail3', NOW(), 'admin', NOW(), 'admin');

-- REVIEW 데이터 삽입
INSERT INTO review (product_code, member_code, review_title, review_content, created_date, created_person, modified_date, modified_person)
VALUES 
(1, 'm01', '리뷰 1', '리뷰 내용 1', NOW(), 'm01', NOW(), 'm01'),
(1, 'm02', '리뷰 2', '리뷰 내용 2', NOW(), 'm02', NOW(), 'm02'),
(1, 'm03', '리뷰 3', '리뷰 내용 3', NOW(), 'm03', NOW(), 'm03'),
(1, 'm04', '리뷰 4', '리뷰 내용 4', NOW(), 'm04', NOW(), 'm04'),
(1, 'm05', '리뷰 5', '리뷰 내용 5', NOW(), 'm05', NOW(), 'm05'),
(6, 'm01', '리뷰 6', '리뷰 내용 6', NOW(), 'm01', NOW(), 'm01'),
(7, 'm02', '리뷰 7', '리뷰 내용 7', NOW(), 'm02', NOW(), 'm02'),
(1, 'm03', '리뷰 8', '리뷰 내용 8', NOW(), 'm03', NOW(), 'm03'),
(1, 'm04', '리뷰 9', '리뷰 내용 9', NOW(), 'm04', NOW(), 'm04'),
(1, 'm05', '리뷰 10', '리뷰 내용 10', NOW(), 'm05', NOW(), 'm05'),
(1, 'm01', '리뷰 11', '리뷰 내용 11', NOW(), 'm01', NOW(), 'm01'),
(1, 'm02', '리뷰 12', '리뷰 내용 12', NOW(), 'm02', NOW(), 'm02'),
(1, 'm03', '리뷰 13', '리뷰 내용 13', NOW(), 'm03', NOW(), 'm03'),
(1, 'm04', '리뷰 14', '리뷰 내용 14', NOW(), 'm04', NOW(), 'm04'),
(1, 'm05', '리뷰 15', '리뷰 내용 15', NOW(), 'm05', NOW(), 'm05'),
(16, 'm01', '리뷰 16', '리뷰 내용 16', NOW(), 'm01', NOW(), 'm01'),
(17, 'm02', '리뷰 17', '리뷰 내용 17', NOW(), 'm02', NOW(), 'm02'),
(18, 'm03', '리뷰 18', '리뷰 내용 18', NOW(), 'm03', NOW(), 'm03'),
(19, 'm04', '리뷰 19', '리뷰 내용 19', NOW(), 'm04', NOW(), 'm04'),
(1, 'm05', '리뷰 20', '리뷰 내용 20', NOW(), 'm05', NOW(), 'm05');

COMMIT;



COMMIT;
select * from attachment ;
SELECT * FROM product ;
SELECT * FROM company ;
select * from board;
select * from member;
select * from category;
select * from review;


