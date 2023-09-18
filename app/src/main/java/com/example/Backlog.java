package com.example;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.example.libs.BacklogIssue;
import com.nulabinc.backlog4j.BacklogClient;
import com.nulabinc.backlog4j.BacklogClientFactory;
import com.nulabinc.backlog4j.Issue;
import com.nulabinc.backlog4j.ResponseList;
import com.nulabinc.backlog4j.User;
import com.nulabinc.backlog4j.api.option.GetIssuesParams;
import com.nulabinc.backlog4j.conf.BacklogConfigure;
import com.nulabinc.backlog4j.conf.BacklogJpConfigure;

public class Backlog {
    private BacklogConfigure configure;
    private BacklogClient client;

    Backlog(BacklogCredential credential) throws MalformedURLException {
        this.configure = new BacklogJpConfigure(credential.getSpaceId()).apiKey(credential.getApiKey());
        this.client = new BacklogClientFactory(this.configure).newClient();
    }

    public String fetchMyselfId() {
        User myself = this.client.getMyself();
        return myself.getUserId();
    }

    /**
     * 課題の一覧を取得
     * @return
     */
    public List<BacklogIssue> fetchIssues(Boolean hasMyself) {
        // TODO プロジェクトID取得処理
        String projectId = "191696";

        // 課題一覧取得
        List<String> projectIds = Arrays.asList(projectId);
        GetIssuesParams params = new GetIssuesParams(projectIds);
        ResponseList<Issue> issues = this.client.getIssues(params);

        // API仕様変更の差異を吸収するために値クラスに詰め替え
        List<BacklogIssue> retValues = new ArrayList<>();
        for (Issue i: issues) {
            BacklogIssue bi = new BacklogIssue(i.getId(), i.getSummary());
            retValues.add(bi);
        }
        
        return retValues;
    }
}
