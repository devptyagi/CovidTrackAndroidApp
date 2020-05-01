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
import com.devtyagi.covidtrack.model.StateData;

import java.util.ArrayList;
import java.util.Collection;

public class StatewiseRecyclerAdapter extends RecyclerView.Adapter<StatewiseRecyclerAdapter.StatewiseViewHolder> implements Filterable {

    Context context;
    ArrayList<StateData> itemList;
    ArrayList<StateData> itemListAll;

    Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            ArrayList<StateData> filteredList =new ArrayList<>();
            if(constraint.toString().isEmpty()) {
                filteredList.addAll(itemListAll);
            }else {
                for(StateData data : itemListAll){
                    if(data.getStateName().toLowerCase().contains(constraint.toString().toLowerCase())) {
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
            itemList.addAll((Collection<? extends StateData>) results.values);
            notifyDataSetChanged();
        }
    };

    public StatewiseRecyclerAdapter(Context context, ArrayList<StateData> itemList) {
        this.context = context;
        this.itemList = itemList;
        this.itemListAll = new ArrayList<>(itemList);
    }

    @NonNull
    @Override
    public StatewiseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_statewise_single_row, parent, false);
        return new StatewiseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StatewiseViewHolder holder, int position) {
        StateData data = itemList.get(position);
        holder.txtStateName.setText(data.getStateName());
        holder.txtCases.setText(data.getCases());
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

    class StatewiseViewHolder extends RecyclerView.ViewHolder{

        TextView txtStateName;
        TextView txtCases;
        TextView txtRecovered;
        TextView txtDeaths;

        public StatewiseViewHolder(@NonNull View itemView) {
            super(itemView);
            txtStateName = (TextView) itemView.findViewById(R.id.txtState);
            txtCases = (TextView) itemView.findViewById(R.id.txtTotalCases);
            txtRecovered = (TextView) itemView.findViewById(R.id.txtRecovered);
            txtDeaths = (TextView) itemView.findViewById(R.id.txtDeaths);
        }
    }

}
