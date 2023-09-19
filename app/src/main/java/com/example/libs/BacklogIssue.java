package com.example.libs;

import java.util.Date;
import java.util.List;

public class BacklogIssue {
    public long id; // id
    public String summary; // タイトル
    public String description; // 説明
    public String status; // ステータス名
    public String priority; // 優先度名
    public String assignee; // 担当者名
    public Date startDate; // 対応開始日時
    public Date dueDate; // 締め切り日時
    public List<BacklogIssueComment> comments;

    public BacklogIssue(
        long id,
        String summary,
        String description,
        String status,
        String priority,
        String assignee,
        Date startDate,
        Date dueDate
    ) {
        this.id = id;
        this.summary = summary;
        this.description = description;
        this.status = status;
        this.priority = priority;
        this.assignee = assignee;
        this.startDate = startDate;
        this.dueDate = dueDate;
    }
}
