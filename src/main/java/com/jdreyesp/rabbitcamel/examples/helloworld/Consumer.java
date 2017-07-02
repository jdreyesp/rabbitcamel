package com.jdreyesp.rabbitcamel.examples.helloworld;

import org.apache.camel.CamelContext;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.impl.DefaultCamelContext;

/**
 * Created by jreyes on 2/7/17.
 */
public class Consumer {

    public static void main(String[] args) throws Exception {

        final CamelContext camelContext = new DefaultCamelContext();

        camelContext.addRoutes(new RouteBuilder() {
            @Override
            public void configure() throws Exception {
                from("rabbitmq://localhost:5672/helloworld?queue=hello").to("log:foo");
            }
        });

        camelContext.start();
    }
}
