package fr.area42.mygavolt.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import fr.area42.mygavolt.Models.Intervention;
import fr.area42.mygavolt.R;

/**
 * Created by allardk on 02/02/2018.
 */

public class InterventionAdapter extends RecyclerView.Adapter<InterventionAdapter.ViewHolder> {

    private ListActivity context;
    private List<Intervention> interventionList;


    public InterventionAdapter(ListActivity context, List<Intervention> interventions) {
        this.context = context;
        this.interventionList = interventions;
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
        String customerContact;

        if (intervention.addressCustomer.contact.firstname != null && intervention.addressCustomer.contact.lastname != null) {
            customerContact = intervention.addressCustomer.contact.firstname + " " + intervention.addressCustomer.contact.lastname;
        } else {
            customerContact = intervention.addressCustomer.customer.firstname + " " + intervention.addressCustomer.customer.lastname;
        }
        holder.id.setText(String.format("#%s", intervention.id));
        holder.date.setText(String.format("Le %s", formatDate(intervention.date)));
        holder.customerName.setText((intervention.addressCustomer.customer.businessName.equalsIgnoreCase("") ?
                customerContact : intervention.addressCustomer.customer.businessName + " - " + customerContact));
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

                    putExtra(intent, intervention);

                    context.startActivity(intent);
                }
            });
        }
    }

    private void putExtra(Intent intent, Intervention intervention) {
        intent.putExtra("id", intervention.id);
        intent.putExtra("date", formatDate(intervention.date));
    }

    private String formatDate(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy à HH:mm", Locale.FRANCE);

        return format.format(date);
    }
}
