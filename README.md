<div align="center">
    JPQL 기본 문법
</div>

#### JPQL
- `select m from Member m where m.age > 18`
- 엔티티와 속성은 대소문자 구분한다.
- JPQL 키워드는 대소문자 구분 안한다(select, from where..)
- 엔티티 이름 사용, 테이블 이름을 사용하지 않는다.
- 별칭은 필수

- TypeQuery
    - 반환 타입이 명확할 때 사용
  ```java
  TypedQuery<Member> query = em.createQuery("select m from Member m", Member.class);
  ```
- Query
    - 반환 타입이 명확하지 않을 때 사용
  ```java
  Query query2 = em.createQuery("select m.username, m.age from Member m");
  ```

- 결과 조회 API
    - query.getResultList()
        - 결과가 하나 이상일 때 리스트 반환
        - 결과가 없으면 빈 리스트 반환
    - query.getSingleResult()
        - 결과과 정확히 하나, 단일 객체 반환
        - 결과가 없으면 NoResultException
        - 둘 이사이면 NonUniqueResultException

#### 프로젝션
- select 절에 조회할 대상을 지정하는 것
- 프로젝션 대상: 엔티티, 임베디드 타입, 스칼라 타입(숫자, 문자 등 기본 데이터 타입)

- 프로젝션 - 여러 값 조회
- `select m.username, m.age from Member m`
  - Query 타입으로 조회
  ```java
  List resultList = em.createQuery("select m.username, m.age from Member m")
                            .getResultList();
  Object o = resultList.get(0);
  Object[] result = (Object[]) o;
  ```
  - Object[] 타입으로 조회
  ```java
   List<Object[]> resultList = em.createQuery("select m.username, m.age from Member m", Object[].class)
                    .getResultList();
  Object[] result = resultList.get(0);  
  ```
  - new 명령어로 조회
    - 단순 값을 DTO로 바로 조회
  ```java
  List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                    .getResultList();
  MemberDTO memberDTO = resultList.get(0);
  ```
  
#### 페이징 API
- JPA는 페이징을 다음 두 API로 추상화
- setFirstResult(int startPosition)
  - 조회 시작 위치
- setMaxResults(int maxResult)
  - 조회할 데이터 수

#### 조인
- 내부 조인
- 외부 조인
- 세타 조인

#### 서브 쿼리
- 서브 쿼리 한계
  - JPA는 where, having 절에서만 서브 쿼리 사용 가능
  - from 절의 서브 쿼리는 현재 JPQL에서 불가능
    - 조인으로 풀 수 있으면 풀어서 해결

#### JPQL 타입 표현
- 문자: '헬로'
- 숫자: 10L(Long), 10D(Double), 10F(Float)
- Boolean: TRUE, FALSE
- ENUM: enum 클래스의 패키지명까지 모두 적어줘야 함
- 엔티 타입: TYPE(m) = Member (상속 관계에서 사용)

#### JPQL 기본 함수
- CONCAT
- SUBSTRING
- TRIM
- LOWER, UPPER
- LENGTH
- LOCATE
- ABS, SQRT, MOD
- SIZE, INDEX(JPA 용도)

- 사용자 정의 함수 호출
  - 하이버네이트는 사용 전 방언에 추가해야 한다.
