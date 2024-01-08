# 👨‍👩‍👧‍👦 와글와글 (Wagle Wagle)

<div align="center">
<img width="329" alt="Wagle" src=".png">

[![Hits](https://hits.seeyoufarm.com/api/count/incr/badge.svg?url=https%3A%2F%2Fgithub.com%2Fsofteerbootcamp-3nd%2Fsoftee5-mobil2team-BE&count_bg=%23203A40&title_bg=%23BCC1CD&icon=&icon_color=%23FFFFFF&title=hits&edge_flat=true)](https://hits.seeyoufarm.com)

</div>


## Deploy ✨
- **FE** : [https://mobil2team-fe.vercel.app/](https://mobil2team-fe.vercel.app/)<br>
- **BE** : [http://13.209.90.251:80/](http://13.209.90.251:80/)<br>

<br/>

## Aboud Project 🚋
### **소프티어 부트캠프 3기 - 워밍업 프로젝트 모빌2팀**
> **개발 기간: 2024.01.04 ~ 2024.01.10**

#### 지루한 지하철.. 내 옆 사람은 무슨 생각하지? 🤔💭
지하철을 타고있는 사람들의 공감대를 바탕으로 콘텐츠를 공유/탐색하며 지하철 이동 중의 지루함을 해소하는 웹서비스

<br/>

## Functions 📪
### 1. 지금 내 생각은❕
역 별로 소소한 글을 올려 당장의 나의 기분을 표현하고 다른 사람에게 정보를 공유한다.
### 2. 지금 다른 사람들은❔
비슷한 시간대에 지하철을 타는 사람들의 글을 통해 공감대를 형성하고 지루한 지하철에서의 시간을 달랜다. 
<br/>(지하철 이용의 지루함 공감, 재밌는 짤 업로드 등)
### 3. 와글와글 지하철 ⁉️
정서를 표현하는 캐릭터와 공감성을 담은 재치있는 랜덤 닉네임을 통해 소소한 재미를 부여한다. 
<br/>(ex. 닉네임: 출근하기 싫은 박명수)

<br/>

## Stacks 🐈

### Environment
![Intellij](https://img.shields.io/badge/Intellijidea-000000?style=for-the-badge&logo=intellijidea&logoColor=white)
![Git](https://img.shields.io/badge/Git-F05032?style=for-the-badge&logo=Git&logoColor=white)
![Github](https://img.shields.io/badge/GitHub-181717?style=for-the-badge&logo=GitHub&logoColor=white)             

### BackEnd
![SpringBoot](https://img.shields.io/badge/Springboot-6DB33F?style=for-the-badge&logo=Springboot&logoColor=white)
![H2](https://img.shields.io/badge/H2-4479A1?style=for-the-badge&logo=H2&logoColor=white)
![Docker](https://img.shields.io/badge/Docker-2496ED?style=for-the-badge&logo=Docker&logoColor=white)

### CI/CD
![GithubActions](https://img.shields.io/badge/Github_Actions-2088FF?style=for-the-badge&logo=githubactions&logoColor=white)

### Deploy
![AWS EC2](https://img.shields.io/badge/AWS_EC2-FF9900?style=for-the-badge&logo=amazonec2&logoColor=white)

### Communication
![Slack](https://img.shields.io/badge/Slack-4A154B?style=for-the-badge&logo=Slack&logoColor=white)
![Notion](https://img.shields.io/badge/Notion-000000?style=for-the-badge&logo=Notion&logoColor=white)

---
## Pages 📺

---
## 아키텍쳐

### 디렉토리 구조
```bash
├── README.md
├── .gitignore
├── Dockerfile
├── HELP.md
├── LICENSE
├── build.gradle
├── gradlew
├── gradlew.bat
├── settings.gradle
├── .github : github actions
│   └── workflows
│       └── gradle.yml
├── data : h2 data file
│   ├── demo.mv.db
│   └── demo.trace.db
├── gradlew
│   └── wrapper
│       ├── gradle-wrapper.jar
│       └── gradle-wrapper.properties
└── src
    ├── main
    │   ├── java/com/softee5/mobil2team
    │   │   ├── config
    │   │   │   ├── ExceptionHandler.java
    │   │   │   ├── GeneralException.java
    │   │   │   ├── ResponseCode.java
    │   │   │   ├── SwaggerConfig.java
    │   │   │   └── WebConfig.java
    │   │   ├── controller
    │   │   │   ├── PostController.java
    │   │   │   ├── StationController.java
    │   │   │   └── TestController.java
    │   │   ├── dto
    │   │   │   ├── BriefInfoDto.java
    │   │   │   ├── DataResponseDto.java
    │   │   │   ├── ErrorResponseDto.java
    │   │   │   ├── HotStationDto.java
    │   │   │   ├── ImageDto.java
    │   │   │   ├── ImageListDto.java
    │   │   │   ├── NearStationDto.java
    │   │   │   ├── PageInfoDto.java
    │   │   │   ├── PageResponseDto.java
    │   │   │   ├── PostDto.java
    │   │   │   ├── PostInfoDto.java
    │   │   │   ├── PostListDto.java
    │   │   │   ├── ResponseDto.java
    │   │   │   ├── StationListDto.java
    │   │   │   ├── TagListDto.java
    │   │   │   └── TestDto.java
    │   │   ├── entity
    │   │   │   ├── Image.java
    │   │   │   ├── Post.java
    │   │   │   ├── Station.java
    │   │   │   └── Tag.java
    │   │   ├── repository
    │   │   │   ├── ImageRepository.java
    │   │   │   ├── PostRepository.java
    │   │   │   ├── StationRepository.java
    │   │   │   └── TagRepository.java
    │   │   ├── service
    │   │   │   ├── PostService.java
    │   │   │   ├── StationService.java
    │   │   │   ├── TagService.java
    │   │   │   └── TestService.java
    │   │   └── Mobil2teamApplication.java
    │   └── resources
    │       ├── static
    │       │   └── images
    │       └── application.yml
    └── test/java/com/softee5/mobil2team

```

```
-->

## 개발팀 소개
