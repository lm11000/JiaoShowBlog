package feng.jiaoshoublog_bate10.entity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import feng.jiaoshoublog_bate10.adapter.FragmentListAdapter;
import feng.jiaoshoublog_bate10.R;
import feng.jiaoshoublog_bate10.SkipActivity;

/**
 * Created by feng on 2016/6/30.
 */
public class FragmentAll extends Fragment implements Handler.Callback,AdapterView.OnItemClickListener {
    private static final int INTERNET = 1;
    @BindView(R.id.fragmentList)
    ListView fragmentList;
    List<Article> articleList;
    int page = 1;
    Internet internet = new Internet();
    String jsonArray;
    Gson gson = new Gson();
    String type;
    Handler handler;

    public void setType(String type) {
        this.type = type;
    }

    public void getFragmentAll() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String str = internet.getRun("http://162.243.234.73:80/api/v1/articles?article_type=" + type + "&page=" + page);
                    Message message = handler.obtainMessage();
                    message.obj = str;
                    message.what = INTERNET;
                    handler.sendMessage(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View all = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this,all);
        handler = new Handler(this);
        getFragmentAll();
        fragmentList.setOnItemClickListener(this);
        return all;
    }
    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what != INTERNET) {
            return false;
        }
        jsonArray = msg.obj.toString();
        articleList = gson.fromJson(jsonArray, new TypeToken<List<Article>>() {
        }.getType());
        fragmentList.setAdapter(new FragmentListAdapter(articleList));
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent intent=new Intent(parent.getContext(), SkipActivity.class);
        intent.putExtra("article",articleList.get(position));
        startActivity(intent);
    }
}


