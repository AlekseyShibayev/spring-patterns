package com.company.app.habr.domain.repository;

import com.company.app.habr.domain.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PostRepository extends JpaRepository<Post, Long> {
}