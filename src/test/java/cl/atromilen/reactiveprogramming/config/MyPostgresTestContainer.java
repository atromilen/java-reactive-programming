package cl.atromilen.reactiveprogramming.config;

import org.testcontainers.containers.PostgreSQLContainer;

public class MyPostgresTestContainer extends PostgreSQLContainer<MyPostgresTestContainer> {
    private static final String IMAGE_VERSION = "postgres:latest";
    private static final String DATABASE_NAME = "testdb";
    private static final String DATABASE_USERNAME = "test";
    private static final String DATABASE_PASSWORD = "test";
    private static MyPostgresTestContainer container;

    private MyPostgresTestContainer() {
        super(IMAGE_VERSION);
    }

    public static MyPostgresTestContainer getInstance(){
        if (container == null){
            container = new MyPostgresTestContainer().
                    withDatabaseName(DATABASE_NAME)
                    .withUsername(DATABASE_USERNAME)
                    .withPassword(DATABASE_PASSWORD);
        }

        return container;
    }
}
