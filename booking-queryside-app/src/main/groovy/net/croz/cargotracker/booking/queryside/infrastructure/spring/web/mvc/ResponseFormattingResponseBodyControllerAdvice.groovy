package net.croz.cargotracker.booking.queryside.infrastructure.spring.web.mvc

import groovy.transform.CompileStatic
import net.croz.cargotracker.infrastructure.project.web.spring.mvc.ResponseFormattingResponseBodyAdvice
import org.springframework.web.bind.annotation.ControllerAdvice

@ControllerAdvice
@CompileStatic
class ResponseFormattingResponseBodyControllerAdvice extends ResponseFormattingResponseBodyAdvice {
}