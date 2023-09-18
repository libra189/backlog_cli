package com.example;

import java.io.File;
import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BacklogCredential {
    @JsonProperty("api_key")
    public String api_key;
    
    @JsonProperty("space_id")
    public String space_id;

    // public String organiztion_name;

    BacklogCredential(String space_id, String api_key) {
        this.space_id = space_id;
        this.api_key = api_key;
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
