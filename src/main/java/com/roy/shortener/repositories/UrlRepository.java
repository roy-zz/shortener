package com.roy.shortener.repositories;

import com.roy.shortener.base.domains.Url;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends JpaRepository<Url, Long> {

  Optional<Url> findTopByOrigin(String url);

}
