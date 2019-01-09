package com.ipd.rainbow.utils;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import okio.Buffer;
import okio.BufferedSink;
import okio.ForwardingSink;
import okio.Okio;

public class ProgressRequestBody extends RequestBody {
    private RequestBody mOriginalBody;
    private long mTotalLength = -1;
    private long mCurrentLength = -1;
    private UploadCallbacks mListener;


    public interface UploadCallbacks {
        void onProgressUpdate(int percentage);
    }

    public ProgressRequestBody(RequestBody requestBody, UploadCallbacks listener) {
        mOriginalBody = requestBody;
        mListener = listener;
    }


    @Nullable
    @Override
    public MediaType contentType() {
        return mOriginalBody.contentType();
    }

    @Override
    public long contentLength() throws IOException {
        return mOriginalBody.contentLength();
    }

    @Override
    public void writeTo(BufferedSink sink) throws IOException {
        final Handler handler = new Handler(Looper.getMainLooper());
        // 获取总的长度
        mTotalLength = contentLength();

        ForwardingSink forwardingSink = new ForwardingSink(sink) {
            @Override
            public void write(Buffer source, long byteCount) throws IOException {
                super.write(source, byteCount);
                mCurrentLength += byteCount;
                handler.post(new ProgressUpdater(mCurrentLength, mTotalLength));
            }
        };
        sink = Okio.buffer(forwardingSink);
        mOriginalBody.writeTo(sink);
        sink.flush();
    }


    private class ProgressUpdater implements Runnable {
        private long mUploaded;
        private long mTotal;

        public ProgressUpdater(long uploaded, long total) {
            mUploaded = uploaded;
            mTotal = total;
        }

        @Override
        public void run() {
            mListener.onProgressUpdate((int) (100 * mUploaded / mTotal));
        }
    }
}
