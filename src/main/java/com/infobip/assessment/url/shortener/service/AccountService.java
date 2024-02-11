package com.infobip.assessment.url.shortener.service;

import com.infobip.assessment.url.shortener.dao.request.AccountRequest;
import com.infobip.assessment.url.shortener.dao.response.AccountResponse;
import com.infobip.assessment.url.shortener.mapper.AccountMapper;
import com.infobip.assessment.url.shortener.persistence.repository.AccountRepository;
import lombok.Builder;
import lombok.ToString;
import org.springframework.stereotype.Service;


@ToString
@Builder(toBuilder = true)
@Service
public class AccountService {

    private final AccountRepository repository;

    AccountService(AccountRepository accountRepository) {
        this.repository = accountRepository;
    }

    public AccountResponse createAccount(AccountRequest account) {
        var existing = repository.findByAccountId(account.getAccountId());
        if (existing != null) {
            return AccountMapper.mapEntityTo400Response(account.getAccountId());
        }

        var entity = AccountMapper.mapRequestToEntity(account);

        var saved = repository.save(entity);

        return AccountMapper.mapEntityTo201Response(saved);
    }
}
