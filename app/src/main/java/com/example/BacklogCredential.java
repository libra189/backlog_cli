package com.example;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class BacklogCredential {
    @JsonProperty("api_key")
    private String apiKey;

    @JsonProperty("space_id")
    private String spaceId;

    @JsonProperty("project_key")
    private String projectKey;

    BacklogCredential() {
    }

    BacklogCredential(String filePath) throws NoSuchFileException, IOException, StreamReadException, DatabindException {
        if (Files.notExists(Paths.get(filePath))) {
            throw new NoSuchFileException(filePath);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        BacklogCredential credential = objectMapper.readValue(new File(filePath), BacklogCredential.class);
        this.apiKey = credential.getApiKey();
        this.spaceId = credential.getSpaceId();
        this.projectKey = credential.getProjectKey();
    }

    BacklogCredential(String spaceId, String apiKey, String projectKey) {
        this.spaceId = spaceId;
        this.apiKey = apiKey;
        this.projectKey = projectKey;
    }

    // getter
    public String getApiKey() {
        return this.apiKey;
    }

    public String getSpaceId() {
        return this.spaceId;
    }

    public String getProjectKey() {
        return this.projectKey;
    }

    // setter
    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public void setSpaceId(String spaceId) {
        this.spaceId = spaceId;
    }

    /**
     * 認証情報をファイルにjson形式で書き込み
     * 
     * @param filePath jsonファイルへのパス
     */
    public void toJson(String filePath) throws IOException, StreamWriteException, DatabindException {
        ObjectMapper mapper = new ObjectMapper();
        mapper.enable(SerializationFeature.INDENT_OUTPUT);
        mapper.writeValue(new File(filePath), this);
    }

    /**
     * 認証情報をjsonファイルから読み込み
     * 
     * @param filePath jsonファイルへのパス
     */
    public void loadJson(String filePath) throws IOException, StreamWriteException, DatabindException {
        if (Files.notExists(Paths.get(filePath))) {
            throw new NoSuchFileException(filePath);
        }

        ObjectMapper objectMapper = new ObjectMapper();
        BacklogCredential credential = objectMapper.readValue(new File(filePath), BacklogCredential.class);
        this.apiKey = credential.getApiKey();
        this.spaceId = credential.getSpaceId();
    }
}
