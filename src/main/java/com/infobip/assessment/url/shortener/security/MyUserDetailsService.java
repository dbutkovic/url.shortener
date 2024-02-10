package com.infobip.assessment.url.shortener.security;

import com.infobip.assessment.url.shortener.persistence.entity.AccountEntity;
import com.infobip.assessment.url.shortener.persistence.repository.AccountRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class MyUserDetailsService implements UserDetailsService {

    @Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String accountId) {
        AccountEntity accountEntity = accountRepository.findByAccountId(accountId);
        if (accountEntity == null) {
            throw new UsernameNotFoundException(accountId);
        }
        return new MyUserPrincipal(accountEntity);
    }
}