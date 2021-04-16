## URL 단축 서비스

### 설치 / 빌드

- java 설치
```console
$ sudo yum install java-11-openjdk-devel
```

- gradle 설치
```console
$ wget https://services.gradle.org/distributions/gradle-5.6.2-bin.zip -P /tmp
$ mkdir /opt/gradle
$ unzip -d /opt/gradle /tmp/gradle-5.6.2-bin.zip
$ sudo chmod +x /etc/profile.d/gradle.sh
$ source /etc/profile.d/gradle.sh
```

- 프로젝트 설치
```console
$ git clone https://github.com/roy-zz/shortener.git
```

- 프로젝트 빌드 및 실행
```console
$ cd {PROJECT_HOME}
$ gradle build
$ gradle run
```

* 접속 주소 = `http://localhost:8888`
* h2 콘솔 접속 주소 `http://localhost:8888` (계정 정보는 application.yml 참고)