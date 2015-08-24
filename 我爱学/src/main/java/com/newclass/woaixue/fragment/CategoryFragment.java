package com.newclass.woaixue.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.lidroid.xutils.ViewUtils;
import com.lidroid.xutils.view.annotation.ViewInject;
import com.newclass.woaixue.R;
import com.newclass.woaixue.bean.Document;

import java.util.List;

/**
 * Created by liaorubei on 2015/8/24.
 */
public class CategoryFragment extends Fragment {

    @ViewInject(R.id.listView)
    private ListView listView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        FrameLayout inflate = (FrameLayout) inflater.inflate(R.layout.fragment_category, container, false);
        ViewUtils.inject(inflate);

        listView = (ListView) inflate.findViewById(R.id.listView);
        return inflate;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void initData(List<Document> list) {
        String[] ds = new String[list.size()];
        for (int i = 0; i < list.size(); i++) {
            ds[i] = list.get(i).title;

        }
        listView.setAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, ds));

    }
}
