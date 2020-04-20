package net.croz.cargotracker.api.open.shared.exceptional.violation

import groovy.transform.CompileStatic
import groovy.transform.Immutable
import groovy.transform.MapConstructor
import groovy.transform.PropertyOptions
import groovy.transform.TupleConstructor
import groovy.transform.VisibilityOptions
import groovy.transform.options.Visibility
import net.croz.cargotracker.lang.groovy.constructor.support.PostMapConstructorCheckable
import net.croz.cargotracker.lang.groovy.transform.options.RelaxedPropertyHandler

@Immutable
@PropertyOptions(propertyHandler = RelaxedPropertyHandler)
@TupleConstructor(visibilityId = "privateVisibility", pre = { throw new IllegalArgumentException("Calling a private constructor is not allowed") })
@VisibilityOptions(id = "privateVisibility", value = Visibility.PRIVATE)
@MapConstructor(post = { postMapConstructorCheckProtocol(args as Map) })
@CompileStatic
class ViolationInfo implements PostMapConstructorCheckable {
  static final ViolationInfo UNKNOWN = new ViolationInfo(severity: Severity.ERROR, violationCode: ViolationCode.UNKNOWN)
  static final ViolationInfo NOT_FOUND = new ViolationInfo(severity: Severity.WARNING, violationCode: ViolationCode.NOT_FOUND)

  Severity severity
  ViolationCode violationCode

  @Override
  void postMapConstructorCheck(Map<String, ?> constructorArguments) {
    assert severity
    assert violationCode
  }
}