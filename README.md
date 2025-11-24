# 🎬 Netflix Clone Project: 넷플릭스 클론 사이트

## ✨ 소개
Java Spring Boot와 현대적인 프론트엔드 프레임워크를 활용하여 Netflix의 주요 기능을 구현하는 것을 목표로 하는 개인 프로젝트입니다.

## 🚀 주요 기술 스택
| 구분 | 기술 | 설명 |
| :--- | :--- | :--- |
| **백엔드** | **Java** | 메인 프로그래밍 언어 |
| | **Spring Boot** | RESTful API 구축 및 서버 개발 프레임워크 |
| | **Spring Security** | JWT 기반 사용자 인증 및 인가 처리 |
| **데이터베이스**| **MariaDB** | 관계형 데이터베이스 |
| **프론트엔드** | (예정) **React** 또는 **Vue.js** | SPA (Single Page Application) 개발 (아직 미정) |

---

## 🏗️ 개발 환경 설정 (백엔드 기준)

### 1. 사전 요구 사항
* Java Development Kit (JDK 17+)
* MariaDB (Local 설치 또는 Docker 환경)
* Git

### 2. 프로젝트 실행 방법
1.  이 저장소를 클론합니다.
    ```bash
    git clone [GitHub 레포지토리 URL]
    ```
2.  `src/main/resources/application.properties` 파일에 데이터베이스 연결 정보를 설정합니다.
3.  IDE (IntelliJ IDEA 등)에서 프로젝트를 열고 Spring Boot Application을 실행합니다.

## 🔑 진행 상황

* [x] Spring Boot 기본 설정 및 환경 구축
* [x] MariaDB 연동 설정 완료
* [ ] Spring Security (JWT) 기반 인증/인가 기능 구현 중
* [ ] 사용자 회원가입 및 로그인 API 개발
* [ ] 프론트엔드 환경 설정 및 연동 준비