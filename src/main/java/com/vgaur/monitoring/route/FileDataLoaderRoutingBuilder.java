package com.vgaur.monitoring.route;

import com.vgaur.monitoring.storage.InMemoryDatabase;
import com.vgaur.monitoring.util.FileIdentifier;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component public class FileDataLoaderRoutingBuilder extends RouteBuilder {

    InMemoryDatabase inMemoryDatabase =
        InMemoryDatabase.INSTANCE.SINGLE_INSTANCE.getInMemoryDatabase();

    private static final String FILE_BASE_PATH =
        "/files/";

    @Override public void configure() throws Exception {


        from("direct:loaddata").process(exchange -> {
            String fileIdentifier = exchange.getIn().getBody(String.class);
            String classPathResource = FileIdentifier.lookUp(fileIdentifier);
            exchange.getIn().setHeader("FILE_NAME", classPathResource);
        }).to("direct:load");

        from("direct:load").pollEnrich("file:"+FILE_BASE_PATH+"?fileName=${header.FILE_NAME}")
            .log("${header.FILE_NAME}").log("${body}").split(body().tokenize("\n")).streaming()
            .process(exc -> {
                System.out.println(exc.getIn().getBody());
                //inMemoryDatabase.lo

            });


    }
}
