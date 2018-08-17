package com.ipd.taxiu.bean;

/**
 * Created by Miss on 2018/8/15
 */
public class QuestionListBean {
    private int commonQuestionId;
    private String title;
    private String content;
    private int sort;
    private String createTime;
    private int status;

    public QuestionListBean(String title, String content) {
        this.title = title;
        this.content = content;
    }

    public int getCommonQuestionId() {
        return commonQuestionId;
    }

    public void setCommonQuestionId(int commonQuestionId) {
        this.commonQuestionId = commonQuestionId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
