package com.abc.entities;

import java.io.Serializable;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;


@Embeddable
public class FollowId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "following_user_id")
    private int followingUserId;
    
    @Column(name = "followed_user_id")
    private int followedUserId;

    // Constructor không đối số
    public FollowId() {
    }

    // Constructor có đối số
    public FollowId(int followingUserId, int followedUserId) {
        this.followingUserId = followingUserId;
        this.followedUserId = followedUserId;
    }

    // Getter & Setter
    public int getFollowingUserId() {
        return followingUserId;
    }
    public void setFollowingUserId(int followingUserId) {
        this.followingUserId = followingUserId;
    }
    public int getFollowedUserId() {
        return followedUserId;
    }
    public void setFollowedUserId(int followedUserId) {
        this.followedUserId = followedUserId;
    }
    
    // Cần ghi đè equals và hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FollowId)) return false;
        FollowId that = (FollowId) o;
        return followingUserId == that.followingUserId &&
               followedUserId == that.followedUserId;
    }

    @Override
    public int hashCode() {
        int result = Integer.hashCode(followingUserId);
        result = 31 * result + Integer.hashCode(followedUserId);
        return result;
    }
}
