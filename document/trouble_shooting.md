
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
