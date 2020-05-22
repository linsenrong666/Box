package com.box.lib.router;

import android.os.Parcel;
import android.os.Parcelable;

import com.box.lib.router.url.CommonModule;

import java.util.HashMap;

/**
 * Description
 *
 * @author Linsr 2018/9/8 下午4:32
 */
public class Params extends HashMap<String, Object> implements Parcelable,
        CommonModule.Activity.FragmentContainerParams {

    public Params() {

    }

    public Params(String fragmentPath) {
        put(FRAGMENT_PATH, fragmentPath);
    }

    public Params add(String key, Object value) {
        this.put(key, value);
        return this;
    }

    protected Params(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Params> CREATOR = new Creator<Params>() {
        @Override
        public Params createFromParcel(Parcel in) {
            return new Params(in);
        }

        @Override
        public Params[] newArray(int size) {
            return new Params[size];
        }
    };
}
