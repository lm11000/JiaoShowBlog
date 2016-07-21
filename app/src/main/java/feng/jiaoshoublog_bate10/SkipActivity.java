package feng.jiaoshoublog_bate10;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import feng.jiaoshoublog_bate10.adapter.FootViewCommentAdapter;
import feng.jiaoshoublog_bate10.adapter.SkipListAdapter;
import feng.jiaoshoublog_bate10.entity.Article;
import feng.jiaoshoublog_bate10.entity.Comment;
import feng.jiaoshoublog_bate10.entity.Internet;

/**
 * Created by feng on 2016/6/30.
 */
public class SkipActivity extends AppCompatActivity {

    Internet internet = new Internet();
    int page = 1;
    @BindView(R.id.skipList)
    ListView skipList;
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    Gson gson = new Gson();
    List<Comment> comments;
    private FootHolder footHolder;
    private FootViewCommentAdapter footViewCommentAdapter;
    private Article article;
    private List<Comment> commentsMore;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_skip);
        ButterKnife.bind(this);
        article = (Article) getIntent().getSerializableExtra("article");
        toolbar.setTitle(article.title);
        setSupportActionBar(toolbar);
        //headView的内容设置
        //将listHead给Inflater出来
        View head = LayoutInflater.from(this).inflate(R.layout.listhead_skip, skipList, false);
        final HeadHolder headHolder = new HeadHolder(head);
        final String pictureUrl = article.cover;
        String headTitleString = article.title;
        skipList.addHeaderView(head);
        //如果图片是no_pic的话，则进行另一种风格的显示
        if (Objects.equals(pictureUrl, "/image/no_pic.jpg")) {
            //setVisibility可以让控件可见或者不可见
            headHolder.headTitle.setBackgroundColor(Color.WHITE);
            headHolder.headTitle.setTextColor(Color.BLACK);
            headHolder.headPicture.setVisibility(View.GONE);
        }
        Picasso.with(this).load("http://162.243.234.73/" + pictureUrl)
                .into(headHolder.headPicture);
        headHolder.headTitle.setText(headTitleString);
        //footView的内容设置
        //将listFoot给Inflater出来
        View foot = LayoutInflater.from(this).inflate(R.layout.listfoot_skip, skipList, false);
        footHolder = new FootHolder(foot);
        skipList.addFooterView(foot);
    }
    /**
     * 为了能够即时刷新留言（），使用resume，使得每次从撰写留言界面回来以后都可以进行一次get请求，所以把get请求些在resume方法里。\
     * 因为仅仅是留言部分刷新，所以仅让留言部分的代码写在这里就行了.
     */
    @Override
    protected void onResume() {
        super.onResume();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    final String jsonArray = internet.getRun("http://162.243.234.73:80/api/v1/leave_messages?article_id="
                            + article.id + "&page=" + page);
                    comments = gson.fromJson(jsonArray, new TypeToken<List<Comment>>() {
                    }.getType());
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            footViewCommentAdapter = new FootViewCommentAdapter();
                            footViewCommentAdapter.setComments(comments);
                            footHolder.comment.removeAllViews();
                            for (int i = 0; i < comments.size(); i++) {
                                footHolder.comment.addView(footViewCommentAdapter.getView(i, footHolder.comment));
                            }
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
// 这个方法设定了当点击more按钮的时候，再次去进行一次get请求。而这次get请求将page+1
        footHolder.more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            page++;
                            final String jsonArrayMore = internet.getRun("http://162.243.234.73:80/api/v1/leave_messages?article_id="
                                    + article.id + "&page=" + page);
                            commentsMore = gson.fromJson(jsonArrayMore, new TypeToken<List<Comment>>() {
                            }.getType());
                            if (commentsMore.size()==0){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(SkipActivity.this, "无更多留言", Toast.LENGTH_SHORT).show();

                                    }
                                });
                                return;
                            }
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    for (int i1 = 0; i1 < commentsMore.size(); i1++) {
                                        footViewCommentAdapter.setComments(commentsMore);
                                        footHolder.comment.addView(footViewCommentAdapter.getView(i1, footHolder.comment));
                                }
                                }
                            });
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
        //点击撰写留言按钮时
        footHolder.write.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SkipActivity.this, WriteActivity.class);
                intent.putExtra("id", article.id);
                startActivity(intent);
            }
        });
        skipList.setAdapter(new SkipListAdapter(article));
    }

    class HeadHolder {
        @BindView(R.id.headTitle)
        TextView headTitle;
        @BindView(R.id.headPicture)
        ImageView headPicture;
        @BindView(R.id.footLine)
        View footline;

        HeadHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


    static class FootHolder {
        @BindView(R.id.footTitle)
        TextView footTitle;
        @BindView(R.id.more)
        TextView more;
        @BindView(R.id.comment)
        LinearLayout comment;
        @BindView(R.id.write)
        TextView write;

        FootHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
