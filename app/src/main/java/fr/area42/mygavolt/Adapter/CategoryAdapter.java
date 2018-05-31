package fr.area42.mygavolt.Adapter;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.TextureView;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.area42.mygavolt.Customers.FilterActivity;
import fr.area42.mygavolt.Interventions.ShowActivity;
import fr.area42.mygavolt.Models.Category;
import fr.area42.mygavolt.Models.Intervention;
import fr.area42.mygavolt.R;


/**
 * Created by allardk on 30/05/2018.
 */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private FilterActivity context;
    private List<Category> categoriesList;


    public CategoryAdapter(FilterActivity context, List<Category> categories) {
        this.context = context;
        this.categoriesList = categories;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_list_category_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(categoriesList.get(position));

        Category category = categoriesList.get(position);

        holder.id.setText(String.format("#%s", category.id));
    }

    @Override
    public int getItemCount() {
        return categoriesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextureView id;
        public TextureView label;

        public ViewHolder(View itemView) {
            super(itemView);

            id = itemView.findViewById(R.id.idCategory);
            label = itemView.findViewById(R.id.label);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Category category = (Category) view.getTag();

                    Intent intent = new Intent(view.getContext(), ShowActivity.class);

                    putExtra(intent, category);

                    context.startActivity(intent);
                }
            });
        }

        public void putExtra(Intent intent, Category category) {
            intent.putExtra("id", category.id);
        }
    }
}
