# 📑 Java MVC 및 CRUD 프로젝트
<img width="991" height="557" alt="최종" src="https://github.com/user-attachments/assets/0bb50e51-3f14-4866-9e41-2f2f482e6120" />

<img width="1280" height="684" alt="업데이트" src="https://github.com/user-attachments/assets/a5a1ee00-eed9-4a66-ab59-735b91c13a96" />


## 프로젝트 소개
---
- Java, JSP, Servlet, JDBC를 활용한 사용자 CRUD 웹 애플리케이션 프로젝트  
- MVC 패턴 적용으로 Model–View–Controller 계층 분리 구현  
- Tomcat 10.1.28 위에서 동작하며, 회원의 로그인·로그아웃, 등록·조회·수정·삭제 기능을 제공  
- 비즈니스 로직은 DAO(데이터 접근 객체)에, 화면 처리는 JSP에 위임하여 유지보수성 향상  

🧰 기술 스택 <br>
- OS: Windows <br>
- 개발 도구: IntelliJ (IDEA 2024.3.4) <br>
- WAS: Apache Tomcat 10.1.28 <br>
- JDK: Java 17 <br>
- 데이터베이스: MySQL <br>
- 주요 라이브러리: Jakarta Servlet 6.0.0, JDBC API <br>
- 프로젝트 기간: 2024.12 <br>
- 작업인원: 개인 프로젝트  

## 🔍 프로젝트 목표
---
- Java 웹 개발의 기본 흐름(JSP/Servlet, JDBC, MVC 패턴)을 이해 및 실습  
- MVC 구조로 로직 분리
- RDB 연동 및 JDBC 예외 처리로 안정적인 데이터 입출력 환경 구축  
- 사용자에게 친절한 로그인·세션 관리 및 오류 처리 제공  

## 📗 주요 기능
---
- **회원 관리(CRUD)**  
  - 회원 등록(post.do), 조회(list.do / detail.do), 수정(update.do), 삭제(delete.do)  
  - DAO를 통한 MySQL 연동 및 PreparedStatement 활용으로 SQL 인젝션 방지  
- **인증 & 세션 관리**  
  - 로그인(login.do) 성공 시 HttpSession에 `logined`/`admin` 속성 저장  
  - 로그아웃(logout.do) 시 세션 무효화 및 메인 페이지 리다이렉트  
- **URI 기반 액션 분기**  
  - `MemberController`에서 요청 URI의 action 파라미터로 각 기능 분기 처리  
- **예외 처리 & 오류 페이지**  
  - 파라미터 누락·형식 오류 시 `error.jsp`로 포워드  
  - DAO 처리 결과에 따라 `message.jsp` 또는 `error.jsp` 분기  

## 🛠️ 문제 및 해결
---
- **MVC 분리 개념 혼란**: Controller, Service, DAO 역할 구분이 어려워 초기 설계 시 계층 간 의존성 혼재  
  - → 서비스 계층 추가 검토, Controller에서는 요청 분기만 담당하도록 리팩토링  
- **예외 처리 구현 어려움**: JDBC 예외와 파라미터 형식 오류 시 사용자 안내 로직 작성  
  - → 공통 예외 처리 메서드 작성 및 JSP 기반 에러 페이지 통합 적용  

## 📌 성과 및 배운 점
---
- Java 웹 애플리케이션의 요청 처리 흐름(Servlet → JSP) 구조를 체득  
- MVC 패턴 적용으로 관심사 분리 원칙(SOC)을 실전에서 경험  
- JDBC API로 MySQL 연동 및 PreparedStatement 활용을 통한 보안 강화 학습  
- Servlet과 JSP 포워딩/리다이렉션 차이를 이해하고, 사용자 친화적 오류 처리 방법 습득  
- 예외 처리 로직 설계로 견고한 웹 애플리케이션 개발 역량 향상  
