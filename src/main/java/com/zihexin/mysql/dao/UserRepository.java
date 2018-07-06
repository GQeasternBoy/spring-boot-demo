package com.zihexin.mysql.dao;

import com.zihexin.mysql.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2018/7/3.
 */
@Repository
public interface UserRepository extends JpaRepository<User,Long>{

    User findByIdAndName(Long id,String name);

    User findByIdOrName(Long id,String name);

    List<User> findByCreatedateBetween(Date start,Date end);

    List<User> findByCreatedateLessThan(Date start);

    List<User> findByCreatedateGreaterThan(Date start);

    List<User> findByNameIsNull();

    List<User> findByNameIsNotNull();

    List<User> findByNameLike(String name);

    List<User> findByNameNotLike(String name);

    List<User> findByNameOrderByIdDesc(String name);

    List<User> findByNameNot(String name);

    List<User> findByNameIn(Collection<String> namList);
}
