package com.keren.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.qhad.ads.sdk.adcore.Qhad;
import com.qhad.ads.sdk.interfaces.IQhAdEventListener;
import com.qhad.ads.sdk.interfaces.IQhBannerAd;

import java.util.List;
import java.util.Map;

import io.paperdb.Paper;

public class KuaiDiHishtory extends Activity {
    SimpleAdapter adapter=null;
    List<Map<String,String>> maps=null;
    String[] name=new String[]{"gongsi","danhao","time"};
    int[] id=new int[]{R.id.gongsi,R.id.danhao,R.id.time};
    ListView listView;
    ImageView back;
    IQhBannerAd bannerad;
    private LinearLayout adv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kuai_di_hishtory);
        Paper.init(this);
        listView=(ListView)findViewById(R.id.listview);
        adv = (LinearLayout) findViewById(R.id.it_adv);
       back=(ImageView)findViewById(R.id.kuaidi_back);
        if(Paper.book("hishtory").exist("lishi")){
         maps=Paper.book("hishtory").read("lishi");

            adapter=new SimpleAdapter(this,maps,R.layout.kuaidi_hishtory_list_item,name,id);
            listView.setAdapter(adapter);

        }
        Event();
        init360adv();
    }
    private void init360adv() {
        bannerad = Qhad.showBanner(adv, KuaiDiHishtory.this, "FPPbPT3RTs", false);
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
    void Event(){
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent=new Intent();
                intent.putExtra("url","http://m.kuaidi100.com/index_all.html?type="+maps.get(i).get("gongsi")+"&postid="+maps.get(i).get("danhao")+"#result");
                intent.setClass(getBaseContext(),KuaiDiListActivity.class);
                startActivity(intent);

            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                KuaiDiHishtory.this.finish();
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bannerad.closeAds();
    }
}
