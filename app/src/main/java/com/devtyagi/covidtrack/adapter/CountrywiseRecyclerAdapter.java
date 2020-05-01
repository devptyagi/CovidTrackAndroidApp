package com.devtyagi.covidtrack.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.devtyagi.covidtrack.R;
import com.devtyagi.covidtrack.model.CountryData;

import java.util.ArrayList;
import java.util.Collection;

public class CountrywiseRecyclerAdapter extends RecyclerView.Adapter<CountrywiseRecyclerAdapter.CountrywiseViewHolder> implements Filterable {

    Context context;
    ArrayList<CountryData> itemList;
    ArrayList<CountryData> itemListAll;

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<CountryData> filteredList =new ArrayList<>();
            if(constraint.toString().isEmpty()) {
                filteredList.addAll(itemListAll);
            }else {
                for(CountryData data : itemListAll){
                    if(data.getCountryName().toLowerCase().contains(constraint.toString().toLowerCase())) {
                        filteredList.add(data);
                    }
                }
            }
            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            itemList.clear();
            itemList.addAll((Collection<? extends CountryData>) results.values);
            notifyDataSetChanged();
        }
    };

        public CountrywiseRecyclerAdapter(Context context, ArrayList<CountryData> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.itemListAll = new ArrayList<>(itemList);
    }

    @NonNull
    @Override
    public CountrywiseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_worldwide_single_row, parent, false);
        return new CountrywiseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CountrywiseViewHolder holder, int position) {
        CountryData data = itemList.get(position);
        holder.txtCountryName.setText(data.getCountryName());
        holder.txtCases.setText(data.getTotalCases());
        holder.txtRecovered.setText(data.getRecovered());
        holder.txtDeaths.setText(data.getDeaths());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    class CountrywiseViewHolder extends RecyclerView.ViewHolder {

        TextView txtCountryName;
        TextView txtCases;
        TextView txtRecovered;
        TextView txtDeaths;

        public CountrywiseViewHolder(@NonNull View itemView) {
            super(itemView);
            txtCountryName = (TextView) itemView.findViewById(R.id.txtCountryName);
            txtCases = (TextView) itemView.findViewById(R.id.txtTotalCases);
            txtRecovered = (TextView) itemView.findViewById(R.id.txtRecovered);
            txtDeaths = (TextView) itemView.findViewById(R.id.txtDeaths);
        }
    }
}
