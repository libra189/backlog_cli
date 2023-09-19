package com.example;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Init {
    // Path to credential file
    private String credentialFilePath = "./credential.json";

    public void exec() {
        try {
            // 既存認証情報
            BacklogCredential credential = null;

            if (Files.exists(Paths.get(credentialFilePath))) {
                // JSONファイルから認証情報を読み込み
                credential = new BacklogCredential(credentialFilePath);
            } else {
                credential = new BacklogCredential("", "", "");
            }

            // 標準入力受け取り
            // 既存認証情報があれば[]内に表示
            Scanner scanner = new Scanner(System.in);

            // スペースID入力
            System.out.printf("スペースID[%s]: ", credential.getSpaceId());
            String spaceId = scanner.nextLine();
            if (spaceId == "") {
                spaceId = credential.getSpaceId();
            }

            // APIキー入力
            System.out.printf("APIキー[%s]: ", credential.getApiKey());
            String apiKey = scanner.nextLine();
            if (apiKey == "") {
                apiKey = credential.getApiKey();
            }

            // プロジェクトキー入力
            System.out.printf("プロジェクトキー[%s]: ", credential.getProjectKey());
            String projectKey = scanner.nextLine();
            if (projectKey == "") {
                projectKey = credential.getProjectKey();
            }

            scanner.close();

            BacklogCredential newCredential = new BacklogCredential(spaceId, apiKey, projectKey);
            newCredential.toJson(credentialFilePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
