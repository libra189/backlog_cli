package com.example.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.io.BufferedOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.Backlog;
import com.example.BacklogCredential;
import com.example.libs.BacklogIssue;
import com.example.libs.BacklogIssueComment;

import util.io.StandardOutputStream;

public class IssueViewTest {
    @Mock
    private BacklogCredential mockedCredential;

    @Mock
    private Backlog mockedBacklog;

    // @Mock
    // private BacklogIssueComment mockedBacklogIssueComment;

    private IssueView issueView;
    // private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    // private final PrintStream originalOut = System.out;

    private StandardOutputStream out = new StandardOutputStream();

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // モックの初期化
        issueView = new IssueView();
        // System.setOut(new PrintStream(outContent)); // 標準出力をキャプチャ

        System.setOut(out);
    }

    @AfterEach
    void tearDown() {
        // System.setOut(originalOut); // 標準出力を元に戻す

        System.setOut(new PrintStream(new BufferedOutputStream(new FileOutputStream(FileDescriptor.out), 128), true,
                StandardCharsets.UTF_8));
    }

    @Test
    void testExecWithCredentialFileExists() {
        try {
            // モックの設定
            when(mockedCredential.getApiKey()).thenReturn("your_api_key");
            when(mockedCredential.getSpaceId()).thenReturn("your_space_id");
            when(mockedCredential.getProjectKey()).thenReturn("your_project_key");

            // モックの振る舞いを設定
            Date now = new Date();
            Calendar dueDateCalendar = Calendar.getInstance();
            dueDateCalendar.setTime(now);

            dueDateCalendar.add(Calendar.MONTH, 3);
            Date dueDate = dueDateCalendar.getTime();

            BacklogIssue mockedIssue = new BacklogIssue(
                    123,
                    "Test issue #1",
                    "This issue is TEST.",
                    "status",
                    "priority",
                    "Test User #1",
                    now, dueDate,
                    now, now);
            when(mockedBacklog.fetchIssueInfo(123)).thenReturn(mockedIssue);

            Calendar createdCalendar = Calendar.getInstance();
            createdCalendar.setTime(now);

            createdCalendar.add(Calendar.DAY_OF_MONTH, 3);
            BacklogIssueComment mockedIssueComment = new BacklogIssueComment(234, 123, "Comment", "Test User #2",
                    createdCalendar.getTime(),
                    createdCalendar.getTime());
            when(mockedBacklog.fetchIssueComments(123)).thenReturn(Arrays.asList(mockedIssueComment));

            // テスト実行
            issueView.exec(123, false);

            // 標準出力の内容をキャプチャ
            String capturedOutput = out.readLine();

            // 期待される結果を検証
            String expectedStr = String.format("View this issue on Backlog: %s",
                    mockedIssueComment.getUrl("your_space_id"));
            assertEquals(expectedStr,
                    "View this issue on Backlog: https://your_space_id.backlog.jp/view/123#comment-234");
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}
