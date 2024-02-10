package com.infobip.assessment.url.shortener.mapper;

import com.infobip.assessment.url.shortener.dao.request.AccountRequest;
import com.infobip.assessment.url.shortener.dao.response.AccountResponse;
import com.infobip.assessment.url.shortener.persistence.entity.AccountEntity;
import org.apache.commons.lang3.RandomStringUtils;

public class AccountMapper {

    private AccountMapper() {
    }

    public static AccountEntity mapRequestToEntity(AccountRequest accountRequest) {
        var password = RandomStringUtils.randomAlphanumeric(8);

        return new AccountEntity().toBuilder()
                .accountId(accountRequest.getAccountId())
                .password(password)
                .build();
    }

    public static AccountResponse mapEntityTo201Response(AccountEntity accountEntity) {
        return new AccountResponse().toBuilder()
                .success(true)
                .description(String.format("User created with account id: '%s'", accountEntity.getAccountId()))
                .password(accountEntity.getPassword())
                .build();
    }

    public static AccountResponse mapEntityTo400Response(String accountId) {
        return new AccountResponse().toBuilder()
                .success(false)
                .description(String.format("User with account id: '%s' already exists!", accountId))
                .password(null)
                .build();
    }
}
