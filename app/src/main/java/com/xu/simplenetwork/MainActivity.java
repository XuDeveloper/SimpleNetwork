package com.xu.simplenetwork;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Toast;

import com.xu.simplenetwork.request.MediaType;
import com.xu.simplenetwork.request.PostBody;
import com.xu.simplenetwork.request.Request;

import static com.xu.simplenetwork.Utils.JSON;

/**
 * Created by Administrator on 2016/10/8.
 */

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PostBody postBody = new PostBody.Builder().type(JSON).content("12333").build();
        Request request = new Request.Builder().url("123").buildPostRequest(postBody);
        Toast.makeText(MainActivity.this, postBody.toString(), Toast.LENGTH_LONG).show();
    }
}
