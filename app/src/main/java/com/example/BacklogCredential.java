package com.example;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BacklogCredential {
    @JsonProperty("api_key")
    private String apiKey;

    @JsonProperty("space_id")
    private String spaceId;

    // public String organiztion_name;

    BacklogCredential() {}

    BacklogCredential(String spaceId, String apiKey) {
        this.spaceId = spaceId;
        this.apiKey = apiKey;
    }

    // getter
    public String getApiKey() {
        return apiKey;
    }

    public String getSpaceId() {
        return spaceId;
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
     */
    public void toJson(String file_path) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(new File(file_path), this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
