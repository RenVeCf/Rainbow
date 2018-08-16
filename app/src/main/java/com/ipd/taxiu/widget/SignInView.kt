package com.ipd.taxiu.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.SignInDayBean
import com.ipd.taxiu.utils.DateUtils
import com.ipd.taxiu.utils.StringUtils
import kotlinx.android.synthetic.main.activity_sign_in.view.*
import kotlinx.android.synthetic.main.item_sign_in.view.*
import java.util.*

class SignInView : ConstraintLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    fun setDate(time: Long, signInDayList: List<SignInDayBean>) {
        tv_date.text = "${DateUtils.getYear(time)}年${DateUtils.getMonth(time)}月"
        sign_in_recycler_view.adapter = SigninAdapter(time, signInDayList)
    }


    inner class SigninAdapter(val time: Long, private val signInDayList: List<SignInDayBean>) : RecyclerView.Adapter<SigninAdapter.ViewHolder>() {
        private var emptyCount = 0

        private val mCalendar by lazy { Calendar.getInstance() }

        init {
            mCalendar.set(Calendar.YEAR, DateUtils.getYear(time))
            mCalendar.set(Calendar.MONTH, DateUtils.getMonth(time) - 1)
            mCalendar.set(Calendar.DAY_OF_MONTH, 1)
            emptyCount = mCalendar.get(Calendar.DAY_OF_WEEK) - 1
        }

        override fun getItemCount(): Int {
            val days = DateUtils.getDays(DateUtils.getYear(time), DateUtils.getMonth(time))
            return days + emptyCount
        }

        override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
            return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_sign_in, parent, false))
        }


        override fun onBindViewHolder(holder: ViewHolder, position: Int) {
            if (position < emptyCount) {
                holder.itemView.tv_day.setBackgroundDrawable(null)
                holder.itemView.tv_score.visibility = View.INVISIBLE
                holder.itemView.tv_day.text = ""
                return
            }

            val realPosition = position - emptyCount
            val day = realPosition + 1
            val dateStr = "${mCalendar.get(Calendar.YEAR)}-${StringUtils.formatTimeStr(mCalendar.get(Calendar.MONTH) + 1)}-${StringUtils.formatTimeStr(day)}"

            val signInDay = signInDayList.find { it.CREATETIME == dateStr }
            if (signInDay != null) {
                holder.itemView.tv_day.setBackgroundResource(R.mipmap.sign_in_already_bg)
                holder.itemView.tv_score.visibility = View.VISIBLE
                holder.itemView.tv_score.text = "${signInDay.SCORE}积分"
            } else {
                holder.itemView.tv_day.setBackgroundDrawable(null)
                holder.itemView.tv_score.visibility = View.INVISIBLE
            }

            holder.itemView.tv_day.text = day.toString()
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }

}