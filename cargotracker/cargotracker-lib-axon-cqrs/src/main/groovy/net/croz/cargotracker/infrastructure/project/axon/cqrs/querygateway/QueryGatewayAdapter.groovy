package net.croz.cargotracker.infrastructure.project.axon.cqrs.querygateway

import groovy.transform.CompileStatic
import net.croz.cargotracker.infrastructure.project.boundary.api.conversation.OperationRequest
import org.axonframework.messaging.GenericMessage
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryExecutionException
import org.axonframework.queryhandling.QueryGateway

import java.util.concurrent.CompletionException

/**
 * Simplifies the API usage and exception handling of Axon <code>QueryGateway</code>.
 */
@CompileStatic
class QueryGatewayAdapter {
  private final QueryGateway queryGateway

  QueryGatewayAdapter(QueryGateway queryGateway) {
    this.queryGateway = queryGateway
  }

  /**
   * Delegates calls to the <code>QueryGateway.query()</code> method.
   * <p/>
   * In case when an exception is thrown from <code>QueryGateway</code>, it unwraps details exception (if available), and rethrows it to the caller.
   *
   * @see #query(java.lang.Object, java.util.Map, java.lang.Class)
   */
  @SuppressWarnings("GrUnnecessaryPublicModifier")
  public <R, Q> R query(OperationRequest<Q> queryOperationRequest, Class<R> queryResponseClass) {
    return query(queryOperationRequest.payload, queryOperationRequest.metaData, queryResponseClass)
  }

  /**
   * Delegates calls to the <code>QueryGateway.query()</code> method.
   * <p/>
   * In case when an exception is thrown from <code>QueryGateway</code>, it unwraps details exception (if available), and rethrows it to the caller.
   *
   * @param query The query to be executed.
   * @param metaData The metadata to dispatch with the query.
   * @param <R> The type of result expected from query execution.
   * @return the result of query execution.
   * @throws AssertionError when query is null.
   * @throws CompletionException when cause is not an instance of <code>QueryExecutionException</code>.
   * @throws QueryExecutionException when details exception is not available.
   * @throws Throwable when available as details of <code>QueryExecutionException</code>.
   */
  @SuppressWarnings("GrUnnecessaryPublicModifier")
  public <R, Q> R query(Q query, Map<String, ?> metaData, Class<R> queryResponseClass) {
    assert query != null

    GenericMessage queryMessage = new GenericMessage(query, metaData)

    R queryResponse
    try {
      queryResponse = queryGateway.query(query.getClass().name, queryMessage, ResponseTypes.instanceOf(queryResponseClass)).join()
    }
    catch (CompletionException completionException) {
      if (completionException.cause instanceof QueryExecutionException) {
        QueryExecutionException queryExecutionException = completionException.cause as QueryExecutionException
        if (queryExecutionException.details.isPresent()) {
          Throwable detailsThrowable = queryExecutionException.details.get() as Throwable
          throw detailsThrowable
        }

        throw queryExecutionException
      }

      throw completionException
    }

    return queryResponse
  }
}