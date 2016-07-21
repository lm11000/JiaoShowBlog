package feng.jiaoshoublog_bate10;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import feng.jiaoshoublog_bate10.entity.Internet;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;
/**
 * Created by feng on 2016/7/5.
 */
public class WriteActivity extends AppCompatActivity {
    Internet internet = new Internet();
    @BindView(R.id.userName)
    EditText userName;
    @BindView(R.id.userEmail)
    EditText userEmail;
    @BindView(R.id.userContent)
    EditText userContent;
    @BindView(R.id.submit)
    Button submit;
    @BindView(R.id.toolBar)
    Toolbar toolbar;
    private RequestBody formBody;
    Boolean shut_down=true;
    OkHttpClient client = new OkHttpClient();
    /**
     * 将EditText上的文字读取下来设置到hash表里用OKhttp的框架实现网络post请求。
     *
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wirte);
        ButterKnife.bind(this);
//        if (id == 0) {
//            return;
//        }
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (shut_down){

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            final String name = userName.getText().toString();
                            final String email = userEmail.getText().toString();
                            final String content = userContent.getText().toString();
                            final int id = getIntent().getIntExtra("id", 0);
                            if (Objects.equals(name, "")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(WriteActivity.this,"请输入姓名", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }
                            if (Objects.equals(email, "")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(WriteActivity.this,"请输入email", Toast.LENGTH_SHORT).show();
                                    }
                                });
                                return;
                            }
                            if (Objects.equals(content, "")){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toast.makeText(WriteActivity.this,"请输入内容", Toast.LENGTH_SHORT).show();
                                    }
                                });

                                return;
                            }
                            formBody = new FormBody.Builder()
                                    .add("article_id", String.valueOf(id))//问：为什么这里要string
                                    .add("name", name)
                                    .add("email", email)
                                    .add("content", content)
                                    .build();

                            final Response response = client.newCall(internet.postRun
                                    ("http://162.243.234.73:80/api/v1/leave_messages", formBody)).execute();
                            if (response.code() > 300 && response.code() != 400) {
                                Toast.makeText(WriteActivity.this, "网络异常" + response.code(), Toast.LENGTH_SHORT).show();
                            } else if (response.code() == 400) {
                                Toast.makeText(WriteActivity.this, "输入参数不正确", Toast.LENGTH_SHORT).show();
                            }else {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        try {
                                            submit.setText(response.body().string());
                                            shut_down=false;
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                });
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }}
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId()==android.R.id.home){
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
