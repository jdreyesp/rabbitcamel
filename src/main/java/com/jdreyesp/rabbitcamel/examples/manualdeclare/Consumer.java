package com.jdreyesp.rabbitcamel.examples.manualdeclare;

import org.apache.camel.CamelContext;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.rabbitmq.RabbitMQConstants;
import org.apache.camel.impl.DefaultCamelContext;
import org.apache.camel.model.language.SimpleExpression;

/**
 * Dead lettered message after 3 times with requeue and NACK. A log is printed when maximum retry times is reached
 */
public class Consumer {

    public static void main(String[] args) throws Exception {

        final Consumer consumer = new Consumer();
        final CamelContext camelContext = new DefaultCamelContext();

        final RouteBuilder builder = new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("rabbitmq://localhost:5672/retry?" +
                        "exchangeType=topic&" +
                        "queue=retry&" +
                        "autoAck=false&" +
                        "deadLetterExchange=deadLetterExchange&deadLetterQueue=deadLetter&deadLetterRoutingKey=pepe")
                        .bean(consumer, "doWork");

            }
        };

        builder.onException(Exception.class)
                .maximumRedeliveries(3)
                .redeliveryDelay(0)
                .handled(true)
                .setHeader(RabbitMQConstants.REQUEUE, new SimpleExpression("true"))
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
