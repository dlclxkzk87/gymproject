# GYM Management Program

> ** JAVA + Oracle 기반의 헬스장 관리 프로그램 프로젝트 **
> KOSTA_300기 풀스택 과정에서 진행한 개발 프로젝트입니다.

---
## 팀 소개 - 포인트
| 이름   | 역할                               | GitHub 링크                                 |
|--------|------------------------------------|---------------------------------------------|
| 조세창 | 팀장, adminview 개발, 산출물 작성 | [sechangcho](https://github.com/dlclxkzk87) |
| 양소영 | sql, pay관련 기능 개발            | [soyoungyang](https://github.com/sy18346) |
| 성준우 | pt, registerview 기능 개발        | [junwoosung](https://github.com/sjw042635) |
| 성시진 | membership, loginview 기능 개발   | [SijinSeong](https://github.com/SijinSeong) |
| 김하준 | memberview, startview 기능 개발, 산출물 작성   | [hajunkim](https://github.com/Kimajun0919) |
---

## 프로젝트 기간
- **2025.03.01 ~ 2025.04.09** (총 5주)

## 프로젝트 개요
GYM management은 헬스장에서 회원과 관리자가 하나의 프로그램으로
각각의 개인정보 및 헬스장 관리를 일원화 시킨 **CUI기반 Java 어플리케이션** 입니다.

---
## 사용 기술 스택
### Backend
- Java 11
- Oracle
### IDE, 개발도구
- Eclipse
- SQL Developer
### 협업 도구
- Git & GitHub
- Notion, Figma, gamma, google drive
---

## :카메라: 주요 기능
- [x] 회원가입 / 로그인 / 로그아웃
- [x] 회원정보 조회/수정/탈퇴
- [x] 회원권 및 pt이용권 결제
- [x] 관리자 - 회원정보 조회/수정/삭제
- [x] 관리자 - 일/주/월 수입 통계 내역 조회
---

## 서비스 화면
> ![메인화면](![image](https://github.com/user-attachments/assets/0cd83494-baef-45a5-a880-a80d9414a96b))
> ![회원메뉴](![image](https://github.com/user-attachments/assets/3c73fa43-af62-4197-a52d-716a47fdcb20)
)
> ![관리자메뉴](![image](https://github.com/user-attachments/assets/cc5c5f66-76cb-44f3-a7aa-f18008f15422)
)

---
## 폴더 구조
```bash         
    └─src
        ├─adminview
        │      AdminDeleteView.java
        │      AdminImportStatisticsView.java
        │      AdminSearchView.java
        │      AdminUpdateView.java
        │      AdminView.java
        │      MemberListView.java
        │      
        ├─db
        │      ConnectionProvider.java
        │      
        ├─gym
        │  ├─dao
        │  │      AdminDAO.java
        │  │      MemberDAO.java
        │  │      MembershipDAO.java
        │  │      PayDAO.java
        │  │      PtDAO.java
        │  │      
        │  └─vo
        │          AdminVO.java
        │          MembershipVO.java
        │          MemberVO.java
        │          PayVO.java
        │          PtVO.java
        │          
        ├─loginview
        │      Login.java
        │      LoginAdmin.java
        │      LoginMember.java
        │      
        ├─main
        │      MainTest.java
        │      
        ├─memberview
        │      MemberDeleteView.java
        │      MemberPayView.java
        │      MemberSearchView.java
        │      MemberUpdateView.java
        │      MemberView.java
        │      
        ├─registerview
        │      RegisterAdmin.java
        │      RegisterMember.java
        │      
        └─startview
                StartView.java


```

## 향후 발전 과제
- 시설 관리 기능 추가
- 웹 기반으로 확장 구축
