package com.syscho.graphql.jsonplaceholder;

import com.syscho.graphql.generated.types.Album;
import com.syscho.graphql.generated.types.MasterData;
import com.syscho.graphql.generated.types.Post;
import com.syscho.graphql.generated.types.UserInfo;
import graphql.schema.DataFetchingEnvironment;

import java.util.List;
import java.util.concurrent.CompletableFuture;

interface IUserQuery {
    List<UserInfo> findAllUsers();

    CompletableFuture<List<Post>> findAllPosts();

    List<Album> findAllAlbums();

    MasterData findAll() throws InterruptedException;

    MasterData findAllWithFilter(DataFetchingEnvironment environment);
}
