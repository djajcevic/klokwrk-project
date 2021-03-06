package org.klokwrk.cargotracker.booking.queryside

import groovy.transform.CompileStatic
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

/**
 * Booking query-side application.
 */
@SuppressWarnings("DuplicateStringLiteral")
@SpringBootApplication
@EnableJpaRepositories(basePackages = ["org.klokwrk.cargotracker.booking.queryside.rdbms.projection.domain.querymodel"])
@EntityScan(basePackages = ["org.klokwrk.cargotracker.booking.queryside.rdbms.projection.domain.querymodel"])
@CompileStatic
class BookingQuerySideApplication {
  static void main(String[] args) {
    SpringApplication.run(BookingQuerySideApplication, args)
  }
}
