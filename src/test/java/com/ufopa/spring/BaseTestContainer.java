package com.ufopa.spring;

import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@Testcontainers
public abstract class BaseTestContainer {

  @Container
  static PostgreSQLContainer<?> pgsql = DatabaseTestContainer.getInstance();

}

class DatabaseTestContainer extends PostgreSQLContainer<DatabaseTestContainer> {

  private static final String IMAGE_VERSION = "postgres:15-alpine";
  private static DatabaseTestContainer container;

  private DatabaseTestContainer() {
    super(IMAGE_VERSION);
  }

  public static DatabaseTestContainer getInstance() {
    if (container == null)
      container = new DatabaseTestContainer();
    return container;
  }

  @Override
  public void start() {
    super.start();
    System.setProperty("DB_URL", container.getJdbcUrl());
    System.setProperty("DB_USER", container.getUsername());
    System.setProperty("DB_PASS", container.getPassword());
  }

  @Override
  public void stop() {
  }

}
