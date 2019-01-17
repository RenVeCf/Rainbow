package com.ipd.rainbow.ui.activity.mine

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.os.Environment
import android.view.Menu
import android.view.MenuItem
import com.ipd.jumpbox.jumpboxlibrary.utils.BitmapUtils
import com.ipd.jumpbox.jumpboxlibrary.utils.CommonUtils
import com.ipd.rainbow.R
import com.ipd.rainbow.bean.UploadResultBean
import com.ipd.rainbow.event.UpdateUserInfoEvent
import com.ipd.rainbow.imageload.ImageLoader
import com.ipd.rainbow.presenter.mine.UserPresenter
import com.ipd.rainbow.ui.BaseUIActivity
import com.ipd.rainbow.ui.activity.CropActivity
import com.ipd.rainbow.utils.PictureChooseUtils
import com.ipd.rainbow.utils.UploadUtils
import com.ipd.rainbow.widget.ChooseSexDialog
import com.tencent.bugly.crashreport.biz.UserInfoBean
import kotlinx.android.synthetic.main.activity_user_info.*
import org.greenrobot.eventbus.EventBus
import java.io.File


class UserInfoActivity : BaseUIActivity(), UserPresenter.IUserView {

    companion object {
        fun launch(activity: Activity) {
            val intent = Intent(activity, UserInfoActivity::class.java)
            activity.startActivity(intent)
        }
    }

    override fun getToolbarTitle(): String = "个人资料"

    override fun getContentLayout(): Int = R.layout.activity_user_info


    private var mPresenter: UserPresenter? = null
    override fun onViewAttach() {
        super.onViewAttach()
        mPresenter = UserPresenter()
        mPresenter?.attachView(this, this)
    }

    override fun onViewDetach() {
        super.onViewDetach()
        mPresenter?.detachView()
        mPresenter = null
    }


    override fun initView(bundle: Bundle?) {
        initToolbar()
    }

    override fun loadData() {
//        showProgress()
//        mPresenter?.loadUserInfo()
    }

    override fun initListener() {
        ll_avatar.setOnClickListener {
            PictureChooseUtils.showDialog(mActivity)
        }

        ll_sex.setOnClickListener {
            //性别
            val sexDialog = ChooseSexDialog(this, R.style.recharge_pay_dialog, tv_sex,
                    "选择您的性别", "男", "女")
            sexDialog.show()
        }

    }


    private var mNewAvatarUrl = ""
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        var path: String
        if (resultCode == RESULT_OK) {
            if (requestCode == PictureChooseUtils.PHOTOTAKE) {
                path = CommonUtils.getPhotoSavePath(this, Environment.DIRECTORY_PICTURES) + "/" +
                        PictureChooseUtils.getPhotoSaveName()
                CropActivity.launch(this, path)
            }

            if (requestCode == PictureChooseUtils.PHOTOZOOM) {
                if (data == null)
                    return
                path = BitmapUtils.getInstance().getPath(this, data.data)
                CropActivity.launch(this, path)

            }

            if (requestCode == PictureChooseUtils.IMAGE_COMPLETE) {//截图返回
                if (data == null) return
                path = data.getStringExtra("path")
                val file = File(path)
                if (file.exists()) {
                    UploadUtils.uploadPic(mActivity, true, path, object : UploadUtils.UploadCallback {
                        override fun onProgress(progress: Int) {

                        }

                        override fun uploadSuccess(resultBean: UploadResultBean) {
                            mNewAvatarUrl = resultBean.data
                            ImageLoader.loadAvatar(mActivity, resultBean.data, civ_avatar)
                        }

                        override fun uploadFail(errMsg: String) {
                            toastShow(errMsg)
                        }

                    })
                }
            }


        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_save, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.save) {
            //保存
            val username = et_nickname.text.toString().trim()
            val sex = tv_sex.text.toString().trim()
            mPresenter?.updateUserInfo(
                    avatar = mNewAvatarUrl,
                    username = username,
                    sex = sex
            )
            return true
        }

        return super.onOptionsItemSelected(item)
    }


    override fun loadUserInfoSuccess(info: UserInfoBean) {
//        ImageLoader.loadAvatar(mActivity, HttpUrl.IMAGE_URL + info.LOGO, civ_avatar)
//        et_nickname.setText(info.NICKNAME)
//        tv_sex.text = when (info.SEX) {
//            "0" -> "男"
//            "1" -> "女"
//            else -> ""
//        }
//        tv_phone.text = info.PHONE
        showContent()
    }

    override fun loadUserInfoFail(errMsg: String) {
        showError(errMsg)
    }

    override fun updateUserInfoSuccess() {
        EventBus.getDefault().post(UpdateUserInfoEvent())
        toastShow(true, "修改成功")
        finish()
    }

    override fun updateUserInfoFail(errMsg: String) {
        toastShow(errMsg)
    }


}