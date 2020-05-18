package org.klokwrk.cargotracker.booking.queryside.cargosummary.domain.facade

import groovy.transform.CompileStatic
import org.axonframework.queryhandling.QueryGateway
import org.klokwrk.cargotracker.lib.axon.cqrs.querygateway.QueryGatewayAdapter
import org.klokwrk.cargotracker.lib.boundary.api.conversation.OperationRequest
import org.klokwrk.cargotracker.lib.boundary.api.conversation.OperationResponse
import org.klokwrk.cargotracker.lib.boundary.api.metadata.ResponseMetaDataReport
import org.springframework.stereotype.Service

@Service
@CompileStatic
class CargoSummaryQueryFacadeService {
  private final QueryGatewayAdapter queryGatewayAdapter

  CargoSummaryQueryFacadeService(QueryGateway queryGateway) {
    this.queryGatewayAdapter = new QueryGatewayAdapter(queryGateway)
  }

  OperationResponse<CargoSummaryQueryResponse> queryCargoSummary(OperationRequest<CargoSummaryQueryRequest> cargoSummaryQueryOperationRequest) {
    CargoSummaryQueryResponse cargoSummaryQueryResponse = queryGatewayAdapter.query(cargoSummaryQueryOperationRequest, CargoSummaryQueryResponse)
    return cargoSummaryQueryOperationResponseFromCargoSummaryQueryResponse(cargoSummaryQueryResponse)
  }

  OperationResponse<CargoSummaryQueryResponse> cargoSummaryQueryOperationResponseFromCargoSummaryQueryResponse(CargoSummaryQueryResponse cargoSummaryQueryResponse) {
    ResponseMetaDataReport responseMetaDataReport = ResponseMetaDataReport.createBasicInfoMetaDataReport()
    return new OperationResponse<CargoSummaryQueryResponse>(payload: cargoSummaryQueryResponse, metaData: responseMetaDataReport.propertiesFiltered)
  }
}
