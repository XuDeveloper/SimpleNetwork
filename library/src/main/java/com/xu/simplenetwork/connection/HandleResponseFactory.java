package com.xu.simplenetwork.connection;

/**
 * Created by Xu on 2016/11/27.
 */

public class HandleResponseFactory {

    public static IHandleResponse getHandleResponse(String type) {
        switch (type.toLowerCase()) {
            case "default":
                return new DefaultHandleResponse();
            case "redirect":
                return new RedirectHandleResponse();
        }
        return null;
    }
}
