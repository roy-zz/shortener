package com.roy.shortener.repositories;

import com.roy.shortener.base.domains.URL;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface URLRepository extends JpaRepository<URL, Long> {}
