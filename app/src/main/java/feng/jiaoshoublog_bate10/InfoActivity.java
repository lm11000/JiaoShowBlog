package feng.jiaoshoublog_bate10;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
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
import okhttp3.Response;

/**
 * Created by Administrator on 2016/7/6.
 */
public class InfoActivity extends AppCompatActivity {
    final static int INTERNET = 1;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.name)
    EditText name;
    @BindView(R.id.userEmail)
    EditText userEmail;
    @BindView(R.id.phone)
    EditText phone;
    @BindView(R.id.content)
    EditText content;
    @BindView(R.id.submit)
    Button submit;
    Internet internet = new Internet();
    private FormBody.Builder formBodyBuilder;
    OkHttpClient client = new OkHttpClient();
    Boolean shut_down = true;
    private int phoneInt;
    private String contentString;
    private String emailString;
    private String nameString;
    private Handler handler;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);
        ButterKnife.bind(this);
        setSupportActionBar(toolBar);
        ActionBar actionBar = getSupportActionBar();//通过get方法，拿到Actionbar的变量
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what != INTERNET) {
                    return;
                }
                Response response = (Response) msg.obj;
                if (response.code() > 300 && response.code() != 400) {
                    Toast.makeText(InfoActivity.this, "网络异常" + response.code(), Toast.LENGTH_SHORT).show();
                } else if (response.code() == 400) {
                    Toast.makeText(InfoActivity.this, "输入参数不正确", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        submit.setText(response.body().string());
                        shut_down = false;
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                super.handleMessage(msg);
            }
        };
        if (actionBar != null) {//判断actionbar不为null是因为如果在手机上，actionbar是出于null状态的
            actionBar.setDisplayHomeAsUpEnabled(true);//这是告诉actionbar这个activity是有上一级的。由此会自动生成一个带返回图标的item；
        }
        /**
         * 这个方法实现了Post请求，将数据数据从EditText中读取出来并且发到接口。并且同时将回传过来的信息覆盖掉提交按钮。并且取消提交按钮的点击功能
         */
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            if (shut_down) {
                                nameString = name.getText().toString();
                                emailString = userEmail.getText().toString();
                                contentString = content.getText().toString();

                                        if (Objects.equals(nameString, "")){
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(InfoActivity.this,"请输入姓名", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            return;
                                        }
                                        if (Objects.equals(emailString, "")){
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(InfoActivity.this,"请输入email", Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                            return;
                                        }
                                        if (Objects.equals(contentString, "")){
                                            runOnUiThread(new Runnable() {
                                                @Override
                                                public void run() {
                                                    Toast.makeText(InfoActivity.this,"请输入内容", Toast.LENGTH_SHORT).show();
                                                }
                                            });

                                            return;
                                        }

                                formBodyBuilder = new FormBody.Builder().
                                        add("name", nameString).
                                        add("content", contentString).
                                        add("email", emailString);
                                if (phone.getText().length() != 0) {
                                    phoneInt = Integer.parseInt(phone.getText().toString());
                                    formBodyBuilder.add("phone", phoneInt + "");
                                }
                                final Response response = client.newCall(
                                        internet.postRun("http://162.243.234.73:80/api/v1/contact_me", formBodyBuilder.build())).execute();
                                Message message = new Message();
                                message.obj = response;
                                message.what = INTERNET;
                                handler.sendMessage(message);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        });
    }

    /**
     * 此方法实现了点击返回键跳转。问：这个方法是如何实现跳转的？答：通过判断，如果拿到的点选的item的id等同于android的home的id，就finnish。而home在上文已经被定义了
     * @param item
     * @return
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();//finish的意思是把自己关掉，关掉自己则界面会回到上一级，因为在之前我们设置了上一级的位置，所以起到了返回的功能.finsh的同时还会回收掉自己的资源
        }
        return super.onOptionsItemSelected(item);
    }
}
