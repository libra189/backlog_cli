package com.example;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import com.example.libs.BacklogIssue;

public class IssueList {
    // Path to credential file
    private String credentialFilePath = "./credential.json";

    public void exec() {
        try {
            if (Files.notExists(Paths.get(credentialFilePath))) {
                throw new NoSuchFileException(credentialFilePath);
            }

            BacklogCredential credential = new BacklogCredential(credentialFilePath);
            Backlog client = new Backlog(credential);

            Boolean hasMyself = true;
            for (BacklogIssue i : client.fetchIssues(hasMyself)) {
                System.out.printf("#%d: %s\n", i.id, i.summary);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
