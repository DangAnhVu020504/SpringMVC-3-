package com.abc.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "posts")
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    
    @Column(name = "title", nullable = false)
    private String title;
    
    @Column(name = "body", columnDefinition = "TEXT")
    private String body;
    
    @Column(name = "user_id", nullable = false)
    private int userId;
    
    @Column(name = "status")
    private String status;
    
    @Column(name = "created_at")
    private String createdAt;
    
    // Constructor không đối số
    public Post() {
    }
    
    // Constructor đầy đủ (bao gồm id nếu cần)
    public Post(int id, String title, String body, int userId, String status, String createdAt) {
        this.id = id;
        this.title = title;
        this.body = body;
        this.userId = userId;
        this.status = status;
        this.createdAt = createdAt;
    }
    
    // Constructor không cần id và createdAt
    public Post(String title, String body, int userId, String status) {
        this.title = title;
        this.body = body;
        this.userId = userId;
        this.status = status;
    }
    
    // Getter & Setter
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getBody() {
        return body;
    }
    public void setBody(String body) {
        this.body = body;
    }
    public int getUserId() {
        return userId;
    }
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getCreatedAt() {
        return createdAt;
    }
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
