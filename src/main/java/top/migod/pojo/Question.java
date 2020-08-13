package top.migod.pojo;

import java.util.Date;

public class Question {
    private int qid;
    private int uid;
    private String content;
    private Date time;

    public Question() {
    }

    public Question(int qid, int uid, String content, Date time) {
        this.qid = qid;
        this.uid = uid;
        this.content = content;
        this.time = time;
    }

    @Override
    public String toString() {
        return "Question{" +
                "qid=" + qid +
                ", uid=" + uid +
                ", content='" + content + '\'' +
                ", time=" + time +
                '}';
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
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
