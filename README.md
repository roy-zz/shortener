## URL 단축 서비스

### 설치 / 빌드
- 설치 및 빌드 방법과 영상은 centos7 기준으로 작성되었습니다.

- java 설치
```console
// 설치
$ sudo yum install java-1.8.0-openjdk-devel
// 설치 확인
$ java -version
```

- gradle 설치
```console
// 설치
$ wget https://services.gradle.org/distributions/gradle-6.8.3-bin.zip
$ sudo mkdir /opt/gradle
$ unzip -d /opt/gradle gradle-6.8.3-bin.zip
// 환경변수 설정
$ export PATH=$PATH:/opt/gradle/gradle-6.8.3/bin
// 설치 확인
$ gradle -v
```

- 프로젝트 다운로드
```console
// git 설치
$ sudo yum install git
// 프로젝트 다운로드
$ git clone https://github.com/roy-zz/shortener.git
```

- 프로젝트 빌드 및 실행
```console
$ cd shortener
$ gradle build
$ gradle run
```

* 접속 주소 = `http://localhost:8888`
* h2 콘솔 접속 주소 `http://localhost:8888/h2-console` (계정 정보는 application.yml 참고)

### 프로젝트 설명
URL을 입력받아 짧게 줄여주고, Shortening된 URL을 입력하면 원래 URL로 리다이렉트하는 URL Shortening Service

예) https://en.wikipedia.org/wiki/URL_shortening => http://localhost/JZfOQNro

* URL 입력폼 제공 및 결과 출력

  - Backend: REST api
  - Frontend: jQuery
  - 성공시 단축된 URL과 총 단축 요청받은 횟수를 화면에 표시
  - 단축 실패시 실패 사유를 화면에 표시(URL 유효성 검사는 bitly.com 과 유사하게 구현하였음)
  
* URL Shortening Key는 8 Character 이내로 생성되어야 합니다.

  - 단축된 URL이 `https://localhost:8888/{shortening_key}` 라고 하였을 때 shortening_key 는 OriginURL 이 저장된 row의 ID를 Base62 encoding 한 값이 되도록 구현
  
* 동일한 URL에 대한 요청은 동일한 Shortening Key로 응답해야 합니다.
* 동일한 URL에 대한 요청 수 정보를 가져야 한다(화면 제공 필수 아님)  

  - 입력받은 URL이 DB에 존재하는지 조회
  - 만약 이미 저장된 URL이라면 저장되어있는 row의 requested_count 를 +1
  - 저장되어 있지 않는 URL이라면 새로운 row를 생성 
  
* Shortening된 URL을 요청받으면 원래 URL로 리다이렉트 합니다.

  - 단축 ULR인 `http://localhost:8888/{encoded_rowId}` 로 접속할 경우 원본 URL로 Redirect
  - 원본 URL을 찾을 수 없는 경우 `DataNotFoundException` 발생
   
* Database 사용은 필수 아님

  - H2 데이터베이스 사용
  - 서비스 실행시 flyway를 사용하여 테이블을 생성.
  
 ```sql
CREATE TABLE `url`
(
    `id`              BIGINT(20)    NOT NULL AUTO_INCREMENT,
    `origin`          VARCHAR(1000) NOT NULL COMMENT '변경전 주소',
    `requested_count` INT(20) DEFAULT 0 COMMENT '단축 요청 횟수',
    PRIMARY KEY (`id`),
    UNIQUE KEY `uk_origin` (`origin`)
); 
```
* id = row의 고유ID, 단축된 URL이 `http://localhost:8888/{shortening_key}` 라고 하였을 때 shortening_key는 id가 Base62 encoding된 값이 됨

* origin = 클라이언트에게 입력받은 단축되지 않은 url

* requested_count = 단축 요청받은 횟수 