package pt.sensae.services.rule.management.backend.infrastructure.endpoint.graphql.controller;

import com.netflix.graphql.dgs.exceptions.DefaultDataFetcherExceptionHandler;
import com.netflix.graphql.types.errors.TypedGraphQLError;
import graphql.GraphQLError;
import graphql.execution.DataFetcherExceptionHandler;
import graphql.execution.DataFetcherExceptionHandlerParameters;
import graphql.execution.DataFetcherExceptionHandlerResult;
import org.springframework.stereotype.Component;
import pt.sensae.services.rule.management.backend.domain.exceptions.InvalidScenarioException;

import java.util.concurrent.CompletableFuture;

@Component
public class CustomDataFetchingExceptionHandler implements DataFetcherExceptionHandler {
    private final DefaultDataFetcherExceptionHandler defaultHandler = new DefaultDataFetcherExceptionHandler();

    @Override
    public CompletableFuture<DataFetcherExceptionHandlerResult> handleException(DataFetcherExceptionHandlerParameters handlerParameters) {
        if (handlerParameters.getException() instanceof InvalidScenarioException exception) {

            GraphQLError graphqlError = TypedGraphQLError.newBadRequestBuilder()
                    .message(exception.getError().getDetails())
                    .path(handlerParameters.getPath())
                    .build();
            return CompletableFuture.completedFuture(DataFetcherExceptionHandlerResult.newResult()
                    .error(graphqlError)
                    .build());
        } else {
            return defaultHandler.handleException(handlerParameters);
        }
    }
}
