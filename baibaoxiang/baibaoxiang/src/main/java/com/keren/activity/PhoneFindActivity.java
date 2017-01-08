package com.keren.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
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

import com.baidu.apistore.sdk.ApiCallBack;
import com.baidu.apistore.sdk.ApiStoreSDK;
import com.baidu.apistore.sdk.network.Parameters;
import com.keren.utils.ApiConfig;
import com.qhad.ads.sdk.adcore.Qhad;
import com.qhad.ads.sdk.interfaces.IQhAdEventListener;
import com.qhad.ads.sdk.interfaces.IQhBannerAd;
import com.qq.e.ads.banner.ADSize;
import com.qq.e.ads.banner.AbstractBannerADListener;
import com.qq.e.ads.banner.BannerView;

import org.json.JSONObject;

public class PhoneFindActivity extends Activity {
    LinearLayout lcontainer;
    TextView shang, diqu, err;
    EditText phonenum;
    Button chaxun;
    ImageView back;
    IQhBannerAd bannerad;
    private LinearLayout adv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_find);
        initView();
        Event();
        init360adv();
    }

    void initView() {
        lcontainer = (LinearLayout) findViewById(R.id.llcontainer);
        shang = (TextView) findViewById(R.id.tvyunyingshang);
        diqu = (TextView) findViewById(R.id.tvdiqu);
        err = (TextView) findViewById(R.id.err);
        phonenum = (EditText) findViewById(R.id.etphone);
        chaxun = (Button) findViewById(R.id.btchaxun);
        back = (ImageView) findViewById(R.id.phone_back);
        adv = (LinearLayout) findViewById(R.id.it_adv);
    }

    private void init360adv() {
        bannerad = Qhad.showBanner(adv, PhoneFindActivity.this, "FPPbPT3RTs", false);
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

    void Event() {
        chaxun.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                request();
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent();
                intent1.setClass(PhoneFindActivity.this, MainActivity.class);

                startActivity(intent1);

                PhoneFindActivity.this.finish();
            }
        });
    }

    void request() {
        if (!TextUtils.isEmpty(phonenum.getText().toString())) {
            Parameters parameters = new Parameters();
            parameters.put("phone", phonenum.getText().toString());
            ApiStoreSDK.execute(ApiConfig.NET_PHONE_FIND, ApiStoreSDK.GET, parameters, new ApiCallBack() {
                @Override
                public void onSuccess(int i, String s) {
                    Log.v("dfsfesd", s);
                    try {
                        JSONObject json = new JSONObject(s);
                        if (json.getInt("errNum") == 0) {
                            err.setText("");
                            lcontainer.setVisibility(View.VISIBLE);
                            JSONObject j = json.getJSONObject("retData");
                            shang.setText("所属运营商：" + j.getString("supplier"));
                            if (j.has("city"))
                                diqu.setText(" 所属地区：" + j.getString("province") + " " + j.getString("city"));
                            else diqu.setText("所属地区：" + j.getString("province"));
                        } else {
                            lcontainer.setVisibility(View.GONE);
                            err.setText(json.getString("retMsg"));
                        }


                    } catch (Exception e) {

                    }

                }

                @Override
                public void onError(int i, String s, Exception e) {
                    Toast.makeText(PhoneFindActivity.this, s, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onComplete() {
                    super.onComplete();
                }
            });
        } else {
            Toast.makeText(PhoneFindActivity.this, "手机号不能为空", Toast.LENGTH_SHORT).show();
        }

    }

    @SuppressLint("InlinedApi")
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        // TODO Auto-generated method stub
        if (keyCode == KeyEvent.KEYCODE_BACK) {//退出键
            Intent intent1 = new Intent();
            intent1.setClass(PhoneFindActivity.this, MainActivity.class);

            startActivity(intent1);

            PhoneFindActivity.this.finish();
        }

        return super.onKeyDown(keyCode, event);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        bannerad.closeAds();
    }
}
