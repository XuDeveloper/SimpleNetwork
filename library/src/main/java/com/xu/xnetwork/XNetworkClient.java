package com.xu.xnetwork;

import android.app.Activity;

import com.xu.xnetwork.call.XNetworkCall;
import com.xu.xnetwork.call.XNetworkCallBack;
import com.xu.xnetwork.config.XNetworkConfig;
import com.xu.xnetwork.executor.XNetworkExecutor;
import com.xu.xnetwork.request.Request;
import com.xu.xnetwork.request.XNetworkCallManager;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Xu on 2016/12/24.
 */

public class XNetworkClient {

    private static final String TAG = "XNetworkClient";

    private static XNetworkConfig mConfig;
    private static Map<Activity, XNetworkCallManager> managerMap;
    private static XNetworkExecutor executor;

    public XNetworkClient(XNetworkConfig config) {
        this.mConfig = config;
        managerMap = new HashMap<>();
        executor = new XNetworkExecutor();
    }

    public static void newRequest(Activity activity, Request request, XNetworkCallBack callBack) {
        if (callBack == null) throw new NullPointerException("callback is null!");
        XNetworkCallManager manager = checkNetworkCallManager(activity, executor, true);
        manager.createNetworkCallAndExecute(request, mConfig, callBack);
    }

    /**
     * 访问activity对应的XNetworkCallManager对象
     *
     * @param activity
     * @param createNew 当XNetworkCallManager对象为null时是否创建新的XNetworkCallManager对象
     * @return
     */
    private static XNetworkCallManager checkNetworkCallManager(Activity activity, XNetworkExecutor executor, boolean createNew) {
        XNetworkCallManager manager;
        if ((manager = managerMap.get(activity)) == null) {
            if (createNew) {
                manager = new XNetworkCallManager(executor);
                managerMap.put(activity, manager);
            } else {
                throw new NullPointerException(activity.getClass().getSimpleName() + "'s XNetworkCallManager is null!");
            }
        }
        return manager;
    }

    /**
     * 取消指定Activity中发起的所有HTTP请求
     *
     * @param activity
     */
    public static void cancelAllNetworkCall(Activity activity) {
        checkNetworkCallManager(activity, executor, false).cancelAllRequest();
    }

    /**
     * 取消线程池中整个阻塞队列所有HTTP请求
     */
    public static void cancelAllNetworkCall() {
        executor.removeAllTask();
    }

    /**
     * 取消指定Activity中未执行的请求
     *
     * @param activity
     */
    public static void cancelBlockingNetworkCall(Activity activity) {
        checkNetworkCallManager(activity, executor, false).cancelBlockingNetworkCall();
    }

    /**
     * 取消指定请求
     *
     * @param activity
     * @param call
     */
    public static void cancelDesignatedNetworkCall(Activity activity, XNetworkCall call) {
        checkNetworkCallManager(activity, executor, false).cancelDesignatedNetworkCall(call);
    }

    /**
     * 关闭线程池，并等待任务执行完成，不接受新任务
     */
    public static void shutdown() {
        executor.shutdown();
    }

    /**
     * 关闭，立即关闭，并挂起所有正在执行的线程，不接受新任务
     */
    public static void shutdownRightnow() {
        executor.shutdownRightnow();
    }


}
