package net.croz.cargotracker.infrastructure.axon.querygateway

import groovy.transform.CompileStatic
import net.croz.cargotracker.api.open.shared.conversation.OperationRequest
import net.croz.cargotracker.api.open.shared.exceptional.exception.QueryException
import org.axonframework.messaging.GenericMessage
import org.axonframework.messaging.responsetypes.ResponseTypes
import org.axonframework.queryhandling.QueryExecutionException
import org.axonframework.queryhandling.QueryGateway

import java.util.concurrent.CompletionException

@CompileStatic
class QueryGatewayAdapter {
  private QueryGateway queryGateway

  QueryGatewayAdapter(QueryGateway queryGateway) {
    this.queryGateway = queryGateway
  }

  @SuppressWarnings("GrUnnecessaryPublicModifier")
  public <R, Q> R query(OperationRequest<Q> queryOperationRequest, Class<R> queryResponseClass) {
    GenericMessage queryMessage = new GenericMessage(queryOperationRequest.payload, queryOperationRequest.metaData)

    R queryResponse = null
    try {
      queryResponse = queryGateway.query(queryOperationRequest.payload.getClass().name, queryMessage, ResponseTypes.instanceOf(queryResponseClass)).join()
    }
    catch(CompletionException completionException) {
      if (completionException?.cause instanceof QueryExecutionException) {
        QueryExecutionException queryExecutionException = completionException.cause as QueryExecutionException
        if (queryExecutionException.details.isPresent()) {
          QueryException queryException = queryExecutionException.details.get() as QueryException
          throw queryException
        }
      }
      else {
        throw completionException
      }
    }

    return queryResponse
  }
}
