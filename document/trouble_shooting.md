
## 📎 Trouble Shooting

### [ H2 ] 로컬이 아닌 서버에서도 계속 DB 데이터를 유지할 수 있는 방법
#### Ver 1.
```yaml
spring:
  datasource:
    hikari:
      jdbc-url: 'jdbc:h2:./data/demo'
      driver-class-name: org.h2.Driver
      username: {{ username }}
      password: {{ password }}
```

- DB는 가볍고 설정이 쉬운 H2를 사용
- DB 상태를 저장하는 `.db` 파일들을 저장할 경로를 **./data/demo/** 로 설정

##### 문제 상황
- 로컬에서 테스트를 했을 때, 재부팅을 할 때마다 DB가 초기화되는 문제 발생


#### Ver 2.

```yaml
spring:
  datasource:
    hikari:
      jdbc-url: 'jdbc:h2:file:./data/demo'
      driver-class-name: org.h2.Driver
      username: {{ username }}
      password: {{ password }}
```
- H2 Database: In-Memory mode, Embedded mode, Server mode 등 여러 가지 모드 존재
- 그 중에서 파일로 DB 데이터를 저장하고, 해당 파일을 읽어오는 `File mode` 선택
- 재부팅 할 때마다 DB가 초기화되는 문제 해결

##### 문제 상황
- 로컬에서는 정상 작동하나, 서버에서는 데이터가 유지되지 않는 문제 발생

#### Ver 3.

```yaml
spring:
  datasource:
    hikari:
      jdbc-url: 'jdbc:h2:file:./data/demo'
      driver-class-name: org.h2.Driver
      username: {{ username }}
      password: {{ password }}
```
- 우선, DB data를 계속 유지하는 방법으로는 `File mode`를 사용하는 것이 맞음
- **./data/demo** 라는 경로에 있는 `.db` 파일들을 제대로 읽어오지 못 하는 것 원인
- 서버 배포 시 Docker를 사용했기 때문에, 컨테이너 내 디렉토리와 컨테이너 외부인 호스트의 디렉토리를 구분해야 했던 것
- 컨테이너와 호스크 간의 Shared Volume 사용을 통해 해결

<br/>

### [ SpringBoot ] 데이터 생성 시간, 수정 시간이 한국 시간과 달랐던 문제
#### Ver 1.
```java
    @PrePersist
    protected void onCreate() {
        createdDatetime = new Date();
        updatedDatetime = new Date();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedDatetime = new Date();
    }
```
- DB에 데이터를 생성(create)하고 수정(update)할 때, 그 시간이 자동으로 기록되도록 설정

##### 문제 상황
- DB에 저장된 데이터를 확인해보니, 기록된 시간들이 한국 시간과 다르게 기록되는 문제

#### Ver 2.
```sql
ALTER TABLE  image ALTER COLUMN created_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP + INTERVAL '9' HOUR NOT NULL;
ALTER TABLE  image ALTER COLUMN updated_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP + INTERVAL '9' HOUR NOT NULL;

ALTER TABLE  post ALTER COLUMN created_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP + INTERVAL '9' HOUR NOT NULL;
ALTER TABLE  post ALTER COLUMN updated_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP + INTERVAL '9' HOUR NOT NULL;

ALTER TABLE  station ALTER COLUMN created_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP + INTERVAL '9' HOUR NOT NULL;
ALTER TABLE  station ALTER COLUMN updated_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP + INTERVAL '9' HOUR NOT NULL;

ALTER TABLE  tag ALTER COLUMN created_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP + INTERVAL '9' HOUR NOT NULL;
ALTER TABLE  tag ALTER COLUMN updated_datetime TIMESTAMP DEFAULT CURRENT_TIMESTAMP + INTERVAL '9' HOUR NOT NULL;
```
- 이미 테이블을 생성하고 데이터들이 저장된 상태였기 때문에, 컬럼의 설정을 바꾸는 형식으로 해결 시도
- `한국 시각 = Default 시각 + 9H` 이므로, 위와 같이 쿼리 실행

##### 문제 상황
- Insert Query를 통해 데이터를 저장하면 한국 시각으로 잘 저장이 되었으나, API 요청을 하면 다시 Default 시각으로 저장되는 문제 발생

#### Ver 3.
```java
@SpringBootApplication
 public class Mobil2teamApplication {

     public static void main(String[] args) {
         SpringApplication.run(Mobil2teamApplication.class, args);
     }

     @PostConstruct
     public void init() {
         // timezone 설정
         TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
     }

 }
```
- API 요청 시 문제가 발생했으므로, Java 코드 수정 필요
- `@SpringBootApplication` 이 설정된 파일에, TimeZone을 서울 기준으로 설정하는 코드 작성

##### 문제 상황
- 여전히 API 요청 시에는 적용이 안 되는 문제

#### Ver 4.
```java
@SpringBootApplication
public class Mobil2teamApplication {

    public static void main(String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
        SpringApplication.run(Mobil2teamApplication.class, args);
    }

}
```
- 같은 코드의 순서를 변경
- 어플리케이션이 실행되기 전에 '먼저' TimeZone 설정을 적용함
- 문제 해결!

<br/>

### [ CI/CD ] Github Actions를 이용한 배포 문제
#### Ver 1.
```yaml
    - name: Build with Gradle
       uses: gradle/gradle-build-action@bd5760595778326ba7f1441bcf7e88b49de61a25 # v2.6.0
       with:
         arguments: build

     - name: Upload build artifact
       uses: actions/upload-artifact@v2
       with:
         name: mobil2team
         path: build/libs/mobil2team-0.0.1-SNAPSHOT.jar
    ...
        - name: Deploy
         uses: appleboy/ssh-action@v0.1.6
         with:
           key: ${{ secrets.PRIVATE_KEY }}
           script: |
               pgrep java | xargs kill -9 
               nohup java -jar /home/${{ secrets.EC2_USERNAME }}/mobil2team.jar > app.log 2>&1 &
```
- build를 통해 `.jar`를 생성하고, 해당 파일을 호스트 서버 디렉토리로 전송
- 호스트 서버에서 `.jar` 파일을 실행해 최종 배포

##### 문제 상황
- Github Actions 실행 시 빌드 파일이 생성되지 않거나, 빌드 파일을 찾을 수 없거나, 빌드 파일을 호스트 서버에 전송할 수 없는 문제 발생

#### Ver 2.
```yaml
    - name: Build with Gradle
      run: ./gradlew build -x test
    ...
    - name: Build and push
      id: docker_build
      uses: docker/build-push-action@v2
      with:
        context: .
        file: ./Dockerfile
        push: true
        tags: ${{ secrets.DOCKER_REPO }}:latest
```
- 프로젝트 기간이 짧아 최대한 빨리 배포해야 하는 상황
- `Ver 1.` 방법의 에러를 해결하는 대신, 새로운 방법으로 배포 결정: Docker 사용
- 빌드 파일을 통해 도커 이미지를 생성한 후, 도커 허브에 이를 Push
- 호스트 서버에서 해당 이미지를 pull 받은 후 컨테이너를 실행하는 방식으로 최종 배포

<br/>

### [ SpringBoot ] 지하철 역 별로 많이 쓰이는 태그 리스트 순서를 반환하는 기능
#### Ver 1.
```java
/* 역 별 태그 리스트 */
public DataResponseDto<TagListDto> getTagList(Long id) {
    // tag 목록 전체 조회
    List<Tag> tagList = tagRepository.findAll();

    // 현재 시간에서 2시간 전 계산
    Calendar calendar = Calendar.getInstance();
    calendar.add(Calendar.HOUR_OF_DAY, -2);
    Date date = calendar.getTime();

    // 2시간 전 ~ 현재 시간까지 작성된 글 리스트 불러오기
    List<Post> posts = postRepository.findAllByStationIdAndCreatedDatetimeAfter(id, date);

    List<TagDto> results = new ArrayList<>();

    // 게시글이 있는 경우
    if(!posts.isEmpty()) {
        // 태그별 카운팅
        Map<Tag, Long> tagCountMap = posts.stream()
                .collect(Collectors.groupingBy(Post::getTag, Collectors.counting()));

        // 태그 오름차순으로 정렬
        results.addAll(tagCountMap.entrySet().stream()
                .sorted((tag1, tag2) -> {
                    // 카운트 수 높은 순으로 정렬
                    int compareByCount = tag2.getValue().compareTo(tag1.getValue());

                    // 카운트 수 동일할 경우 id 오름차순으로 정렬
                    if (compareByCount == 0) {
                        return Long.compare(tag1.getKey().getId(), tag2.getKey().getId());
                    }
                    return compareByCount;
                })
                .map(tag -> new TagDto(tag.getKey().getId(), tag.getKey().getName()))
                .toList());
    }

    // 남은 tag 추가
    for (Tag t : tagList) {
        TagDto dto = new TagDto(t.getId(), t.getName());
        if(!results.contains(dto)) {
            results.add(dto);
        }
    }

    return DataResponseDto.of(new TagListDto(results));
}
```
- 서비스 단에서 필요한 정보 데이터베이스 조회하며 로직 구성

##### 문제 상황
- 데이터베이스에서 불러온 엔티티를 DTO로 변경하는 과정을 중복적으로 처리함
- 데이터베이스 조회를 여러번 사용함

#### Ver 2.
```java
@Query(value = "SELECT t.id from tag t " +
        "left outer join " +
        "(select p.tag_id as tagId, count(tag_id) as count " +
        "from post p " +
        "where p.station_id = :id " +
        "AND p.created_datetime >= CURRENT_TIMESTAMP - INTERVAL '5' DAY " +
        "GROUP BY tag_id) as a " +
        "ON t.id = a.tagId " +
        "ORDER BY a.count desc", nativeQuery = true)
List<Long> getTagList(@Param("id") Long id);
```
- tag 테이블과 post 테이블을 조인하여 태그 리스트 순서를 반환하는 쿼리로 로직 수정

<br/>

### [ SpringBoot ] select query를 여러 번 사용한 코드
#### Ver 1.
```java
Station station = stationRepository.findById(postDto.getStationId()).orElse(null);
Tag tag = tagRepository.findById(postDto.getTagId()).orElse(null);
Image image;
if (postDto.getImageId() != null) {
   image = imageRepository.findById(postDto.getImageId()).orElse(null);
} else {
   image = null;
}
post.setStation(station);
post.setTag(tag);
post.setImage(image);
```
- **Post** 엔티티에 **Station**, **Tag**, **Image** 엔티티가 `@ManyToOne` 으로 연결되어 있는 상황
- Post 객체를 save하기 위해서는 해당 객체에 Station, Tag, Image 객체 설정이 필요

##### 문제 상황
- Station, Tag, Image 객체를 찾기 위해 select query를 반복적으로 실행 -> 비효율적으로 느껴짐

##### Ver 2.
```java
@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Image {
    ...
}
```
```java
post.setStation(Station.builder().id(postDto.getStationId()).build());
post.setTag(postDto.getTagId() != null ? Tag.builder().id(postDto.getTagId()).build() : null);
post.setImage(postDto.getImageId() != null ? Image.builder().id(postDto.getImageId()).build() : null);
```
- select query 대신 Builder 활용
- 각 엔티티 클래스에 `@Builder` 설정
- `findById` 대신 **id**와 **Builder**를 사용해 객체를 생성
- 추가적으로, 코드의 간결함을 위해 if문 대신 삼항연산자 이용

<br/>

### [ SpringBoot ] 도메인 주소와 HTTPS 설정하기
#### Ver 1.
```yaml
server:
  port: 80
```
- 기존에는 서버 포트를 80으로 설정해, HTTP로만 접근 가능

#### Ver 2.
##### (1) 도메인 주소 구매
<img width="1334" alt="image" src="https://github.com/softeerbootcamp-3nd/softee5-mobil2team-BE/assets/48647199/4ae6cbcf-bf77-4764-871c-193fc469a3bb">


- 가비아에서 도메인 구매

##### (2) AWS Route 53
<img width="1250" alt="image" src="https://github.com/softeerbootcamp-3nd/softee5-mobil2team-BE/assets/48647199/d056f550-6744-4dca-ae50-fe4c2a5c8b96">

- AWS의 **Route 53**에서 구매한 도메인 이름을 바탕으로 호스팅 영역 생성하기

##### (3) 구입한 도메인 설정
<img width="1106" alt="image" src="https://github.com/softeerbootcamp-3nd/softee5-mobil2team-BE/assets/48647199/255f83e5-b776-4cd9-8501-282c59dfdcb1">

<img width="1174" alt="image" src="https://github.com/softeerbootcamp-3nd/softee5-mobil2team-BE/assets/48647199/5b928f6c-45ae-4dcc-95d8-bd824465068f">

- 가비아에서 구입한 도메인의 네임서버(1~4차)를, AWS에서 생성한 호스팅 영역에서 NS 레코드의 라우팅 대상 값으로 대체 (마지막의 '.'은 포함 x)
- 소유자 인증 완료

##### (4) SSL(TLS)를 위한 인증서 발급
<img width="1285" alt="image" src="https://github.com/softeerbootcamp-3nd/softee5-mobil2team-BE/assets/48647199/fdfa7fe4-fb5c-4297-9e7a-8654b5c4624e">

- AWS의 **Certificate Manager** 에서 도메인 이름으로 인증서 발급 받기
- ! CNAME 레코드 생성하기
  - 발급 받은 인증서의 `Route 53에서 레코드 생성` 버튼을 통해 레코드 생성
  - 인증서 발급 대기: 최대 2시간까지는 기다려보기

##### (5) EC2 인스턴스의 인바운드 규칙 설정
<img width="1046" alt="image" src="https://github.com/softeerbootcamp-3nd/softee5-mobil2team-BE/assets/48647199/6eb93aaf-224a-4013-b5a7-56fe6a95b3fa">

- Port `443` : Anywhere-IPv4, Anywhere-IPv6 설정
- Port `80` : Anywhere-IPv4, Anywhere-IPv6 설정

##### (6) Target Group 생성
<img width="1051" alt="image" src="https://github.com/softeerbootcamp-3nd/softee5-mobil2team-BE/assets/48647199/e41297cb-53b7-48bb-937d-8534e63df2b1">


- AWS의 **Load Balancing** 에서 타겟 그룹 생성
  - Port 번호를 `80` 으로 설정
  - VPC는 EC2 인스턴스와 동일하게
  - Health Check Path 설정 : 항상 200을 response하는 API
  - 사용할 EC2 인스턴스와 포트번호 설정 후 타겟 그룹에 추가
  - 타겟 그룹 생성

##### (7) Load Balancer 생성
<img width="1280" alt="image" src="https://github.com/softeerbootcamp-3nd/softee5-mobil2team-BE/assets/48647199/51a52c6c-700a-4552-9bf1-c11bed2f3664">

- VPC : EC2 인스턴스가 사용하는 VPC
- Network Mapping : 최소 2개의 Ability Zone 설정
  - Subnet: EC2 인스턴스가 사용하는 Subnet (private이 아닌 public이어야 함)
- 보안 그룹 : EC2 인스턴스가 사용하는 보안 그룹
- Port `443` 과 `80` 에 대한 리스너 생성
  - Forward to 에 타겟 그룹 설정
  - 발급 받은 인증서 적용

##### (8) 도메인 레코드 생성 - A 레코드
<img width="1233" alt="image" src="https://github.com/softeerbootcamp-3nd/softee5-mobil2team-BE/assets/48647199/02cd0b22-789f-4310-85c3-020e765d4dea">


- AWS의 **Route 53** 에서 생성한 호스팅 영역에서, `레코드 생성`을 통해 A 레코드 생성
  - 레코드 이름(서브 도메인) 사용은 선택
  - 별칭 체크는 필수
  - 트래픽 라우팅 대상은 생성한 Load Balancer로 지정

##### (9) Load Balancer의 리스너 규칙 추가
<img width="1068" alt="image" src="https://github.com/softeerbootcamp-3nd/softee5-mobil2team-BE/assets/48647199/47c03af9-d4ac-4016-b74b-45731559acc1">

- Port `443`과 `80`에 대한 리스너 각각 존재
- 각 리스너에서 규칙 편집
  - 전달 대상은 생성한 타겟 그룹, 100%로 지정

##### (10) Health Check
<img width="1058" alt="image" src="https://github.com/softeerbootcamp-3nd/softee5-mobil2team-BE/assets/48647199/3d724cef-b703-42ae-8674-58a5b604c625">

- AWS의 EC2 > Load Balancing > **Target Group** 에서 생성한 타겟 그룹의 Health Status 확인
- `Health checks` 탭에서 설정 수정
  - 프로토콜, URI(Path), Success Code 설정
  - [ `80`, "/test", 200 ] 과 [ `443`, "/test", 200 ] 으로 지정
- Health Check 에서 `Healthy`가 뜨는지 확인

##### (11) Server URL mapping
```yaml
server:
  url: https://api.waglewagle.store
```
- **application.yml** 에 위 코드 추가
- 끝 !!!!!!



















