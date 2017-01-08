package com.keren.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.keren.sortlistview.SearchActivity;
import com.qhad.ads.sdk.adcore.Qhad;
import com.qhad.ads.sdk.interfaces.IQhAdEventListener;
import com.qhad.ads.sdk.interfaces.IQhBannerAd;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

public class KuaiDiMainActivity extends Activity implements View.OnClickListener {

    private ImageView kuaidi_back;
    private TextView companynametxt;
    private LinearLayout kuaidi_in;
    private EditText kuaidi_num;
    private LinearLayout kuaidi_num_in;
    private Button searchbtn;
    private Button historybtn;
    private  LinearLayout content,adv;
    IQhBannerAd bannerad;
    BannerView bv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuai_di_main);
        Paper.init(this);
        initView();
        initBanner();
        this.bv.loadAD();
      init360adv();
    }

    private void initView() {
        kuaidi_back = (ImageView) findViewById(R.id.kuaidi_back);
        content=(LinearLayout)findViewById(R.id.content);
        adv=(LinearLayout)findViewById(R.id.adv);
        companynametxt = (TextView) findViewById(R.id.companynametxt);
        kuaidi_in = (LinearLayout) findViewById(R.id.kuaidi_in);
        kuaidi_num = (EditText) findViewById(R.id.kuaidi_num);
        kuaidi_num_in = (LinearLayout) findViewById(R.id.kuaidi_num_in);
        searchbtn = (Button) findViewById(R.id.searchbtn);
        historybtn = (Button) findViewById(R.id.historybtn);
        SharedPreferences s=getSharedPreferences("company",MODE_PRIVATE);
      kuaidi_num.setText(s.getString("num", ""));
        companynametxt.setText(s.getString("name", ""));
        searchbtn.setOnClickListener(this);
        historybtn.setOnClickListener(this);
        kuaidi_back.setOnClickListener(this);
        kuaidi_in.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.searchbtn:
submit();
                SharedPreferences s=getSharedPreferences("company",MODE_PRIVATE);
                SharedPreferences.Editor e=s.edit();
                e.putString("num",kuaidi_num.getText().toString());
                e.putString("name",companynametxt.getText().toString());
                e.commit();

                break;
            case R.id.historybtn:
Intent i=new Intent(getBaseContext(),KuaiDiHishtory.class);
                startActivity(i);
                break;
            case R.id.kuaidi_back:
                Intent intent1=new Intent();
                intent1.setClass(KuaiDiMainActivity.this, MainActivity.class);

                startActivity(intent1);
                KuaiDiMainActivity.this.finish();
                break;
            case R.id.kuaidi_in:
                Intent intent2=new Intent();
                intent2.setClass(KuaiDiMainActivity.this, SearchActivity.class);

                startActivityForResult(intent2, 2
                );
                break;
        }
    }

    private void submit() {
        // validate
        String num = kuaidi_num.getText().toString().trim();
        String num1 = companynametxt.getText().toString().trim();
        if (TextUtils.isEmpty(num)) {
            Toast.makeText(this, "请输入订单号", Toast.LENGTH_SHORT).show();
            return;
        }
else if(TextUtils.isEmpty(num1)){
            Toast.makeText(this, "请选择快递公司", Toast.LENGTH_SHORT).show();
            return;
        }
        DateFormat df   =   new SimpleDateFormat( "yyyy-MM-dd  HH:mm:ss ");
        String time =df.format(new Date());
        if(Paper.book("hishtory").exist("lishi")){
            List<Map<String,String>> maps=Paper.book("hishtory").read("lishi");
            HashMap<String,String> map=new HashMap<String, String>();
            map.put("gongsi",companynametxt.getText().toString());
            map.put("danhao",kuaidi_num.getText().toString());
            map.put("time",time);
            maps.add(map);
            Paper.book("hishtory").write("lishi",maps);
        }else {
            Log.v("dfsssssss",companynametxt.getText().toString()+kuaidi_num.getText().toString());
            List<Map<String,String>> maps=new ArrayList<Map<String, String>>();
            HashMap<String,String> map=new HashMap<String, String>();
            map.put("gongsi",companynametxt.getText().toString());
            map.put("danhao",kuaidi_num.getText().toString());
            map.put("time",time);
            maps.add(map);
            Paper.book("hishtory").write("lishi",maps);
        }

        Intent intent=new Intent();
        intent.putExtra("url","http://m.kuaidi100.com/index_all.html?type="+companynametxt.getText().toString()+"&postid="+kuaidi_num.getText().toString()+"#result");
        intent.setClass(getBaseContext(),KuaiDiListActivity.class);
        startActivity(intent);

        // TODO validate success, do something


    }

    @SuppressLint("InlinedApi") @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode==KeyEvent.KEYCODE_BACK) {//退出键
            Intent intent1=new Intent();
            intent1.setClass(KuaiDiMainActivity.this, MainActivity.class);

            startActivity(intent1);
            KuaiDiMainActivity.this.finish();
        }

        return super.onKeyDown(keyCode, event);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(data!=null) {
            if(requestCode==2) {
             companynametxt.setText(data.getExtras().getString("company"));
            }
        }
    }
    private void initBanner() {
        this.bv = new BannerView(this, ADSize.BANNER,"1105250689", "1050515065515890");
        bv.setRefresh(30);
        bv.setADListener(new AbstractBannerADListener() {

            @Override
            public void onNoAD(int arg0) {

            }

            @Override
            public void onADReceiv() {

            }
        });
        content.addView(bv);
    }
    private void init360adv(){
         bannerad = Qhad.showBanner(adv, KuaiDiMainActivity.this, "FPPbPT3RTs", false);
        bannerad.setAdEventListener(new IQhAdEventListener() {
            @Override
            public void onAdviewGotAdSucceed() {

            }

            @Override
            public void onAdviewGotAdFail() {

            }

            @Override
            public void onAdviewRendered() {

            }

            @Override
            public void onAdviewIntoLandpage() {

            }

            @Override
            public void onAdviewDismissedLandpage() {

            }

            @Override
            public void onAdviewClicked() {

            }

            @Override
            public void onAdviewClosed() {

            }

            @Override
            public void onAdviewDestroyed() {

            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bannerad.closeAds();
        bv.destroy();

    }


}
