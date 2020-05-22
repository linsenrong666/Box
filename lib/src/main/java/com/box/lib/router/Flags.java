package com.box.lib.router;

import android.content.Intent;

import java.util.ArrayList;

/**
 * Description
 *
 * @author Linsr 2018/12/30 下午3:57
 */
public class Flags extends ArrayList<Integer> {

    public static Flags clearTask() {
        Flags flags = new Flags();
        flags.add(Intent.FLAG_ACTIVITY_CLEAR_TASK);
        flags.add(Intent.FLAG_ACTIVITY_NEW_TASK);
        return flags;
    }
}
