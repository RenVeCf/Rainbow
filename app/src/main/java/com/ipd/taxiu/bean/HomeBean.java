package com.ipd.taxiu.bean;

import java.util.List;

public class HomeBean {

    public IndexHeaderbean headerInfo;
    public IndexBoutiqueBean boutique;
    public IndexTopicBean topic;
    public IndexTalkBean talk;
    public IndexClassRoomBean classRoom;


    public static class IndexHeaderbean {
        public List<BannerBean> banners;
        public HomeResultBean.PETBean petInfo;

        public IndexHeaderbean(List<BannerBean> banners, HomeResultBean.PETBean petInfo) {
            this.banners = banners;
            this.petInfo = petInfo;
        }
    }

    public static class IndexBoutiqueBean {
        public List<TaxiuBean> taxiuBoutiqueList;

        public IndexBoutiqueBean(List<TaxiuBean> taxiuBoutiqueList) {
            this.taxiuBoutiqueList = taxiuBoutiqueList;
        }
    }


    public static class IndexTopicBean {
        public TopicBean topic;


        public IndexTopicBean(TopicBean topic) {
            this.topic = topic;
        }
    }

    public static class IndexTalkBean {
        public List<TalkBean> talkList;


        public IndexTalkBean(List<TalkBean> talkList) {
            this.talkList = talkList;
        }
    }

    public static class IndexClassRoomBean {
        public ClassRoomBean classRoom;


        public IndexClassRoomBean(ClassRoomBean classRoom) {
            this.classRoom = classRoom;
        }
    }


}
