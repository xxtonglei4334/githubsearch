package demo.github.tonglei.com.githubsearch.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tonglei@tomoon.cn on 2017/4/27.
 */

public class GitHubUser {
    @SerializedName("id")
    private long id;
    @SerializedName("login")
    private String userName;
    @SerializedName("avatar_url")
    private String avatar;
    @SerializedName("subscriptions_url")
    private String subscriptions;
    private List<String> languages;
    private List<Repo> repos;

    public long getId() {
        return id;
    }

    public void setId(long mId) {
        id = mId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String mUserName) {
        userName = mUserName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String mAvatar) {
        avatar = mAvatar;
    }

    public String getSubscriptions() {
        return subscriptions;
    }

    public void setSubscriptions(String mSubscriptions) {
        subscriptions = mSubscriptions;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> mLanguages) {
        languages = mLanguages;
    }

    public List<Repo> getRepos() {
        return repos;
    }

    public void setRepos(List<Repo> mRepos) {
        repos = mRepos;
    }

    @Override
    public String toString() {
        return String.format("%s(%d)", userName, id);
    }
}
