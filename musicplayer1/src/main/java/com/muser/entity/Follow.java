package com.muser.entity;

public class Follow {
    private mUser fans =new mUser();   //我的粉丝
    private mUser follows =new mUser();  //我的关注

    public Follow() {
    }

    public Follow(mUser fans, mUser follows) {
        this.fans = fans;
        this.follows = follows;
    }

    public mUser getFans() {
        return fans;
    }

    public void setFans(mUser fans) {
        this.fans = fans;
    }

    public mUser getFollows() {
        return follows;
    }

    public void setFollows(mUser follows) {
        this.follows = follows;
    }
}
