package demo.github.tonglei.com.githubsearch;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import demo.github.tonglei.com.githubsearch.adapters.GitHubUserListAdapter;
import demo.github.tonglei.com.githubsearch.models.UserList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView mListView;
    private EditText mSearchEditText;
    private Button mSearchButton;
    private GitHubUserListAdapter mAdapter;

    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();
    GitHubService service = retrofit.create(GitHubService.class);

    private Call mSearchCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mSearchEditText = (EditText) findViewById(R.id.search_edit);
        mSearchButton = (Button) findViewById(R.id.search_button);
        mListView = (ListView) findViewById(R.id.search_listview);
        mSearchButton.setOnClickListener(this);
        mAdapter = new GitHubUserListAdapter(this, null);
        mListView.setAdapter(mAdapter);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.search_button) {
            handleSearchClick();
        }
    }

    private void handleSearchClick() {
        String keyword = mSearchEditText.getText().toString().trim();
        if (TextUtils.isEmpty(keyword)) {
            toastError("请输入查询条件");
            return;
        }

        // clear list
        clearResult();

        // do http request
        Call<UserList> call = service.searchUser(keyword);
        mSearchCall = call;
        call.enqueue(new Callback<UserList>() {
            @Override
            public void onResponse(Call<UserList> call, Response<UserList> response) {
                if (response.isSuccessful()) {
                    UserList data = response.body();
                    mAdapter.addData(data.getItems());
                } else {
                    toastError("error:" + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<UserList> call, Throwable t) {
                toastError("搜索失败,请重试");
            }
        });
    }

    private void toastError(String msg) {
        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
    }

    private void clearResult() {
        mAdapter.resetData(null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mSearchCall != null) {
            mSearchCall.cancel();
            mSearchCall = null;
        }
        LanguageLoader.cancelAll();
    }
}
