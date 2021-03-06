package org.klokwrk.lang.groovy.constructor.support.stub

import groovy.transform.CompileStatic
import groovy.transform.MapConstructor
import org.klokwrk.lang.groovy.constructor.support.PostMapConstructorCheckable

@MapConstructor(post = { postMapConstructorCheckProtocol(args as Map) })
@CompileStatic
class TestStrictPerson implements PostMapConstructorCheckable {
  String firstName

  @Override
  Boolean postMapConstructorShouldThrowForEmptyConstructorArguments() {
    return true
  }

  @Override
  void postMapConstructorCheck(Map<String, ?> constructorArguments) {
  }
}
