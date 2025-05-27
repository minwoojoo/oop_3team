1) Main.java
•	진입점(Entry Point)
프로그램 실행의 시작점이 되는 클래스입니다.(보통 public static void main(String[] args)가 있음)


2) config/
•	설정 관련 패키지
DBConfig.java: 데이터베이스 연결 설정을 담당(mySQL과 연결을 담당하는 코드입니다)
AppConfig.java: 공통 설정(앱 전체에 적용되는 설정) 담당


3) common/
공통 모듈(여러 곳에서 재사용되는 코드)

•	network/:
소켓 통신(서버/클라이언트) 관련 클래스
Server.java: 서버 소켓 구현
Client.java: 클라이언트 소켓 구현
ClientHandler.java: 클라이언트별 처리 담당
PacketRouter.java: 수신 메시지 라우팅(분배) 담당

• dto/:
통신용 DTO(Data Transfer Object, 직렬화용)
Packet.java: 패킷 데이터 구조
Request.java: 요청 데이터 구조
Response.java: 응답 데이터 구조

•	enums/:
상수 타입 분리(열거형)
CommandType.java: 명령 타입 정의
StatusType.java: 상태 타입 정의

•	util/:
유틸리티 클래스(암호화, 시간, 검증 등)
DBUtil.java: DB 관련 유틸리티
PasswordUtil.java: 비밀번호 암호화/검증
TimeUtil.java: 시간 관련 유틸리티


4) auth/
•	인증 관련 기능(로그인, 회원가입 등)
controller/:
UI 또는 소켓 명령 처리

service/:
실제 로직 처리

dao/:
DB 접근(데이터베이스와의 연동)

model/:
User, Session 등 인증 관련 데이터 구조(Entity class라고 생각하시면 편할 거 같아요)


5) friend/
•	친구 관리 기능
controller/:
친구 관련 UI/명령 처리

service/:
친구 관련 비즈니스 로직

dao/:
친구 관련 DB 접근

model/:
Friend, BlockedFriend 등 데이터 구조(entity class)


6) chatroom/
•	채팅방 및 메시지 관리

controller/:
채팅방 UI/명령 처리

service/:
채팅방/메시지 관련 로직

dao/:
채팅방/메시지 DB 접근

model/:
ChatRoom, Message, Thread 등 데이터 구조 


7) gui/
•	GUI(Swing 기반) 관련 코드

attendance/:
출퇴근 기능 관련 화면

auth/:
로그인/회원가입 화면

friend/:
친구 관리 화면(친구추가, 친구프로필, 1대1채팅방 등)

chatroom/:
채팅 화면(1:n대화방 생성, 그룹채팅방, 메뉴바 등)

common/:
메인 프레임(앱의 메인 윈도우) => 친구메인화면, 대화방메인화면, 설정메인화면

keyword/:
키워드 기반 우선 알림 기능 관련 화면

memo/:
메모기능 관련 화면

schedule/:
일정 등록 관련 화면

setting/:
설정 화면 (프로필 업데이트, 차단목록, 메모목록 화면)


8) DB/
•	DB 관련 스크립트 및 문서
•	schema.sql:
데이터베이스 테이블 생성 스크립트 (일단 java에 파일 만들어놓긴 했는데 mySQL에서 정의해놓으면 여기서는 삭제해도 될 거 같습니다)

## DB 설정
1. `config/DBConfig.example.java`를 `config/DBConfig.java`로 복사합니다.
2. `DBConfig.java`에서 DB_URL, USER, PASSWORD를 실제 값으로 수정합니다.
=>실제 DBConfig.java파일은 민감한 정보를 보호하기 위해 .gitignore파일에 넣었습니다.
