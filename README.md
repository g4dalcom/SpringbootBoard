# 📝SpringbootBoard

**2022-08-15 ~ (진행중)**

[1. 개발일지(노션)](https://rhetorical-durian-6e6.notion.site/1c551f6a8e5e423bb336c051547be197?v=dcc7831a1302448b974c1e32f14edf0d)

---

### ✔ 구현 기능
(22-08-17)
- 전체 게시글 목록 조회 (게시글 번호, 제목, 작성자명, 작성날짜(내림차순 정렬))
- 게시글 작성 (제목, 작성자명, 비밀번호, 작성내용)
- 게시글 상세조회
  - 게시글 작성 폼과 동일하나 이전에 작성한 내용이 들어가있고 읽기전용으로 보여짐
  - 수정 삭제 목록으로 돌아가는 버튼이 있음
- 게시글 수정
  - 게시글 상세조회 페이지에서 수정버튼을 누르면 수정페이지로 이동
  - 수정한 후 게시글 목록으로 이동
- 게시글 삭제
  - 수정 기능과 동일
  
  (22-08-18)
- 게시글 비밀번호 일치여부 확인 기능
  - 상세페이지에서 삭제, 수정페이지에서 수정 가능하도록 구현

---

### ❌ 미구현 기능
- AWS 배포 이후 작성날짜가 맞지 않는 현상
- 페이징 처리 버튼 바로잡기

---

### ⚒ Trouble Shooting

- H2 데이터베이스에서 MySQL로 바꾸자 글쓰기 페이지(/boardWrite)로 넘어가지 않는 오류가 발생함(404오류)
    
  ![11](https://user-images.githubusercontent.com/93110733/185192001-93f9b902-9b49-4587-84b5-e0b513d08522.JPG)
  ![배경](https://user-images.githubusercontent.com/93110733/185192064-9cb6779a-fca3-45b6-a370-5ae4c38c943a.png)

  - 해결 : 게시글 등록은 /boardWrite 라는 url로 접속을 했을 때 “addBoardForm” 이라는 html 뷰를 보여주게 되어있다. 아래 [application.properties]는 위 4개 코드가 H2 DB를 사용할 때 코드이고 아래 4개 코드가 MySQL 로 바꾸면서 변경한 코드이다. 코드 내용은 모르지만 직관적으로 view와 .html 이 연관되어 있는듯하여 네번째 코드를 활성화시켜주었더니 오류가 해결되긴 하였다.
  - 해당 내용을 구글링해본 결과, 기본적으로 서버에서 뷰 파일의 확장자가 무엇인지 명시하는 것이고 들어오는 URL에는 영향을 미치지 않으며 뷰의 이름을 해석하는 데에만 사용된다고 한다…. 는데 아직 서버 관련해서 지식이 너무 부족해서 무슨 뜻인지 이해를 못했다.
