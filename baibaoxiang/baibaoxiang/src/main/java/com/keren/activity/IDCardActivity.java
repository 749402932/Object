package com.keren.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
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

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.keren.utils.ApiConfig;
import com.qhad.ads.sdk.adcore.Qhad;
import com.qhad.ads.sdk.interfaces.IQhAdEventListener;
import com.qhad.ads.sdk.interfaces.IQhBannerAd;

import org.json.JSONObject;

public class IDCardActivity extends Activity {
    LinearLayout lcontainer;
    TextView diqu,shengri,xingzuo,sex,shengxiao,err;
    EditText phonenum;
    Button chaxun;
    ImageView back;
    IQhBannerAd bannerad;
    private LinearLayout adv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_idcard);
        initView();
        Event();
        init360adv();
    }
    void initView(){
        lcontainer=(LinearLayout)findViewById(R.id.illcontainer);
        diqu=(TextView)findViewById(R.id.idiqu);
        shengri=(TextView)findViewById(R.id.iriqi);
        xingzuo=(TextView)findViewById(R.id.ixingzuo);
        sex=(TextView)findViewById(R.id.isex);
        shengxiao=(TextView)findViewById(R.id.ishengxiao);
        err=(TextView)findViewById(R.id.ierr);
        phonenum=(EditText)findViewById(R.id.ietphone);
        chaxun=(Button)findViewById(R.id.ibtchaxun);
        back=(ImageView)findViewById(R.id.iphone_back);
        adv = (LinearLayout) findViewById(R.id.it_adv);
    }
    void Event(){
        chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1=new Intent();
                intent1.setClass(IDCardActivity.this, MainActivity.class);

                startActivity(intent1);

                IDCardActivity.this.finish();
            }
        });
    }
    private void init360adv() {
        bannerad = Qhad.showBanner(adv, IDCardActivity.this, "FPPbPT3RTs", false);
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
    void request(){
        if(!TextUtils.isEmpty(phonenum.getText().toString())){
            Parameters parameters=new Parameters();
            parameters.put("idcard",phonenum.getText().toString());
            ApiStoreSDK.execute(ApiConfig.NET_ID_CARD_FIND,ApiStoreSDK.GET,parameters,new ApiCallBack(){
                @Override
                public void onSuccess(int i, String s) {
                    Log.v("dfsfesd",s);
                    try {
                        JSONObject json=new JSONObject(s);
                        if(json.getInt("error")==0){
                            err.setText("");
                            lcontainer.setVisibility(View.VISIBLE);
                            JSONObject j=json.getJSONObject("data");
                            diqu.setText("所属地区："+j.getString("address"));
                         shengri.setText("出生日期："+j.getString("birthday"));
                            xingzuo.setText("星座："+j.getString("constellation"));
                            sex.setText("性别："+j.getString("gender"));
                            shengxiao.setText("生肖："+j.getString("zodiac"));
                        }else {
                            lcontainer.setVisibility(View.GONE);
                            err.setText(json.getString("msg"));
                        }


                    }catch (Exception e){

                    }

                }

                @Override
                public void onError(int i, String s, Exception e) {
                    Toast.makeText(IDCardActivity.this,s,Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onComplete() {
                    super.onComplete();
                }
            });
        }else {
            Toast.makeText(IDCardActivity.this,"身份证不能为空！",Toast.LENGTH_SHORT).show();
        }

    }
    @SuppressLint("InlinedApi") @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode==KeyEvent.KEYCODE_BACK) {//退出键
            Intent intent1=new Intent();
            intent1.setClass(IDCardActivity.this, MainActivity.class);

            startActivity(intent1);

            IDCardActivity.this.finish();
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        bannerad.closeAds();
    }
}
