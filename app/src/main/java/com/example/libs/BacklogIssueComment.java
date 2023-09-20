package com.example.libs;

import java.util.Date;

public class BacklogIssueComment {
    public long id;
    public String content;
    public String createdUser;
    public Date created;
    public Date updated;

    private String urlPath = "api/v2/issues"; // FIXME: パス修正

    public BacklogIssueComment(long id, String content, String createdUser, Date created, Date updated) {
        this.id = id;
        this.content = content;
        this.createdUser = createdUser;
        this.created = created;
        this.updated = updated;
    }

    /**
     * 該当課題へのURLを取得
     * 
     * @return String
     */
    public String getUrl() {
        String spaceKey = "nulab-exam"; // FIXME: credential.jsonから取得

        String url = String.format("https://%s.backlog.jp/%s/%s", spaceKey, this.urlPath, this.id);
        return url;
    }

    public void print() {
        System.out.printf("View the full review : %s\n\n", getUrl());
        System.out.printf("%s %tc\n", this.createdUser, this.updated);
        System.out.println(this.content);
    }
}
