package com.example.spring_project.repository;

import com.example.spring_project.entity.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    @Query("select b from Book b where b.writerId = :writerId")
    List<Book> findAllByWriterId(Long writerId);
}
