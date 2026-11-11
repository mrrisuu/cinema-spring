package com.project.cinema.client;

import com.project.cinema.common.exception.ExternalServiceException;
import com.project.cinema.dto.OmdbMovieDetail;
import com.project.cinema.dto.OmdbSearchResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.time.Duration;

@Component
public class OmdbClient {

    private final RestClient restClient;
    private final String apiKey;

    public OmdbClient(
            @Value("${omdb.base-url}") String baseUrl,
            @Value("${omdb.api-key}") String apiKey
    ) {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(Duration.ofSeconds(5));
        factory.setReadTimeout(Duration.ofSeconds(10));

        this.restClient = RestClient.builder()
                .baseUrl(baseUrl)
                .requestFactory(factory)
                .build();
        this.apiKey = apiKey;
    }

    // Cari film berdasarkan kata kunci judul (s=), 10 hasil per halaman.
    public OmdbSearchResponse search(String title, int page, String year, String type) {
        requireApiKey();
        try {
            return restClient.get()
                    .uri(b -> {
                        b.queryParam("apikey", apiKey)
                                .queryParam("s", "{s}")
                                .queryParam("page", page);
                        if (year != null && !year.isBlank()) {
                            b.queryParam("y", year.trim());
                        }
                        if (type != null && !type.isBlank()) {
                            b.queryParam("type", type.trim());
                        }
                        return b.build(title);
                    })
                    .retrieve()
                    .body(OmdbSearchResponse.class);
        } catch (RestClientException e) {
            throw new ExternalServiceException("Gagal menghubungi OMDb", e);
        }
    }

    // Ambil detail satu film berdasarkan IMDb ID (i=).
    public OmdbMovieDetail findByImdbId(String imdbId) {
        requireApiKey();
        try {
            return restClient.get()
                    .uri(b -> b.queryParam("apikey", apiKey)
                            .queryParam("i", "{i}")
                            .queryParam("plot", "short")
                            .build(imdbId))
                    .retrieve()
                    .body(OmdbMovieDetail.class);
        } catch (RestClientException e) {
            throw new ExternalServiceException("Gagal menghubungi OMDb", e);
        }
    }

    private void requireApiKey() {
        if (apiKey == null || apiKey.isBlank()) {
            throw new ExternalServiceException(
                    "OMDb API key belum dikonfigurasi (isi environment variable OMDB_API_KEY)");
        }
    }
}