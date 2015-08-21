package com.newclass.woaixue;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.newclass.woaixue.bean.Document;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {
    private List<Document> list;
    private ListView listview;
    private MyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
    }

    private void initView() {
        listview = (ListView) findViewById(R.id.listview);
        adapter = new MyAdapter(this, R.layout.item_course, list);
        listview.setAdapter(adapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                Toast.makeText(MainActivity.this,list.get(position).getTitle(),Toast.LENGTH_SHORT).show();
                intent.putExtra("id", id);
              //  startActivity(intent);
            }
        });
    }

    private void initData() {
        list = new ArrayList<Document>();


        new Thread() {
            @Override
            public void run() {

                try {
                    URL url = new URL("http://192.168.3.119:801/NewClass/Docs");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");

                    if (connection.getResponseCode() == 200) {
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder sb = new StringBuilder();
                        String temp;
                        while ((temp = reader.readLine()) != null) {
                            sb.append(temp);
                        }

                        JSONArray ar = new JSONArray(sb.toString());
                        for (int i = 0; i < ar.length(); i++) {

                            JSONObject o = ar.getJSONObject(i);
                            Document doc=new Document(o.getInt("id"),o.getString("title"));
                            list.add(doc);
                        }

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                adapter.notifyDataSetChanged();
                            }
                        });


                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    class MyAdapter extends ArrayAdapter<Document> {

        public MyAdapter(Context context, int resource, List<Document> objects) {
            super(context, resource, objects);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            LinearLayout layout = (LinearLayout) View.inflate(MainActivity.this, R.layout.item_course, null);
            TextView textView = (TextView) layout.findViewById(R.id.tv_title);
            textView.setText(list.get(position).getTitle());
            return layout;
        }
    }
}
