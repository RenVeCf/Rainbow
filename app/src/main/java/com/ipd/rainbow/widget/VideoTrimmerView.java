package com.ipd.rainbow.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.ipd.jumpbox.jumpboxlibrary.utils.LogUtils;
import com.ipd.rainbow.R;
import com.ipd.rainbow.adapter.VideoTrimmerAdapter;
import com.ipd.rainbow.utils.BackgroundExecutor;
import com.ipd.rainbow.utils.SingleCallback;
import com.ipd.rainbow.utils.UiThreadExecutor;
import com.ipd.rainbow.utils.trimvideo.IVideoTrimmerView;
import com.ipd.rainbow.utils.trimvideo.TrimVideoListener;
import com.ipd.rainbow.utils.trimvideo.TrimVideoUtil;

import java.util.ArrayList;


public class VideoTrimmerView extends FrameLayout implements IVideoTrimmerView {

    private static final String TAG = VideoTrimmerView.class.getSimpleName();

    private int mMaxWidth = TrimVideoUtil.INSTANCE.getVIDEO_FRAMES_WIDTH();
    private Context mContext;
    private RelativeLayout mLinearVideo;
    private VideoView mVideoView;
    private RecyclerView mVideoThumbRecyclerView;
    private RangeSeekBarView mRangeSeekBarView;
    private LinearLayout mSeekBarLayout;
    private ImageView mRedProgressIcon;
    private TextView mVideoShootTipTv;
    private float mAverageMsPx;//每毫秒所占的px
    private float averagePxMs;//每px所占用的ms毫秒
    private Uri mSourceUri;
    private TrimVideoListener mOnTrimVideoListener;
    private int mDuration = 0;
    private VideoTrimmerAdapter mVideoThumbAdapter;
    private boolean isFromRestore = false;
    //new
    private long mLeftProgressPos, mRightProgressPos;//左右进度
    private long mRedProgressBarPos = 0;
    private long scrollPos = 0;
    private int mScaledTouchSlop;
    private int lastScrollX;
    private boolean isSeeking;
    private boolean isOverScaledTouchSlop;
    private int mThumbsTotalCount;
    private Handler mAnimationHandler = new Handler();

    public VideoTrimmerView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VideoTrimmerView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        this.mContext = context;
        LayoutInflater.from(context).inflate(R.layout.video_trimmer_view, this, true);

        mLinearVideo = findViewById(R.id.layout_surface_view);
        mVideoView = findViewById(R.id.video_loader);
        mSeekBarLayout = findViewById(R.id.seekBarLayout);
        mRedProgressIcon = findViewById(R.id.positionIcon);
        mVideoShootTipTv = findViewById(R.id.video_shoot_tip);

        mLinearVideo.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                mLinearVideo.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                ViewGroup.LayoutParams params = mLinearVideo.getLayoutParams();
                params.width = mLinearVideo.getMeasuredWidth();
                params.height = params.width;
                mLinearVideo.setLayoutParams(params);
            }

        });

        mVideoThumbRecyclerView = findViewById(R.id.video_frames_recyclerView);
        mVideoThumbRecyclerView.setLayoutManager(new LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false));
        mVideoThumbAdapter = new VideoTrimmerAdapter(mContext, new ArrayList<Bitmap>());
        mVideoThumbRecyclerView.setAdapter(mVideoThumbAdapter);
        mVideoThumbRecyclerView.addOnScrollListener(mOnScrollListener);


        mAnimationHandler.post(mAnimationRunnable);
        setUpListeners();
    }

    private void initRangeSeekBarView() {
        if (mRangeSeekBarView != null) return;
        int rangeWidth;
        mLeftProgressPos = 0;
        if (mDuration <= TrimVideoUtil.INSTANCE.getMAX_SHOOT_DURATION()) {
            mThumbsTotalCount = TrimVideoUtil.INSTANCE.getMAX_COUNT_RANGE();
            rangeWidth = mMaxWidth;
            mRightProgressPos = mDuration;
        } else {
            mThumbsTotalCount = (int) (mDuration * 1.0f / (TrimVideoUtil.INSTANCE.getMAX_SHOOT_DURATION() * 1.0f) * TrimVideoUtil.INSTANCE.getMAX_COUNT_RANGE());
            rangeWidth = mMaxWidth / TrimVideoUtil.INSTANCE.getMAX_COUNT_RANGE() * mThumbsTotalCount;
            mRightProgressPos = TrimVideoUtil.INSTANCE.getMAX_SHOOT_DURATION();
        }
        mVideoThumbRecyclerView.addItemDecoration(new SpacesItemDecoration2(TrimVideoUtil.INSTANCE.getRECYCLER_VIEW_PADDING(), mThumbsTotalCount));
        mRangeSeekBarView = new RangeSeekBarView(mContext, mLeftProgressPos, mRightProgressPos);
        mRangeSeekBarView.setSelectedMinValue(mLeftProgressPos);
        mRangeSeekBarView.setSelectedMaxValue(mRightProgressPos);
        mRangeSeekBarView.setStartEndTime(mLeftProgressPos, mRightProgressPos);
        mRangeSeekBarView.setMinShootTime(TrimVideoUtil.INSTANCE.getMIN_SHOOT_DURATION());
        mRangeSeekBarView.setNotifyWhileDragging(true);
        mRangeSeekBarView.setOnRangeSeekBarChangeListener(mOnRangeSeekBarChangeListener);
        mSeekBarLayout.addView(mRangeSeekBarView);

        mAverageMsPx = mDuration * 1.0f / rangeWidth * 1.0f;
        averagePxMs = (mMaxWidth * 1.0f / (mRightProgressPos - mLeftProgressPos));
    }

    public void initVideoByURI(final Uri videoURI) {
        mSourceUri = videoURI;
        mVideoView.setVideoURI(mSourceUri);
        mVideoView.requestFocus();
        mVideoShootTipTv.setText(String.format(mContext.getResources().getString(R.string.video_shoot_tip), TrimVideoUtil.INSTANCE.getVIDEO_MAX_TIME()));
    }

    private void startShootVideoThumbs(final Context context, final Uri videoUri, int totalThumbsCount, long startPosition, long endPosition) {
        TrimVideoUtil.INSTANCE.backgroundShootVideoThumb(context, videoUri, totalThumbsCount, startPosition, endPosition,
                new SingleCallback<Bitmap, Integer>() {
                    @Override
                    public void onSingleCallback(final Bitmap bitmap, final Integer interval) {
                        UiThreadExecutor.runTask("", new Runnable() {
                            @Override
                            public void run() {
                                if (bitmap == null) return;
                                mVideoThumbAdapter.addBitmap(bitmap);
                            }
                        }, 0L);
                    }
                });
    }

    private void videoPrepared(MediaPlayer mp) {
        mDuration = mVideoView.getDuration();
        seekTo((int) mRedProgressBarPos);

        if (!isFromRestore) {
            initRangeSeekBarView();
            startShootVideoThumbs(mContext, mSourceUri, mThumbsTotalCount, 0, mDuration);
        } else {
            isFromRestore = false;
        }
        mp.start();
    }

    private void videoCompleted() {
        seekTo(mLeftProgressPos);
    }

    private void onVideoReset() {
        mVideoView.pause();
    }

    private void playVideoOrPause() {
        mRedProgressBarPos = mVideoView.getCurrentPosition();
        LogUtils.e("tag", "progress:" + mRedProgressBarPos);
        if (mVideoView.isPlaying()) {
            mVideoView.pause();
        } else {
            mVideoView.start();
        }
    }

    public void onVideoPause() {
        if (mVideoView.isPlaying()) {
            seekTo(mLeftProgressPos);//复位
            mVideoView.pause();
            mRedProgressIcon.setVisibility(GONE);
        }
    }

    public void setOnTrimVideoListener(TrimVideoListener onTrimVideoListener) {
        mOnTrimVideoListener = onTrimVideoListener;
    }

    private void setUpListeners() {
        findViewById(R.id.tv_confirm).setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                onSaveClicked();
            }
        });
        mVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            @Override
            public void onPrepared(MediaPlayer mp) {
                videoPrepared(mp);
            }
        });
        mVideoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                videoCompleted();
            }
        });
    }

    private void onSaveClicked() {
        if (mRightProgressPos - mLeftProgressPos < TrimVideoUtil.INSTANCE.getMIN_SHOOT_DURATION()) {
            Toast.makeText(mContext, "视频长不足3秒,无法上传", Toast.LENGTH_SHORT).show();
        } else {
            mVideoView.pause();
            TrimVideoUtil.INSTANCE.trim(mSourceUri.getPath(), TrimVideoUtil.INSTANCE.getTrimmedVideoPath(mContext), mLeftProgressPos, mRightProgressPos, mOnTrimVideoListener);
        }
    }

    private void seekTo(long msec) {
        mVideoView.seekTo((int) msec);
    }

    private boolean getRestoreState() {
        return isFromRestore;
    }

    public void setRestoreState(boolean fromRestore) {
        isFromRestore = fromRestore;
    }

    private final RangeSeekBarView.OnRangeSeekBarChangeListener mOnRangeSeekBarChangeListener = new RangeSeekBarView.OnRangeSeekBarChangeListener() {
        @Override
        public void onRangeSeekBarValuesChanged(RangeSeekBarView bar, long minValue, long maxValue, int action, boolean isMin,
                                                RangeSeekBarView.Thumb pressedThumb) {
            mLeftProgressPos = minValue + scrollPos;
            mRedProgressBarPos = mLeftProgressPos;
            mRightProgressPos = maxValue + scrollPos;
            switch (action) {
                case MotionEvent.ACTION_DOWN:
                    isSeeking = false;
                    onVideoPause();
                    break;
                case MotionEvent.ACTION_MOVE:
                    isSeeking = true;
                    break;
                case MotionEvent.ACTION_UP:
                    isSeeking = false;
                    seekTo((int) mLeftProgressPos);
                    playVideoOrPause();
                    break;
                default:
                    break;
            }

            mRangeSeekBarView.setStartEndTime(mLeftProgressPos, mRightProgressPos);
        }
    };

    private final RecyclerView.OnScrollListener mOnScrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                //滑动结束
                playVideoOrPause();
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            if (mVideoView.isPlaying()) {
                playVideoOrPause();
            }
            isSeeking = false;
            int scrollX = calcScrollXDistance();
            //达不到滑动的距离
            if (Math.abs(lastScrollX - scrollX) < mScaledTouchSlop) {
                isOverScaledTouchSlop = false;
                return;
            }
            isOverScaledTouchSlop = true;
            //初始状态,why ? 因为默认的时候有35dp的空白！
            if (scrollX == -TrimVideoUtil.INSTANCE.getRECYCLER_VIEW_PADDING()) {
                scrollPos = 0;
            } else {
                isSeeking = true;
                scrollPos = (long) (mAverageMsPx * (TrimVideoUtil.INSTANCE.getRECYCLER_VIEW_PADDING() + scrollX));
                mLeftProgressPos = mRangeSeekBarView.getSelectedMinValue() + scrollPos;
                mRightProgressPos = mRangeSeekBarView.getSelectedMaxValue() + scrollPos;
//                Log.e(TAG, "onScrolled >>>> mLeftProgressPos = " + mLeftProgressPos);
//                Log.e(TAG, "onScrolled >>>> mRightProgressPos = " + mRightProgressPos);
                mRedProgressBarPos = mLeftProgressPos;
                if (mVideoView.isPlaying()) {
                    mVideoView.pause();
                }
                mRedProgressIcon.setVisibility(GONE);
                seekTo(mLeftProgressPos);
                mRangeSeekBarView.setStartEndTime(mLeftProgressPos, mRightProgressPos);
                mRangeSeekBarView.invalidate();
            }
            lastScrollX = scrollX;
        }

    };

    /**
     * 水平滑动了多少px
     */
    private int calcScrollXDistance() {
        LinearLayoutManager layoutManager = (LinearLayoutManager) mVideoThumbRecyclerView.getLayoutManager();
        int position = layoutManager.findFirstVisibleItemPosition();
        View firstVisibleChildView = layoutManager.findViewByPosition(position);
        int itemWidth = firstVisibleChildView.getWidth();
        return (position) * itemWidth - firstVisibleChildView.getLeft();
    }

    private Runnable mAnimationRunnable = new Runnable() {

        @Override
        public void run() {
            updateVideoProgress();
        }
    };

    private void updateVideoProgress() {
        long currentPosition = mVideoView.getCurrentPosition();
//        Log.e(TAG, "updateVideoProgress currentPosition = " + currentPosition + ",mRightProgressPos:" + mRightProgressPos);


        if (mVideoView.isPlaying()) {
            if (currentPosition >= mRightProgressPos) {
                mRedProgressIcon.setVisibility(GONE);
                seekTo(mLeftProgressPos);//复位
                mVideoView.pause();
            } else {
                mRedProgressIcon.setVisibility(View.VISIBLE);
                mRedProgressBarPos = (int) (TrimVideoUtil.INSTANCE.getRECYCLER_VIEW_PADDING() + (currentPosition - scrollPos) * averagePxMs);
//                Log.e("tag", "mRedProgressBarPos:" + mRedProgressBarPos);
                LayoutParams params = (LayoutParams) mRedProgressIcon.getLayoutParams();
                params.leftMargin = (int) mRedProgressBarPos;
                mRedProgressIcon.setLayoutParams(params);
            }

        } else {
            mRedProgressIcon.setVisibility(View.GONE);
        }
        mAnimationHandler.postDelayed(mAnimationRunnable, 100);
    }


    @Override
    public void onStop(boolean isBackPressed) {
        if (isBackPressed) {
            mVideoView.stopPlayback();
        }
    }

    @Override
    public void onDestroy() {
        BackgroundExecutor.cancelAll("", true);
        UiThreadExecutor.cancelAll("");
        mAnimationHandler.removeCallbacksAndMessages(null);
        mAnimationHandler = null;
    }
}
