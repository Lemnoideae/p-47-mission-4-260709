import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.assertj.core.api.Assertions.assertThat;

public class AppTest {
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
        TestUtil.clearDatabase();
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                1번 명언이 등록되었습니다.
                """);
        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.");
    }
    @Test
    @DisplayName("4단계: 등록할 때마다 생성되는 명언번호가 증가")
    void t4() throws IOException {
        TestUtil.clearDatabase();
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                """);
        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.")
                .contains("명언 :")
                .contains("작가 :")
                .contains("2번 명언이 등록되었습니다.");
    }
    @Test
    @DisplayName("5단계: 목록")
    void t5() throws IOException {
        TestUtil.clearDatabase();
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
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.")
                .contains("명언 :")
                .contains("작가 :")
                .contains("2번 명언이 등록되었습니다.")
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.");
    }
    @Test
    @DisplayName("6단계: 1번 명언 삭제")
    void t6() throws IOException {
        TestUtil.clearDatabase();
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                삭제?id=1
                """);
        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.")
                .contains("명언 :")
                .contains("작가 :")
                .contains("2번 명언이 등록되었습니다.")
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.")
                .contains("1번 명언이 삭제되었습니다.");
    }
    @Test
    @DisplayName("7단계: 존재하지 않는 명언 삭제에 대한 예외처리")
    void t7() throws IOException {
        TestUtil.clearDatabase();
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                삭제?id=1
                삭제?id=1
                """);
        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.")
                .contains("명언 :")
                .contains("작가 :")
                .contains("2번 명언이 등록되었습니다.")
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.")
                .contains("1번 명언이 삭제되었습니다.")
                .contains("1번 명언은 존재하지 않습니다.");
    }
    @Test
    @DisplayName("8단계: 명언 수정")
    void t8() throws IOException {
        TestUtil.clearDatabase();
        String out = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                삭제?id=1
                삭제?id=1
                수정?id=3
                수정?id=2
                현재와 자신을 사랑하라.
                홍길동
                목록
                """);
        assertThat(out)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.")
                .contains("명언 :")
                .contains("작가 :")
                .contains("2번 명언이 등록되었습니다.")
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.")
                .contains("1번 명언이 삭제되었습니다.")
                .contains("1번 명언은 존재하지 않습니다.")
                .contains("3번 명언은 존재하지 않습니다.")
                .contains("명언(기존) : 과거에 집착하지 마라.")
                .contains("명언 :")
                .contains("작가(기존) : 작자미상")
                .contains("작가 :")
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("2 / 홍길동 / 현재와 자신을 사랑하라.")
        ;
    }
    @Test
    @DisplayName("9단계: 파일을 통한 영속성")
    void t9() throws IOException {
        TestUtil.clearDatabase();
        AppTestRunner.run("""
                등록
                명언 1
                작가 1
                등록
                명언 2
                작가 2
                빌드
                """);

        Path json_data = Path.of("db/wiseSaying/data.json");
        String json_str = Files.readString(json_data);

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
    @DisplayName("10단계: data.json 빌드")
    void t10() throws IOException {
        TestUtil.clearDatabase();
        String out1 = AppTestRunner.run("""
                등록
                현재를 사랑하라.
                작자미상
                등록
                과거에 집착하지 마라.
                작자미상
                목록
                삭제?id=1
                삭제?id=1
                수정?id=2
                현재와 자신을 사랑하라.
                홍길동
                목록
                빌드
                """);
        assertThat(out1)
                .contains("명언 :")
                .contains("작가 :")
                .contains("1번 명언이 등록되었습니다.")
                .contains("명언 :")
                .contains("작가 :")
                .contains("2번 명언이 등록되었습니다.")
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("2 / 작자미상 / 과거에 집착하지 마라.")
                .contains("1 / 작자미상 / 현재를 사랑하라.")
                .contains("1번 명언이 삭제되었습니다.")
                .contains("1번 명언은 존재하지 않습니다.")
                .contains("명언(기존) : 과거에 집착하지 마라.")
                .contains("명언 :")
                .contains("작가(기존) : 작자미상")
                .contains("작가 :")
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("2 / 홍길동 / 현재와 자신을 사랑하라.")
                .contains("data.json 파일의 내용이 갱신되었습니다.");

        // 종료 후 다시 시작.

        String out2 = AppTestRunner.run("목록");
        assertThat(out2)
                .contains("번호 / 작가 / 명언")
                .contains("----------------------")
                .contains("2 / 홍길동 / 현재와 자신을 사랑하라.");
        TestUtil.clearDatabase();
    }
}