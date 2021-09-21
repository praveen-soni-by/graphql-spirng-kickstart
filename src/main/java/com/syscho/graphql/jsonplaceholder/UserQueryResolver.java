package com.syscho.graphql.jsonplaceholder;

import com.syscho.graphql.generated.resolver.JsonplaceholderQueryResolver;
import com.syscho.graphql.generated.types.Album;
import com.syscho.graphql.generated.types.MasterData;
import com.syscho.graphql.generated.types.Post;
import com.syscho.graphql.generated.types.UserInfo;
import com.syscho.graphql.jsonplaceholder.client.JsonPlaceHolderClient;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientRequestException;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


@RequiredArgsConstructor
@Component
public class UserQueryResolver implements JsonplaceholderQueryResolver {

    private final JsonPlaceHolderClient jsonPlaceHolderClient;
    private final ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());

    public CompletableFuture<List<Post>> findAllPosts_Test() {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return jsonPlaceHolderClient.getPosts().block();
            } catch (WebClientRequestException exception) {
                throw new RuntimeException("Service is down , try after some time");
            }
        }, executorService);

    }


    @Override
    public List<UserInfo> findAllUsers(DataFetchingEnvironment env) throws Exception {
        return jsonPlaceHolderClient.getUsers().block();
    }

    @Override
    public List<Post> findAllPosts(DataFetchingEnvironment env) throws Exception {
        // return CompletableFuture.supplyAsync(() -> {
        //    try {
        return jsonPlaceHolderClient.getPosts().block();
        //    } catch (WebClientRequestException exception) {
        //       throw new RuntimeException("Service is down , try after some time");
        //   }
        // }, executorService);
    }

    @Override
    public List<Album> findAllAlbums(DataFetchingEnvironment env) throws Exception {
        return jsonPlaceHolderClient.getAlbums().block();
    }

    @Override
    public MasterData findAll(DataFetchingEnvironment env) throws Exception {
        MasterData masterData = new MasterData();
        Mono<List<UserInfo>> users = jsonPlaceHolderClient.getUsers();
        Mono<List<Album>> albums = jsonPlaceHolderClient.getAlbums();
        Mono<List<Post>> posts = jsonPlaceHolderClient.getPosts();
        return Mono.zip(users, albums, posts)
                .map(objects -> {
                    masterData.setUsers(objects.getT1());
                    masterData.setAlbums(objects.getT2());
                    masterData.setPosts(objects.getT3());
                    return masterData;
                }).block();
    }

    public MasterData findAllWithFilter(DataFetchingEnvironment environment) {
        MasterData masterData = new MasterData();
        Mono<List<UserInfo>> users = null;
        Mono<List<Post>> posts = null;
        Mono<List<Album>> albums = null;

        if (environment.getSelectionSet().contains("users")) {
            users = jsonPlaceHolderClient.getUsers();
        }
        if (environment.getSelectionSet().contains("posts")) {
            posts = jsonPlaceHolderClient.getPosts();
        }
        if (environment.getSelectionSet().contains("albums")) {
            albums = jsonPlaceHolderClient.getAlbums();
        }

        if (null != users) masterData.setUsers(users.block());
        if (null != posts) masterData.setPosts(posts.block());
        if (null != albums) masterData.setAlbums(albums.block());

        return masterData;
    }
}

