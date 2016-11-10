package com.xu.simplenetwork;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.xu.simplenetwork.request.RequestBody;
import com.xu.simplenetwork.request.Request;

import static com.xu.simplenetwork.Utils.JSON;

/**
 * Created by Xu on 2016/10/8.
 */

// TODO: 2016/11/10  1.加入header； 2.多请求调度
public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        RequestBody requestBody = new RequestBody.Builder().type(JSON).content("12333").build();
        Request request = new Request.Builder().url("123").buildPostRequest(requestBody);
        Toast.makeText(MainActivity.this, requestBody.toString(), Toast.LENGTH_LONG).show();
    }
}
