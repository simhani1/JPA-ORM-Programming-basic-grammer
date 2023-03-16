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