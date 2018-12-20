package com.ipd.taxiu.widget.guideview.component;

import android.view.LayoutInflater;
import android.view.View;

import com.ipd.taxiu.R;
import com.ipd.taxiu.widget.guideview.Component;

/**
 * Created by binIoter on 16/6/17.
 */
public class GuideMineComponent implements Component {

    @Override
    public View getView(LayoutInflater inflater) {
        View view = inflater.inflate(R.layout.layout_guide_mine, null);
        return view;
    }

    @Override
    public int getAnchor() {
        return Component.ANCHOR_TOP;
    }

    @Override
    public int getFitPosition() {
        return Component.FIT_END;
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
