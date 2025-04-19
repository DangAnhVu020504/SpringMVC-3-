package com.abc.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abc.entities.Post;

@Repository
@Transactional
public class PostDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Lấy danh sách tất cả các bài đăng cho user có id được truyền vào.
     * Truy vấn bao gồm:
     * - Các bài đăng của các user mà user hiện tại đang theo dõi (dựa vào Follow)
     * - Hoặc bài đăng của chính user đó.
     * Sắp xếp theo createdAt giảm dần.
     *
     * Lưu ý: Truy vấn này giả định rằng entity Follow được ánh xạ với composite key (FollowId)
     * có các thuộc tính: followingUserId và followedUserId.
     */
    public List<Post> getALLPost(int userId) {
        Session session = sessionFactory.getCurrentSession();
        // Ví dụ truy vấn HQL sử dụng phép LEFT JOIN để lấy bài đăng của:
        // - những user mà userId đang theo dõi (f.id.followingUserId = :userId)
        // - hoặc bài đăng của chính user đó (p.userId = :userId)
        String hql = "SELECT DISTINCT p " +
                     "FROM Post p LEFT JOIN com.abc.entities.Follow f " +
                     "     ON p.userId = f.id.followedUserId " +
                     "WHERE (f.id.followingUserId = :userId) OR (p.userId = :userId) " +
                     "ORDER BY p.createdAt DESC";
        Query<Post> query = session.createQuery(hql, Post.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    /**
     * Lấy danh sách bài đăng của một user theo userId.
     */
    public List<Post> getPostById(int userId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "FROM Post p WHERE p.userId = :userId ORDER BY p.createdAt DESC";
        Query<Post> query = session.createQuery(hql, Post.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    /**
     * Tạo mới một bài đăng.
     */
    public boolean createdPost(Post post) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(post);
            return true;
        } catch (Exception e) {
            // Ghi log lỗi thật đầy đủ trong thực tế
            e.printStackTrace();
            return false;
        }
    }
}
