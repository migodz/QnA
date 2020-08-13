package top.migod.pojo;

import java.util.Date;

public class Answer {
    private int aid;
    private Integer uid;
    private int qid;
    private String content;
    private Date time;

    public Answer() {
    }

    public Answer(int aid, int uid, int qid, String content, Date time) {
        this.aid = aid;
        this.uid = uid;
        this.qid = qid;
        this.content = content;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "aid=" + aid +
                ", uid=" + uid +
                ", qid=" + qid +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }

    public int getAid() {
        return aid;
    }

    public void setAid(int aid) {
        this.aid = aid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }
}
