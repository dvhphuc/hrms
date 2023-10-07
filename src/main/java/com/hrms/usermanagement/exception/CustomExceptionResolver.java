package com.hrms.usermanagement.exception;

import graphql.ErrorClassification;
import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CustomExceptionResolver extends DataFetcherExceptionResolverAdapter {
    @Override
    protected GraphQLError resolveToSingleError(Throwable ex, DataFetchingEnvironment environment) {
        if (ex instanceof Exception) {
            return new GraphQLError() {
                @Override
                public String getMessage() {
                    return ex.getMessage();
                }

                @Override
                public List<SourceLocation> getLocations() {
                    return null;
                }

                @Override
                public ErrorClassification getErrorType() {
                    return ErrorType.DataFetchingException;
                }
            };
        }
        return super.resolveToSingleError(ex, environment);
    }
}
