package com.syscho.graphql.jsonplaceholder;

import com.syscho.graphql.generated.types.MasterData;
import graphql.kickstart.tools.GraphQLQueryResolver;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;


@RequiredArgsConstructor
@Component
@Slf4j
public class JsonPlaceHolderQueryResolver implements GraphQLQueryResolver {

    public MasterData masterData() {
        return new MasterData();
    }
}

