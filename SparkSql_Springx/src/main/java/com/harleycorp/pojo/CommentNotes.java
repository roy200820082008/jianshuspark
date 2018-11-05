package com.harleycorp.pojo;

public class CommentNotes {
    private Integer id;

    private String noteId;

    private String time;

    private String commentText;

    private Integer userid;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId == null ? null : noteId.trim();
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        String[] timeArr = time.split("\\+");
        time = timeArr[0].replace("T"," ");
        this.time = time == null ? null : time.trim();
    }

    public String getCommentText() {
        return commentText;
    }

    public void setCommentText(String commentText) {
        this.commentText = commentText == null ? null : commentText.trim();
    }

    public Integer getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }
}