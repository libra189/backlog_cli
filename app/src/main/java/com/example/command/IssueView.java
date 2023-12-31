package com.example.command;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import com.example.Backlog;
import com.example.BacklogCredential;
import com.example.libs.BacklogIssue;
import com.example.libs.BacklogIssueComment;

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
            BacklogIssue issue = client.fetchIssueInfo(issueId);
            issue.print(client.spaceId);

            if (hasComments) {
                // コメントを表示
                System.out.println("");
                for (BacklogIssueComment c : client.fetchIssueComments(issueId)) {
                    c.print(client.spaceId);
                    System.out.println("");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
