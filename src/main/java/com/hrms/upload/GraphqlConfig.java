package com.hrms.upload;

import graphql.annotations.annotationTypes.GraphQLType;
import graphql.kickstart.servlet.apollo.ApolloScalars;
import graphql.schema.GraphQLScalarType;
import org.modelmapper.internal.bytebuddy.asm.Advice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;

@Configuration
public class GraphqlConfig {
    @Bean
    public GraphQLScalarType uploadScalar() {
        return ApolloScalars.Upload;
    }

    @Bean
    public RuntimeWiringConfigurer uploadRuntimeWiringConfigurer() {
        return builder -> builder.scalar(uploadScalar());
    }
}
