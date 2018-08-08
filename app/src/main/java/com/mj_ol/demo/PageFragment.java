package com.mj_ol.demo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PageFragment extends Fragment {
    private String content;
    private TextView mTextView;

    public PageFragment(String content){
        this.content = content;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
        View view = inflater.inflate(R.layout.page_fragment,container,false);
        mTextView = (TextView)view.findViewById(R.id.text_content);
        mTextView.setText(content);
        return view;
    }
}
