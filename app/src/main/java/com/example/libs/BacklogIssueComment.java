package com.example.libs;

import java.util.Date;

public class BacklogIssueComment {
    public long id;
    public long issueId;
    public String content;
    public String createdUser;
    public Date created;
    public Date updated;

    private String urlFormat = "https://%s.backlog.jp/%s/%d#comment-%d";

    public BacklogIssueComment(long id, long issueId, String content, String createdUser, Date created, Date updated) {
        this.id = id;
        this.issueId = issueId;
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
    public String getUrl(String spaceId) {
        return String.format(urlFormat, spaceId, "view", this.issueId, this.id);
    }

    public void print(String spaceId) {
        System.out.printf("View the full review : %s\n\n", getUrl(spaceId));
        System.out.printf("%s %tc\n", this.createdUser, this.updated);
        System.out.println(this.content);
    }
}
