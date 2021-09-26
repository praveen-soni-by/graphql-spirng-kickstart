package com.syscho.graphql.jsonplaceholder;

import com.syscho.graphql.generated.types.Album;
import com.syscho.graphql.generated.types.MasterData;
import com.syscho.graphql.generated.types.Post;
import com.syscho.graphql.generated.types.UserInfo;
import com.syscho.graphql.jsonplaceholder.client.JsonPlaceHolderClient;
import graphql.kickstart.tools.GraphQLResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.CompletableFuture;


/**
 * The type Master data query resolver.
 */
@RequiredArgsConstructor
@Component
@Slf4j
public class MasterDataQueryResolver implements GraphQLResolver<MasterData> {

    private final JsonPlaceHolderClient jsonPlaceHolderClient;

    /**
     * Posts completable future.
     * Fields level data fetcher for posts field of MasterData
     * @param masterData the master data
     * @return the completable future
     */
    public CompletableFuture<List<Post>> posts(MasterData masterData) {
        log.info("executing nested data fetcher : posts");
        return jsonPlaceHolderClient.getPosts().toFuture();
    }

    /**
     * Users completable future.
     * Fields level data fetcher for users field of MasterData
     * @param masterData the master data
     * @return the completable future
     */
    public CompletableFuture<List<UserInfo>> users(MasterData masterData) {
        log.info("executing nested data fetcher : users");
        return jsonPlaceHolderClient.getUsers().toFuture();
    }

    /**
     * Albums completable future.
     *Fields level data fetcher for albums field of MasterData
     * @param masterData the master data
     * @return the completable future
     */
    public CompletableFuture<List<Album>> albums(MasterData masterData) {
        log.info("executing nested data fetcher : albums");
        return jsonPlaceHolderClient.getAlbums().toFuture();
    }
}

