package feng.jiaoshoublog_bate10;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import feng.jiaoshoublog_bate10.adapter.ViewPagerAdapter;
import feng.jiaoshoublog_bate10.entity.FragmentAll;

public class MainActivity extends AppCompatActivity {


    ViewPagerAdapter viewPagerAdapter;
    String[] types = new String[]{"all", "collect", "work", "diary"};
    List<Fragment> fragments;
    @BindView(R.id.toolBar)
    Toolbar toolBar;
    @BindView(R.id.viewPager)
    ViewPager viewPager;
    @BindView(R.id.tabStrip)
    PagerTabStrip tabStrip;
    int tab=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        //设置tabStrip的属性
        //设置Tab选中时的颜色
        tabStrip.setTabIndicatorColor(ContextCompat.getColor(this, android.R.color.black));
        //设置Tab是否显示下划线
        tabStrip.setDrawFullUnderline(true);
        //设置Tab背景色
        tabStrip.setBackgroundColor(ContextCompat.getColor(this, android.R.color.white));
        //设置Tab间的距离.
        tabStrip.setTextSpacing(50);


        toolBar.setTitle("叫兽Blog");
        setSupportActionBar(toolBar);
        //设置Toolbar的icon（左上角的图标）
        toolBar.setNavigationIcon(R.mipmap.ic_launcher);

        //MenuItem的点击事件
        Toolbar.OnMenuItemClickListener onMenuItemClick = new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                startActivity(new Intent(MainActivity.this, InfoActivity.class));
                return true;
            }
        };
        toolBar.setOnMenuItemClickListener(onMenuItemClick);

        //设置fragment.
        fragments = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            FragmentAll fragmentAll = new FragmentAll();
            fragmentAll.setType(types[i]);
            fragments.add(fragmentAll);
        }
        //设置ViewPager
        viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager(), fragments, types);
        viewPager.setAdapter(viewPagerAdapter);
    }

    /**
     * 这个方法定义了Menu的布局文件,将menu和xml绑定
     *
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void onPageSelected(int position)
    {
        setTab(position);
    }
    //用一个int型变量设置viewpager的位置。这个变量等会会设置成PagerTabStrip的值。这个int型的变量就是实际Viewpager的位置
    public void setTab(int tab) {
        viewPager.setCurrentItem(tab);
    }


}