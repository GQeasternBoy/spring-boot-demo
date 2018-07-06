package com.zihexin.mysql.entity;

import lombok.ToString;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by Administrator on 2018/6/26.
 */
@Entity
@Table(name = "department")
@ToString
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private Date createdate;

    public Department() {
    }

    public Department(String name,Date createdate) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getCreatedate() {
        return createdate;
    }

    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }
}
