package io.openshift.booster.java_rest_http_crud;

import java.net.URL;
import java.sql.DriverManager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "io.openshift.booster.java_rest_http_crud")
public class BoosterApplication {

    public static void main(String[] args) throws Exception {
        URL url;
        try {
//            System.out.println(System.getenv("JDBC_URL") + " > " + System.getenv("DB_USER") + " to " + "172.17.0.2");

            Class.forName("org.postgresql.Driver");
            DriverManager.getConnection("jdbc:postgresql://172.30.167.31:5432/db-demo", "postgres",
                    "password");
//            DriverManager.getConnection("jdbc:postgresql://172.17.0.2:5432/upperio", "upperio_user",
//                    "upperio//s3cr37");
//            DriverManager.getConnection(System.getenv("JDBC_URL"), System.getenv("DB_USER"),
//                    System.getenv("DB_PASSWORD"));
            System.out.println("DB is available!!");
            url = BoosterApplication.class.getResource("/BOOT-INF/classes/db.properties");
        } catch (Exception e) {
            System.out.println("DB is no available!!: " + e.getLocalizedMessage());
            url = BoosterApplication.class.getResource("/BOOT-INF/classes/no-db.properties");
        }

        System.out.println("url " + ", " + url);
        System.setProperty("spring.config.location", url.toString());

        SpringApplication.run(BoosterApplication.class, args);
    }
}