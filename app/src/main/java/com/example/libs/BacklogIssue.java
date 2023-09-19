package com.example.libs;

public class BacklogIssue {
    public long id; // id
    public String summary; // summary タイトル
    public String description; // description 説明
    public String status; // status.name ステータス名
    public String priority; // priority.name 優先度名
    public String assignee; // assignee.name 担当者名
    public String startDate; // startDate 対応開始日時
    public String dueDate; // dueDate 締め切り日時
}
