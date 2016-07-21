package feng.jiaoshoublog_bate10.adapter;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import feng.jiaoshoublog_bate10.R;
import feng.jiaoshoublog_bate10.entity.Article;

/**
 * Created by feng on 2016/6/30.
 */
public class SkipListAdapter extends BaseAdapter {
    Article article;

    public SkipListAdapter(Article article) {
        this.article = article;
    }

    @Override
    public int getCount() {
        return article.items.size();
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
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_skip, parent, false);
            convertView.setTag(new ViewHolder(convertView));
        }
        ViewHolder holder = (ViewHolder) convertView.getTag();
        holder.content.setText(Html.fromHtml(article.items.get(position).content));
        //html.fromHtml(html)是指可以将文字通过html语言（或方式）来解析，可以用来完美输出html内容。
        return convertView;
    }
    static class ViewHolder {
        @BindView(R.id.content)
        TextView content;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}
