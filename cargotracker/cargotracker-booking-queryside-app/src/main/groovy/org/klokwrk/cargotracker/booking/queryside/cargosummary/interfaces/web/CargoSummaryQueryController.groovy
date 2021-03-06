package org.klokwrk.cargotracker.booking.queryside.cargosummary.interfaces.web

import groovy.transform.CompileStatic
import org.klokwrk.cargotracker.booking.queryside.cargosummary.domain.facade.CargoSummaryQueryFacadeService
import org.klokwrk.cargotracker.booking.queryside.cargosummary.domain.facade.CargoSummaryQueryRequest
import org.klokwrk.cargotracker.booking.queryside.cargosummary.domain.facade.CargoSummaryQueryResponse
import org.klokwrk.cargotracker.lib.boundary.api.metadata.constant.MetaDataConstant
import org.klokwrk.cargotracker.lib.boundary.api.operation.OperationRequest
import org.klokwrk.cargotracker.lib.boundary.api.operation.OperationResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
@CompileStatic
class CargoSummaryQueryController {
  private final CargoSummaryQueryFacadeService cargoBookingQueryApplicationService

  CargoSummaryQueryController(CargoSummaryQueryFacadeService cargoBookingQueryApplicationService) {
    this.cargoBookingQueryApplicationService = cargoBookingQueryApplicationService
  }

  @PostMapping("/cargo-summary")
  OperationResponse<CargoSummaryQueryResponse> cargoSummaryQuery(@RequestBody CargoSummaryQueryWebRequest webRequest, Locale locale) {
    OperationResponse<CargoSummaryQueryResponse> cargoSummary = cargoBookingQueryApplicationService.queryCargoSummary(createOperationRequest(webRequest, CargoSummaryQueryRequest, locale))
    return cargoSummary
  }

  /**
   * Creates {@link OperationRequest} from <code>webRequest</code> DTO.
   *
   * @param <P> Type of the {@link OperationRequest}'s payload.
   */
  @SuppressWarnings("GrUnnecessaryPublicModifier")
  public <P> OperationRequest<P> createOperationRequest(Object webRequest, Class<P> operationRequestPayloadType, Locale locale) {
    OperationRequest<P> operationRequest = new OperationRequest(
        payload: operationRequestPayloadType.newInstance(webRequest.properties),
        metaData: [(MetaDataConstant.INBOUND_CHANNEL_REQUEST_LOCALE_KEY): locale]
    )

    return operationRequest
  }
}
