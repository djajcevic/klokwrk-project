package org.klokwrk.cargotracker.booking.commandside.cargobook.axon.api

import groovy.transform.CompileStatic
import groovy.transform.MapConstructor
import groovy.transform.PropertyOptions
import org.klokwrk.cargotracker.booking.domain.model.Location
import org.klokwrk.lang.groovy.transform.options.RelaxedPropertyHandler

@PropertyOptions(propertyHandler = RelaxedPropertyHandler)
@MapConstructor(noArg = true)
@CompileStatic
class CargoBookedEvent {
  String aggregateIdentifier

  Location originLocation
  Location destinationLocation
}
