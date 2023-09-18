package com.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.util.Scanner;

import com.fasterxml.jackson.databind.ObjectMapper;

public class Init {
    public void exec() {
        System.out.println("run init exec");
        
        // 認証情報ファイル読み込み

        // Path to credential file
        String credential_file_path = "./credential.json";
        Path file_path = Paths.get(credential_file_path);

        // ObjectMapperのインスタンスを作成
        ObjectMapper objectMapper = new ObjectMapper();

        try {
            // 既存認証情報
            BacklogCredential credential = null;

            if (Files.exists(file_path)){
                // JSONファイルをJavaオブジェクトにマッピング
                credential = objectMapper.readValue(new File(credential_file_path), BacklogCredential.class);
            } else {
                credential = new BacklogCredential("", "");
            }

            // 標準入力受け取り
            // 既存認証情報があれば[]内に表示
            Scanner scanner = new Scanner(System.in);

            // スペースID入力
            System.out.printf("スペースID[%s]: ", credential.getSpaceId());
            String space_id = scanner.nextLine();
            if (space_id == "") {
                space_id = credential.getSpaceId();
            }

            // APIキー入力
            System.out.printf("APIキー[%s]: ", credential.getApiKey());
            String api_key = scanner.nextLine();
            if (api_key == "") {
                api_key = credential.getApiKey();
            }

            scanner.close();
 
            BacklogCredential newCredential = new BacklogCredential(space_id, api_key);
            newCredential.toJson(credential_file_path);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
