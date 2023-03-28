package java.com.alumnimanagmentsystem.RVAdapter;

import android.content.Context;
import android.content.Intent;
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

import java.com.alumnimanagmentsystem.Activities.AlumniDetailActivity;
import java.com.alumnimanagmentsystem.Model.AlumniModel;
import java.com.alumnimanagmentsystem.R;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class AlumniRVAdapter extends RecyclerView.Adapter<AlumniRVAdapter.ViewHolder> implements Filterable {

    List<AlumniModel> alumniModelArrayList;
    List<AlumniModel> alumniModelArrayListFull;
    Context context;

    public AlumniRVAdapter(List<AlumniModel> alumniModelArrayList, Context context) {
        this.alumniModelArrayList = alumniModelArrayList;
        this.context = context;
        alumniModelArrayListFull  = new ArrayList<>(alumniModelArrayList);
    }

    @NonNull
    @Override
    public AlumniRVAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.alumni_recycler_view_items, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AlumniRVAdapter.ViewHolder holder, int position) {

        AlumniModel alumniModel = alumniModelArrayList.get(position);
        holder.alumniProfileImageView.setImageResource(alumniModel.getImageID());
        holder.alumniNameTextView.setText(alumniModel.getAlumniName());
        holder.alumniDepartmentTextView.setText(alumniModel.getAlumniDepartment());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, AlumniDetailActivity.class);
                context.startActivity(i);
                Animatoo.INSTANCE.animateSlideUp(context);
            }
        });

    }

    @Override
    public int getItemCount() {
        return alumniModelArrayList.size();
    }

    @Override
    public Filter getFilter() {
        return (Filter) alumniModelArrayListFilter;
    }
    private Filter alumniModelArrayListFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {

            List<AlumniModel> filteredList = new ArrayList<>();
            if(constraint == null || constraint.length() ==0 ){
                filteredList.addAll(alumniModelArrayListFull);
            }
            else {
                String filterPattern = constraint.toString().toLowerCase(Locale.ROOT).trim();
                for (AlumniModel item: alumniModelArrayListFull
                     ) {
                    if (item.getAlumniName().toLowerCase(Locale.ROOT).contains(filterPattern)){
                        filteredList.add(item);
                    }
                    
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {

            alumniModelArrayList.clear();
            alumniModelArrayList.addAll((List) results.values);
            notifyDataSetChanged();
        }
    };


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView alumniNameTextView, alumniDepartmentTextView;
        ImageView alumniProfileImageView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            alumniNameTextView = itemView.findViewById(R.id.alumniNameTextView);
            alumniDepartmentTextView = itemView.findViewById(R.id.alumiDegreeTextView);
            alumniProfileImageView = itemView.findViewById(R.id.alumniImageView);

        }
    }

}
