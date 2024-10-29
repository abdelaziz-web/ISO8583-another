package ma.stand.iso8583;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import ma.stand.iso8583.service.*;
import org.jpos.iso.ISOMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@SpringBootApplication
@OpenAPIDefinition(
        info = @Info(
                title = "ISO8583 API",
                version = "1.0",
                description = "API for ISO8583 message processing"
        )
)
@EnableMongoRepositories(basePackages = "ma.stand.iso8583.Model")

@ComponentScan(basePackages = {"ma.stand.iso8583.service","ma.stand.iso8583.controller","ma.stand.iso8583.Model","ma.stand.iso8583.cache"})
public class Iso8583Application {

    @Autowired
    private MongoOperations mongoTemplate;

    @Autowired
    auth_resp authResp ;



    public static void main(String[] args) throws Exception {
        SpringApplication.run(Iso8583Application.class, args);



    }
/*
    @Bean
    public CommandLineRunner createCollections() {
        return args -> {
            // List of collections to create if they do not exist
            String[] collections = {"m0100", "m0110", "m0400", "m0410"};

            // Check if each collection exists and create it if it does not
            for (String collectionName : collections) {
                if (!this.mongoTemplate.collectionExists(collectionName)) {
                    this.mongoTemplate.createCollection(collectionName);
                    System.out.println("Collection " + collectionName + " created.");
                } else {
                    System.out.println("Collection " + collectionName + " already exists.");
                }
            }
        };
    }
*/

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins("*")
                        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                        .allowedHeaders("*");
            }
        };

    }



}
