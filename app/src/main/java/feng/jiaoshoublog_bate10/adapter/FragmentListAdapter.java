package feng.jiaoshoublog_bate10.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import feng.jiaoshoublog_bate10.R;
import feng.jiaoshoublog_bate10.entity.Article;

/**
 * Created by feng on 2016/6/30.
 */
public class FragmentListAdapter extends BaseAdapter {
    List<Article> articleList;

    public FragmentListAdapter(List<Article> articleList) {
        this.articleList = articleList;
    }

    @Override
    public int getCount() {
        return articleList.size();
    }

    @Override
    public Object getItem(int position) {
        return 0;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_fragment, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.title.setText(articleList.get(position).title);
        holder.description.setText(articleList.get(position).description);
        if (Objects.equals(articleList.get(position).cover, "/image/no_pic.jpg")){
           return convertView;
        }
        Picasso.with(parent.getContext()).load("http://162.243.234.73/" + articleList.get(position).cover).
                into(holder.picture);

        return convertView;
    }

    static class ViewHolder {
        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.description)
        TextView description;
        @BindView(R.id.headPicture)
        ImageView picture;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}


