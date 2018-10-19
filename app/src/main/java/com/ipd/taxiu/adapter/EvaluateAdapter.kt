package com.ipd.taxiu.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.ipd.taxiu.R
import com.ipd.taxiu.bean.ProductBean
import com.ipd.taxiu.bean.UploadProductEvaluateBean
import com.ipd.taxiu.imageload.ImageLoader
import com.ipd.taxiu.widget.RatingBar
import kotlinx.android.synthetic.main.item_evaluate.view.*

/**
Created by Miss on 2018/8/13
 */
class EvaluateAdapter(val context: Context, private val data: List<ProductBean>?) : RecyclerView.Adapter<EvaluateAdapter.ViewHolder>() {
    private var mRecyclerView: RecyclerView? = null
    private var VIEW_FOOTER: View? = null

    //Type
    private val TYPE_NORMAL = 1000
    private val TYPE_FOOTER = 1002

    private var selectPosition = -1

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        return if (viewType == TYPE_FOOTER) {
            ViewHolder(this?.VIEW_FOOTER!!)
        } else {
            ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_evaluate, parent, false))
        }
    }

    override fun getItemCount(): Int {
        var count = data?.size ?: 0
        if (VIEW_FOOTER != null) {
            count++
        }
        return count
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (!isFooterView(position)) {
            val info = data!![position]
            holder.itemView.picture_recycler_view.init(4)
            holder.itemView.picture_recycler_view.adapter.setOnItemClickListener(object : PictureAdapter.OnItemClickListener {
                override fun choosePicture(itemPos: Int) {
                    selectPosition = position
                    holder.itemView.picture_recycler_view.adapter.choosePicture()
                }

                override fun showDeleteDialog(itemPos: Int) {
                    holder.itemView.picture_recycler_view.adapter.showDeleteDialog(itemPos)
                }

            })
            ImageLoader.loadNoPlaceHolderImg(context, info.LOGO, holder.itemView.image_view)
            holder.itemView.tv_commodity_name.text = info.PROCUCT_NAME

            holder.itemView.product_star.star = info.star
            holder.itemView.product_star.setOnRatingChangeListener {
                info.star = it
                setStar(holder.itemView.product_star, holder.itemView.tv_product_star)
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

    fun addFooterView(footerView: View) {
        if (haveFooterView()) {
            throw IllegalStateException("footerView has already exists!")
        } else {
            val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            footerView.layoutParams = params
            VIEW_FOOTER = footerView
            notifyItemInserted(itemCount - 1)

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

    fun getProductEvaluateInfo(callback: (errMsg: String, list: List<UploadProductEvaluateBean>?) -> Unit) {
        val list = ArrayList<UploadProductEvaluateBean>()
        data?.forEachIndexed { index, info ->
            if (mRecyclerView == null) return@forEachIndexed
            val itemView = mRecyclerView!!.layoutManager!!.findViewByPosition(index)
            val content = itemView.et_comment_content.text.toString().trim()
            val pictureList = itemView.picture_recycler_view.getPictureList()
            val star = itemView.product_star.star
            if (star == 0f) {
                callback.invoke("请对商品打分", null)
                return
            }

            var picStr = ""

            pictureList.forEach {
                if (TextUtils.isEmpty(it.url)) {
                    callback.invoke("图片暂未上传成功", null)
                    return
                }
                picStr += "${it.url};"
            }


            if (!TextUtils.isEmpty(picStr)) {
                picStr.substring(0, picStr.length - 1)
            }
            list.add(UploadProductEvaluateBean(info.ORDER_DETAIL_ID, content, picStr, 0))
        }
        callback.invoke("", list)
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}