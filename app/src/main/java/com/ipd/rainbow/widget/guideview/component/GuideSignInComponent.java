package com.ipd.rainbow.widget.guideview.component;

import android.view.LayoutInflater;
import android.view.View;

import com.ipd.rainbow.R;
import com.ipd.rainbow.widget.guideview.Component;

/**
 * Created by binIoter on 16/6/17.
 */
public class GuideSignInComponent implements Component {

    @Override
    public View getView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.layout_guide_signin, null);
        return view;
    }

    @Override
    public int getAnchor() {
        return Component.ANCHOR_LEFT;
    }

    @Override
    public int getFitPosition() {
        return Component.FIT_START;
    }

    @Override
    public int getXOffset() {
        return -30;
    }

    @Override
    public int getYOffset() {
        return 0;
    }
}
