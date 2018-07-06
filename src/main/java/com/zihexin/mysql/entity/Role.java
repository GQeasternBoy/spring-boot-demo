package com.zihexin.mysql.entity;

import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Administrator on 2018/6/26.
 */
@Entity
@Table(name = "role")
@ToString
public class Role implements Serializable{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Role() {
    }

    public Role(String name) {
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
}
