package com.ipd.rainbow.widget

import android.content.Context
import android.graphics.Color
import android.text.*
import android.text.style.ClickableSpan
import android.util.AttributeSet
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import com.ipd.jumpbox.jumpboxlibrary.utils.LogUtils
import com.ipd.rainbow.bean.CommentReplyBean


class CommentsView : LinearLayout {
    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init()
    }

    private var hasMore = false //是否还有更多
    private var mDatas: List<CommentReplyBean>? = null
    private var listener: onItemClickListener? = null


    private fun init() {
        orientation = LinearLayout.VERTICAL
    }

    fun setHasMore(hasMore: Boolean) {
        this.hasMore = hasMore
    }

    /**
     * 设置评论列表信息
     *
     * @param list
     */
    fun setList(list: List<CommentReplyBean>) {
        mDatas = list
    }

    fun setOnItemClickListener(listener: onItemClickListener) {
        this.listener = listener
    }

    fun notifyDataSetChanged() {
        removeAllViews()
        if (mDatas == null || mDatas!!.isEmpty()) {
            return
        }
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT)
        layoutParams.setMargins(0, 10, 0, 10)
        mDatas?.forEachIndexed reply@{ index, commentReplyBean ->
            LogUtils.e("tag", "index:$index")
            addView(getView(index), index, layoutParams)
        }
        if (hasMore) {
            addView(getMoreReplyView(mDatas?.size ?: 0), layoutParams)
        }
    }

    private fun getMoreReplyView(position: Int): View {
        val textView = TextView(context)
        textView.textSize = 12f
        textView.setTextColor(Color.parseColor("#525455"))
        textView.text = "查看更多回复"
        textView.setOnClickListener {
            listener?.onItemClick(position, null)
        }
        return textView
    }

    private fun getView(position: Int): View {
        val item = mDatas!![position]
        val replyUser = item.replyName
        var hasReply = false   // 是否有回复
        if (!TextUtils.isEmpty(replyUser)) {
            hasReply = true
        }
        val textView = TextView(context)
        textView.textSize = 12f

        val builder = SpannableStringBuilder()
        val commentUser = item.userName
        if (hasReply) {
            builder.append(setClickableSpan(commentUser, commentUser))
            builder.append(" 回复 ")
            builder.append(setClickableSpan(replyUser, replyUser))

        } else {
            builder.append(setClickableSpan(commentUser, commentUser))
        }
        builder.append(" : ")
        builder.append(setClickableSpanContent(item.content, position))
        textView.text = builder

        textView.setOnClickListener {
            if (listener != null) {
                listener!!.onItemClick(position, item)
            }
        }

        return textView
    }

    /**
     * 设置评论内容点击事件
     */
    fun setClickableSpanContent(item: String, position: Int): SpannableString {
        val string = SpannableString(item)
        val span = object : ClickableSpan() {
            override fun onClick(widget: View) {
            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                // 设置显示的内容文本颜色
                ds.color = Color.parseColor("#525455")
                ds.isUnderlineText = false
            }
        }
        string.setSpan(span, 0, string.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return string
    }

    /**
     * 设置评论用户名字点击事件
     */
    fun setClickableSpan(item: String, userName: String): SpannableString {
        val string = SpannableString(item)
        val span = object : ClickableSpan() {
            override fun onClick(widget: View) {

            }

            override fun updateDrawState(ds: TextPaint) {
                super.updateDrawState(ds)
                // 设置显示的用户名文本颜色
                ds.color = Color.parseColor("#0054B3")
                ds.isUnderlineText = false
            }
        }

        string.setSpan(span, 0, string.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return string
    }

    /**
     * 定义一个用于回调的接口
     */
    interface onItemClickListener {
        fun onItemClick(position: Int, bean: CommentReplyBean?)
    }
}