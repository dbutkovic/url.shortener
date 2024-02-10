package com.infobip.assessment.url.shortener.service;

import com.infobip.assessment.url.shortener.persistence.entity.AccountEntity;
import com.infobip.assessment.url.shortener.persistence.repository.AccountRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {

    private final AccountRepository accountRepository;

    public SecurityService(AccountRepository accountRepository) {
        this.accountRepository = accountRepository;
    }

    public AccountEntity getAuthenticatedAccount(String accountId) {
        var account = accountRepository.findByAccountId(accountId);
        if (account == null ) {
            throw new EntityNotFoundException(String.format("User with username '%s' does not exists!", accountId));
        }
        return account;
    }
}
