import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {

    private static final Path DATA_JSON_PATH = Path.of("db/wiseSaying/data.json");
    private static final Path LAST_ID_PATH = Path.of("db/wiseSaying/lastId.txt");

    @BeforeEach
    void setUp() throws IOException {
        TestUtil.clearDatabase();
    }

    @Test
    @DisplayName("1단계: 종료")
    void t1() throws IOException {
        String out = AppTestRunner.run("종료");
        assertThat(out).contains("== 명언 앱 ==");
    }

    @Test
    @DisplayName("2단계: 등록")
    void t2() throws IOException {
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                """);

        assertThat(out)
                .contains("명언 :")
                .contains("작가 :");
    }

    @Test
    @DisplayName("3단계: 등록시 생성된 명언번호 노출")
    void t3() throws IOException {
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                """);

        assertThat(out).contains("1번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("4단계: 등록할 때마다 생성되는 명언번호가 증가")
    void t4() throws IOException {
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                """);

        assertThat(out)
                .contains("1번 명언이 등록되었습니다.")
                .contains("2번 명언이 등록되었습니다.");
    }

    @Test
    @DisplayName("5단계: 목록")
    void t5() throws IOException {
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                """);

        assertThat(out)
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("6단계: 1번 명언 삭제")
    void t6() throws IOException {
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                삭제?id=1
                """);

        assertThat(out).contains("1번 명언이 삭제되었습니다.");
    }

    @Test
    @DisplayName("7단계: 존재하지 않는 명언 삭제에 대한 예외처리")
    void t7() throws IOException {
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                삭제?id=1
                삭제?id=1
                """);

        assertThat(out)
                .contains("1번 명언이 삭제되었습니다.")
                .contains("1번 명언은 존재하지 않습니다.");
    }

    @Test
    @DisplayName("8단계: 명언 수정")
    void t8() throws IOException {
        String out = AppTestRunner.run("""
                등록
                과거에 집착하지 마라.
                작자미상
                수정?id=3
                수정?id=1
                현재와 자신을 사랑하라.
                홍길동
                목록
                """);

        assertThat(out)
                .contains("3번 명언은 존재하지 않습니다.")
                .contains("명언(기존) : 과거에 집착하지 마라.")
                .contains("작가(기존) : 작자미상")
                .contains("1 / 홍길동 / 현재와 자신을 사랑하라.");
    }

    @Test
    @DisplayName("9단계: 파일을 통한 영속성")
    void t9() throws IOException {
        AppTestRunner.run("""
                등록
                명언 1
                작가 1
                등록
                명언 2
                작가 2
                빌드
                """);

        String json_str = Files.readString(DATA_JSON_PATH);

        assertThat(json_str)
                .contains("[")
                .contains("  {")
                .contains("    \"id\": 1")
                .contains("    \"content\": \"명언 1\"")
                .contains("    \"author\": \"작가 1\"")
                .contains("  },")
                .contains("  {")
                .contains("    \"id\": 2")
                .contains("    \"content\": \"명언 2\"")
                .contains("    \"author\": \"작가 2\"")
                .contains("  }")
                .contains("]");
    }

    @Test
    @DisplayName("10단계: data.json 빌드 및 불러오기 검증")
    void t10() throws IOException {
        String out1 = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                수정?id=1
                현재와 자신을 사랑하라.
                홍길동
                빌드
                """);

        assertThat(out1).contains("data.json 파일의 내용이 갱신되었습니다.");

        String out2 = AppTestRunner.run("목록");

        assertThat(out2)
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("1 / 홍길동 / 현재와 자신을 사랑하라.");
    }

    // 11, 12단계는 TDD 구현 관련.

    @Test
    @DisplayName("13단계: 검색")
    void t13() throws IOException {
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록?keywordType=content&keyword=과거
                목록?keywordType=author&keyword=작자
                """);

        assertThat(out)
                .contains("----------------------")
                .contains("검색 타입 : content")
                .contains("검색어 : 과거")
                .contains("----------------------")
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")

                .contains("----------------------")
                .contains("검색 타입 : author")
                .contains("검색어 : 작자")
                .contains("----------------------")
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }

    @Test
    @DisplayName("14단계: 페이징")
    void t14() throws IOException {
        Files.writeString(LAST_ID_PATH, Files.readString(
                Path.of("db/testDb/14tLastId.txt")));
        Files.writeString(DATA_JSON_PATH, Files.readString(
                Path.of("db/testDb/14t.json")));

        String out = AppTestRunner.run("""
                목록
                목록?page=2
                """);

        assertThat(out)
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("10 / 작자미상 10 / 명언 10")
                .contains("9 / 작자미상 9 / 명언 9")
                .contains("8 / 작자미상 8 / 명언 8")
                .contains("7 / 작자미상 7 / 명언 7")
                .contains("6 / 작자미상 6 / 명언 6")
                .contains("----------------------")
                .contains("페이지 : [1] / 2")

                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("5 / 작자미상 5 / 명언 5")
                .contains("4 / 작자미상 4 / 명언 4")
                .contains("3 / 작자미상 3 / 명언 3")
                .contains("2 / 작자미상 2 / 명언 2")
                .contains("1 / 작자미상 1 / 명언 1")
                .contains("----------------------")
                .contains("페이지 : 1 / [2]");
    }
}