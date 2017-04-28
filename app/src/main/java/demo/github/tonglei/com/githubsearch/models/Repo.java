package demo.github.tonglei.com.githubsearch.models;

/**
 * Created by tonglei@tomoon.cn on 2017/4/27.
 */

public class Repo {
    public int id;
    private String language;

    public int getId() {
        return id;
    }

    public void setId(int mId) {
        id = mId;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String mLanguage) {
        language = mLanguage;
    }

    @Override
    public String toString() {
        return String.format("repo:%d(%s)", id, language);
    }
}
