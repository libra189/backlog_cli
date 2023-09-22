package com.example.command;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;

public class InitTest {
    // テストで使用する一時ファイルのパス
    private final String testFilePath = "./credential.json";

    @AfterEach
    void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get(testFilePath));
    }

    @Test
    void testExec() {
        Init init = new Init();

        // 標準入力
        InputStream in = new ByteArrayInputStream("space_id\napi_key\nproject_key".getBytes());
        System.setIn(in);

        init.exec();

        // ファイルが正常に作成されたかを確認
        assertTrue(Files.exists(Paths.get(testFilePath)));
    }
}
