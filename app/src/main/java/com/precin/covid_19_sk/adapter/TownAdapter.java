package com.precin.covid_19_sk.adapter;

import android.content.Context;
import android.content.res.Configuration;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.precin.covid_19_sk.R;
import com.precin.covid_19_sk.model.Towns;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

public class TownAdapter extends RecyclerView.Adapter<TownAdapter.PlanetHolder> {

    private Context context;
    private ArrayList<Towns> towns;

    public TownAdapter(Context context, ArrayList<Towns> towns) {
        this.context = context;
        this.towns = towns;
    }

    @NonNull
    @Override
    public PlanetHolder onCreateViewHolder(@NonNull ViewGroup parent, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_town, parent, false);
        return new PlanetHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PlanetHolder holder, int position) {
        Towns towns = this.towns.get(position);
        holder.setDetails(towns);
    }

    @Override
    public int getItemCount() {
        return towns.size();
    }

    class PlanetHolder extends RecyclerView.ViewHolder {

        //convert numbers to locale
        Configuration sysConfig = context.getResources().getConfiguration();
        Locale curLocale = sysConfig.locale;
        NumberFormat nf = NumberFormat.getInstance(curLocale);

        private TextView txtTown, txtInfectedTown, txtTodayNew;

        PlanetHolder(View itemView) {
            super(itemView);
            txtTown = itemView.findViewById(R.id.txtTown);
            txtInfectedTown = itemView.findViewById(R.id.txtInfectedTown);
            txtTodayNew = itemView.findViewById(R.id.txtTodayNew);
        }

        void setDetails(Towns towns) {
            txtTown.setText(towns.getTown());
            txtInfectedTown.setText(nf.format(new Integer(towns.getInfectedTown())));
            //txtDistance.setText(nf.format(new Integer(99999)));
            if (towns.getTodayNew() < 1 && towns.getTodayNew() !=0)  {
                txtTodayNew.setText("");
                if(towns.getTodayNew() == 0){
                    txtTodayNew.setText("");
                }
                else{
                    txtTodayNew.setText(Html.fromHtml("<font color='red'>+" + nf.format(new Integer(towns.getTodayNew()))+"</font>"));
                }
            }
            else{
                if(towns.getTodayNew() == 0){
                    txtTodayNew.setText(Html.fromHtml("<font color='green'>" + nf.format(new Integer(towns.getTodayNew()))+"</font>"));
                }
                else{
                    txtTodayNew.setText(Html.fromHtml("<font color='red'>+" + nf.format(new Integer(towns.getTodayNew()))+"</font>"));
                }
            }
        }
    }
}
