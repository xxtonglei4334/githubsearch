package demo.github.tonglei.com.githubsearch;

import java.util.List;

import demo.github.tonglei.com.githubsearch.models.Repo;
import demo.github.tonglei.com.githubsearch.models.UserList;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by tonglei@tomoon.cn on 2017/4/27.
 */

public interface GitHubService {

    @GET("search/users")
    Call<UserList> searchUser(@Query("q") String keyword);

    @GET("users/{user}/repos")
    Call<List<Repo>> listRepo(@Path("user") String userName);
}
