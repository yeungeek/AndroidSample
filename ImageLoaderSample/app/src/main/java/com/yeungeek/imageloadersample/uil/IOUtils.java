package com.yeungeek.imageloadersample.uil;

import java.io.Closeable;

/**
 * Created by yeungeek on 2016/2/11.
 */
public class IOUtils {
    public static void closeQuietly(/*Auto*/Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (RuntimeException rethrown) {
                throw rethrown;
            } catch (Exception ignored) {
            }
        }
    }
}
