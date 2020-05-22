package com.box.lib.router.url;

/**
 * Description
 *
 * @author Linsr 2018/9/8 下午4:26
 */
public class CommonModule {

    private static final String ROOT = "/common/";
    private static final String ACTIVITY = ROOT + "activity/";
    private static final String FRAGMENT = ROOT + "fragment/";

    public interface Activity {
        String FRAGMENT_CONTAINER = ACTIVITY + "fragmentContainer";

        interface FragmentContainerParams {
            String TITLE_TEXT = "title_text";
            String HIDE_TITLE_LAYOUT = "hide_title_layout";
            String FRAGMENT_PATH = "fragment_path";
            String PARAMS = "params";
        }
    }

    public interface Fragment {
        String WEB_VIEW = FRAGMENT + "webView";

        interface WebViewParams {
            String URL = "url";
        }
    }
}
