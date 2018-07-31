package com.ipd.taxiu.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.jumpbox.jumpboxlibrary.utils.LogUtils
import com.ipd.taxiu.R
import com.ipd.taxiu.utils.DateUtils
import kotlinx.android.synthetic.main.activity_sign_in.view.*
import kotlinx.android.synthetic.main.item_sign_in.view.*
import java.text.SimpleDateFormat
import java.util.*

class SignInView : ConstraintLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    fun setDate(time: Long) {
        tv_date.text = "${DateUtils.getYear(time)}年${DateUtils.getMonth(time)}月"
        sign_in_recycler_view.adapter = SigninAdapter(time)
    }


    inner class SigninAdapter(val time: Long) : RecyclerView.Adapter<SigninAdapter.ViewHolder>() {
        private var emptyCount = 0

        override fun getItemCount(): Int {
            val calendar = Calendar.getInstance()
            calendar.set(Calendar.YEAR, DateUtils.getYear(time))
            calendar.set(Calendar.MONTH, DateUtils.getMonth(time) - 1)
            calendar.set(Calendar.DAY_OF_MONTH, 1)
            emptyCount = calendar.get(Calendar.DAY_OF_WEEK) - 1
            LogUtils.e("tag", "${SimpleDateFormat("yyyy-MM-dd").format(calendar.time)},$emptyCount")
            val days = DateUtils.getDays(DateUtils.getYear(time), DateUtils.getMonth(time))
            return days + emptyCount
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sign_in, parent, false))
        }


        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (position < emptyCount) {
                holder.itemView.tv_day.text = ""
                return
            }
            holder.itemView.tv_day.text = "${position - emptyCount + 1}"
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }

}