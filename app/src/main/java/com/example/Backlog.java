package com.example;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.libs.BacklogIssue;
import com.nulabinc.backlog4j.BacklogClient;
import com.nulabinc.backlog4j.BacklogClientFactory;
import com.nulabinc.backlog4j.BacklogException;
import com.nulabinc.backlog4j.Issue;
import com.nulabinc.backlog4j.Project;
import com.nulabinc.backlog4j.ResponseList;
import com.nulabinc.backlog4j.User;
import com.nulabinc.backlog4j.api.option.GetIssuesParams;
import com.nulabinc.backlog4j.conf.BacklogConfigure;
import com.nulabinc.backlog4j.conf.BacklogJpConfigure;

public class Backlog {
    private BacklogConfigure configure;
    private BacklogClient client;
    private String projectKey;

    Backlog(BacklogCredential credential) throws MalformedURLException {
        this.configure = new BacklogJpConfigure(credential.getSpaceId()).apiKey(credential.getApiKey());
        this.client = new BacklogClientFactory(this.configure).newClient();
        this.projectKey = credential.getProjectKey();
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
     * @param isFetchAllTasks 全ユーザの課題を取得
     * @return List<BacklogIssue>
     */
    public List<BacklogIssue> fetchIssues(Boolean isFetchAllTasks) throws BacklogException {
        // パラメーターの設定
        List<Long> projectIds = Arrays.asList(this.fetchProjectId());
        GetIssuesParams params = new GetIssuesParams(projectIds);

        if (!isFetchAllTasks) {
            // 自身の課題のみ取得
            long myselfUserId = this.fetchMyselfId();
            params.assigneeIds(Arrays.asList(myselfUserId));
        }

        // 課題一覧取得
        ResponseList<Issue> issues = this.client.getIssues(params);

        // API仕様変更の差異を吸収するために値クラスに詰め替え
        List<BacklogIssue> retValues = new ArrayList<>();
        for (Issue i : issues) {
            BacklogIssue bi = new BacklogIssue(i.getId(), i.getSummary());
            retValues.add(bi);
        }

        return retValues;
    }
}
