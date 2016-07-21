package feng.jiaoshoublog_bate10.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import feng.jiaoshoublog_bate10.R;
import feng.jiaoshoublog_bate10.entity.Comment;

/**
 * Created by feng on 2016/7/1.
 */
public class FootViewCommentAdapter {
    List<Comment> comments;

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }

    public View getView(int position, ViewGroup parent) {
        View convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listfoot_comment_item, parent, false);

        ViewHolder holder = new ViewHolder(convertView);
        holder.name.setText(comments.get(position).name);
        holder.commentContent.setText(comments.get(position).content);
        holder.createdAt.setText(comments.get(position).created_at);


        return convertView;
    }

     class ViewHolder {
        @BindView(R.id.name)
        TextView name;
        @BindView(R.id.commentContent)
        TextView commentContent;
        @BindView(R.id.created_at)
        TextView createdAt;

        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }


}
