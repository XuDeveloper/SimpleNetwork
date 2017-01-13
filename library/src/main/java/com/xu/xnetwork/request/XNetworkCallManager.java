package com.xu.xnetwork.request;

import com.xu.xnetwork.call.XNetworkCall;
import com.xu.xnetwork.call.XNetworkCallBack;
import com.xu.xnetwork.config.XNetworkConfig;
import com.xu.xnetwork.executor.XNetworkExecutor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;

/**
 * Created by Xu on 2016/12/24.
 */

public class XNetworkCallManager {

    private ArrayList<XNetworkCall> list;
    private XNetworkExecutor executor;

    public XNetworkCallManager(XNetworkExecutor executor) {
        this.list = new ArrayList<>();
        this.executor = executor;
    }

    private void addXNetworkCall(XNetworkCall call) {
        list.add(call);
    }

    public XNetworkCall createNetworkCall(Request request, XNetworkConfig config, XNetworkCallBack callBack) {
        XNetworkCall call = new XNetworkCall(request, config, callBack);
        addXNetworkCall(call);
        return call;
    }

    public void createNetworkCallAndExecute(Request request, XNetworkConfig config, XNetworkCallBack callBack) {
        XNetworkCall call = new XNetworkCall(request, config, callBack);
        addXNetworkCall(call);
        executor.execute(call);
    }

    /**
     * 取消所有的网络请求(包括正在执行的)
     */
    public void cancelAllRequest() {
        BlockingQueue queue = executor.getQueue();
        for (int i = list.size() - 1; i >= 0; i--) {
            XNetworkCall call = list.get(i);
            if (queue.contains(call)) {
                queue.remove(call);
            } else {
                call.disconnect();
            }
        }
        list.clear();
    }

    /**
     * 取消未执行的网络请求
     */
    public void cancelBlockingNetworkCall() {
        // 取交集(即取出那些在线程池的阻塞队列中等待执行的请求)
        List<XNetworkCall> intersection = (List<XNetworkCall>) list.clone();
        intersection.retainAll(executor.getQueue());
        // 分别删除
        executor.getQueue().removeAll(intersection);
        list.removeAll(intersection);
    }

    /**
     * 取消指定的网络请求
     */
    public void cancelDesignatedNetworkCall(XNetworkCall call) {
        if (!executor.removeTaskFromQueue(call)) {
            call.disconnect();
        }
    }

}
