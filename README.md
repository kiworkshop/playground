## 배운점

### SimpleJdbcInsertOperations, SqlParameterSource

SQL을 하드코딩하는 것보다는 한 단계 추상화된 JDBC 기술들을 사용하는 게 더 낫다.

- 오탈자 등 휴먼 에러로 인한 버그를 줄일 수 있다.
- 재사용성과 유지보수성이 개선된다.
    - SQL을 직접 작성하면 끽해봐야 매직 넘버나 스트링을 빼는 정도로만 재사용성을 높일 수 있다.
    - 반면 얘네들은 엔티티를 자동 매핑해주기도 하고... 스트링의 나열로 보는 것보다 가독성도 낫다.
- 그래도 여전히 불편하다.

## 의문점

### 왜 엔티티를 쓸 일이 없는가

DocumentApproval 엔티티를 만들지 않았는데도 API가 동작한다....? 이래도 되는 건가... 무슨 짓을 저지른 건가...

### documentService.findById()

1. 테이블별로 메서드 분리해서 각각 찾아오기
    - document, user, document_approval 테이블 각각 조회...? 에반데
    - 게다가 SELECT문에서 조인해서 document 가져오면 document rowMapper도 같이 수정해야됨 진짜 구리다
    - 우선 response 형식에 approvers가 없으니까 document_approval 테이블은 패스
    - 근데 documentDao가 user 테이블까지 접근하는건 짱별로인듯

2. 테이블 조인해서 한 번에 찾아오기
    - 이럴거면 테이블별로 dao를 왜 나누냐..?

### DB 테스트 시 초기 데이터로의 의존성

현재 dao 테스트가 data.sql에서 제공하는 초기 데이터셋에 의존하고 있다.

- findById(): 문서가 이미 저장되어 있어야 find가 가능하다.
- addDocument(): pk를 검증할 수 없다. auto_increment pk 값을 모르기 때문이다.

해결책은 뭘까

1. 그냥 초기 적재 데이터에 의존한다.
    - 적재 데이터가 바뀌면 그 때마다 테스트가 깨지지 않나..
    - 영향 크게 안 받게 해놔도 언젠간 깨지지 않나..
2. find 하기 전에 데이터를 넣던지, add 하기 전에 pk를 미리 알아 온다.
    - 이전에 수행하는 로직에 의존하지 않나..