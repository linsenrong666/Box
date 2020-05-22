package com.box.lib.utils;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;

import androidx.core.app.ActivityCompat;

import com.box.lib.app.BoxApp;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.UUID;

/**
 * Description
 *
 * @author Linsr 2018/5/20 上午10:17
 */
public class DeviceUuidFactory {

    private static final String PREFS_FILE = "device_id";
    private static final String PREFS_DEVICE_ID = "device_id";
    private UUID uuid;
    private String deviceType = "0";

    private static final String TYPE_ANDROID_ID = "1";
    private static final String TYPE_DEVICE_ID = "2";
    private static final String TYPE_RANDOM_UUID = "3";

    private DeviceUuidFactory() {
        init(BoxApp.getInstance());
    }

    private volatile static DeviceUuidFactory mInstance;

    public static DeviceUuidFactory getInstance() {
        if (mInstance == null) {
            synchronized (DeviceUuidFactory.class) {
                if (mInstance == null) {
                    mInstance = new DeviceUuidFactory();
                }
            }
        }
        return mInstance;
    }

    @SuppressLint("HardwareIds")
    private void init(Context context) {
        if (uuid == null) {
            synchronized (DeviceUuidFactory.class) {
                if (uuid == null) {
                    final SharedPreferences prefs = context.getSharedPreferences(PREFS_FILE, 0);
                    final String id = prefs.getString(PREFS_DEVICE_ID, null);
                    if (id != null) {
                        uuid = UUID.fromString(id);
                    } else {
                        final String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
                        try {
                            if (!"9774d56d682e549c".equals(androidId)) {
                                deviceType = TYPE_ANDROID_ID;
                                uuid = UUID.nameUUIDFromBytes(androidId.getBytes("utf8"));
                            } else {
                                final String deviceId;
                                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                                    //没有权限
                                    return;
                                }
                                deviceId = ((TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE)).getDeviceId();
                                if (deviceId != null
                                        && !"0123456789abcdef".equals(deviceId.toLowerCase())
                                        && !"000000000000000".equals(deviceId.toLowerCase())) {
                                    deviceType = TYPE_DEVICE_ID;
                                    uuid = UUID.nameUUIDFromBytes(deviceId.getBytes("utf8"));
                                } else {
                                    deviceType = TYPE_RANDOM_UUID;
                                    uuid = UUID.randomUUID();
                                }
                            }
                        } catch (UnsupportedEncodingException e) {
                            deviceType = TYPE_RANDOM_UUID;
                            uuid = UUID.randomUUID();
                        } finally {
                            uuid = UUID.fromString(deviceType + uuid.toString());
                        }
                        prefs.edit().putString(PREFS_DEVICE_ID, uuid.toString()).apply();
                    }
                }
            }
        }
    }

    public UUID getDeviceUUID() {
        return uuid;
    }

    public String getDeviceUUIDStr() {
        return getDeviceUUID().toString();
    }

    public String getDeviceUuidMD5() {
        String res = encrypt(getDeviceUUIDStr());
        return res;
    }

    /**
     * 加密方法
     *
     * @param plaintext 明文
     * @return ciphertext 密文
     */
    public final static String encrypt(String plaintext) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            byte[] btInput = plaintext.getBytes();
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }
}


