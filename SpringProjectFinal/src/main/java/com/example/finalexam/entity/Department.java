package com.example.finalexam.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "department")
@Data
public class Department  {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "name", unique = true, nullable = false, length = 50)
    private String name;

    @Column(name = "total_member", unique = true, length = 50)
    private Integer totalMember;

    @Column(name = "created_date")
    @CreationTimestamp
    private Date createdDate;

    @Column(name = "type", nullable = false)
    private String type;

    @OneToMany(mappedBy = "department")
    private List<Account> accountList;


    public Department id(Integer id) {
        this.id = id;
        return this;
    }

    public Department name(String name) {
        this.name = name;
        return this;
    }

    public Department totalNumber(Integer totalNumber) {
        this.totalMember = totalNumber;
        return this;
    }

    public Department createdDate(Date createdDate) {
        this.createdDate = createdDate;
        return this;
    }

    public Department type(String type) {
        this.type = type;
        return this;
    }
}
