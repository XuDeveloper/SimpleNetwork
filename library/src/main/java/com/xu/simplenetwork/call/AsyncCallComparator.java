package com.xu.simplenetwork.call;

import java.util.Comparator;

/**
 * Created by Xu on 2016/11/3.
 */

public class AsyncCallComparator<T> implements Comparator<T> {

    @Override
    public int compare(T o1, T o2) {
        if ((o1 instanceof AsyncCall) && (o2 instanceof AsyncCall)) {
            AsyncCall task1 = (AsyncCall) o1;
            AsyncCall task2 = (AsyncCall) o2;
            int result;
            if ((task1.getAsyncCallpriority() == 0) && (task2.getAsyncCallpriority() == 0)) {
                result = (int) (task2.getCreateTime() - task1.getCreateTime());
            } else {
                result = task2.getAsyncCallpriority() - task1.getAsyncCallpriority();
            }
            return result;
        }
        return 0;
    }
}
