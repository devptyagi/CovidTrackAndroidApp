package com.devtyagi.covidtrack.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.widget.SearchView;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.devtyagi.covidtrack.R;
import com.devtyagi.covidtrack.adapter.CountrywiseRecyclerAdapter;
import com.devtyagi.covidtrack.adapter.StatewiseRecyclerAdapter;
import com.devtyagi.covidtrack.model.CountryData;
import com.devtyagi.covidtrack.model.StateData;
import com.devtyagi.covidtrack.util.ConnectionManager;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class WorldWideFragment extends Fragment {

    public WorldWideFragment() {
    }

    RecyclerView recyclerCountryWise;
    RecyclerView.LayoutManager layoutManager;
    ConnectionManager connectionManager;
    ArrayList<CountryData> countryDataList = new ArrayList<>();
    CountrywiseRecyclerAdapter recyclerAdapter;
    RelativeLayout progressLayout;
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_world_wide, container, false);
        recyclerCountryWise = (RecyclerView) view.findViewById(R.id.recyclerCountryWise);
        layoutManager = new LinearLayoutManager(getActivity());
        connectionManager = new ConnectionManager();
        setHasOptionsMenu(true);
        progressLayout = (RelativeLayout) view.findViewById(R.id.progressLayout);
        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        progressLayout.setVisibility(View.VISIBLE);
        if(connectionManager.checkConnectivity((Context)getActivity())){
            fetchData();
        }else {
            AlertDialog.Builder dialog = new AlertDialog.Builder((Context)getActivity());
            dialog.setTitle("Error");
            dialog.setMessage("Internet Connection not Found");
            dialog.setPositiveButton("Open Settings", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent settingsIntent = new Intent(Settings.ACTION_WIRELESS_SETTINGS);
                    startActivity(settingsIntent);
                    getActivity().finish();
                }
            });
            dialog.setNegativeButton("EXIT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ActivityCompat.finishAffinity((Activity)getActivity());
                }
            });
            dialog.create();
            dialog.show();
        }
        return view;
    }

    public void fetchData() {
        RequestQueue queue = Volley.newRequestQueue((Context)getActivity());
        String url = "https://covid-19-tracking.p.rapidapi.com/v1";
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try{
                            progressLayout.setVisibility(View.GONE);
                            for(int i=0;i<response.length()-1;i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                CountryData countryDataObject = new CountryData(
                                        jsonObject.getString("Country_text"),
                                        jsonObject.getString("Active Cases_text"),
                                        jsonObject.getString("Total Cases_text"),
                                        jsonObject.getString("Total Recovered_text"),
                                        jsonObject.getString("Total Deaths_text")
                                );
                                countryDataList.add(countryDataObject);
                                recyclerAdapter = new CountrywiseRecyclerAdapter((Context)getActivity(), countryDataList);
                                recyclerCountryWise.setAdapter(recyclerAdapter);
                                recyclerCountryWise.setLayoutManager(layoutManager);
                            }
                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText((Context)getActivity(), "Volley Error Occured", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> headers = new HashMap<>();
                headers.put("x-rapidapi-host", "covid-19-tracking.p.rapidapi.com");
                headers.put("x-rapidapi-key", "3f431ba691msh1c9ab1f21cfa8e5p1f32e7jsn8ece14f135e0");
                return headers;
            }
        };
        queue.add(jsonArrayRequest);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.main_menu, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                recyclerAdapter.getFilter().filter(newText);
                return false;
            }
        });
        super.onCreateOptionsMenu(menu, inflater);
    }

}
