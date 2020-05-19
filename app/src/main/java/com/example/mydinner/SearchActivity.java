package com.example.mydinner;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.protocol.ClientContext;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;


public class SearchActivity extends AppCompatActivity {
    SearchView searchView = null;
    private List<Dinner> dinners = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        Button addNewDinnerBtn =  findViewById(R.id.add_dinner_button);

        addNewDinnerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent goToNewDinnerActivity = new Intent(SearchActivity.this, NewDinnerActivity.class);
                startActivity(goToNewDinnerActivity);

            }
        });

//        RecyclerView recyclerViewDinner = findViewById(R.id.dishList);
//        AdapterDish adapterDish = new AdapterDish(dinners);
//        recyclerViewDinner.setAdapter(adapterDish);
//        recyclerViewDinner.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchView == null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(SearchActivity.this.getComponentName()));
            searchView.setIconified(false);
        }


        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        if (Intent.ACTION_SEARCH.equals(intent.getAction())){
            String query = intent.getStringExtra(SearchManager.QUERY);
            if (searchView != null) {
                searchView.clearFocus();
            }
            new GetResults(query).execute();
        }
    }


    class GetResults extends AsyncTask<String, String, String> {

        String searchQuery;

        public GetResults(String searchQuery) {
            this.searchQuery = searchQuery;
        }

        @Override
        protected String doInBackground(String... params) {
            String serverURL = "http://aistyapp.byethost31.com/selectDinnerByType.php";

            List<NameValuePair> nameValue = new ArrayList<NameValuePair>();

            nameValue.add(new BasicNameValuePair("type", searchQuery));
            try {
                BasicCookieStore cookieStore = new BasicCookieStore();
                BasicClientCookie cookie = new BasicClientCookie("__test", "e5e4b30a3699e26c5ecd32704080416c");
                cookie.setDomain(".aistyapp.byethost31.com");
                cookie.setPath("/");
                cookieStore.addCookie(cookie);

                HttpClient httpClient = new DefaultHttpClient();
                HttpPost httpPost = new HttpPost(serverURL);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValue));
                HttpContext localContext = new BasicHttpContext();
                localContext.setAttribute(ClientContext.COOKIE_STORE, cookieStore);
                HttpResponse httpResponse = httpClient.execute(httpPost, localContext);

                BufferedReader reader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                StringBuffer result = new StringBuffer();
                String line;

                while((line = reader.readLine()) != null) {
                    result.append(line);
                }
                return result.toString();

            } catch (ClientProtocolException e) {

            } catch (IOException e) {
                e.printStackTrace();
                return e.toString();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String result) {

            if (result.equals("no rows")) {
                Toast.makeText(SearchActivity.this, "Rezultatu nera", Toast.LENGTH_SHORT).show();
            } else {
                try {
                    dinners.clear();
                    JSONArray jsonArray = new JSONArray(result);
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        boolean deliverable = false;
                        if (jsonObject.getString("deliverable").equals("true")) {
                            deliverable = true;
                        }
                        Dinner dinner = new Dinner(
                                jsonObject.getString("dish_type"),
                                jsonObject.getString("dish_name"),
                                Double.parseDouble(jsonObject.getString("price")),
                                deliverable,
                                jsonObject.getString("payment")
                        );
                        dinners.add(dinner);
                    }
                    RecyclerView recyclerViewDinner = findViewById(R.id.dishList);
                    AdapterDish adapterDish = new AdapterDish(dinners);
                    recyclerViewDinner.setAdapter(adapterDish);
                    recyclerViewDinner.setLayoutManager(new LinearLayoutManager(SearchActivity.this));

                } catch (JSONException e) {
                    Toast.makeText(SearchActivity.this, e.toString(), Toast.LENGTH_LONG).show();
                    Toast.makeText(SearchActivity.this, result.toString(), Toast.LENGTH_LONG).show();
                }
            }


        }
    }


}
