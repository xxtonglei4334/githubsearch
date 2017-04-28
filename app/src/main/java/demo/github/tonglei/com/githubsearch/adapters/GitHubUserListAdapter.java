package demo.github.tonglei.com.githubsearch.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import demo.github.tonglei.com.githubsearch.LanguageLoader;
import demo.github.tonglei.com.githubsearch.R;
import demo.github.tonglei.com.githubsearch.models.GitHubUser;
import demo.github.tonglei.com.githubsearch.models.Repo;

/**
 * Created by tonglei@tomoon.cn on 2017/4/27.
 */

public class GitHubUserListAdapter extends BaseAdapter {

    private Context mContext;
    private List<GitHubUser> mData;

    public GitHubUserListAdapter(Context context, List<GitHubUser> data) {
        mContext = context;
        mData = data;
    }


    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        bindItem(holder, mData.get(position));

        return convertView;
    }

    private void bindItem(ViewHolder holder, GitHubUser item) {
        Glide.with(mContext).load(item.getAvatar()).fitCenter().placeholder(R.mipmap.ic_launcher).into(holder.imageView);
        holder.userNameView.setText(item.getUserName());
        List<Repo> repos = item.getRepos();
        StringBuffer sb = new StringBuffer();
        if (repos == null) {
            // TODO: 2017/4/27 异步加载repo列表
            Log.i("test", "后台加载常用语言");
            LanguageLoader.load(item, holder.lauguageView);
        } else {
            Set<String> languageSet = new HashSet<>();
            for (Repo repo : repos) {
                Log.i("test", repo.toString());
                if (repo.getLanguage() != null && !languageSet.contains(repo.getLanguage())) {
                    languageSet.add(repo.getLanguage());
                }
            }

            for (String language:languageSet) {
                sb.append(language).append(" ");
            }
        }

        holder.lauguageView.setText(sb.toString());
    }

    public void resetData(List<GitHubUser> data) {
        mData = data;
        notifyDataSetChanged();
    }

    public void addData(List<GitHubUser> data) {
        if (mData == null) {
            mData = new ArrayList<>(data);
        } else {
            mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public class ViewHolder {
        private ImageView imageView;
        private TextView userNameView;
        private TextView lauguageView;

        ViewHolder(View itemView) {
            imageView = (ImageView) itemView.findViewById(R.id.imageview);
            userNameView = (TextView) itemView.findViewById(R.id.nameView);
            lauguageView = (TextView) itemView.findViewById(R.id.languageView);
        }
    }

}
