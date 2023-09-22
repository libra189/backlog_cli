package com.example.libs;

import java.util.Date;

public class BacklogIssue {
    public long id; // id
    public String summary; // タイトル
    public String description; // 説明
    public String status; // ステータス名
    public String priority; // 優先度名
    public String assignee; // 担当者名
    public Date startDate; // 対応開始日時
    public Date dueDate; // 締め切り日時
    public Date created;
    public Date updated;

    private String urlFormat = "https://%s.backlog.jp/view/%s";

    public BacklogIssue() {
    }

    public BacklogIssue(
            long id,
            String summary,
            String description,
            String status,
            String priority,
            String assignee,
            Date startDate,
            Date dueDate,
            Date created,
            Date updated) {
        this.id = id;
        this.summary = summary;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.assignee = assignee;
        this.startDate = startDate;
        this.dueDate = dueDate;
        this.created = created;
        this.updated = updated;
    }

    /**
     * 該当課題へのURLを取得
     * 
     * @return String
     */
    public String getUrl(String spaceId) {
        return String.format(urlFormat, spaceId, this.id);
    }

    /**
     * 内容を表示
     */
    public void print(String spaceId) {
        System.out.printf("View this issue on Backlog: %s\n\n", getUrl(spaceId));
        System.out.println(this.summary);
        System.out.printf("%s %s %tc\n\n", this.status, this.assignee, this.updated);
        System.out.println(this.description);
    }

    /**
     * 1行{length}文字に整形して内容を表示
     * 
     * @param length
     */
    // public void print(int length) {
    // List<String> strs = new ArrayList<>();
    // for (int i = 0; i < StringUtils.length(this.description); i += length) {
    // strs.add(StringUtils.substring(this.description, i, i + length));
    // }

    // System.out.printf(this.summary);
    // System.out.printf("assigned for %s\n\n", this.assignee);
    // for (String s : strs) {
    // System.out.printf(" %s\n", s);
    // }
    // System.out.printf("View this issue on Backlog: %s\n", getUrl());
    // }
}
