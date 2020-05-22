package com.box.lib.router.url;

/**
 * Description
 *
 * @author Linsr 2018/7/10 下午6:00
 */
public class MainModule {

    private static final String ROOT = "/main/";
    private static final String ACTIVITY = ROOT + "activity/";
    private static final String FRAGMENT = ROOT + "fragment/";

    public interface Activity {
        String MAIN = ACTIVITY + "main";

        interface MainParams {
            String SELECT_PAGE = "select_page";
            int HOME_PAGE = 0;
            int CART_PAGE = 3;
        }
    }

    public interface Fragment {
        String HOME = FRAGMENT + "home";

    }
}
