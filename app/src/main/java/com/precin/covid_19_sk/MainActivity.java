package com.precin.covid_19_sk;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.precin.covid_19_sk.adapter.TownAdapter;
import com.precin.covid_19_sk.model.Towns;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;


public class MainActivity extends Activity {
    
    TextView txt_infected, txt_tested, txt_infectedAG, txt_testedAG, txtRecovered, txtDeceased, newTotal, region1, new1, totalInfected1,dateUpdate, region2, new2, totalInfected2, region3, new3, totalInfected3, region4, new4, totalInfected4, region5, new5, totalInfected5, region6, new6, totalInfected6, region7, new7, totalInfected7, region8, new8, totalInfected8;
    ProgressDialog pd;
    private ImageButton button1;
    private RecyclerView recyclerView;
    private TownAdapter adapter;
    private ArrayList<Towns> townsArrayList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new TownAdapter(this, new ArrayList<Towns>());
        recyclerView.setAdapter(adapter);
        DividerItemDecoration divider = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(MainActivity.this, R.drawable.custom_divider));
        recyclerView.addItemDecoration(divider);
        button1 = (ImageButton) findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PopupMenu popup = new PopupMenu(MainActivity.this, button1);
                popup.getMenuInflater()
                        .inflate(R.menu.popup_menu, popup.getMenu());
                popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case R.id.one:
                                new AlertDialog.Builder(MainActivity.this, R.style.AppCompatAlertDialogStyle)
                                        .setTitle(Html.fromHtml(("<font color=\"black\">Covid-19-SK</font>")))
                                        .setMessage(Html.fromHtml("<font color=\"black\">Aktuálne informácie o koronavíruse na Slovensku.<br>Dáta sú prevzaté zo stránky: https://mapa.covid.chat<br><br>©2020 Precin Studio</font>"))
                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int which) {
                                                dialog.cancel();
                                            }})
                                        .show();
                                return true;

                            case R.id.three:
                                String emailUrl = "mailto:mr.babinec@gmail.com?subject=Predmet &body= ";
                                Intent request = new Intent(Intent.ACTION_VIEW);
                                request.setData(Uri.parse(emailUrl));
                                try {
                                    startActivity(Intent.createChooser(request, "Poslať email..."));
                                } catch (android.content.ActivityNotFoundException ex) {
                                    Toast.makeText(MainActivity.this, "Nemáte nainštalovaného emailového klienta.", Toast.LENGTH_SHORT).show();
                                }
                                return true;
                        }
                        return true;
                    }
                });
                popup.show();
            }
        });



        new JsonTask().execute("https://api.apify.com/v2/key-value-stores/GlTLAdXAuOz6bLAIO/records/LATEST?disableRedirect=true");
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new JsonTask().execute("https://api.apify.com/v2/key-value-stores/GlTLAdXAuOz6bLAIO/records/LATEST?disableRedirect=true"); // your code
                pullToRefresh.setRefreshing(false);

            }
        });


        txt_infected = (TextView) findViewById(R.id.txt_infected);
        txt_tested = (TextView) findViewById(R.id.txt_tested);
        txt_infectedAG = (TextView) findViewById(R.id.txt_infectedAG);
        txt_testedAG = (TextView) findViewById(R.id.txt_testedAG);
        txtDeceased = (TextView) findViewById(R.id.txtDeceased);
        region1 = (TextView) findViewById(R.id.region1);
        new1 = (TextView) findViewById(R.id.new1);
        totalInfected1 = (TextView) findViewById(R.id.totalInfected1);
        dateUpdate = (TextView) findViewById(R.id.date);
        region2 = (TextView) findViewById(R.id.region2);
        new2 = (TextView) findViewById(R.id.new2);
        totalInfected2 = (TextView) findViewById(R.id.new2Total);
        region3 = (TextView) findViewById(R.id.region3);
        new3 = (TextView) findViewById(R.id.new3);
        totalInfected3 = (TextView) findViewById(R.id.new3Total);
        region4 = (TextView) findViewById(R.id.region4);
        new4 = (TextView) findViewById(R.id.new4);
        totalInfected4 = (TextView) findViewById(R.id.new4Total);
        region5 = (TextView) findViewById(R.id.region5);
        new5 = (TextView) findViewById(R.id.new5);
        totalInfected5 = (TextView) findViewById(R.id.new5Total);
        region6 = (TextView) findViewById(R.id.region6);
        new6 = (TextView) findViewById(R.id.new6);
        totalInfected6 = (TextView) findViewById(R.id.new6Total);
        region7 = (TextView) findViewById(R.id.region7);
        new7 = (TextView) findViewById(R.id.new7);
        totalInfected7 = (TextView) findViewById(R.id.new7Total);
        region8 = (TextView) findViewById(R.id.region8);
        new8 = (TextView) findViewById(R.id.new8);
        totalInfected8 = (TextView) findViewById(R.id.new8Total);
    }



    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Počkajte prosím...");
            pd.setCancelable(false);
            pd.show();
        }

        protected String doInBackground(String... params) {


            HttpURLConnection connection = null;
            BufferedReader reader = null;
            try {
                URL url = new URL(params[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.connect();


                InputStream stream = connection.getInputStream();

                reader = new BufferedReader(new InputStreamReader(stream));

                StringBuffer buffer = new StringBuffer();
                String line = "";

                while ((line = reader.readLine()) != null) {
                    buffer.append(line+"\n");
                    //Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)
                }

                return buffer.toString();


            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
                try {
                    if (reader != null) {
                        reader.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return null;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                initView();
            }

            try {

                JSONObject jObj = new JSONObject(result);

                JSONArray arr = jObj.getJSONArray("regionsData");

                //convert numbers to locale
                Configuration sysConfig = getResources().getConfiguration();
                Locale curLocale = sysConfig.locale;
                NumberFormat nf = NumberFormat.getInstance(curLocale);
                String d = jObj.getString("lastUpdatedAtSource");
                Date date = Date.from( Instant.parse( d ));
                SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy", Locale.UK);
                String formattedDate = sdf.format(date);

                dateUpdate.setText("Dáta zo dňa: \n" + formattedDate);

                txtDeceased.setText(Html.fromHtml(nf.format(new Integer(jObj.getString("deceased")))+"<sup><small>\t&nbsp;+"+nf.format(new Integer(jObj.getString("newDeceased")))+"</small></sup>"));

                txt_tested.setText(Html.fromHtml(nf.format(new Integer(jObj.getString("testedPCR")))+"<sup><small><font color='#FFFF00'>\t&nbsp;+"+nf.format(new Integer(jObj.getString("newTestedPCR")))+"</font></small></sup>"));
                txt_infected.setText(Html.fromHtml(nf.format(new Integer(jObj.getString("infectedPCR")))+"<sup><small><font color='red'>\t&nbsp;+"+nf.format(new Integer(jObj.getString("newInfectedPCR")))+"</font></small></sup>"));

                txt_testedAG.setText(Html.fromHtml(nf.format(new Integer(jObj.getString("testedAG")))+"<sup><small><font color='#FFFF00'>\t&nbsp;+"+nf.format(new Integer(jObj.getString("newTestedAG")))+"</font></small></sup>"));
                txt_infectedAG.setText(Html.fromHtml(nf.format(new Integer(jObj.getString("infectedAG")))+"<sup><small><font color='red'>\t&nbsp;+"+nf.format(new Integer(jObj.getString("newInfectedAG")))+"</font></small></sup>"));


                region1.setText(arr.getJSONObject(0).getString("region"));
                new1.setText(arr.getJSONObject(0).getString("newInfected"));
                totalInfected1.setText(nf.format(new Integer(arr.getJSONObject(0).getString("totalInfected"))));
                region2.setText(arr.getJSONObject(1).getString("region"));
                new2.setText(arr.getJSONObject(1).getString("newInfected"));
                totalInfected2.setText(nf.format(new Integer(arr.getJSONObject(1).getString("totalInfected"))));
                region3.setText(arr.getJSONObject(2).getString("region"));
                new3.setText(arr.getJSONObject(2).getString("newInfected"));
                totalInfected3.setText(nf.format(new Integer(arr.getJSONObject(2).getString("totalInfected"))));
                region4.setText(arr.getJSONObject(3).getString("region"));
                new4.setText(arr.getJSONObject(3).getString("newInfected"));
                totalInfected4.setText(nf.format(new Integer(arr.getJSONObject(3).getString("totalInfected"))));
                region5.setText(arr.getJSONObject(4).getString("region"));
                new5.setText(arr.getJSONObject(4).getString("newInfected"));
                totalInfected5.setText(nf.format(new Integer(arr.getJSONObject(4).getString("totalInfected"))));
                region6.setText(arr.getJSONObject(5).getString("region"));
                new6.setText(arr.getJSONObject(5).getString("newInfected"));
                totalInfected6.setText(nf.format(new Integer(arr.getJSONObject(5).getString("totalInfected"))));
                region7.setText(arr.getJSONObject(6).getString("region"));
                new7.setText(arr.getJSONObject(6).getString("newInfected"));
                totalInfected7.setText(nf.format(new Integer(arr.getJSONObject(6).getString("totalInfected"))));
                region8.setText(arr.getJSONObject(7).getString("region"));
                new8.setText(arr.getJSONObject(7).getString("newInfected"));
                totalInfected8.setText(nf.format(new Integer(arr.getJSONObject(7).getString("totalInfected"))));


                JSONArray dist = jObj.getJSONArray("districts");
                int distLen = dist.length();
                for (int i = 0; i < distLen; i++) {
                    Towns towns = new Towns(dist.getJSONObject(i).getString("town"), dist.getJSONObject(i).getInt("totalInfected"), dist.getJSONObject(i).getInt("newInfected"));
                    townsArrayList.add(towns);
                }


            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this,
                        "Nepodarilo sa načítať dáta.\nSkontrolujte internetové pripojenie.", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        townsArrayList = new ArrayList<>();
        adapter = new TownAdapter(this, townsArrayList);
        recyclerView.setAdapter(adapter);
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                pd.dismiss();
            }
        }, 1000);
    }
}