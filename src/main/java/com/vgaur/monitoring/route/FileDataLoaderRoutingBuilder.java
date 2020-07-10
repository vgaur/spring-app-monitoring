package com.vgaur.monitoring.route;

import com.vgaur.monitoring.util.FileIdentifier;
import org.apache.camel.builder.RouteBuilder;

public class FileDataLoaderRoutingBuilder extends RouteBuilder {

    @Override public void configure() throws Exception {
        from("direct:loaddata").process(exchange -> {
            String fileIdentifier = exchange.getIn().getBody(String.class);
            String classPathResource = FileIdentifier.lookUp(fileIdentifier);
            exchange.getIn().setBody("resource:classpath:" + classPathResource);
        });


    }
}
