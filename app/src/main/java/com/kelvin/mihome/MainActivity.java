package com.kelvin.mihome;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;

import com.kelvin.mihome.fragment.FirstFragment;
import com.kelvin.mihome.fragment.SecondFragment;
import com.kelvin.mihome.fragment.TabFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class MainActivity extends AppCompatActivity implements TabFragment.OnFragmentInteractionListener{

    private static final String TAG = "MainActivity";
    private static final String TAG_FIRST = "TAG_FIRST";
    private static final String TAG_SECOND = "TAG_SECOND";
    private static final String TAG_THREE = "TAG_THREE";
    private static final String TAG_FOUR = "TAG_FOUR";
    Unbinder unbinder;

    Fragment mCurrentFragment;
    @BindView(R.id.content_container)
    FrameLayout contentContainer;
    private StopRefreshInterface stopRefreshInterface;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        TabFragment tabFragment = (TabFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_tab);
        stopRefreshInterface = tabFragment;

        changeFragment(mCurrentFragment, FirstFragment.newInstance("",""), TAG_FIRST);
    }

    private void changeFragment(Fragment from, Fragment to, String tag){
        changeFragment(getSupportFragmentManager(),from,to,R.id.content_container, tag);
    }

    private void changeFragment(FragmentManager manager, Fragment from, Fragment to, int containerId, String tag){
        FragmentTransaction trans = manager.beginTransaction();
        if (from == to) return;
        if (from == null){
            trans.add(containerId,to,tag);
        }else{
            trans.replace(containerId,to,tag);
//            trans.hide(from);
//            trans.show(to);
            trans.addToBackStack(null);
        }
        trans.commit();
        mCurrentFragment = to;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbinder.unbind();
    }

    public void QQ(View view) {
        joinQQGroup2("ImXwPSnRsBrxgxOFG0C8VahK0ce5E9nC");
    }

    /****************
     *
     * 发起添加群流程。群号：不怕神一样的对手(556131937) 的 key 为： ImXwPSnRsBrxgxOFG0C8VahK0ce5E9nC
     * 调用 joinQQGroup(ImXwPSnRsBrxgxOFG0C8VahK0ce5E9nC) 即可发起手Q客户端申请加群 不怕神一样的对手(556131937)
     *
     * @param key 由官网生成的key
     * @return 返回true表示呼起手Q成功，返回fals表示呼起失败
     ******************/
    public boolean joinQQGroup2(String key) {
        Intent intent = new Intent();
        intent.setData(Uri.parse("mqqopensdkapi://bizAgent/qm/qr?url=http%3A%2F%2Fqm.qq.com%2Fcgi-bin%2Fqm%2Fqr%3Ffrom%3Dapp%26p%3Dandroid%26k%3D" + key));
        // 此Flag可根据具体产品需要自定义，如设置，则在加群界面按返回，返回手Q主界面，不设置，按返回会返回到呼起产品界面
        // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        try {
            startActivity(intent);
            return true;
        } catch (Exception e) {
            // 未安装手Q或安装的版本不支持
            return false;
        }
    }

    @Override
    public void onFragmentSelected(int index) {
//        Log.e(TAG, "onFragmentSelected: "+index );
        FragmentManager manager = getSupportFragmentManager();
        Fragment to = null;
        String tag = "";
        switch (index){
            case 0:
                to = manager.findFragmentByTag(TAG_FIRST);
                to = to==null? FirstFragment.newInstance("","") : to;
                tag = TAG_FIRST;
                break;
            case 1:
                to = manager.findFragmentByTag(TAG_SECOND);
                to = to==null ? SecondFragment.newInstance("","") : to;
                tag = TAG_SECOND;
                break;
            case 2:
                to = manager.findFragmentByTag(TAG_THREE);
                to = to==null ? SecondFragment.newInstance("","") : to;
                tag = TAG_THREE;
                break;
            case 3:
                to = manager.findFragmentByTag(TAG_FOUR);
                to = to==null ? SecondFragment.newInstance("","") : to;
                tag = TAG_FOUR;
                break;

        }
        changeFragment(mCurrentFragment,to, tag);
    }

    @Override
    public void onRefresh() {
        Log.e(TAG, "onRefresh: " );
        Intent intent = new Intent(Constant.ACTION);
        sendBroadcast(intent);
    }

    public void stopRefresh() {
        stopRefreshInterface.stop();
    }
}
