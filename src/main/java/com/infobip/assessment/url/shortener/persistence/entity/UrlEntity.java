package com.infobip.assessment.url.shortener.persistence.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
@Table(name = "url")
public class UrlEntity {

    @Id()
    @Column(name = "id")
    private UUID id;

    @Column(name = "accountId")
    private String accountId;

    @Column(name = "longUrl")
    private String longUrl;

    @Column(name = "shortUrl")
    private String shortUrl;

    @Column(name = "redirectType")
    private Short redirectType;

    @Column(name = "numberOfCalls")
    private Integer numberOfCalls;
}
