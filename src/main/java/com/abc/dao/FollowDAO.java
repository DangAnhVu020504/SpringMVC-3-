package com.abc.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abc.entities.Follow;
import com.abc.entities.FollowId;
import com.abc.entities.User;

@Repository
@Transactional
public class FollowDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Lấy danh sách người theo dõi của user có id được truyền vào.
     * (Người theo dõi được xác định bằng việc lấy các User có id trùng với f.id.followingUserId 
     * trong bảng Follow với f.id.followedUserId = :id)
     */
    public List<User> getFollowerUser(int id) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT u " +
                     "FROM Follow f, User u " +
                     "WHERE u.id = f.id.followingUserId AND f.id.followedUserId = :id";
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    /**
     * Lấy danh sách các user mà user có id truyền vào đang theo dõi.
     * (Lấy các User có id trùng với f.id.followedUserId trong Follow với f.id.followingUserId = :id)
     */
    public List<User> getFollowedUsers(int id) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT u " +
                     "FROM Follow f, User u " +
                     "WHERE u.id = f.id.followedUserId AND f.id.followingUserId = :id";
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    /**
     * Theo dõi user: Tạo mới một đối tượng Follow với composite key gồm followingUserId và followedUserId.
     * Thời gian tạo được gán theo hiện tại.
     */
    public void followUser(int followingUserId, int followedUserId) {
        Session session = sessionFactory.getCurrentSession();
        // Ở đây, tạo mới Follow với thời gian hiện tại. Có thể cải tiến bằng kiểu dữ liệu Date hoặc LocalDateTime.
        Follow follow = new Follow(followingUserId, followedUserId, java.time.LocalDateTime.now().toString());
        session.save(follow);
    }

    /**
     * Bỏ theo dõi user: Xoá đối tượng Follow dựa trên composite key.
     */
    public void unfollowUser(int followingUserId, int followedUserId) {
        Session session = sessionFactory.getCurrentSession();
        FollowId followId = new FollowId(followingUserId, followedUserId);
        Follow follow = session.get(Follow.class, followId);
        if (follow != null) {
            session.delete(follow);
        }
    }

    /**
     * Gợi ý những user chưa được user có id truyền vào theo dõi.
     * Sử dụng truy vấn con để loại ra các user đã được theo dõi.
     */
    public List<User> getSuggestedFollows(int userId) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT u FROM User u " +
                     "WHERE u.id <> :userId " +
                     "  AND u.id NOT IN (SELECT f.id.followedUserId FROM Follow f WHERE f.id.followingUserId = :userId)";
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    /**
     * Tìm kiếm user có số lượng theo dõi (following) hoặc được theo dõi (followers) đạt ngưỡng cho trước.
     * Sử dụng subquery đếm số Follow thỏa điều kiện.
     */
    public List<User> searchUsersByFollowCounts(int minFollowing, int minFollower) {
        Session session = sessionFactory.getCurrentSession();
        String hql = "SELECT u FROM User u " +
                     "WHERE (SELECT COUNT(f) FROM Follow f WHERE f.id.followingUserId = u.id) >= :minFollowing " +
                     "   OR (SELECT COUNT(f) FROM Follow f WHERE f.id.followedUserId = u.id) >= :minFollower";
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("minFollowing", (long) minFollowing);
        query.setParameter("minFollower", (long) minFollower);
        return query.getResultList();
    }
}
