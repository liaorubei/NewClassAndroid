package com.newclass.woaixue;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newclass.woaixue.bean.Document;
import com.newclass.woaixue.fragment.CategoryFragment;
import com.newclass.woaixue.util.NetWorkUtil;
import com.viewpagerindicator.TitlePageIndicator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends ActionBarActivity {
    private List<Document> list;

//    @ViewInject(R.id.indicator)
//    private TitlePageIndicator indicator;
/*    @ViewInject(R.id.viewPager)
    private ViewPager viewPager;*/

    private MyPagerAdapter adapter;
    private List<Fragment> mainFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
 /*       ViewUtils.inject(this);
        viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));*/







     //   initData();
    }


    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {

            return MyFragment.newInstance(position);
        }

        @Override
        public int getCount() {

            return 10;
        }
    }


    public static class MyFragment extends ListFragment {
        int mNum;

        static MyFragment newInstance(int num) {
            MyFragment f = new MyFragment();

            // Supply num input as an argument.
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);

            return f;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_pager_list, container, false);
            return view;
        }

        @Override
        public void onActivityCreated(Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, new String[]{"等级", "等级2", "等级3", "等级4"}));
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Log.i("FragmentList", "Item clicked: " + id);
        }
    }


    private void initData() {
        list = new ArrayList<Document>();

        HttpUtils httpUtils = new HttpUtils();
        httpUtils.send(HttpRequest.HttpMethod.GET, NetWorkUtil.NewClassDocs, new RequestCallBack<String>() {

            private PagerAdapter ai;

            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {

                Gson gson = new Gson();
                list = gson.fromJson(responseInfo.result, new TypeToken<List<Document>>() {
                }.getType());

                CategoryFragment categoryFragment = new CategoryFragment();

                mainFragment = new ArrayList<Fragment>();
                mainFragment.add(categoryFragment);

                adapter = new MyPagerAdapter(getSupportFragmentManager());
 /*               viewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
                ai = viewPager.getAdapter();*/


                categoryFragment.initData(list);
                adapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.i("log", "onFailure " + s);
            }
        });


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
            textView.setText(list.get(position).title);
            return layout;
        }
    }
}
