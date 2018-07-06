package com.zihexin.mysql.dao;

import com.zihexin.mysql.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/7/3.
 */
@Repository
public interface RoleRepository extends JpaRepository<Role,Long>{
}
