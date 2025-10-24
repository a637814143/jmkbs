package com.example.movie.domain.movie;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MovieRepository extends JpaRepository<Movie, Long> {

    @Query("SELECT m FROM Movie m "
            + "WHERE (:keyword IS NULL OR LOWER(m.title) LIKE LOWER(CONCAT('%', :keyword, '%'))) "
            + "AND (:region IS NULL OR LOWER(m.region) = LOWER(:region)) "
            + "AND (:language IS NULL OR LOWER(m.language) = LOWER(:language))")
    Page<Movie> search(@Param("keyword") String keyword,
            @Param("region") String region,
            @Param("language") String language,
            Pageable pageable);
}
