package demo.github.tonglei.com.githubsearch.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by tonglei@tomoon.cn on 2017/4/27.
 */

public class UserList {
    @SerializedName("total_count")
    private int totalCnt;
    @SerializedName("items")
    private List<GitHubUser> items;

    public int getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(int mTotalCnt) {
        totalCnt = mTotalCnt;
    }

    public List<GitHubUser> getItems() {
        return items;
    }

    public void setItems(List<GitHubUser> mItems) {
        items = mItems;
    }
}
