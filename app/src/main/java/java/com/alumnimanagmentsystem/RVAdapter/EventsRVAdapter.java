package java.com.alumnimanagmentsystem.RVAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;

import java.com.alumnimanagmentsystem.Activities.EventDetailActivity;
import java.com.alumnimanagmentsystem.Model.EventModel;
import java.com.alumnimanagmentsystem.Model.NewsModel;
import java.com.alumnimanagmentsystem.Model.PostsModel;
import java.com.alumnimanagmentsystem.R;
import java.util.ArrayList;

public class EventsRVAdapter extends RecyclerView.Adapter<EventsRVAdapter.ViewHolder>{

    private ArrayList<EventModel> eventModelArrayList;
    private Context context;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";

    public EventsRVAdapter(ArrayList<EventModel> eventModelArrayList, Context context) {
        this.eventModelArrayList = eventModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_recycler_view_items, parent, false);
        return new EventsRVAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

       EventModel eventModel = eventModelArrayList.get(position);
        holder.mSubTitleTextView.setText(eventModel.getDescription());
        holder.mTitleTexView.setText(eventModel.getTitle());


        String url = "http://ec2-3-134-111-243.us-east-2.compute.amazonaws.com:3000/event-image/" + eventModel.getEvent_id();


        GlideUrl glideUrl = new GlideUrl(url,
                new LazyHeaders.Builder()
                        .addHeader("Authorization", retrieveToken())
                        .build());
        Glide.with(context)
                .load(glideUrl)
                .placeholder(R.drawable.default_imag)
                .into(holder.mNewsImageView);


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, EventDetailActivity.class);
                i.putExtra("title", eventModel.getTitle());
                i.putExtra("description", eventModel.getDescription());
                i.putExtra("eventID", eventModel.event_id);
                context.startActivity(i);
                Animatoo.INSTANCE.animateZoom(context);

            }
        });

    }

    public void addItems(ArrayList<EventModel> items) {
        int startPosition = eventModelArrayList.size();
       eventModelArrayList = new ArrayList<>();
       eventModelArrayList.addAll(items);
        notifyItemRangeInserted(startPosition, items.size());
    }

    @Override
    public int getItemCount() {
        return eventModelArrayList.size();
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

    public String retrieveToken(){
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }
}
