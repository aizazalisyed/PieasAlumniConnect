package java.com.alumnimanagmentsystem.RVAdapter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;

import java.com.alumnimanagmentsystem.Activities.AlumniDetailActivity;
import java.com.alumnimanagmentsystem.Model.Alumnus;
import java.com.alumnimanagmentsystem.Model.ProfilePicture;
import java.com.alumnimanagmentsystem.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AlumniListRVAdapter extends RecyclerView.Adapter<AlumniListRVAdapter.ViewHolder> {

    List<Alumnus> alumniModelArrayList;
    List<Alumnus> alumniModelArrayListFull;
    Context context;
    String fileName = "My_Pref";
    String key = "TOKEN_STRING";
    String defaultValue = "";

    public AlumniListRVAdapter(List<Alumnus> alumniModelArrayList, Context context) {
        this.alumniModelArrayList = alumniModelArrayList;
        this.context = context;
        alumniModelArrayListFull = alumniModelArrayList;
    }

    @NonNull
    @Override
    public AlumniListRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.alumni_recycler_view_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumniListRVAdapter.ViewHolder holder, int position) {

        Alumnus alumniModel = alumniModelArrayList.get(position);


        String urlAlumniProfile = "http://ec2-3-134-111-243.us-east-2.compute.amazonaws.com:3000" + "/alumni/" + alumniModel.getAlumni_id() + "/avatar";
        GlideUrl glideUrl = new GlideUrl(urlAlumniProfile,
                new LazyHeaders.Builder()
                        .addHeader("Authorization", retrieveToken())
                        .build());
        Glide.with(context)
                .load(glideUrl)
                .placeholder(R.drawable.default_user)
                .into(holder.alumniProfileImageView);

        holder.alumniNameTextView.setText(alumniModel.getName());
        holder.alumniDepartmentTextView.setText(alumniModel.getDegree().getDepartment().getDepartment_name());
        holder.yearOfGraduation.setText(alumniModel.getYearOfGrad());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AlumniDetailActivity.class);
                i.putExtra("alumniID", alumniModel.getAlumni_id());
                context.startActivity(i);
                Animatoo.INSTANCE.animateSlideUp(context);
            }
        });

    }

    @Override
    public int getItemCount() {
        return alumniModelArrayList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView alumniNameTextView, alumniDepartmentTextView;
        TextView yearOfGraduation;
        ImageView alumniProfileImageView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            alumniNameTextView = itemView.findViewById(R.id.alumniNameTextView);
            alumniDepartmentTextView = itemView.findViewById(R.id.alumiDegreeTextView);
            alumniProfileImageView = itemView.findViewById(R.id.alumniImageView);
            yearOfGraduation = itemView.findViewById(R.id.yearOfGraduation);

        }
    }

    public String retrieveToken() {
        SharedPreferences sharedPreferences = context.getSharedPreferences(fileName, Context.MODE_PRIVATE);
        String token = sharedPreferences.getString(key, defaultValue);
        return token;
    }
}
