package com.kelvin.mihome.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.kelvin.mihome.R;
import com.kelvin.mihome.StopRefreshInterface;
import com.kelvin.mihome.view.BottomButton;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TabFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TabFragment extends Fragment implements StopRefreshInterface{
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String TAG = "TabFragment";

    private String mParam1;
    private String mParam2;
    private int mCurrentTab = 0;//默认第一页

    private OnFragmentInteractionListener mListener;

    @BindView(R.id.bb_music)
    BottomButton bbMusic;
    @BindView(R.id.bb_song)
    BottomButton bbSong;
    @BindView(R.id.bb_people)
    BottomButton bbPeople;
    @BindView(R.id.bb_mime)
    BottomButton bbMime;
    @BindView(R.id.llt_bottom)
    LinearLayout lltBottom;
    Unbinder unbinder;

    public TabFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment TabFragment.
     */
    public static TabFragment newInstance(String param1, String param2) {
        TabFragment fragment = new TabFragment();
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
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tab, container, false);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void stop() {
        Log.e(TAG, "stop: " +mCurrentTab);
        switch (mCurrentTab){
            case 0:
                bbMusic.stopRefresh();
                break;
            case 1:
                bbSong.stopRefresh();
                break;
            case 2:
                bbPeople.stopRefresh();
                break;
            case 3:
                bbMime.stopRefresh();
                break;
        }
    }

    public interface OnFragmentInteractionListener {
        void onFragmentSelected(int index);

        void onRefresh();
    }

    @OnClick({R.id.bb_music, R.id.bb_song, R.id.bb_people, R.id.bb_mime})
    public void click(View view) {
        switch (view.getId()) {
            case R.id.bb_music:
                reset(bbMusic, 0);
                break;
            case R.id.bb_song:
                reset(bbSong, 1);
                break;
            case R.id.bb_people:
                reset(bbPeople, 2);
                break;
            case R.id.bb_mime:
                reset(bbMime, 3);
                break;
        }
    }

    private void reset(BottomButton bb, int index) {

        //先确定是否刷新。已经是选中状态时，并且不在刷新状态，直接刷新
        if (bb.getSelected() && !bb.isRefreshing()) {
            bb.startRefresh();
            mListener.onRefresh();
            return;
        }

        switch (index){
            case 0:
                //关闭其他tab刷新
                bbSong.stopRefresh();
                bbPeople.stopRefresh();
                bbMime.stopRefresh();
                break;
            case 1:
                bbMusic.stopRefresh();
                bbPeople.stopRefresh();
                bbMime.stopRefresh();
                break;
            case 2:
                bbMusic.stopRefresh();
                bbSong.stopRefresh();
                bbMime.stopRefresh();
                break;
            case 3:
                bbMusic.stopRefresh();
                bbSong.stopRefresh();
                bbPeople.stopRefresh();
                break;
        }

        //先确定是否刷新，再设置当前为选中状态
        bbMusic.setSelected(false);
        bbSong.setSelected(false);
        bbPeople.setSelected(false);
        bbMime.setSelected(false);
        bb.setSelected(true);

        mListener.onFragmentSelected(index);
        mCurrentTab = index;
    }
}
