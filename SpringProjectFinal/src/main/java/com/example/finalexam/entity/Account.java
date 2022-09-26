package com.example.finalexam.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Formula;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLUpdate;
import org.hibernate.annotations.Where;

import javax.persistence.*;

@Entity
@Table(name = "account")
@Data
public class Account  {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    private Integer id;

    @Column(name = "username", unique = true, nullable = false, length = 50)
    private String username;

    @Column(name = "password", unique = true, nullable = false, length = 50)
    private String password;

    @Column(name = "first_name", unique = true, nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", unique = true, nullable = false, length = 50)
    private String lastName;

    @Column(name = "role")
    private String role;

    @Formula("concat(first_name,' ',last_name)")
    private String fullName;

    @ManyToOne
    @JoinColumn(name = "department_id",referencedColumnName = "id")
    private Department department;
    

    public Account id(Integer id) {
        this.id = id;
        return this;
    }

    public Account username(String username) {
        this.username = username;
        return this;
    }

    public Account password(String password) {
        this.password = password;
        return this;
    }

    public Account firstName(String firstName) {
        this.firstName = firstName;
        return this;
    }

    public Account lastName(String lastName) {
        this.lastName = lastName;
        return this;
    }

    public Account role(String role) {
        this.role = role;
        return this;
    }

    public Account department(Department department) {
        this.department = department;
        return this;
    }


//    @PreRemove
//    private void preRemove(){
//        this.department = null;
//    }


}
