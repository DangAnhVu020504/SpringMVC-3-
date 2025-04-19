package com.abc.entities;

import java.util.Date;
import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "created_at")
    private String createdAt;

    @Column(name = "email")
    private String email;

    @Column(name = "birthday")
    private Date birthday;

    // ánh xạ mối quan hệ province_id
    @ManyToOne
    @JoinColumn(name = "province_id", referencedColumnName = "idProvince")
    private Province province;

    public User() {}

    public User(int id, String username, String password, String createdAt) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getter & Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getCreatedAt() { return createdAt; }
    public void setCreatedAt(String createdAt) { this.createdAt = createdAt; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public Date getBirthday() { return birthday; }
    public void setBirthday(Date birthday) { this.birthday = birthday; }

    public Province getProvince() { return province; }
    public void setProvince(Province province) { this.province = province; }
}
