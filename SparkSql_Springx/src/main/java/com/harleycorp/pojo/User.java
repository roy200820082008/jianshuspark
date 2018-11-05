package com.harleycorp.pojo;

public class User {
    private Integer id;

    private String slug;

    private String nickname;

    private String updateTime;

    private String latestTime;

    private String joinTime;

    private String headPic;

    private Integer gender;

    private Integer isContract;

    private Integer followingNum;

    private Integer followersNum;

    private Integer articlesNum;

    private Integer wordsNum;

    private Integer beLikedNum;

    public User()
    {

    }
    public User(Integer id, String slug, String nickname, String updateTime, String latestTime, String joinTime, String headPic, Integer gender, Integer isContract, Integer followingNum, Integer followersNum, Integer articlesNum, Integer wordsNum, Integer beLikedNum) {
        this.id = id;
        this.slug = slug;
        this.nickname = nickname;
        this.updateTime = updateTime;
        this.latestTime = latestTime;
        this.joinTime = joinTime;
        this.headPic = headPic;
        this.gender = gender;
        this.isContract = isContract;
        this.followingNum = followingNum;
        this.followersNum = followersNum;
        this.articlesNum = articlesNum;
        this.wordsNum = wordsNum;
        this.beLikedNum = beLikedNum;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug == null ? null : slug.trim();
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname == null ? null : nickname.trim();
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime == null ? null : updateTime.trim();
    }

    public String getLatestTime() {
        return latestTime;
    }

    public void setLatestTime(String latestTime) {
        this.latestTime = latestTime == null ? null : latestTime.trim();
    }

    public String getJoinTime() {
        return joinTime;
    }

    public void setJoinTime(String joinTime) {
        this.joinTime = joinTime == null ? null : joinTime.trim();
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic == null ? null : headPic.trim();
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Integer getIsContract() {
        return isContract;
    }

    public void setIsContract(Integer isContract) {
        this.isContract = isContract;
    }

    public Integer getFollowingNum() {
        return followingNum;
    }

    public void setFollowingNum(Integer followingNum) {
        this.followingNum = followingNum;
    }

    public Integer getFollowersNum() {
        return followersNum;
    }

    public void setFollowersNum(Integer followersNum) {
        this.followersNum = followersNum;
    }

    public Integer getArticlesNum() {
        return articlesNum;
    }

    public void setArticlesNum(Integer articlesNum) {
        this.articlesNum = articlesNum;
    }

    public Integer getWordsNum() {
        return wordsNum;
    }

    public void setWordsNum(Integer wordsNum) {
        this.wordsNum = wordsNum;
    }

    public Integer getBeLikedNum() {
        return beLikedNum;
    }

    public void setBeLikedNum(Integer beLikedNum) {
        this.beLikedNum = beLikedNum;
    }
}