package com.xu.xnetwork;

import android.app.Activity;

import com.xu.xnetwork.call.XNetworkCall;
import com.xu.xnetwork.call.XNetworkCallBack;
import com.xu.xnetwork.config.XNetworkConfig;
import com.xu.xnetwork.executor.XNetworkExecutor;
import com.xu.xnetwork.request.Request;
import com.xu.xnetwork.request.XRequestManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xu on 2016/12/24.
 */

public class XNetworkClient {

    private static final String TAG = "XNetworkClient";

    private static XNetworkConfig mConfig;
    private static Map<Activity, XRequestManager> managerMap;
    private static XNetworkExecutor executor;

    public XNetworkClient(XNetworkConfig config) {
        this.mConfig = config;
        managerMap = new HashMap<>();
        executor = new XNetworkExecutor();
    }

//    public static Response newRequest(Activity activity, Request request) {
//        XRequestManager manager = checkRequestManager(activity, true);
//        manager.addSynRequest(request);
//        XSynNetworkCall call = new XSynNetworkCall(request, mConfig);
//        executor.executeSyn(call);
//        return call.getResponse();
//    }

    public static void newRequest(Activity activity, Request request, XNetworkCallBack callBack) {
        if (callBack == null) throw new NullPointerException("callback is null!");
        XRequestManager manager = checkRequestManager(activity, true);
        XNetworkCall call = manager.createNetworkCall(request, mConfig, callBack);
        executor.executeAsync(call);
    }

    /**
     * 访问activity对应的RequestManager对象
     *
     * @param activity
     * @param createNew 当RequestManager对象为null时是否创建新的RequestManager对象
     * @return
     */
    private static XRequestManager checkRequestManager(Activity activity, boolean createNew) {
        XRequestManager manager;
        if ((manager = managerMap.get(activity)) == null) {
            if (createNew) {
                manager = new XRequestManager();
                managerMap.put(activity, manager);
            } else {
                throw new NullPointerException(activity.getClass().getSimpleName() + "'s RequestManager is null!");
            }
        }
        return manager;
    }
}
