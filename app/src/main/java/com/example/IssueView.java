package com.example;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import com.example.libs.BacklogIssue;

public class IssueView {
    // Path to credential file
    private String credentialFilePath = "./credential.json";

    public void exec(long issueId, Boolean hasComments) {
        try {
            // 認証情報読み込み
            if (Files.notExists(Paths.get(credentialFilePath))) {
                throw new NoSuchFileException(credentialFilePath);
            }

            BacklogCredential credential = new BacklogCredential(credentialFilePath);
            Backlog client = new Backlog(credential);

            // 課題情報を取得
            BacklogIssue issue = client.fetchIssueInfo(issueId, hasComments);
            // for (BacklogIssueSummary i : client.fetchIssues(isFetchAllTasks)) {
            // System.out.printf("#%d: %s\n", i.id, i.summary);
            // }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
