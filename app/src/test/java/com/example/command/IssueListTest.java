package com.example.command;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.example.Backlog;
import com.example.BacklogCredential;
import com.example.libs.BacklogIssueSummary;

import util.io.StandardOutputStream;

public class IssueListTest {

    private StandardOutputStream out = new StandardOutputStream();

    @Mock
    private BacklogCredential mockedCredential;

    @Mock
    private Backlog mockedBacklog;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this); // モックの初期化
        System.setOut(out);
    }

    @Test
    void testExecWithCredentialFileExists() {
        try {
            // モックの設定
            when(mockedCredential.getApiKey()).thenReturn("your_api_key");
            when(mockedCredential.getSpaceId()).thenReturn("your_space_id");
            when(mockedCredential.getProjectKey()).thenReturn("your_project_key");

            // モックの振る舞いを設定
            List<BacklogIssueSummary> mockIssues = new ArrayList<>();
            mockIssues.add(new BacklogIssueSummary(1, "Issue 1"));
            mockIssues.add(new BacklogIssueSummary(2, "Issue 2"));
            when(mockedBacklog.fetchIssues(true)).thenReturn(mockIssues);

            IssueList issueList = new IssueList();
            issueList.exec(false);

            // 標準出力の内容をキャプチャ
            String capturedOutput = out.readLine();

            // 期待される結果を検証
            assertEquals("#1: Issue1\n", capturedOutput);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
    }
}
