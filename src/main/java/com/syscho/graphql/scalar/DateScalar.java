
package com.syscho.graphql.scalar;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingSerializeException;
import graphql.schema.GraphQLScalarType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Configuration
public class DateScalar {

    @Bean
    public GraphQLScalarType dateTimeScalar() {
        final DateTimeFormatter format = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        return GraphQLScalarType.newScalar()
                .name("DateTime_Scalar")
                .description("Java 8 LocalDateTime as scalar.")
                .coercing(new Coercing<LocalDateTime, String>() {
                    @Override
                    public String serialize(final Object dataFetcherResult) {
                        if (dataFetcherResult instanceof LocalDateTime) {
                            return ((LocalDateTime) dataFetcherResult).format(format);
                        } else {
                            throw new CoercingSerializeException("Expected a LocalDate object.");
                        }
                    }

                    @Override
                    public LocalDateTime parseValue(final Object input) {
                        return LocalDateTime.parse(input.toString(), format);
                    }

                    @Override
                    public LocalDateTime parseLiteral(final Object input) {
                        if (input instanceof StringValue) {
                            return LocalDateTime.parse(((StringValue) input).getValue(), format);
                        }
                        throw new CoercingParseLiteralException("Value is not a valid ISO date time");
                    }
                }).build();
    }
}
