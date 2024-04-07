/*practiceproject MEMBER 테이블 생성*/

CREATE TABLE "P_MEMBER"(

	"MEMBER_NO" NUMBER CONSTRAINT "MEMBER_PK" PRIMARY KEY,

	"MEMBER_EMAIL" NVARCHAR2(50) NOT NULL,

	"MEMBER_PW" NVARCHAR2(100),

	"MEMBER_NICKNAME" NVARCHAR2(10) NOT NULL,

	"MEMBER_TEL"	CHAR(11) NOT NULL,

	"MEMBER_ADDRESS" NVARCHAR2(150),

	"PROFILE_IMG" VARCHAR2(300),

	"ENROLL_DATE" DATE DEFAULT SYSDATE NOT NULL


);

-- 회원 번호 시퀀스 만들기
CREATE SEQUENCE SEQ_MEMBER_NO NOCACHE;


--샘플 회원 데이터 삽입
INSERT INTO "P_MEMBER"
VALUES(SEQ_MEMBER_NO.NEXTVAL, 
           'member01@kh.or.kr', 
           '$2a$10$Wr8/PFmrQw8B.6C0pucA7eU1FTGHKPtVXFV5TNLgQ5JWuwNG3vKmi',
           '샘플1',
           '01012341234',
           NULL,
           NULL,
           DEFAULT

       
 );
 
COMMIT;

SELECT * FROM "P_MEMBER"; 

DELETE  FROM "P_MEMBER";