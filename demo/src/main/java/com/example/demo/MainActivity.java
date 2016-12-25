package com.example.demo;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

import com.xu.xnetwork.XNetwork;
import com.xu.xnetwork.XNetworkClient;
import com.xu.xnetwork.call.XNetworkCallBack;
import com.xu.xnetwork.request.Request;
import com.xu.xnetwork.response.Response;

/**
 * Created by Xu on 2016/10/8.
 */

// TODO: 2016/11/19  1.requestbody的构建，content-type以及MediaType使用;
public class MainActivity extends Activity {

//    private TextView mTextView;
    private ImageView imageView;
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            Response response = (Response)msg.obj;
            Bitmap result = response.bitmap();
            imageView.setImageBitmap(result);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageView = (ImageView) findViewById(R.id.image);
//        RequestBody requestBody = new RequestBody.Builder().type(JSON).content("12333").build();
        Request request = new Request.Builder().url("http://image101.360doc.com/DownloadImg/2016/11/1816/84856115_4.jpg").buildGetRequest();
        XNetworkClient client = XNetwork.defaultInit(MainActivity.this);
        client.newRequest(this, request, new XNetworkCallBack() {
            @Override
            public void onSuccess(Response response) {
                Message message = Message.obtain();
                message.obj = response;
                handler.sendMessage(message);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });

//        Toast.makeText(MainActivity.this, requestBody.toString(), Toast.LENGTH_LONG).show();

    }
}
