
package com.uade.be_tourapp.utils;

import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

/**
 * Base64 utils to work with images.
 * <p>
 * Part of <i>Matías Uriel Gluck</i> java utils library.
 */
public class Base64Utils {
    private static final Map<String, String> FILE_TYPE_MAP;

    static {
        FILE_TYPE_MAP = new HashMap<>();
        FILE_TYPE_MAP.put("FFD8FF", "jpg");
        FILE_TYPE_MAP.put("89504E47", "png");
        FILE_TYPE_MAP.put("47494638", "gif");
        FILE_TYPE_MAP.put("424D", "bmp");
        FILE_TYPE_MAP.put("52494646", "webp");
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length && i < 4; i++) {
            sb.append(String.format("%02X", bytes[i]));
        }
        return sb.toString();
    }

    public static String getFileExtension(byte[] bytes) {
        String fileTypeHex = bytesToHex(bytes);
        for (Map.Entry<String, String> entry : FILE_TYPE_MAP.entrySet()) {
            if (fileTypeHex.startsWith(entry.getKey())) {
                return entry.getValue();
            }
        }
        return null;
    }

    public static byte[] base64ToBytes(String data) {
        if (data.startsWith("data:image")) {
            data = data.substring(data.indexOf(",") + 1);
        }

        return Base64.getDecoder().decode(data);
    }

    public static String bytesToBase64(byte[] bytes) {
        return "data:image/" + getFileExtension(bytes) + ";base64," + new String(Base64.getEncoder().encode(bytes));
    }
}
