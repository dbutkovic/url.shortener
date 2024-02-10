package com.infobip.assessment.url.shortener.persistence.repository;

import com.infobip.assessment.url.shortener.persistence.entity.UrlEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;

public interface UrlRepository extends JpaRepository<UrlEntity, String>, JpaSpecificationExecutor<UrlEntity> {
    List<UrlEntity> findAllByAccountId(String accountId);

    Optional<UrlEntity> findByShortUrl(String shortUrl);
}
