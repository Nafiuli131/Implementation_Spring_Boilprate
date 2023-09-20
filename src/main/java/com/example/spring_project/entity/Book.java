package com.example.spring_project.entity;

import com.example.spring_project.generics.BaseEntity;
import com.example.spring_project.security.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Book extends BaseEntity {

    private String bookName;
    private Integer bookPrice;

    @ManyToOne
    @JoinColumn(name = "writer_id")
    private User user;

    @Column(name = "writer_id", insertable = false, updatable = false)
    private Long writerId;
}
