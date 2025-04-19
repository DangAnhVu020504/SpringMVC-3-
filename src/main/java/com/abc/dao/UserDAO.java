package com.abc.dao;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.abc.entities.User;

@Repository
@Transactional
public class UserDAO {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Lấy User theo username.
     */
    public User getUserByUserName(String userName) {
        // Sử dụng HQL để tìm user theo username.
        String hql = "FROM User u WHERE u.username = :username";
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("username", userName);
        return query.uniqueResult();
    }

    /**
     * Đăng ký user bằng cách lưu đối tượng user.
     */
    public boolean registerUser(User user) {
        try {
            Session session = sessionFactory.getCurrentSession();
            session.save(user);
            return true;
        } catch (Exception e) {
            // Ghi log hoặc xử lý lỗi thích hợp
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Tìm kiếm user có số lượng follow (following hoặc followed) đạt ngưỡng cho trước.
     * Giả sử rằng entity Follow được ánh xạ với composite key dùng FollowId với các trường:
     * - followId.followingUserId: biểu thị người theo dõi (following)
     * - followId.followedUserId: biểu thị người được theo dõi (followed)
     */
    public List<User> searchUsersByFollowCounts(int minFollowing, int minFollower) {
        /*
           Ví dụ HQL sử dụng subquery:
           - Đếm số lượng Follow có followId.followingUserId = u.id (số người u đang theo dõi)
           - Đếm số lượng Follow có followId.followedUserId = u.id (số người theo dõi u)
         */
        String hql = "SELECT u FROM User u " +
                     "WHERE (SELECT count(f) FROM Follow f WHERE f.id.followingUserId = u.id) >= :minFollowing " +
                     "   OR (SELECT count(f) FROM Follow f WHERE f.id.followedUserId = u.id) >= :minFollower";
        Session session = sessionFactory.getCurrentSession();
        Query<User> query = session.createQuery(hql, User.class);
        query.setParameter("minFollowing", (long) minFollowing);
        query.setParameter("minFollower", (long) minFollower);
        return query.getResultList();
    }
    public void updateUser(User user) {
        sessionFactory.getCurrentSession().update(user);
    }

}
