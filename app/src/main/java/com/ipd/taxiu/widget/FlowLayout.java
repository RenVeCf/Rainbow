package com.ipd.taxiu.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.ipd.jumpbox.jumpboxlibrary.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class FlowLayout extends ViewGroup {
	public Context context;
	public int horizontalSpac = DensityUtil.dip2px(getContext(),12);
	public int verticalSpac = DensityUtil.dip2px(getContext(),12);
	public static final String TAG = "FlowLayout";

	public FlowLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	public FlowLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public FlowLayout(Context context) {
		super(context);
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
		// 控件宽度
		int width = MeasureSpec.getSize(widthMeasureSpec);
		viewWidth = width - getPaddingLeft() - getPaddingRight() - horizontalSpac * 2;
//		Log.e(TAG, "ViewWidth:" + viewWidth);
		if (lineList != null) {
			lineList.clear();
		}

		Line line = null;
		for (int i = 0; i < getChildCount(); i++) {
			View childView = getChildAt(i);
			childView.measure(0, 0);

			if (line == null) {
				line = new Line();
			}

			if (line.viewList.isEmpty()) {
				line.addView(childView);
				if (i == (getChildCount() - 1)) {
					lineList.add(line);
				}
			} else {
				// 判断该行所有子view宽度是否大于控件宽度
				if (line.width + childView.getMeasuredWidth() + horizontalSpac > viewWidth) {
					lineList.add(line);
					// 新建行
					line = new Line();
					line.addView(childView);

					if (i == (getChildCount() - 1)) {
						lineList.add(line);
					}
				} else {
					line.addView(childView);

					if (i == (getChildCount() - 1)) {
						lineList.add(line);
					}
				}
			}

		}

		int height = getPaddingTop() + getPaddingBottom();
		for (int i = 0; i < lineList.size(); i++) {
			height += lineList.get(i).height;
		}
//		height += lineList.size() * verticalSpac + verticalSpac;
		height += lineList.size() * verticalSpac;
		setMeasuredDimension(width, height);

	}




	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {

		int paddingLeft = getPaddingLeft();
		int paddingTop = getPaddingTop();

		for (int i = 0; i < lineList.size(); i++) {
			Line line = lineList.get(i);// 取出每一行

			if (i > 0) {
				paddingTop += lineList.get(i - 1).height + verticalSpac;
			}

			List<View> viewList = line.viewList;
			int remainWidth = getMeasuredWidth() - getPaddingLeft() - getPaddingRight() - line.width;
			float perSpace = remainWidth / viewList.size();
			if (perSpace > 150){
				perSpace = 0;
			}

			for (int j = 0; j < viewList.size(); j++) {
				View chilidView = viewList.get(j);// 取出每个子view
				int widthMeasureSpec = MeasureSpec.makeMeasureSpec((int) (chilidView.getMeasuredWidth() + perSpace),
						MeasureSpec.EXACTLY);
				chilidView.measure(widthMeasureSpec, 0);

				if (j == 0) {
					chilidView.layout(paddingLeft, paddingTop, paddingLeft + chilidView.getMeasuredWidth(),
							paddingTop + chilidView.getMeasuredHeight());
				} else {
					View preView = viewList.get(j - 1);// 获取前一个子view
					int left = preView.getRight() + horizontalSpac;
					chilidView.layout(left, preView.getTop(), left + chilidView.getMeasuredWidth(),
							preView.getBottom());
				}
			}
		}

	}

	public List<Line> lineList = new ArrayList<Line>();

	private int viewWidth;

	class Line {
		public int width;
		public int height;
		public List<View> viewList;

		public Line() {
			viewList = new ArrayList<>();
		}

		public void addView(View view) {
			if (!viewList.contains(view)) {
				viewList.add(view);
			}

			if (viewList.size() == 1) {
				// 第一次添加
				width += view.getMeasuredWidth();
				height = view.getMeasuredHeight();
			} else {
				width += view.getMeasuredWidth() + horizontalSpac;
			}
			height = Math.max(height, view.getMeasuredHeight());

		}

	}
}
