package com.feliopolis.bookskeeper.dao;


import com.feliopolis.bookskeeper.models.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@Transactional
public class RoleDAO {

    @Autowired
    private EntityManager entityManager;

    public List<String> getRoleNames(Long userId) {
        String sql = "Select ur.appRole.roleName from " + Role.class.getName() + " ur " //
                + " where ur.appUser.userId = :userId ";

        Query query = this.entityManager.createQuery(sql, String.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }
}
