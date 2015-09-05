package com.github.alechenninger.teamwork.hub;

import com.github.alechenninger.teamwork.MessageType;
import com.github.alechenninger.teamwork.UserName;
import com.github.alechenninger.teamwork.endpoints.UriFactory;

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
  private final Map<UserName, Predicate> userConsumerFilters = new HashMap<>();
  private final UriFactory uriFactory;
  private final MessageType messageType;

  public CanonicalRouter(MessageType messageType, Predicate canonicalValidator,
      CanonicalTopicUriFactory canonicalTopic, UriFactory uriFactory) {
    this.canonicalTopic = canonicalTopic;
    this.canonicalValidator = canonicalValidator;
    this.uriFactory = uriFactory;
    this.messageType = messageType;
  }

  public void addOrUpdateConsumer(UserName userName, Predicate filter) {
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

        for (Map.Entry<UserName, Predicate> userConsumerFilter : userConsumerFilters.entrySet()) {
          UserName userName = userConsumerFilter.getKey();
          Predicate filter = userConsumerFilter.getValue();

          if (filter.matches(exchange)) {
            consumerUris.add(uriFactory.forUserAndMessageType(userName, messageType));
          } else {
            // TODO: log / report
          }
        }

        return consumerUris;
      }
    };
  }
}
