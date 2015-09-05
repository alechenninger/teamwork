package com.github.alechenninger.teamwork.hub;

import com.github.alechenninger.teamwork.endpoints.ConsumerPickUpUriFactory;

import org.apache.camel.Exchange;
import org.apache.camel.Expression;
import org.apache.camel.Predicate;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.ExpressionAdapter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

// Per type
public class CanonicalRouter extends RouteBuilder {
  private final CanonicalTopicUriFactory canonicalTopic; // factory... could be String uri
  private final Predicate canonicalValidator; // actual thing... could be ValidatorFactory
  private final Map<String, Predicate> userConsumerFilters = new HashMap<>();
  private final ConsumerPickUpUriFactory consumerPickUpUriFactory;
  private final String messageType;

  public CanonicalRouter(String messageType, Predicate canonicalValidator,
      CanonicalTopicUriFactory canonicalTopic, ConsumerPickUpUriFactory consumerPickUpUriFactory) {
    this.canonicalTopic = canonicalTopic;
    this.canonicalValidator = canonicalValidator;
    this.consumerPickUpUriFactory = consumerPickUpUriFactory;
    this.messageType = messageType;
  }

  public void addOrUpdateConsumer(String userName, Predicate filter) {
    userConsumerFilters.put(
        Objects.requireNonNull(userName, "userName"),
        Objects.requireNonNull(filter, "filter"));
  }

  @Override
  public void configure() throws Exception {
    from(canonicalTopic.forMessageType(messageType))
        .validate(canonicalValidator)
        .recipientList(consumersForExchange());
  }

  private Expression consumersForExchange() {
    return new ExpressionAdapter() {
      @Override
      public Object evaluate(Exchange exchange) {
        Set<String> consumerUris = new HashSet<>(userConsumerFilters.size());

        for (Map.Entry<String, Predicate> userConsumerFilter : userConsumerFilters.entrySet()) {
          String userName = userConsumerFilter.getKey();
          Predicate filter = userConsumerFilter.getValue();

          if (filter.matches(exchange)) {
            consumerUris.add(consumerPickUpUriFactory.forUserAndMessageType(userName, messageType));
          } else {
            // TODO: log / report
          }
        }

        return consumerUris;
      }
    };
  }
}
