package com.example.axolotl.repo;

import com.example.axolotl.domain.Post;
import com.example.axolotl.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface PostRepo extends JpaRepository<Post, Long> {

    Post findPostById(Long id);

    List<Post> findAllByTag(String tag);

    @Query(value = "DELETE FROM post p WHERE p.user_id=:authorId", nativeQuery = true)
    @Modifying
    @Transactional
    void deletePostsByAuthorId(@Param("authorId") Long authorId);

    Optional<List<Post>> findAllByAuthorId(long authorId);
}
