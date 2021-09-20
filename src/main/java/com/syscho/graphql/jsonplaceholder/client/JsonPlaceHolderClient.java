package com.syscho.graphql.jsonplaceholder.client;

import com.syscho.graphql.generated.types.Album;
import com.syscho.graphql.generated.types.Post;
import com.syscho.graphql.generated.types.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
@RequiredArgsConstructor
public class JsonPlaceHolderClient {

    private final WebClient webClient;

    private final String USER_ENDPOINT = "/users";
    private final String POST_ENDPOINT = "/posts";
    private final String ALBUM_ENDPOINT = "/albums";

    private Mono<List<UserInfo>> callUsers() {
        return webClient.get()
                .uri(USER_ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<UserInfo>>() {
                });
    }

    private Mono<List<Post>> callPosts() {
        return webClient.get()
                .uri(POST_ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Post>>() {
                });
    }

    private Mono<List<Album>> callAlbum() {
        return webClient.get()
                .uri(ALBUM_ENDPOINT)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<List<Album>>() {
                });
    }

    public Mono<List<UserInfo>> getUsers() {
        return callUsers();
    }

    public Mono<List<com.syscho.graphql.generated.types.Post>> getPosts() {
        return callPosts();
    }

    public Mono<List<com.syscho.graphql.generated.types.Album>> getAlbums() {
        return callAlbum();
    }

}
