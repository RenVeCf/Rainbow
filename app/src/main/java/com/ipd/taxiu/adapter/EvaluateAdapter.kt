package com.ipd.taxiu.adapter

import android.content.Context
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ipd.taxiu.R
import com.ipd.taxiu.widget.RatingBar
import kotlinx.android.synthetic.main.item_evaluate.view.*

/**
Created by Miss on 2018/8/13
 */
class EvaluateAdapter(val context: Context, private val data: List<String>)  :  RecyclerView.Adapter<EvaluateAdapter.ViewHolder>(){
    private var mRecyclerView: RecyclerView? = null
    private var VIEW_FOOTER: View? = null

    //Type
    private val TYPE_NORMAL = 1000
    private val TYPE_FOOTER = 1002

    private var requestCode: Int = 0
    private var resultCode:Int = 0
    private var datas: Intent? = null

    private var selectPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return if (viewType == TYPE_FOOTER) {
            ViewHolder(this!!.VIEW_FOOTER!!)
        } else {
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_evaluate,parent,false))
        }
    }

    override fun getItemCount(): Int {
        var count = data?.size ?: 0
        if (VIEW_FOOTER != null) {
            count++
        }
        return count
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (!isFooterView(position)) {
            holder?.itemView?.picture_recycler_view?.initTwo()
            holder?.itemView?.picture_recycler_view?.getEvaluateAdapter()?.setOnClickListener{ selectPosition = position }
            if (selectPosition == position) {
                holder?.itemView?.picture_recycler_view?.onActivityResult(requestCode, resultCode, datas)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (isFooterView(position)) {
            TYPE_FOOTER
        } else {
            TYPE_NORMAL
        }
    }

    override fun onAttachedToRecyclerView(recyclerView: RecyclerView?) {
        try {
            if (mRecyclerView == null && mRecyclerView != recyclerView) {
                mRecyclerView = recyclerView
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }

    }

    private fun getLayout(layoutId: Int): View {
        return LayoutInflater.from(context).inflate(layoutId, null)
    }

    fun addFooterView(footerView: View) {
        if (haveFooterView()) {
            throw IllegalStateException("footerView has already exists!")
        } else {
            val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            footerView.layoutParams = params
            VIEW_FOOTER = footerView
            notifyItemInserted(itemCount - 1)

//            val rating_star1: RatingBar = footerView.findViewById(R.id.rating_star1)
//            val rating_star2: RatingBar
//            val rating_star3: RatingBar
//            val tv_satisfaction1: TextView
//            val tv_satisfaction2: TextView
//            val tv_satisfaction3: TextView
//
//            rating_star2 = footerView.findViewById(R.id.rating_star2)
//            rating_star3 = footerView.findViewById(R.id.rating_star3)
//
//            tv_satisfaction1 = footerView.findViewById(R.id.description_satisfaction_degree)
//            tv_satisfaction2 = footerView.findViewById(R.id.logistics_satisfaction_degree)
//            tv_satisfaction3 = footerView.findViewById(R.id.attitude_satisfaction_degree)

            setStar(footerView.findViewById(R.id.rating_star1), footerView.findViewById(R.id.description_satisfaction_degree))
            setStar(footerView.findViewById(R.id.rating_star2), footerView.findViewById(R.id.logistics_satisfaction_degree))
            setStar(footerView.findViewById(R.id.rating_star3), footerView.findViewById(R.id.attitude_satisfaction_degree))
        }
    }


    fun haveFooterView(): Boolean {
        return VIEW_FOOTER != null
    }

    private fun isFooterView(position: Int): Boolean {
        return haveFooterView() && position == itemCount - 1
    }

    fun setReset(requestCode: Int, resultCode: Int, datas: Intent, selectPosition: Int) {
        this.requestCode = 0
        this.resultCode = 0
        this.datas = null
        this.selectPosition = -1

        this.requestCode = requestCode
        this.resultCode = resultCode
        this.datas = datas
        this.selectPosition = selectPosition
        notifyDataSetChanged()
    }

    fun getSelectPosition(): Int = selectPosition

    //根据星星设置评语
    private fun setStar(ratingBar: RatingBar, textView: TextView) {
        ratingBar.setOnRatingChangeListener { ratingCount ->
            val starNum = ratingCount.toInt()
            if (starNum == 1) {
                textView.text = "差评"
            }
            if (starNum == 2) {
                textView.text = "一般"
            }
            if (starNum == 3) {
                textView.text = "还行"
            }
            if (starNum == 4) {
                textView.text = "满意"
            }
            if (starNum == 5) {
                textView.text = "非常满意"
            }
        }
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}