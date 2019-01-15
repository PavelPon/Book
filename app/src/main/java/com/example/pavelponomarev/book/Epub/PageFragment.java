package com.example.pavelponomarev.book.Epub;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.example.pavelponomarev.book.R;

public class PageFragment extends Fragment {

    private static final String ARG_TAB_POSITION = "tab_position";
    private OnFragmentReadyListener onFragmentReadyListener;
    public static PageFragment newInstence (int tabPosition){
        PageFragment fragment = new PageFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_TAB_POSITION,tabPosition);
        fragment.setArguments(args);
        return fragment;
    }
    public PageFragment(){}
    public interface OnFragmentReadyListener{
        View onFragmentReady (int position);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        onFragmentReadyListener = (OnFragmentReadyListener) context;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

    onFragmentReadyListener = null;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
       View rootView = inflater.inflate(R.layout.fragment_display,container,false);
        RelativeLayout mainLayout = (RelativeLayout) rootView.findViewById(R.id.fragment_main_layout);
       View view = onFragmentReadyListener.onFragmentReady(getArguments().getInt(ARG_TAB_POSITION));
        if(view!=null){
            mainLayout.addView(view);
        }


       return rootView;
    }
}
