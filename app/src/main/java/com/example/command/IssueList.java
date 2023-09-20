package com.example.command;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import com.example.Backlog;
import com.example.BacklogCredential;
import com.example.libs.BacklogIssueSummary;

public class IssueList {
    // Path to credential file
    private String credentialFilePath = "./credential.json";

    public void exec(Boolean isFetchAllTasks) {
        try {
            if (Files.notExists(Paths.get(credentialFilePath))) {
                throw new NoSuchFileException(credentialFilePath);
            }

            BacklogCredential credential = new BacklogCredential(credentialFilePath);
            Backlog client = new Backlog(credential);

            for (BacklogIssueSummary i : client.fetchIssues(isFetchAllTasks)) {
                System.out.printf("#%d: %s\n", i.id, i.summary);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
