package net.croz.cargotracker.booking.queryside.rdbms.projection.application.factory

import groovy.transform.CompileStatic
import net.croz.cargotracker.booking.api.axon.event.CargoBookedEvent
import net.croz.cargotracker.booking.queryside.rdbms.domain.querymodel.CargoSummaryQueryEntity

@CompileStatic
class CargoSummaryFactoryService {
  static CargoSummaryQueryEntity createCargoSummaryQueryEntity(CargoBookedEvent cargoBookedEvent) {
    // TODO dmurat: automate populating persistent entity
    String aggregateIdentifier = cargoBookedEvent.aggregateIdentifier
    String originLocation = cargoBookedEvent.originLocation.unLoCode.code
    String destinationLocation = cargoBookedEvent.destinationLocation.unLoCode.code
    Long aggregateSequenceNumber = 0

    CargoSummaryQueryEntity cargoSummaryQueryEntity = new CargoSummaryQueryEntity(
        aggregateIdentifier: aggregateIdentifier, aggregateSequenceNumber: aggregateSequenceNumber, originLocation: originLocation, destinationLocation: destinationLocation
    )

    return cargoSummaryQueryEntity
  }
}