package com.infobip.assessment.url.shortener.service;

import com.infobip.assessment.url.shortener.dao.request.UrlRequest;
import com.infobip.assessment.url.shortener.dao.response.ShortUrlResponse;
import com.infobip.assessment.url.shortener.mapper.UrlMapper;
import com.infobip.assessment.url.shortener.persistence.entity.UrlEntity;
import com.infobip.assessment.url.shortener.persistence.repository.UrlRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.Builder;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@ToString
@Builder(toBuilder = true)
@Service
public class UrlService {

    private final UrlRepository repository;

    UrlService(UrlRepository urlRepository) {
        this.repository = urlRepository;
    }


    public ShortUrlResponse createUrl(UrlRequest urlRequest, String accountId) {

        var entity = UrlMapper.mapRequestToEntity(urlRequest);
        entity.setAccountId(accountId);

        return UrlMapper.mapEntityToRequest(repository.save(entity));
    }

    public List<ShortUrlResponse> allUrls(String accountId) {

        if (StringUtils.isEmpty(accountId)) {
            return repository.findAllByAccountId(accountId).stream()
                    .map(UrlMapper::mapEntityToRequest)
                    .toList();
        } else {
            return repository.findAll().stream()
                    .map(UrlMapper::mapEntityToRequest)
                    .toList();
        }
    }

    public UrlEntity getOriginalUrl(String shortUrl) {
        var urlEntity = repository.findByShortUrl(UrlMapper.BASE_URL + shortUrl).orElseThrow(
                () -> new EntityNotFoundException(String.format("Url '%s' do not exist!", shortUrl)));
        urlEntity.setNumberOfCalls(urlEntity.getNumberOfCalls() + 1);
        repository.save(urlEntity);
        return urlEntity;
    }

    public Map<String, Integer> getStatistic(String accountId) {
        Map<String, Integer> statistic = new HashMap<>(){};
        repository.findAllByAccountId(accountId).forEach(url -> statistic.put(url.getLongUrl(), url.getNumberOfCalls()));
        return statistic;
    }
}
