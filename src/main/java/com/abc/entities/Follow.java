package com.abc.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "follows")
public class Follow {
	
	@Id
    @Embedded
    private FollowId id;
    
    @Column(name = "created_at")
    private String createdAt;
    
    // Constructor không đối số
    public Follow() {
    }
    
    // Constructor có đối số
    public Follow(FollowId id, String createdAt) {
        this.id = id;
        this.createdAt = createdAt;
    }
    
    // Nếu muốn tiện tạo đối tượng không cần tạo FollowId bên ngoài,
    // bạn có thể thêm constructor nhận trực tiếp các tham số
    public Follow(int followingUserId, int followedUserId, String createdAt) {
        this.id = new FollowId(followingUserId, followedUserId);
        this.createdAt = createdAt;
    }

    // Getter & Setter
    public FollowId getId() {
        return id;
    }
    public void setId(FollowId id) {
        this.id = id;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
