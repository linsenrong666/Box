package com.box.lib.router.url;

import com.alibaba.android.arouter.facade.template.IProvider;

/**
 * Description
 *
 * @author Linsr 2018/8/6 下午5:01
 */
public class LoginModule {

    private static final String ROOT = "/login/";
    private static final String ACTIVITY = ROOT + "activity/";
    private static final String FRAGMENT = ROOT + "fragment/";

    public interface Activity {
        String LOGIN = ACTIVITY + "login";
        String REGISTER = ACTIVITY + "register";
        String FIND_PASSWORD = ACTIVITY + "find_password";
    }

    public interface Fragment {
    }

    public interface Service {
        String WRAPPER = ROOT + "loginWrapper";

        interface LoginWrapper extends IProvider {
            void logout(android.app.Activity activity);
        }

    }


}