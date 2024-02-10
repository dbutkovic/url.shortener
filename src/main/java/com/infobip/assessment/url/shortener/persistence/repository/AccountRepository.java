package com.infobip.assessment.url.shortener.persistence.repository;

import com.infobip.assessment.url.shortener.persistence.entity.AccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface AccountRepository extends JpaRepository<AccountEntity, String>, JpaSpecificationExecutor<AccountEntity> {

    AccountEntity findByAccountId(String accountId);
}
