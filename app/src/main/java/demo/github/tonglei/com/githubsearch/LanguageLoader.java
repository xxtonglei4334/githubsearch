package demo.github.tonglei.com.githubsearch;

import android.text.TextUtils;
import android.util.Log;
import android.widget.TextView;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import demo.github.tonglei.com.githubsearch.models.GitHubUser;
import demo.github.tonglei.com.githubsearch.models.Repo;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by tonglei@tomoon.cn on 2017/4/27.
 */

public class LanguageLoader {

    private static Map<GitHubUser, Call> calls = new HashMap<>();

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    private static GitHubService service = retrofit.create(GitHubService.class);

    public static void load(GitHubUser user, final TextView textView) {
        textView.setTag(user);
        Call<List<Repo>> call = calls.get(user);
        if (call == null) {
            Log.i("test", "LanguageLoader.laod() start load");
            call = service.listRepo(user.getUserName());
            call.enqueue(new LoadReposCallback(user, textView));
        }
    }

    public static class LoadReposCallback implements Callback<List<Repo>> {
        private GitHubUser mUser;
        private TextView mTextView;

        LoadReposCallback(GitHubUser user, TextView textview) {
            this.mUser = user;
            this.mTextView = textview;
        }

        @Override
        public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
            if (response.isSuccessful()) {
                List<Repo> repos = response.body();
                mUser.setRepos(repos);
                if (mUser.equals(mTextView.getTag())) {
                    Set<String> languageSet = new HashSet<>();
                    for (Repo repo : repos) {
                        Log.i("test", repo.toString());
                        if (repo.getLanguage() != null && !languageSet.contains(repo.getLanguage())) {
                            languageSet.add(repo.getLanguage());
                        }
                    }

                    StringBuffer sb = new StringBuffer();
                    for (String language:languageSet) {
                        sb.append(language).append(" ");
                    }

                    Log.i("test", String.format("load language ok, (%s,%s)", mUser.getUserName(), sb.toString()));
                    mTextView.setText(sb.toString());
                }
            }
        }

        @Override
        public void onFailure(Call<List<Repo>> call, Throwable t) {
            Log.e("test", "load language failed", t);
        }
    }

    public synchronized static void cancelAll() {
        for (Map.Entry<GitHubUser, Call> entry : calls.entrySet()) {
            Call call = entry.getValue();
            if (!call.isCanceled()) {
                call.cancel();
            }
        }

        calls.clear();
    }

}
