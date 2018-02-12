package fr.area42.mygavolt.Utils.Adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import fr.area42.mygavolt.Interventions.ListActivity;
import fr.area42.mygavolt.Interventions.ShowActivity;
import fr.area42.mygavolt.R;
import fr.area42.mygavolt.Utils.Models.Intervention;

/**
 * Created by allardk on 02/02/2018.
 */

public class InterventionAdapter extends RecyclerView.Adapter<InterventionAdapter.ViewHolder> {

    private ListActivity context;
    private List<Intervention> interventionList;


    public InterventionAdapter(ListActivity context, List interventionList) {
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
        Date date = intervention.getDate();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy à HH:mm", Locale.FRANCE);
        String customerContact;

        if (intervention.getContactFirstName() != null && intervention.getContactLastName() != null) {
            customerContact = intervention.getContactFirstName() + " " + intervention.getContactLastName();
        } else {
            customerContact = intervention.getCustomerFirstName() + " " + intervention.getCustomerLastName();
        }

        holder.id.setText(String.format("#%s", intervention.getId()));
        holder.date.setText(String.format("Le %s", format.format(date)));
        holder.customerName.setText((intervention.getCustomerBusinessName().equalsIgnoreCase("") ? customerContact : intervention.getCustomerBusinessName() + " - " + customerContact));
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

                    Intent intent = new Intent(view.getContext(), ShowActivity.class);
                    intent.putExtra("id", intervention.getId());

                    context.startActivity(intent);
                }
            });
        }
    }
}
