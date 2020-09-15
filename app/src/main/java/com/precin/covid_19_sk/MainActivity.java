package com.precin.covid_19_sk;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class MainActivity extends Activity {
    
    TextView txt_infected, txt_tested, txtRecovered, txtDeceased, newTotal, region1, new1, totalInfected1,dateUpdate, region2, new2, totalInfected2, region3, new3, totalInfected3, region4, new4, totalInfected4, region5, new5, totalInfected5, region6, new6, totalInfected6, region7, new7, totalInfected7, region8, new8, totalInfected8;
    ProgressDialog pd;
    String regionBb, newInfectedBb, totalInfectedBb, regionBa, newInfectedBa, totalInfectedBa, regionKe, newInfectedKe, totalInfectedKe, regionNr, newInfectedNr, totalInfectedNr, regionPp, newInfectedPp, totalInfectedPp,regionTn, newInfectedTn, totalInfectedTn,regionTt, newInfectedTt, totalInfectedTt,regionZa, newInfectedZa, totalInfectedZa;
    int newBb, newBa, newKe, newNr, newPp, newTn, newTt, newZa;
    WebView myWebView;
    private ImageButton button1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

                            case R.id.two:
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



        web();
        new JsonTask().execute("https://api.apify.com/v2/key-value-stores/GlTLAdXAuOz6bLAIO/records/LATEST?disableRedirect=true");
        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new JsonTask().execute("https://api.apify.com/v2/key-value-stores/GlTLAdXAuOz6bLAIO/records/LATEST?disableRedirect=true"); // your code
                pullToRefresh.setRefreshing(false);
                web();
            }
        });




        txt_infected = (TextView) findViewById(R.id.txt_infected);
        txt_tested = (TextView) findViewById(R.id.txt_tested);
        txtRecovered = (TextView) findViewById(R.id.txtRecovered);
        txtDeceased = (TextView) findViewById(R.id.txtDeceased);
        region1 = (TextView) findViewById(R.id.region1);
        new1 = (TextView) findViewById(R.id.new1);
        newTotal = (TextView) findViewById(R.id.newTotal);
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


    public void web(){
        myWebView = findViewById(R.id.webview);
        myWebView.loadUrl("https://mapa.covid.chat/table");
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);

    }




    private class JsonTask extends AsyncTask<String, String, String> {

        protected void onPreExecute() {
            super.onPreExecute();
            pd = new ProgressDialog(MainActivity.this);
            pd.setMessage("Počkajte prosím");
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
                    Log.d("Response: ", "> " + line);   //here u ll get whole response...... :-)

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

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);
            if (pd.isShowing()){
                pd.dismiss();
            }

            try {

                JSONObject jObj = new JSONObject(result);
                String infected = jObj.getString("infected");
                String tested = jObj.getString("tested");
                String recovered = jObj.getString("recovered");
                String deceased = jObj.getString("deceased");
                String updated = jObj.getString("updated");

                JSONArray arr = jObj.getJSONArray("regionsData");

                regionBb = arr.getJSONObject(0).getString("region");
                newInfectedBb = arr.getJSONObject(0).getString("newInfected");
                newBb = Integer.parseInt(newInfectedBb);
                totalInfectedBb = arr.getJSONObject(0).getString("totalInfected");

                regionBa = arr.getJSONObject(1).getString("region");
                newInfectedBa = arr.getJSONObject(1).getString("newInfected");
                newBa = Integer.parseInt(newInfectedBa);
                totalInfectedBa = arr.getJSONObject(1).getString("totalInfected");

                regionKe = arr.getJSONObject(2).getString("region");
                newInfectedKe = arr.getJSONObject(2).getString("newInfected");
                newKe = Integer.parseInt(newInfectedKe);
                totalInfectedKe = arr.getJSONObject(3).getString("totalInfected");

                regionNr = arr.getJSONObject(3).getString("region");
                newInfectedNr = arr.getJSONObject(3).getString("newInfected");
                newNr = Integer.parseInt(newInfectedNr);
                totalInfectedNr = arr.getJSONObject(3).getString("totalInfected");

                regionPp = arr.getJSONObject(4).getString("region");
                newInfectedPp = arr.getJSONObject(4).getString("newInfected");
                newPp = Integer.parseInt(newInfectedPp);
                totalInfectedPp = arr.getJSONObject(4).getString("totalInfected");

                regionTn = arr.getJSONObject(5).getString("region");
                newInfectedTn = arr.getJSONObject(5).getString("newInfected");
                newTn = Integer.parseInt(newInfectedTn);
                totalInfectedTn = arr.getJSONObject(5).getString("totalInfected");

                regionTt = arr.getJSONObject(6).getString("region");
                newInfectedTt = arr.getJSONObject(6).getString("newInfected");
                newTt = Integer.parseInt(newInfectedTt);
                totalInfectedTt = arr.getJSONObject(6).getString("totalInfected");

                regionZa = arr.getJSONObject(7).getString("region");
                newInfectedZa = arr.getJSONObject(7).getString("newInfected");
                newZa = Integer.parseInt(newInfectedZa);
                totalInfectedZa = arr.getJSONObject(7).getString("totalInfected");

                int totalInfected = newBb + newBa + newKe + newNr + newPp + newTn + newTt + newZa;
                String s = String.valueOf(totalInfected);

                txt_infected.setText(infected);
                txt_tested.setText(tested);
                txtRecovered.setText(recovered);
                txtDeceased.setText(deceased);
                newTotal.setText(s);
                dateUpdate.setText("Dáta zo dňa: \n" + updated);
                myWebView.setVisibility(View.VISIBLE);

            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(MainActivity.this,
                        "Nepodarilo sa načítať dáta.\nSkontrolujte internetové pripojenie.", Toast.LENGTH_LONG).show();
                myWebView.setVisibility(View.GONE);
                // Do something to recover ... or kill the app.
            }
                region1.setText(regionBb);
                new1.setText(newInfectedBb);
                totalInfected1.setText(totalInfectedBb);
                region2.setText(regionBa);
                new2.setText(newInfectedBa);
                totalInfected2.setText(totalInfectedBa);
                region3.setText(regionKe);
                new3.setText(newInfectedKe);
                totalInfected3.setText(totalInfectedKe);
                region4.setText(regionNr);
                new4.setText(newInfectedNr);
                totalInfected4.setText(totalInfectedNr);
                region5.setText(regionPp);
                new5.setText(newInfectedPp);
                totalInfected5.setText(totalInfectedPp);
                region6.setText(regionTn);
                new6.setText(newInfectedTn);
                totalInfected6.setText(totalInfectedTn);
                region7.setText(regionTt);
                new7.setText(newInfectedTt);
                totalInfected7.setText(totalInfectedTt);
                region8.setText(regionZa);
                new8.setText(newInfectedZa);
                totalInfected8.setText(totalInfectedZa);
        }
    }
}