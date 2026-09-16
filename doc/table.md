# 📋 교육센터 평가 시스템 (Eduvantage) - 상세 DB 테이블 명세서

프로젝트에서 사용된 전체 데이터베이스 테이블 명세서입니다.

---

## 1. 강의 평가 (COURSE_EVALUATION)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 고유 번호 | NUMBER | - | NN | - | - | O |
| MEMBER_ID | 작성자 고유번호 | NUMBER | - | NN | - | MEMBER | - |
| COURSE_NO | 강의 고유번호 | NUMBER | - | NN | - | COURSE | - |
| RATING | 별점 | NUMBER | - | CK(1,2,3,4,5) | - | - | - |
| REVIEW | 강의평 | CLOB | - | NN | - | - | - |
| LIKE_COUNT | 좋아요 | NUMBER | - | NN | 0 | - | - |
| REGDATE | 작성일 | DATE | - | NN | SYSDATE | - | - |

---

## 2. 강의 장바구니 (COURSE_CART)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 장바구니 번호 | NUMBER | - | NN | - | - | O |
| MEMBER_ID | 회원 고유번호 | NUMBER | - | NN | - | MEMBER | - |
| COURSE_NO | 강의 고유번호 | NUMBER | - | NN | - | COURSE | - |
| REGDATE | 장바구니 등록일 | DATE | - | NN | SYSDATE | - | - |

---

## 3. 강의 결제목록 (COURSE_PAYMENT)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 구매 번호 | NUMBER | - | NN | - | - | O |
| MEMBER_ID | 회원 고유번호 | NUMBER | - | NN | - | MEMBER | - |
| COURSE_NO | 강의 고유번호 | NUMBER | - | NN | - | COURSE | - |
| PRICE | 가격 | NUMBER | - | NN | - | - | - |
| REGDATE | 결제일 | DATE | - | NN | SYSDATE | - | - |

---

## 4. 강의별 QNA (COURSE_QNA)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | QNA 번호 | NUMBER | - | NN | 자동증가O | - | O |
| COURSE_NO | 강좌 번호 | NUMBER | - | NN | - | COURSE | - |
| MEMBER_ID | 작성자 고유번호 | NUMBER | - | NN | - | MEMBER | - |
| SUBJECT | 제목 | VARCHAR2 | - | NN | - | - | - |
| CONTENT | 내용 | CLOB | - | NN | - | - | - |
| STATUS | 답변 여부 | CHAR | - | CK(Y,N) | - | - | - |
| HIT | 조회수 | NUMBER | - | NN | 0 | - | - |
| REGDATE | 작성일 | DATE | - | NN | SYSDATE | - | - |

---

## 5. 강의별 QNA 답변 (COURSE_QNA_ANSWER)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 답변 번호 | NUMBER | - | NN | 자동증가O | - | O |
| COURSE_QNA_NO | QNA 번호 | NUMBER | - | NN | - | COURSE_QNA | - |
| MEMBER_ID | 답변한 강사 고유번호 | NUMBER | - | NN | - | MEMBER | - |
| ANSWER | 답변 | CLOB | - | NN | - | - | - |
| REGDATE | 답변 작성일 | DATE | - | NN | SYSDATE | - | - |

---

## 6. 시험문제 (EXAM_QUESTION)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 문제번호 | NUMBER | - | NN | 자동생성-O | - | O |
| TITLE | 문제 | VARCHAR2 | 4000 | NN | - | - | - |
| IMAGE | 문제이미지 | VARCHAR2 | 2000 | N | - | - | - |
| ANSWER | 정답 | VARCHAR2 | 2000 | NN | - | - | - |
| SCORE | 점수 | NUMBER | - | NN | - | - | - |
| DESCRIPTION | 해설 | CLOB | - | N | - | - | - |
| TYPE | 시험종류 | NUMBER | - | N | - | - | - |

---

## 7. 시험문제 보기 (EXAM_OPTION)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 고유 번호 | NUMBER | - | NN | 자동생성-O | - | O |
| QUESTION_NO | 문제 번호 | NUMBER | - | NN | - | EXAM_QUESTION | - |
| OPTION1 | 선택지1 | VARCHAR2 | 2000 | N | - | - | - |
| OPTION2 | 선택지2 | VARCHAR2 | 2000 | N | - | - | - |
| OPTION3 | 선택지3 | VARCHAR2 | 2000 | N | - | - | - |
| OPTION4 | 선택지4 | VARCHAR2 | 2000 | N | - | - | - |

---

## 8. 정기 시험 (SCHEDULED_EXAM)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 시험번호 | NUMBER | - | NN | 자동생성-O | - | O |
| TITLE | 시험이름 | VARCHAR2 | 1000 | NN | - | - | - |
| OPEN_DATE | 시작일자 | TIMESTAMP | - | NN | - | - | - |
| CLOSE_DATE | 종료일자 | TIMESTAMP | - | NN | - | - | - |
| INSTRUCTOR_ID | 생성 강사 | NUMBER | - | N | - | - | - |
| TIME_LIMIT | 시험시간 | NUMBER | - | NN | - | - | - |

---

## 9. 정기 시험 매핑 (SCHEDULED_EXAM_MAP)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 시험번호 | NUMBER | - | NN | 자동생성-O | - | O |
| EXAM_NO | 정기시험번호 | NUMBER | - | NN | - | 정기시험 | - |
| QUESTION_NO | 문제번호 | NUMBER | - | NN | - | 시험문제 | - |

---

## 10. 시험응시 (EXAM_ENROLLMENT)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 시험번호 | NUMBER | - | NN | 자동생성-O | - | O |
| MEMBER_ID | 회원번호 | NUMBER | - | NN | - | 회원 | - |
| EXAM_NO | 정기시험번호 | NUMBER | - | N | - | 정기시험 | - |
| REGDATE | 시험일자 | DATE | - | N | SYSDATE | - | - |
| STARTTIME | 시작시간 | TIMESTAMP | - | N | SYSTIMESTAMP | - | - |
| ENDTIME | 종료시간 | TIMESTAMP | - | N | - | - | - |
| TOTALSCORE | 총점 | NUMBER | 3 | N | - | - | - |
| STATUS | 상태(채점) | VARCHAR2 | 20 | N | - | - | - |

---

## 11. 시험-유저 (EXAM_USER_ANSWER)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 답번호 | NUMBER | - | NN | 자동생성-O | - | O |
| ENROLLMENT_NO | 시험번호 | NUMBER | - | NN | - | 시험 | - |
| QUESTION_NO | 문제번호 | NUMBER | - | NN | - | 문제 | - |
| USER_ANSWER | 제출한 답 | VARCHAR2 | 2000 | N | - | - | - |
| IS_CORRECT | 정답여부 | CHAR | 1 | N | ‘N’ | - | - |
| SCORE | 점수 | NUMBER | - | NN | - | - | - |
| GRADER_ID | 채점자 | NUMBER | - | N | - | 회원 | - |
| GRADED_AT | 채점일 | DATE | - | N | - | - | - |

---

## 12. 강의 (COURSE)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 강좌번호 | NUMBER | - | NN | 자동생성-O | - | O |
| TITLE | 강좌제목 | VARCHAR2 | 1000 | NN | - | - | - |
| INSTRUCTOR_NO | 강사 | NUMBER | - | NN | - | 회원 | - |
| STAR | 별점 | NUMBER | 3,1 | N | - | - | - |
| STUDENT_COUNT | 학생수 | NUMBER | - | N | - | - | - |
| PAY_PRICE | 현재가격 | NUMBER | - | N | - | - | - |
| REGULAR_PRICE | 원래가격 | NUMBER | - | N | - | - | - |
| CONTENT | 상세설명 | CLOB | - | N | - | - | - |
| IMAGES | 상세이미지 | CLOB | - | N | - | - | - |
| THUMBNAIL | 썸네일 | VARCHAR2 | 1000 | N | - | - | - |

---

## 13. 강의 수강 (COURSE_ENROLLMENT)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 수강번호 | NUMBER | - | NN | 자동생성 | - | O |
| MEMBER_ID | 유저번호 | NUMBER | - | NN | - | 회원 | - |
| COURSE_NO | 강좌번호 | NUMBER | - | NN | - | 강의 | - |
| IS_COMPLETED | 수료여부 | CHAR | 1 | N | ‘N’ | - | - |
| PROGRESS | 진행율 | NUMBER | 3 | N | 0 | - | - |
| REGDATE | 등록일 | DATE | - | N | SYSDATE | - | - |

---

## 14. 강의 테크스택 (TECH_STACK)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 테크 번호 | NUMBER | - | NN | 자동생성-O | - | O |
| TECH | 테크 스택 | VARCHAR2 | 50 | NN | - | - | - |
| CATEGORY | 카테고리 | VARCHAR2 | 50 | NN | - | - | - |

---

## 15. 강의-테크스택 매핑 (COURSE_TECH_MAPPING)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| COURSE_NO | 강의 번호 | NUMBER | - | NN | - | 강의 | O (복합키) |
| TECH_NO | 테크 번호 | NUMBER | - | NN | - | 테크스택 | O (복합키) |

---

## 16. 강의 영상 (COURSE_VIDEO)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 영상 번호 | NUMBER | - | NN | - | - | O |
| COURSE_NO | 강의 번호 | NUMBER | - | NN | - | 강의 | - |
| VID | 비디오 ID | VARCHAR2 | 200 | NN | - | - | - |
| TITLE | 제목 | VARCHAR2 | 500 | NN | - | - | - |
| THUMBNAIL | 썸네일 | VARCHAR2 | 1000 | N | - | - | - |
| V_ORDER | 영상순서 | NUMBER | - | NN | - | - | - |

---

## 17. 강의 영상 진도 (COURSE_VIDEO_PROGRESS)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 고유 번호 | NUMBER | - | NN | - | - | O |
| ENROLLMENT_NO | 수강 번호 | NUMBER | - | NN | - | - | - |
| VIDEO_NO | 영상 번호 | NUMBER | - | NN | - | 영상 | - |
| CURRENT_TIME | 현재 시점 | NUMBER | - | NN | - | - | - |
| DURATION | 길이 | NUMBER | - | NN | - | - | - |
| PROGRESS | 진도 | NUMBER | - | NN | - | - | - |
| COMPLETED | 수강여부 | CHAR | 1 | NN | - | - | - |
| UPDATED_AT | 갱신일 | DATE | - | NN | - | - | - |

---

## 18. QnA게시판 (QNA_BOARD)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 게시물 번호 | NUMBER | - | NN | - | - | O |
| MEMBER_ID | 회원 번호 | NUMBER | - | NN | - | 회원 | - |
| SUBJECT | 제목 | VARCHAR2 | 2000 | NN | - | - | - |
| CONTENT | 내용 | CLOB | - | NN | - | - | - |
| REGDATE | 등록일 | DATE | - | N | SYSDATE | - | - |
| STATUS | 답변여부 | CHAR | 1 | NN | ‘N’ | - | - |
| CATEGORY_NO | 카테고리번호 | NUMBER | - | NN | - | 카테고리 | - |

---

## 19. QnA 카테고리 (QNA_CATEGORY)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 번호 | NUMBER | - | NN | - | - | O |
| CATEGORY | 카테고리 | VARCHAR2 | 50 | NN | - | - | - |

---

## 20. QnA 답변 (QNA_REPLY)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 게시물 번호 | NUMBER | - | NN | - | - | O |
| MEMBER_ID | 회원 번호 | NUMBER | - | NN | - | 회원 | - |
| QNA_NO | QnA번호 | NUMBER | - | NN | - | QnA | - |
| CONTENT | 내용 | CLOB | - | NN | - | - | - |
| REGDATE | 등록일 | DATE | - | N | SYSDATE | - | - |

---

## 21. 자유게시판 (FREE_BOARD)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 게시물 번호 | NUMBER | - | NN | - | - | O |
| MEMBER_ID | 회원 번호 | NUMBER | - | N | - | 회원 | - |
| NAME | 이름 | VARCHAR2 | 51 | N | - | - | - |
| SUBJECT | 제목 | VARCHAR2 | 2000 | NN | - | - | - |
| CONTENT | 내용 | CLOB | - | NN | - | - | - |
| HIT | 조회수 | NUMBER | - | N | 0 | - | - |
| PWD | 비밀번호 | VARCHAR2 | 10 | N | - | - | - |
| REGDATE | 등록일 | DATE | - | N | SYSDATE | - | - |

---

## 22. 자유게시판 댓글 (FREE_COMMENT)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 댓글 번호 | NUMBER | - | NN | - | - | O |
| BOARD_NO | 게시물 번호 | NUMBER | - | N | - | 자유게시판 | - |
| MEMBER_ID | 회원 번호 | NUMBER | - | N | - | 회원 | - |
| NAME | 이름 | VARCHAR2 | 100 | N | - | - | - |
| MSG | 내용 | CLOB | - | NN | - | - | - |
| REGDATE | 등록일 | DATE | - | N | SYSDATE | - | - |
| PWD | 비밀번호 | VARCHAR2 | 100 | N | - | - | - |
| PARENT_NO | 부모번호 (대댓글) | NUMBER | - | N | - | 셀프참조 | - |

---

## 23. 공지게시판 (NOTICE_BOARD)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 게시물 번호 | NUMBER | - | NN | - | - | O |
| MEMBER_ID | 회원 번호 | NUMBER | - | NN | - | 회원 | - |
| CATEGORY_NO | 카테고리번호 | NUMBER | - | NN | - | 카테고리 | - |
| SUBJECT | 제목 | VARCHAR2 | 2000 | NN | - | - | - |
| CONTENT | 내용 | CLOB | - | NN | - | - | - |
| HIT | 조회수 | NUMBER | - | N | 0 | - | - |
| REGDATE | 등록일 | DATE | - | N | SYSDATE | - | - |

---

## 24. 공지 카테고리 (NOTICE_CATEGORY)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 번호 | NUMBER | - | NN | - | - | O |
| CATEGORY | 카테고리 | VARCHAR2 | 50 | NN | - | - | - |

---

## 25. 강의별 공지사항 (COURSE_NOTICE)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 게시물 번호 | NUMBER | - | NN | - | - | O |
| COURSE_NO | 강의 번호 | NUMBER | - | NN | - | 강의 | - |
| SUBJECT | 제목 | VARCHAR2 | 2000 | NN | - | - | - |
| CONTENT | 내용 | CLOB | - | NN | - | - | - |
| REGDATE | 작성일 | DATE | - | NN | SYSDATE | - | - |
| HIT | 조회수 | NUMBER | - | NN | 0 | - | - |
| FILENAME | 첨부파일 이름 | VARCHAR2 | 1000 | - | - | - | - |
| FILESIZE | 첨부파일 사이즈 | NUMBER | - | - | 0 | - | - |

---

## 26. 회원 (MEMBER)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| MEMBER_ID | 고유번호 | NUMBER | - | NN (시퀀스) | - | - | O |
| USERNAME | 로그인아이디 | VARCHAR2 | 15 | NN | - | - | - |
| PASSWORD | 비밀번호 | VARCHAR2 | 100 | NN | - | - | - |
| NAME | 이름 | VARCHAR2 | 51 | NN | - | - | - |
| SEX | 성별 | CHAR | 1 | NN | 'M', 'F' | - | - |
| REGDATE | 가입일 | DATE | - | N | SYSDATE | - | - |
| BIRTHDATE | 생년월일 | DATE | - | NN | - | - | - |
| PHONE | 연락처 | VARCHAR2 | 15 | NN | - | - | - |
| POST | 우편번호 | VARCHAR2 | 8 | NN | - | - | - |
| ADDR1 | 기본주소 | VARCHAR2 | 500 | NN | - | - | - |
| ADDR2 | 상세주소 | VARCHAR2 | 1000 | N | - | - | - |
| EMAIL | 이메일 | VARCHAR2 | 500 | N | - | - | - |
| PROFILE_DESC | 자기소개 | CLOB | - | N | - | - | - |
| PROFILE_IMAGE | 프로필 사진 | VARCHAR2 | 500 | N | - | - | - |
| ENABLED | 휴면상태 | NUMBER | - | N | 1 (1=활성화, 0=휴면) | - | - |

---

## 27. 권한 (AUTHORITY)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 권한고유번호 | NUMBER | - | NN | - | - | O |
| MEMBER_ID | 회원고유번호 | NUMBER | - | NN | - | 회원 | - |
| AUTHORITY | 권한 | VARCHAR2 | 20 | NN | ROLE_USER | - | - |

---

## 28. 자동 로그인 (PERSISTENT_LOGINS)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| SERIES | 시리즈값(식별자) | VARCHAR2 | 64 | NN | - | - | O |
| USERNAME | 로그인아이디 | VARCHAR2 | 64 | NN | - | 회원 | - |
| TOKEN | 토큰값 | VARCHAR2 | 64 | NN | - | - | - |
| LAST_USED | 마지막사용일시 | TIMESTAMP | - | NN | - | - | - |

---

## 29. 도서 (BOOK)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 고유번호 | NUMBER | - | NN (시퀀스) | - | - | O |
| TITLE | 제목 | VARCHAR2 | 500 | NN | - | - | - |
| POSTER | 대표이미지 | VARCHAR2 | 500 | N | - | - | - |
| AUTHOR | 작가 | VARCHAR2 | 250 | N | - | - | - |
| PUBDATE | 출간일 | DATE | - | N | - | - | - |
| PAGES | 페이지수 | VARCHAR2 | 10 | N | - | - | - |
| ISBN | ISBN | VARCHAR2 | 15 | N | - | - | - |
| PRICE | 가격 | NUMBER | - | NN | - | - | - |
| BOOK_DESC | 책소개 | CLOB | - | N | - | - | - |
| DESC_IMG | 소개이미지 | VARCHAR2 | 500 | N | - | - | - |
| AUTHOR_DESC | 작가소개 | CLOB | - | N | - | - | - |
| TOC | 목차 | CLOB | - | N | - | - | - |
| CATEGORY | 카테고리 | VARCHAR2 | 51 | N | - | - | - |
| STOCK | 재고 | NUMBER | - | NN | 10 | - | - |
| HIT | 조회수 | NUMBER | - | NN | 0 | - | - |
| LIKE_COUNT | 도서 좋아요 수 | NUMBER | - | NN | 0 | - | - |
| COMMENT_COUNT | 댓글 수 | NUMBER | - | NN | 0 | - | - |

---

## 30. 도서 장바구니 (BOOK_CART)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 장바구니번호 | NUMBER | - | NN (시퀀스) | - | - | O |
| MEMBER_ID | 구매자 고유번호 | NUMBER | - | NN | - | 회원 | - |
| BOOK_NO | 도서고유번호 | NUMBER | - | NN | - | 도서 | - |
| QUANTITY | 수량 | NUMBER | - | NN | 1 | - | - |
| REGDATE | 등록일 | DATE | - | N | SYSDATE | - | - |

---

## 31. 도서 주문 (BOOK_ORDER)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 주문번호 | NUMBER | - | NN (시퀀스) | - | - | O |
| MEMBER_ID | 고유번호(회원) | NUMBER | - | NN | - | 회원 | - |
| TOTAL_PRICE | 총결제금액 | NUMBER | - | NN | 0 | - | - |
| ORDER_STATUS | 주문상태 | VARCHAR2 | 20 | NN | '결제완료' | - | - |
| REGDATE | 주문일시 | DATE | - | N | SYSDATE | - | - |

---

## 32. 도서 주문 상세 (BOOK_ORDER_DETAIL)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 주문상세번호 | NUMBER | - | NN (시퀀스) | - | - | O |
| BOOK_ORDER_NO | 주문번호 | NUMBER | - | NN | - | 주문상세 | - |
| BOOK_NO | 도서번호 | NUMBER | - | NN | - | 도서 | - |
| QUANTITY | 구매수량 | NUMBER | - | NN | 1 | - | - |
| PRICE | 구매당시가격 | NUMBER | - | NN | 0 | - | - |

---

## 33. 도서 좋아요 (BOOK_LIKE)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 좋아요고유번호 | NUMBER | - | NN (시퀀스) | - | - | O |
| BOOK_NO | 도서고유번호 | NUMBER | - | N | - | 도서 | - |
| MEMBER_ID | 회원고유번호 | NUMBER | - | N | - | 회원 | - |
| REGDATE | 좋아요생성일 | DATE | - | N | SYSDATE | - | - |

---

## 34. 도서 댓글 (BOOK_REPLY)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 댓글고유번호 | NUMBER | - | NN (시퀀스) | - | - | O |
| BOOK_NO | 도서고유번호 | NUMBER | - | N | - | 도서 | - |
| MEMBER_ID | 회원고유번호 | NUMBER | - | N | - | 회원 | - |
| NAME | 회원 이름 | VARCHAR2 | 51 | NN | - | - | - |
| MSG | 댓글내용 | CLOB | - | NN | - | - | - |
| REGDATE | 댓글 생성일 | DATE | - | N | SYSDATE | - | - |
| GROUP_ID | 그룹 번호 | NUMBER | - | N | 0 | - | - |
| GROUP_STEP | 그룹 내 순서 | NUMBER | - | N | 0 | - | - |
| GROUP_TAB | 들여쓰기 단계 | NUMBER | - | N | 0 | - | - |
| ROOT | 부모댓글번호 | NUMBER | - | N | 0 | - | - |
| DEPTH | 자식댓글개수 | NUMBER | - | N | 0 | - | - |

---

## 35. 알림 (NOTIFICATIONS)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 알림 고유 번호 | NUMBER | - | NN | 자동증가○ | - | O |
| MEMBER_ID | 회원 고유 번호 | NUMBER | - | NN | - | 회원 | - |
| TYPE | 알림 타입 | VARCHAR2 | - | NN | - | - | - |
| TITLE | 알림 제목 | VARCHAR2 | - | NN | - | - | - |
| CONTENT | 알림 내용 | VARCHAR2 | - | NN | - | - | - |
| RELATED_NO | 알림 대상 ID | NUMBER | - | - | - | - | - |
| EVENT_KEY | 카프카 멱등키 | VARCHAR2 | - | NN | - | - | - |
| IS_READ | 읽음/안읽음 | CHAR | 1 | NN | N | - | - |
| REGDATE | 등록일 | DATE | - | N | SYSDATE | - | - |
| SEND_STATUS | 발송 상태 | VARCHAR2 | 50 | NN | PENDING | - | - |

---

## 36. 시험 구독 테이블 (EXAM_SUBSCRIPTION)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| NO | 고유 번호 | NUMBER | - | NN | 자동증가○ | - | O |
| MEMBER_ID | 회원 고유 번호 | NUMBER | - | NN | - | 회원 | - |
| SCHEDULED_EXAM_NO | 시험 고유 번호 | NUMBER | - | - | - | 시험 | - |
| NOTIFIED_D3 | D-3 알림 발송 여부 | CHAR | - | NN | N | - | - |
| NOTIFIED_DDAY | 당일 알림 발송 여부 | CHAR | - | NN | N | - | - |
| REGDATE | 등록일 | DATE | - | N | SYSDATE | - | - |

---

## 37. 강의 벡터 (COURSE_VECTOR)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| ID | 고유번호 | BIGSERIAL | - | NN | 시퀀스 | - | O |
| TITLE | 강좌제목 | VARCHAR | 1000 | NN | - | - | - |
| COURSE_NO | 강좌번호 | INT8 | - | NN | - | - | - |
| INSTRUCTOR_NO | 강사번호 | INT8 | - | NN | - | - | - |
| CHUNK_NO | 청크번호 | INT4 | - | NN | - | - | - |
| CONTENT | 상세설명 | TEXT | - | NN | - | - | - |
| EMBEDDING | 임베딩 | PUBLIC.VECTOR | - | NN | - | - | - |
| CREATE_AT | 등록일 | TIMESTAMP | - | NN | CURRENT_TIMESTAMP | - | - |

---

## 38. 문제 벡터 (QUESTION_VECTOR)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| ID | 고유번호 | BIGSERIAL | - | NN | 시퀀스 | - | O |
| QUESTION_NO | 회원번호 | INT8 | - | NN | - | - | - |
| COURSE_NO | 시험수 | INT8 | - | NN | - | - | - |
| TITLE | 문제 | VARCHAR | 4000 | NN | - | - | - |
| DESCRIPTION | 해설 | TEXT | - | N | - | - | - |
| THEME | 과목 | INT4 | - | N | - | - | - |
| TYPE | 문제유형 | INT4 | - | N | - | - | - |
| DIFFICULTY | 난이도 | INT4 | - | NN | - | - | - |
| ANSWER | 정답 | VARCHAR | 2000 | NN | - | - | - |
| OPTION1 | 보기1 | VARCHAR | 2000 | - | - | - | - |
| OPTION2 | 보기2 | VARCHAR | 2000 | - | - | - | - |
| OPTION3 | 보기3 | VARCHAR | 2000 | - | - | - | - |
| OPTION4 | 보기4 | VARCHAR | 2000 | - | - | - | - |
| CONTENT | 정보 종합 | TEXT | - | NN | - | - | - |
| EMBEDDING | 임베딩 | PUBLIC.VECTOR | - | NN | - | - | - |
| CREATE_AT | 등록일 | TIMESTAMP | - | N | - | - | - |
| UPDATE_AT | 갱신일 | TIMESTAMP | - | N | - | - | - |

---

## 39. 강의-문제 유사도 매핑 (COURSE_QUESTION_MAP)
| 컬럼명 (영문) | 컬럼명 (한글) | 데이터형 | 데이터 크기 | N-N | DEFAULT | FK | PK |
| :--- | :--- | :--- | :--- | :--- | :--- | :--- | :--- |
| ID | 고유번호 | BIGSERIAL | - | NN | 시퀀스 | - | O |
| COURSE_NO | 시험번호 | INT8 | - | NN | - | - | - |
| QUESTION_NO | 문제번호 | INT8 | - | NN | - | - | - |
| SIMILARITY | 유사도 | FLOAT8 | - | NN | - | - | - |
| CREATED_AT | 생성일 | TIMESTAMP | - | NN | - | - | - |
