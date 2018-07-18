package com.ipd.taxiu.platform.http;

/**
 * Created by jumpbox on 16/5/2.
 */
public interface HttpUrl {
    String SERVER_URL = "http://121.199.8.244:3580/api/";
    String IMAGE_URL = "http://121.199.8.244:3580/";
    String MUSIC_URL = "http://121.199.8.244:3580";


    String REGISTER = "reg";
    String LOGIN = "login";
    //歌单
    String SONG_SHEET = "song_sheet";
    //喜欢的音乐
    String FAVORITE_SONG = "likesong/look";
    //添加喜欢的歌曲
    String ADD_FAVORITE_SONG = "likesong/collect";
    //删除喜欢的歌曲
    String REMOVE_FAVORITE_SONG = "likesong/del";
    //自我评测
    String EVALUATION = "evaluating";
    //用户资料
    String USER_INFO = "userinfo";
    //修改用户资料
    String CHANGE_USER_INFO = "my/userinfo";
    //意见反馈
    String FEEDBACK = "suggestion";
    //上传图片
    String UPLOAD_PIC = "my/upload";
    //上传大包数据
    String UPLOAD_BIG_PACKAGE = "package";
    //上传小包数据
    String UPLOAD_SMALL_PACKAGE = "uploadfile";


}
