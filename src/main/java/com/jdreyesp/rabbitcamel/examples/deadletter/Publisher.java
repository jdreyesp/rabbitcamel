package com.jdreyesp.rabbitcamel.examples.deadletter;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Created by jreyes on 2/7/17.
 */
public class Publisher {

    public static void main(String[] args) throws Exception {

        final CamelContext context = new DefaultCamelContext();

        context.addRoutes(new RouteBuilder() {
            public void configure() {
                from("timer://simple?period=20000").setBody().simple("Test message: ${id}")
                        .to("rabbitmq://localhost/retry?queue=retry&exchangeType=topic");
            }
        });
        context.start();

    }

}
