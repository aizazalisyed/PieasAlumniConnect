package java.com.alumnimanagmentsystem.RVAdapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import java.com.alumnimanagmentsystem.Activities.NewsDetailActivity;
import java.com.alumnimanagmentsystem.Model.NewsModel;
import java.com.alumnimanagmentsystem.R;
import java.util.ArrayList;

public class NewsRVAdapter extends RecyclerView.Adapter<NewsRVAdapter.ViewHolder>{

    private ArrayList<NewsModel> newsModelArrayList;
    private Context context;

    public NewsRVAdapter(ArrayList<NewsModel> newsModelArrayList, Context context) {
        this.newsModelArrayList = newsModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycler_view_items, parent, false);
        return new NewsRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        NewsModel newsModel = newsModelArrayList.get(position);
        holder.mSubTitleTextView.setText(newsModel.getDescription());
        holder.mTitleTexView.setText(newsModel.getTitle());
        holder.mNewsImageView.setImageResource(newsModel.getImageView());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, NewsDetailActivity.class);
                i.putExtra("title", newsModel.getTitle());
                i.putExtra("description", newsModel.getDescription());
                i.putExtra("imageID", newsModel.getImageView());
                context.startActivity(i);
                Animatoo.INSTANCE.animateZoom(context);

            }
        });

    }

    @Override
    public int getItemCount() {
        return newsModelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView mTitleTexView, mSubTitleTextView;
        private ImageView mNewsImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mTitleTexView = itemView.findViewById(R.id.newsHeadingTextView);
            mSubTitleTextView = itemView.findViewById(R.id.subHeadingTextView);
            mNewsImageView = itemView.findViewById(R.id.newsImageView);
        }
    }
}
