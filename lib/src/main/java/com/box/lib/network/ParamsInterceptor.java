package com.box.lib.network;


import androidx.annotation.NonNull;

import com.box.lib.utils.DeviceUuidFactory;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Description
 *
 * @author Linsr 2018/12/15 下午4:44
 */
public class ParamsInterceptor implements Interceptor {

    private DeviceUuidFactory mDeviceUuidFactory;

    ParamsInterceptor() {
        mDeviceUuidFactory = DeviceUuidFactory.getInstance();
    }

    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        if (request.body() instanceof FormBody) {
            FormBody formBody = (FormBody) request.body();
            FormBody.Builder builder = new FormBody.Builder();
            assert formBody != null;
            for (int i = 0; i < formBody.size(); i++) {
                builder.add(formBody.name(i), formBody.value(i));
            }
//            if (AppConfig.getInstance().isLoggedIn()) {
//                builder.add("token", PrefsUtils.getSharedString(ApplicationEx.getInstance(),
//                        UserInfoKey.TOKEN));
//                builder.add("user_id", PrefsUtils.getSharedString(ApplicationEx.getInstance(),
//                        UserInfoKey.USER_ID));
//            }
            builder.add("sign", mDeviceUuidFactory.getDeviceUuidMD5());
            formBody = builder.build();
            request = request.newBuilder().post(formBody).build();
        }
        return chain.proceed(request);
    }
}
