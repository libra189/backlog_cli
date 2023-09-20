package com.example;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.libs.BacklogIssue;
import com.example.libs.BacklogIssueComment;
import com.example.libs.BacklogIssueSummary;
import com.nulabinc.backlog4j.BacklogClient;
import com.nulabinc.backlog4j.BacklogClientFactory;
import com.nulabinc.backlog4j.BacklogException;
import com.nulabinc.backlog4j.Issue;
import com.nulabinc.backlog4j.IssueComment;
import com.nulabinc.backlog4j.Project;
import com.nulabinc.backlog4j.ResponseList;
import com.nulabinc.backlog4j.User;
import com.nulabinc.backlog4j.api.option.GetIssuesParams;
import com.nulabinc.backlog4j.conf.BacklogConfigure;
import com.nulabinc.backlog4j.conf.BacklogJpConfigure;

/**
 * Backlog APIライブラリを利用した通信処理
 * APIの仕様変更時のコード修正範囲を限定するためにサブコマンド実行処理と分離
 */
public class Backlog {
    private BacklogConfigure configure;
    private BacklogClient client;

    public String projectKey;
    public String spaceId;

    public Backlog(BacklogCredential credential) throws MalformedURLException {
        this.configure = new BacklogJpConfigure(credential.getSpaceId()).apiKey(credential.getApiKey());
        this.client = new BacklogClientFactory(this.configure).newClient();
        this.projectKey = credential.getProjectKey();
        this.spaceId = credential.getSpaceId();
    }

    /**
     * 実行ユーザのユーザIDを取得
     * 
     * @return String
     */
    public long fetchMyselfId() throws BacklogException {
        User myself = this.client.getMyself();
        return myself.getId();
    }

    /**
     * プロジェクトのIDを取得
     * 
     * @return long
     */
    public long fetchProjectId() throws BacklogException {
        Project project = this.client.getProject(this.projectKey);
        return project.getId();
    }

    /**
     * 課題の一覧を取得
     * 
     * @param hasFetchAll 全ユーザの課題を取得
     * @return List<BacklogIssue>
     */
    public List<BacklogIssueSummary> fetchIssues(Boolean hasFetchAll) throws BacklogException {
        // パラメーターの設定
        List<Long> projectIds = Arrays.asList(this.fetchProjectId());
        GetIssuesParams params = new GetIssuesParams(projectIds);

        if (!hasFetchAll) {
            // 自身の課題のみ取得
            long myselfUserId = this.fetchMyselfId();
            params.assigneeIds(Arrays.asList(myselfUserId));
        }

        // 課題一覧取得
        ResponseList<Issue> issues = this.client.getIssues(params);

        // API仕様変更の差異を吸収するために値クラスに詰め替え
        List<BacklogIssueSummary> retValues = new ArrayList<>();
        for (Issue i : issues) {
            BacklogIssueSummary bi = new BacklogIssueSummary(i.getId(), i.getSummary());
            retValues.add(bi);
        }

        return retValues;
    }

    /**
     * 課題情報の取得
     * 
     * @param issueId 課題のID
     * @return BacklogIssue
     * @throws BacklogException
     */
    public BacklogIssue fetchIssueInfo(long issueId) throws BacklogException {
        Issue issue = this.client.getIssue(issueId);

        BacklogIssue bi = new BacklogIssue(
                issue.getId(),
                issue.getSummary(),
                issue.getDescription(),
                issue.getStatus().getName(),
                issue.getPriority().getName(),
                issue.getAssignee().getName(),
                issue.getStartDate(),
                issue.getDueDate(),
                issue.getCreated(),
                issue.getUpdated());
        return bi;
    }

    /**
     * 課題に対するコメントを取得
     * 
     * @param issueId 課題のID
     * @return List<BacklogIssueComment>
     * @throws BacklogException
     */
    public List<BacklogIssueComment> fetchIssueComments(long issueId) throws BacklogException {
        ResponseList<IssueComment> comments = this.client.getIssueComments(issueId);

        List<BacklogIssueComment> bics = new ArrayList<>();
        for (IssueComment ic : comments) {
            if (ic.getContent() == null)
                continue;

            BacklogIssueComment bic = new BacklogIssueComment(
                    ic.getId(),
                    issueId,
                    ic.getContent(),
                    ic.getCreatedUser().getName(),
                    ic.getCreated(),
                    ic.getUpdated());
            bics.add(bic);
        }

        return bics;
    }
}
