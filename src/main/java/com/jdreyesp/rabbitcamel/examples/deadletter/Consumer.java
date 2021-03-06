package com.jdreyesp.rabbitcamel.examples.deadletter;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Dead lettered message after 3 times. A log is printed when maximum retry times is reached
 */
public class Consumer {

    public static void main(String[] args) throws Exception {

        final Consumer consumer = new Consumer();
        final CamelContext camelContext = new DefaultCamelContext();

        final RouteBuilder builder = new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("rabbitmq://localhost:5672/retry?" +
                        "addresses=localhost&" +
                        "username=guest&" +
                        "password=guest&" +
                        "routingKey=#&" +
                        "queue=retry&" +
                        "declare=true&" +
                        "autoAck=false&" +
                        "exchangeType=topic&" +
                        "prefetchEnabled=true&" +
                        "prefetchSize=0&" +
                        "prefetchGlobal=false&" +
                        "prefetchCount=1&" +
                        "durable=false&" +
                        "autoDelete=true&" +
                        "deadLetterExchange=deadLetterExchange&" +
                        "deadLetterQueue=deadLetter&" +
                        "deadLetterRoutingKey=#&" +
                        "automaticRecoveryEnabled=true")
                        .to("log:foo");

            }
        };

        builder.onException(Exception.class)
                .maximumRedeliveries(3)
                .redeliveryDelay(0)
                .process(new Processor() {
                    public void process(Exchange exchange) throws Exception {
                        System.out.println("Retried after 3 times!");
                    }
                })
                .end();

        camelContext.addRoutes(builder);
        camelContext.start();
    }

    public void doWork(String task) throws Exception {
        throw new Exception("Controlled exception");
    }
}
