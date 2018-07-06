package com.zihexin.mysql.dao;

import com.zihexin.mysql.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2018/7/3.
 */
@Repository
public interface DepartmentRepository extends JpaRepository<Department,Long> {
}
