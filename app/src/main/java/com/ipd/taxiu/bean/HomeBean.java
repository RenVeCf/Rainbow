package com.ipd.taxiu.bean;

import com.ipd.taxiu.R;

import java.util.ArrayList;
import java.util.List;

public class HomeBean {

    public List<Object> list;

    public IndexBannerBean banner;
    public IndexBoutiqueBean boutique;
    public IndexTopicBean topic;
    public IndexTalkBean talk;
    public IndexClassRoomBean classRoom;
    public List<TaxiuBean> taxiuList;


    public void buildInfo() {
        banner = new IndexBannerBean();
        banner.build();
        boutique = new IndexBoutiqueBean();
        boutique.build();
        topic = new IndexTopicBean();
        topic.build();
        talk = new IndexTalkBean();
        talk.build();
        classRoom = new IndexClassRoomBean();
        classRoom.build();

        taxiuList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            taxiuList.add(new TaxiuBean());
        }
    }

    public static class IndexBannerBean {
        public List<BannerBean> banners;

        public void build() {
            banners = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                banners.add(new BannerBean(R.mipmap.banner_default));
            }
        }
    }

    public static class IndexBoutiqueBean {
        public List<TaxiuBean> taxiuBoutiqueList;

        public void build() {
            taxiuBoutiqueList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                taxiuBoutiqueList.add(new TaxiuBean());
            }
        }
    }


    public static class IndexTopicBean {
        public TopicBean topic;


        public void build() {
            topic = new TopicBean();
        }
    }

    public static class IndexTalkBean {
        public List<TalkBean> talkList;


        public void build() {
            talkList = new ArrayList<>();
            for (int i = 0; i < 5; i++) {
                talkList.add(new TalkBean());
            }
        }
    }

    public static class IndexClassRoomBean {
        public ClassRoomBean classRoom;


        public void build() {
            classRoom = new ClassRoomBean();
        }
    }


}
