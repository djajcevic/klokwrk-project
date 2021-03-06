package org.klokwrk.cargotracker.lib.axon.logging

import groovy.transform.CompileStatic
import groovy.util.logging.Slf4j
import org.axonframework.messaging.Message
import org.axonframework.messaging.annotation.HandlerEnhancerDefinition
import org.axonframework.messaging.annotation.MessageHandlingMember
import org.axonframework.messaging.annotation.WrappedMessageHandlingMember
import org.axonframework.queryhandling.QueryHandler

import java.lang.reflect.Method

/**
 * Defines Axon's {@link HandlerEnhancerDefinition} for detailed logging of query handler executions.
 * <p/>
 * Corresponding Slf4j logger uses '<code>cargotracker.axon.query-handler-logging</code>' category and it logs on <code>DEBUG</code> level. Logger output contains information about query's payload.
 * <p/>
 * Logged output looks similar to this:
 * <pre>
 * ... cargotracker.axon.query-handler-logging - Executing QueryHandler method [MyTestQueryHandler.handleSomeQuery(MyTestQuery)] with payload [query:123]
 * </pre>
 * To register this HandlerEnhancerDefinition, use standard means as described in Axon documentation. In Spring Boot applications only a simple bean declaration is required.
 */
@CompileStatic
class LoggingQueryHandlerEnhancerDefinition implements HandlerEnhancerDefinition {
  @Override
  <T> MessageHandlingMember<T> wrapHandler(MessageHandlingMember<T> originalMessageHandlingMember) {
    MessageHandlingMember selectedMessageHandlingMember = originalMessageHandlingMember
        .annotationAttributes(QueryHandler)
        .map((Map<String, Object> attr) -> new LoggingQueryHandlingMember(originalMessageHandlingMember) as MessageHandlingMember)
        .orElse(originalMessageHandlingMember) as MessageHandlingMember

    return selectedMessageHandlingMember
  }

  @SuppressWarnings("Indentation")
  @Slf4j(category = "cargotracker.axon.query-handler-logging")
  static class LoggingQueryHandlingMember<T> extends WrappedMessageHandlingMember<T> {
    MessageHandlingMember<T> messageHandlingMember

    protected LoggingQueryHandlingMember(MessageHandlingMember<T> messageHandlingMember) {
      super(messageHandlingMember)
      this.messageHandlingMember = messageHandlingMember
    }

    @Override
    Object handle(Message<?> message, T target) throws Exception {
      if (log.isDebugEnabled()) {
        messageHandlingMember.unwrap(Method).ifPresent((Method method) -> {
          Map payloadProperties = message.payload.propertiesFiltered
          log.debug("Executing QueryHandler method [${method.declaringClass.simpleName}.${method.name}(${method.parameterTypes*.simpleName?.join(",")})] with payload ${payloadProperties}")
        })
      }

      return super.handle(message, target)
    }
  }
}
