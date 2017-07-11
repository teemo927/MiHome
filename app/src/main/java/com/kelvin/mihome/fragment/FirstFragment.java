package com.kelvin.mihome.fragment;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.kelvin.mihome.Constant;
import com.kelvin.mihome.MainActivity;
import com.kelvin.mihome.R;
import com.kelvin.mihome.ReceiverRefresh;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FirstFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FirstFragment extends Fragment {
    private static final String TAG = "FirstFragment";
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private CountDownTimer mTimer;

    private BroadcastReceiver mReceiver = new ReceiverRefresh(){
        @Override
        public void onReceive(Context context, Intent intent) {
            Log.e(TAG, "onReceive: FirstFragment" );
            mTimer = new CountDownTimer(5000,1000){

                @Override
                public void onTick(long l) {
                    Log.e(TAG, "刷新倒计时: "+l/1000 );
                }

                @Override
                public void onFinish() {
                    ((MainActivity)getActivity()).stopRefresh();
                }
            }.start();
        }
    };

    public FirstFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FirstFragment.
     */
    public static FirstFragment newInstance(String param1, String param2) {
        Log.e("FirstFragment","new instance");
        FirstFragment fragment = new FirstFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        IntentFilter filter = new IntentFilter();
        filter.addAction(Constant.ACTION);
        filter.setPriority(Integer.MAX_VALUE);
        getActivity().registerReceiver(mReceiver, filter);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mTimer!=null) {
            mTimer.cancel();
        }
    }
}
