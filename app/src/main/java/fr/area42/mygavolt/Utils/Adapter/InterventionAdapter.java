package fr.area42.mygavolt.Utils.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import fr.area42.mygavolt.R;
import fr.area42.mygavolt.Utils.Models.Intervention;

/**
 * Created by allardk on 02/02/2018.
 */

public class InterventionAdapter extends RecyclerView.Adapter<InterventionAdapter.ViewHolder> {

    private Context context;
    private List<Intervention> interventionList;

    public InterventionAdapter(Context context, List interventionList) {
        this.context = context;
        this.interventionList = interventionList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(interventionList.get(position));

        Intervention intervention = interventionList.get(position);

        holder.id.setText(intervention.getId());
    }

    @Override
    public int getItemCount() {
        return interventionList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView id;
        public TextView date;
        public TextView customerName;

        public ViewHolder(View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.idIntervention);
            date = itemView.findViewById(R.id.dateIntervention);
            customerName = itemView.findViewById(R.id.customerNameIntervention);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intervention intervention = (Intervention) view.getTag();

                    Toast.makeText(view.getContext(), intervention.getId() + " " + intervention.getDate(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
