package com.ipd.rainbow.widget

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.SignInDayBean
import com.ipd.rainbow.utils.DateUtils
import com.ipd.rainbow.utils.StringUtils
import kotlinx.android.synthetic.main.activity_sign_in.view.*
import kotlinx.android.synthetic.main.item_sign_in.view.*
import java.util.*

class SignInView : ConstraintLayout {
    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)


    private var mCurData: String = ""
    fun setDate(time: Long, signInDayList: List<SignInDayBean>?) {
        tv_date.text = "${DateUtils.getYear(time)}年${DateUtils.getMonth(time)}月"
        mCurData = "${DateUtils.getYear(time)}-${DateUtils.getMonth(time)}-${DateUtils.getDay(time)}"
        sign_in_recycler_view.adapter = SigninAdapter(time, signInDayList)
    }


    inner class SigninAdapter(val time: Long, private val signInDayList: List<SignInDayBean>?) : RecyclerView.Adapter<SigninAdapter.ViewHolder>() {
        private var emptyCount = 0

        private val mCalendar by lazy { Calendar.getInstance() }

        init {
            mCalendar.set(Calendar.YEAR, DateUtils.getYear(time).toInt())
            mCalendar.set(Calendar.MONTH, DateUtils.getMonth(time).toInt() - 1)
            mCalendar.set(Calendar.DAY_OF_MONTH, 1)
            emptyCount = mCalendar.get(Calendar.DAY_OF_WEEK) - 1
        }

        override fun getItemCount(): Int {
            val days = DateUtils.getDays(DateUtils.getYear(time).toInt(), DateUtils.getMonth(time).toInt())
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

            val signInDay = signInDayList?.find { it.CREATETIME == dateStr }
            if (signInDay != null) {
                holder.itemView.tv_score.visibility = View.VISIBLE
                holder.itemView.tv_score.text = "${signInDay.SCORE}积分"

                if (signInDay.CREATETIME == mCurData) {
                    holder.itemView.tv_day.setBackgroundResource(R.drawable.shape_sign_today_bg)
                    holder.itemView.tv_day.setTextColor(resources.getColor(R.color.white))
                } else {
                    holder.itemView.tv_day.setBackgroundResource(R.mipmap.sign_in_already_bg)
                    holder.itemView.tv_day.setTextColor(resources.getColor(R.color.black))
                }

            } else {
                holder.itemView.tv_day.setBackgroundDrawable(null)
                holder.itemView.tv_score.visibility = View.INVISIBLE
            }

            holder.itemView.tv_day.text = day.toString()
        }


        inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
    }

}