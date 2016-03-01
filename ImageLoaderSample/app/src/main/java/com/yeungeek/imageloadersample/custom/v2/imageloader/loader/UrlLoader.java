package com.yeungeek.imageloadersample.custom.v2.imageloader.loader;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.yeungeek.imageloadersample.custom.v2.imageloader.core.BitmapDecoder;
import com.yeungeek.imageloadersample.custom.v2.imageloader.core.BitmapRequest;
import com.yeungeek.imageloadersample.custom.util.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yeungeek on 2016/2/11.
 */
public class UrlLoader extends AbsLoader {
    @Override
    protected Bitmap onLoadImage(BitmapRequest request) {
        final String imageUri = request.imageUri;
        InputStream is = null;
        try {
            URL url = new URL(imageUri);
            final HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            is = connection.getInputStream();
            is.mark(is.available());

            final InputStream inputStream = is;
            BitmapDecoder decoder = new BitmapDecoder() {
                @Override
                public Bitmap decodeBitmapWithOption(BitmapFactory.Options options) {
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null, options);

                    if (options.inJustDecodeBounds) {
                        try {
                            inputStream.reset();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        connection.disconnect();
                    }
                    return bitmap;
                }
            };

            return decoder.decodeBitmap(request.reqWidth, request.reqHeight);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            IOUtils.closeQuietly(is);
        }
        return null;
    }
}
