package com.xu.simplenetwork.call;

import com.xu.simplenetwork.SimpleNetworkClient;
import com.xu.simplenetwork.request.Request;

/**
 * Created by Xu on 2016/10/15.
 */

public class AsyncCall extends NetworkCall {

    public AsyncCall(SimpleNetworkClient client, Request request) {
        this.client = client;
        this.request = request;
    }

    public AsyncCall(Request request) {
        this.client = getDefaultClient();
        this.request = request;
    }

    public void execute(AsyncCallBack asyncCallBack) {
        client.executor().enqueue();
    }

//    class NetworkRunnable implements Runnable {
//
//        private final AsyncCallBack callBack;
//
//        public NetworkRunnable(AsyncCallBack callBack) {
//            this.callBack = callBack;
//        }
//
//        @Override
//        public void run() {
//            try {
//                SimpleNetworkClient client = getClient();
//                Request request = getRequest();
//                HttpContext context = new HttpContext(client, request);
//                if (context.getResponseCode() == HttpURLConnection.HTTP_OK) {
//                    if (context.isGet()) {
//                        message = Utils.getStringByInputStream(context.getInputStream());
//                    } else if (context.isPost()) {
//                        context.setDefaultRequestProperty();
//                        message = Utils.getStringByInputStream(context.getInputStream());
//                    }
//                }
//            } catch (Exception e) {
//
//            } finally {
//
//            }
//        }
//    }
}
