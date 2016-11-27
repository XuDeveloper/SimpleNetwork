package com.xu.simplenetwork;

import android.app.Activity;
import android.os.Bundle;

import com.xu.simplenetwork.request.Request;
import com.xu.simplenetwork.request.RequestBody;

import static com.xu.simplenetwork.Utils.JSON;

/**
 * Created by Xu on 2016/10/8.
 */

// TODO: 2016/11/19  1.requestbody的构建，content-type以及MediaType使用;2.处理重定向以及其他handleResponse
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestBody requestBody = new RequestBody.Builder().type(JSON).content("12333").build();
        Request request = new Request.Builder().url("123").buildPostRequest(requestBody);
//        Toast.makeText(MainActivity.this, requestBody.toString(), Toast.LENGTH_LONG).show();

    }
}
