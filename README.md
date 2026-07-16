# [BE14] 명언 게시판(자바) 13~14단계 제출

## 구현 필요 기능

### 13단계 : 검색

```java
== 명언 앱 ==
명령) 등록
명언 : 현재를 사랑하라.
작가 : 작자미상
1번 명언이 등록되었습니다.
명령) 등록
명언 : 과거에 집착하지 마라.
작가 : 작자미상
2번 명언이 등록되었습니다.
명령) 목록?keywordType=content&keyword=과거
----------------------
검색타입 : content
검색어 : 과거
----------------------
번호 / 작가 / 명언
----------------------
2 / 작자미상 / 과거에 집착하지 마라.
명령) 목록?keywordType=author&keyword=작자
----------------------
검색타입 : author
검색어 : 작자
----------------------
번호 / 작가 / 명언
----------------------
2 / 작자미상 / 과거에 집착하지 마라.
1 / 작자미상 / 현재를 사랑하라.
명령) 종료
```

### 14단계 : 페이징

* 모든 명언이 한번에 보여지지 않고 페이징됨.
* 샘플 데이터 명언은 10개 생성.
* 한 페이지 당 최대 5개의 명언이 출력.
* 명령 입력시 페이지 수를 생략하면 페이지 1로 간주.
* 내림차순 정렬로 최신글이 가장 위로 와야함.
* 검색어를 적용시키면 검색어가 반영된 목록이 보여져야 함.

```java
== 명언 앱 ==
명령) 목록
번호 / 작가 / 명언
----------------------
10 / 작자미상 10 / 명언 10
9 / 작자미상 9 / 명언 9
8 / 작자미상 8 / 명언 8
7 / 작자미상 7 / 명언 7
6 / 작자미상 6 / 명언 6
----------------------
페이지 : [1] / 2
명령) 목록?page=2
번호 / 작가 / 명언
----------------------
5 / 작자미상 5 / 명언 5
4 / 작자미상 4 / 명언 4
3 / 작자미상 3 / 명언 3
2 / 작자미상 2 / 명언 2
1 / 작자미상 1 / 명언 1
----------------------
페이지 : 1 / [2]
명령) 종료
=======
# [BE14] 명언 게시판(자바) 11~12단계 제출

## 추천 구조 정리(11단계 기준)

* `com.ll.wiseSaying.Main`: App 실행.
* `com.ll.wiseSaying.App`: 사용자의 입력을 받고, 
    그것이 WiseSayingController에게 넘겨야 하는지 판단,
    맞으면 메서드를 호출(인자와 함께).
* `com.ll.wiseSaying.WiseSayingController`:
    고객의 명령을 입력받고, 명령이 적절한지 응답을 표현.
* `com.ll.wiseSaying.WiseSayingService`:
    순수 비즈니스 로직. *(스캐너 및 출력 금지!)*
* `com.ll.wiseSaying.WiseSayingRepository`:
    데이터의 조회/수정/삭제/생성을 담당. *(스캐너 및 출력 금지!)*
* `com.ll.wiseSaying.WiseSaying`:
    명언 객체(번호/내용/작가)

## 12단계 필요 구현

* TDD 적용(JUnit 사용)
* 표준입출력 리다이렉팅
* 통합 테스트만 진행, 단위 테스트는 생략 가능.
* 각 명령어별 테스트만 진행하면 OK.

## 테스트 예시

```java
public class WiseSayingControllerTest {
    @BeforeEach
    void beforeEach() {
        AppTest.clear();
    }

    @Test
    @DisplayName("등록")
    void t3() {
        final String out = AppTest.run("""
                등록
                현재를 사랑하라.
                작자미상
                """);

        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.");
    }
    
    // ...
}
```

## TestUtil

```java
import java.io.*;
import java.util.Scanner;

public class TestUtil {
    // gen == generate 생성하다.
    // 테스트용 스캐너 생성
    public static Scanner genScanner(final String input) {
        final InputStream in = new ByteArrayInputStream(input.getBytes());

        return new Scanner(in);
    }

    // System.out의 출력을 스트림으로 받기
    public static ByteArrayOutputStream setOutToByteArray() {
        final ByteArrayOutputStream output = new ByteArrayOutputStream();
        System.setOut(new PrintStream(output));

        return output;
    }

    // setOutToByteArray 함수의 사용을 완료한 후 정리하는 함수, 출력을 다시 정상화 하는 함수
    public static void clearSetOutToByteArray(final ByteArrayOutputStream output) {
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        try {
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
```

## JUnit 사용법

```java
package com.ll;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AppTest {
    @Test
    public void 더하기() {
        assertEquals(30, 30);
    }
}
```
