# Installation

This project has two environments, `dev` and `prod`.
Both of this environments need docker to run and docker-compose to orchestrate the containers.

Current version:

- `system` : `0.7.0`

## DEV Environment

This environment is manly used by developers running `linux`,`mac` or a `bsd` system (windows is currently not supported) and can be started with:

``` sh
docker-compose -f docker-compose.dev.yml up -d
```

This command will only start the databases, message brokers and load balancers (if existing).
This way the frontend and backend services can be started by the programmer on debug mode if and when needed.

Every external interaction with the environment is secured with `ssl`. To run the environment locally an `ssl certificate` has to be generated.
It is advised to use `openssl`.
Run the following commands to create the certificate `crt` and `key`.

``` sh
openssl req -x509 -out nginx.crt -keyout nginx.key \
  -newkey rsa:2048 -nodes -sha256 \
  -subj '/CN=localhost' -extensions EXT -config <( \
   printf "[dn]\nCN=localhost\n[req]\ndistinguished_name = dn\n[EXT]\nsubjectAltName=DNS:localhost\nkeyUsage=digitalSignature\nextendedKeyUsage=serverAuth")
sudo mv nginx* /etc/nginx/ssl/
```

The endpoints of each backend service are secured with JWT. This token is generated/validated internally by this services. To generate valid and secure JWTs a X509 certificate is used. This certificate keys, private and public, have to be generated, to do so follow [this](https://www.baeldung.com/java-rsa).

To run every frontend service run:

``` sh
cd project/frontend-services
nx serve-mfe
```

To run a single frontend service run:

``` sh
cd project/frontend-services
nx serve <frontend-name>
```

All communications between backend services, databases and message brokers are authenticated so there's a need to create "system" users and it's account passwords, for that create the following files, be sure to replace any identified tag (`<this is a tag>`).

File: `project/frontend-services/apps/sharespot-fleet-management-frontend/src/environments/environment.ts`

```ts
export const environment = {
  production: false,
  endpoints: {
    fleetManagement: {
      backend: {
        domain: "localhost:8086",
        http: {
          scheme: "http://",
          path: "/graphql"
        },
        websocket: {
          scheme: "ws://",
          path: "/subscriptions"
        }
      }
    }
  },
  mapbox: {
    accessToken: "<private key used to access mapbox api>",
    style: "mapbox://styles/mapbox/light-v10"
  }
}
```

File: `project/frontend-services/apps/smart-irrigation-frontend/src/environments/environment.ts`

```ts
export const environment = {
  production: false,
  endpoints: {
    smartIrrigation: {
      backend: {
        domain: "localhost:8098",
        http: {
          scheme: "http://",
          path: "/graphql"
        },
        websocket: {
          scheme: "ws://",
          path: "/subscriptions"
        }
      }
    }
  },
  mapbox: {
    accessToken: "<private key used to access mapbox api>",
    style: "mapbox://styles/mapbox/light-v10"
  }
}
```

File: `project/frontend-services/apps/device-management-frontend/src/environments/environment.ts`

```ts
export const environment = {
  production: false,
  endpoints: {
    deviceRecords: {
      backend: {
        domain: "localhost:8085",
        http: {
          scheme: "http://",
          path: "/graphql"
        }
      }
    }
  }
};
```

File: `project/frontend-services/apps/sharespot-data-processor-frontend/src/environments/environment.ts`

```ts
export const environment = {
  production: false,
  endpoints: {
    dataProcessor: {
      backend: {
        domain: "localhost:8083",
        http: {
          scheme: "http://",
          path: "/graphql"
        }
      }
    }
  }
};
```

File: `project/frontend-services/apps/sharespot-data-decoder-frontend/src/environments/environment.ts`

```ts
export const environment = {
  production: false,
  endpoints: {
    dataDecoder: {
      backend: {
        domain: "localhost:8092",
        http: {
          scheme: "http://",
          path: "/graphql"
        }
      }
    }
  }
};
```

File: `project/frontend-services/apps/sharespot-identity-management-frontend/src/environments/environment.ts`

```ts
export const environment = {
  production: false,
  endpoints: {
    identity: {
      backend: {
        domain: "localhost:8090",
        http: {
          scheme: "http://",
          path: "/graphql"
        }
      }
    }
  }
};
```

File: `project/frontend-services/apps/ui-aggregator/src/environments/environment.ts`

```ts
export const environment = {
  production: false,
  endpoints: {
    deviceRecords: {
      backend: {
        domain: "localhost:8085",
        http: {
          scheme: 'http://',
          path: "/graphql"
        }
      }
    },
    dataProcessor: {
      backend: {
        domain: "localhost:8083",
        http: {
          scheme: 'http://',
          path: "/graphql"
        }
      }
    },
    dataDecoder: {
      backend: {
        domain: "localhost:8092",
        http: {
          scheme: 'http://',
          path: "/graphql"
        }
      }
    },
    fleetManagement: {
      backend: {
        domain: "localhost:8086",
        http: {
          scheme: 'http://',
          path: "/graphql"
        },
        websocket: {
          scheme: 'ws://',
          path: "/subscriptions"
        }
      }
    },
    identity: {
      backend: {
        domain: "localhost:8090",
        http: {
          scheme: 'http://',
          path: "/graphql"
        }
      }
    },
    smartIrrigation: {
      backend: {
        domain: "localhost:8098",
        http: {
          scheme: "http://",
          path: "/graphql"
        },
        websocket: {
          scheme: "ws://",
          path: "/subscriptions"
        }
      }
    }
  },
  domain: "localhost"
};
```

File: `project/secrets/dev/sharespot-common-database.env`

``` conf
POSTGRES_USER=user
POSTGRES_PASSWORD=<key to exchange with device-management, identity-management, data-processor, data-decoder, smart-irrigation and device-commander backends>
```

File: `project/secrets/dev/sharespot-data-store-database.env`

``` conf
MONGO_INITDB_ROOT_USERNAME=root
MONGO_INITDB_ROOT_PASSWORD=<root database password>
MONGO_INITDB_DATABASE=data
```

File: `project/secrets/dev/init-sharespot-data-store-database.env`

``` js
db.createUser({
  user: "user",
  pwd: "<key to exchange with data-store>",
  roles: [{ role: "readWrite", db: "data" }],
});
```

File: `project/backend-services/sharespot-data-processor-master-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8083

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

logging.level.org.springframework.web=DEBUG
logging.level.web=DEBUG
logging.level.com.netflix.graphql.dgs=TRACE


spring.datasource.url=jdbc:postgresql://localhost:5432/transformations
spring.datasource.username=user
spring.datasource.password=<key to exchange with sharespot-common-database>
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

dgs.graphql.graphiql.enabled=true
dgs.graphql.graphiql.path=/graphiql

sensae.auth.pub.key.path=<path to X509 public key>
sensae.auth.issuer=<website domain that generates jwt>
sensae.auth.audience=<website domain that consumes jwt>
```

File: `project/backend-services/sharespot-data-processor-slave-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8081

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

spring.datasource.url=jdbc:postgresql://localhost:5432/transformations
spring.datasource.username=user
spring.datasource.password=<key to exchange with sharespot-common-database>
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
```

File: `project/backend-services/sharespot-data-decoder-master-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8088

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

logging.level.org.springframework.web=DEBUG
logging.level.web=DEBUG
logging.level.com.netflix.graphql.dgs=TRACE


spring.datasource.url=jdbc:postgresql://localhost:5432/decoder
spring.datasource.username=user
spring.datasource.password=<key to exchange with sharespot-common-database>
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

dgs.graphql.graphiql.enabled=true
dgs.graphql.graphiql.path=/graphiql

sensae.auth.pub.key.path=<path to X509 public key>
sensae.auth.issuer=<website domain that generates jwt>
sensae.auth.audience=<website domain that consumes jwt>
```

File: `project/backend-services/sharespot-data-decoder-slave-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8089

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

spring.datasource.url=jdbc:postgresql://localhost:5432/decoder
spring.datasource.username=user
spring.datasource.password=<key to exchange with sharespot-common-database>

spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
```

File: `project/backend-services/sharespot-data-store/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8087

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

logging.level.org.springframework.web=DEBUG
logging.level.web=DEBUG

spring.data.mongodb.database=data
spring.data.mongodb.port=27017
spring.data.mongodb.host=localhost
spring.data.mongodb.username=user
spring.data.mongodb.password=<key to exchange with data store database mongodb> 
```

File: `project/backend-services/device-management-master-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8085

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

logging.level.org.springframework.web=DEBUG
logging.level.web=DEBUG
logging.level.com.netflix.graphql.dgs=TRACE


spring.datasource.url=jdbc:postgresql://localhost:5432/records
spring.datasource.username=user
spring.datasource.password=<key to exchange with sharespot-common-database>
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

dgs.graphql.graphiql.enabled=true
dgs.graphql.graphiql.path=/graphiql

sensae.auth.pub.key.path=<path to X509 public key>
sensae.auth.issuer=<website domain that generates jwt>
sensae.auth.audience=<website domain that consumes jwt>
```

File: `project/backend-services/device-management-slave-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8086

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

spring.datasource.url=jdbc:postgresql://localhost:5432/records
spring.datasource.username=user
spring.datasource.password=<key to exchange with sharespot-common-database>
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
```

File: `project/backend-services/sharespot-data-gateway/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8080

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

sharespot.auth.key=<auth key to authenticate requests>
```

File: `project/backend-services/data-validator-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8093

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest
```

File: `project/backend-services/sharespot-fleet-management-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8085

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

logging.level.org.springframework.web=DEBUG
logging.level.web=DEBUG
logging.level.com.netflix.graphql.dgs=TRACE

spring.datasource.url=jdbc:postgresql://localhost:8812/qdb?sslmode=disable
spring.datasource.username=admin
spring.datasource.password=quest

sensae.auth.pub.key.path=<path to X509 public key>
sensae.auth.issuer=<website domain that generates jwt>
sensae.auth.audience=<website domain that consumes jwt>
```

File: `project/backend-services/sharespot-identity-management-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8090

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

logging.level.org.springframework.web=DEBUG
logging.level.web=DEBUG
logging.level.com.netflix.graphql.dgs=TRACE


spring.datasource.url=jdbc:postgresql://localhost:5432/identity
spring.datasource.username=user
spring.datasource.password=<key to exchange with sharespot-common-database>
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true

dgs.graphql.graphiql.enabled=true
dgs.graphql.graphiql.path=/graphiql

sensae.auth.priv.key.path=<path to X509 private key>
sensae.auth.pub.key.path=<path to X509 public key>
sensae.auth.issuer=<website domain that generates jwt>
sensae.auth.audience=<website domain that consumes jwt>

sensae.auth.external.issuer=<website domain that generates id_tokens>
sensae.auth.external.audience=<registered app id in identity provider service>
```

File: `project/backend-services/sharespot-identity-management-slave-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8091

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

spring.datasource.url=jdbc:postgresql://localhost:5432/identity
spring.datasource.username=user
spring.datasource.password=<key to exchange with sharespot-common-database>
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
```

File: `project/backend-services/device-commander-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8082

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

spring.datasource.url=jdbc:postgresql://localhost:5432/records
spring.datasource.username=user
spring.datasource.password=<key to exchange with sharespot-common-database>
spring.jpa.show-sql=true
spring.jpa.generate-ddl=true
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation=true
```

File: `project/backend-services/smart-irrigation-backend/infrastructure/boot/src/main/resources/application-dev.properties`

``` conf
server.port=8098

spring.rabbitmq.host=localhost
spring.rabbitmq.port=5672
spring.rabbitmq.username=guest
spring.rabbitmq.password=guest

logging.level.org.springframework.web=DEBUG
logging.level.web=DEBUG
logging.level.com.netflix.graphql.dgs=TRACE
logging.level.org.springframework.jdbc.core=TRACE

spring.datasource.data.jdbc-url=jdbc:postgresql://localhost:8898/qdb?sslmode=disable
spring.datasource.data.username=admin
spring.datasource.data.password=quest

spring.datasource.business.jdbc-url=jdbc:postgresql://localhost:5432/irrigation
spring.datasource.business.username=user
spring.datasource.business.password=<key to exchange with sharespot-common-database>

sensae.auth.pub.key.path=<path to X509 public key>
sensae.auth.issuer=<website domain that generates jwt>
sensae.auth.audience=<website domain that consumes jwt>
```

## PROD Environment

This environment is used in production and can be started with (after creating the files below):

``` sh
./project/scripts/reset-prod.sh
```

Every external interaction with the environment is secured with `ssl`. To run the environment in production an `ssl certificate` has to be generated.
It is advised to use the program `certbot`.
Run the following commands to create the certificate `crt` and `key`.

``` sh
sudo certbot --nginx -d <yourdomain>
mkdir /etc/nginx/ssl
ln -s /etc/letsencrypt/live/<yourdomain>/fullchain.pem /etc/nginx/ssl/nginx.crt
ln -s /etc/letsencrypt/live/<yourdomain>/privkey.pem /etc/nginx/ssl/nginx.key
```

The endpoints of each backend service are secured with JWT. This token is generated/validated internally by this services. To generate valid and secure JWTs a X509 certificate is used. This certificate keys, private and public, have to be generated, to do so follow [this](https://www.baeldung.com/java-rsa).

All communications between backend services, databases and message brokers are authenticated so there's a need to create users and it's account passwords, for that create the following files, be sure to replace all identified tags (`<this is a tag>`). All `environment.prod.ts` need it's associated `environment.ts` config.

File: `project/frontend-services/apps/sharespot-fleet-management-frontend/src/environments/environment.prod.ts`

``` ts
export const environment = {
  production: true,
  endpoints: {
    fleetManagement: {
      backend: {
        domain: "localhost",
        http: {
          scheme: "https://",
          path: "/fleet-management/graphql"
        },
        websocket: {
          scheme: "wss://",
          path: "/fleet-management/subscriptions"
        }
      }
    }
  },
  mapbox: {
    accessToken: "<private key used to access mapbox api>",
    style: "mapbox://styles/mapbox/light-v10"
  }
};
```

File: `project/frontend-services/apps/sharespot-fleet-management-frontend/src/environments/environment.prod.ts`

``` ts
export const environment = {
  production: true,
  endpoints: {
    smartIrrigation: {
      backend: {
        domain: "localhost",
        http: {
          scheme: "https://",
          path: "/smart-irrigation/graphql"
        },
        websocket: {
          scheme: "wss://",
          path: "/smart-irrigation/subscriptions"
        }
      }
    }
  },
  mapbox: {
    accessToken: "<private key used to access mapbox api>",
    style: "mapbox://styles/mapbox/light-v10"
  }
};
```

File: `project/frontend-services/apps/device-management-frontend/src/environments/environment.prod.ts`

```ts
export const environment = {
  production: true,
  endpoints: {
    deviceRecords: {
      backend: {
        domain: "localhost",
        http: {
          scheme: "https://",
          path: "/device-records/graphql"
        }
      }
    }
  }
};

```

File: `project/frontend-services/apps/sharespot-data-processor-frontend/src/environments/environment.prod.ts`

```ts
export const environment = {
  production: true,
  endpoints: {
    dataProcessor: {
      backend: {
        domain: "localhost",
        http: {
          scheme: "https://",
          path: "/data-processor/graphql"
        }
      }
    }
  }
};
```

File: `project/frontend-services/apps/sharespot-data-decoder-frontend/src/environments/environment.prod.ts`

```ts
export const environment = {
  production: true,
  endpoints: {
    dataDecoder: {
      backend: {
        domain: "localhost",
        http: {
          scheme: "https://",
          path: "/data-decoder/graphql"
        }
      }
    }
  }
};
```

File: `project/frontend-services/apps/sharespot-identity-management-frontend/src/environments/environment.prod.ts`

```ts
export const environment = {
  production: true,
  endpoints: {
    identity: {
      backend: {
        domain: "localhost",
        http: {
          scheme: "https://",
          path: "/identity-management/graphql"
        }
      }
    }
  }
};
```

File: `project/frontend-services/apps/ui-aggregator/src/environments/environment.prod.ts`

```ts
export const environment = {
  production: true,
  endpoints: {
    deviceRecords: {
      backend: {
        domain: "<public domain>",
        http: {
          scheme: 'https://',
          path: "/device-management/graphql"
        }
      }
    },
    dataProcessor: {
      backend: {
        domain: "<public domain>",
        http: {
          scheme: 'https://',
          path: "/data-processor/graphql"
        }
      }
    },
    dataDecoder: {
      backend: {
        domain: "<public domain>",
        http: {
          scheme: 'https://',
          path: "/data-decoder/graphql"
        }
      }
    },
    fleetManagement: {
      backend: {
        domain: "<public domain>",
        http: {
          scheme: 'https://',
          path: "/fleet-management/graphql"
        },
        websocket: {
          scheme: 'wss://',
          path: "/fleet-management/subscriptions"
        }
      }
    },
    identity: {
      backend: {
        domain: "<public domain>",
        http: {
          scheme: 'https://',
          path: "/identity-management/graphql"
        }
      }
    },
    smartIrrigation: {
      backend: {
        domain: "<public domain>",
        http: {
          scheme: "https://",
          path: "/smart-irrigation/graphql"
        },
        websocket: {
          scheme: "wss://",
          path: "/smart-irrigation/subscriptions"
        }
      }
    }
  },
  domain: "<public domain>"
};
```

File. `project/secrets/prod/init-sharespot-data-store-database.js`

``` js
db.createUser({
  user: "user",
  pwd: "<key to exchange with data-store>",
  roles: [{ role: "readWrite", db: "data" }],
});
```

File. `project/secrets/prod/sharespot-common-database.env`

``` conf
POSTGRES_USER=user
POSTGRES_PASSWORD=<key to exchange with device-management, identity-management, data-processor, data-decoder, smart-irrigation and device-commander backends>
```

File. `project/secrets/prod/sharespot-data-decoder-master-backend.env`

``` conf
SPRING_DATASOURCE_URL=jdbc:postgresql://sharespot-common-database:5432/decoder
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=<key to exchange with sharespot-common-database>

SENSAE_AUTH_PUB_KEY_PATH=<path to X509 public key>
SENSAE_AUTH_ISSUER=<website domain that generates jwt>
SENSAE_AUTH_AUDIENCE=<website domain that consumes jwt>
```

File. `project/secrets/prod/sharespot-data-decoder-slave-backend.env`

``` conf
SPRING_DATASOURCE_URL=jdbc:postgresql://sharespot-common-database:5432/decoder
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=<key to exchange with sharespot-common-database>
```

File. `project/secrets/prod/sharespot-data-processor-master-backend.env`

``` conf
SPRING_DATASOURCE_URL=jdbc:postgresql://sharespot-common-database:5432/transformations
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=<key to exchange with sharespot-common-database>

SENSAE_AUTH_PUB_KEY_PATH=<path to X509 public key>
SENSAE_AUTH_ISSUER=<website domain that generates jwt>
SENSAE_AUTH_AUDIENCE=<website domain that consumes jwt>
```

File. `project/secrets/prod/sharespot-data-processor-slave-backend.env`

``` conf
SPRING_DATASOURCE_URL=jdbc:postgresql://sharespot-common-database:5432/transformations
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=<key to exchange with sharespot-common-database>
```

File. `project/secrets/prod/sharespot-data-store-backend.env`

``` conf
SPRING_DATA_MONGODB_DATABASE=data
SPRING_DATA_MONGODB_PORT=27017
SPRING_DATA_MONGODB_HOST=sharespot-data-store-database
SPRING_DATA_MONGODB_USERNAME=user
SPRING_DATA_MONGODB_PASSWORD=<key to exchange with data-store>
```

File. `project/secrets/prod/sharespot-data-store-database.env`

``` conf
MONGO_INITDB_ROOT_USERNAME=root
MONGO_INITDB_ROOT_PASSWORD=<key to exchange with data-store>
MONGO_INITDB_DATABASE=data
```

File. `project/secrets/prod/device-management-master-backend.env`

``` conf
SPRING_DATASOURCE_URL=jdbc:postgresql://sharespot-common-database:5432/records
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=<key to exchange with sharespot-common-database>

SENSAE_AUTH_PUB_KEY_PATH=<path to X509 public key>
SENSAE_AUTH_ISSUER=<website domain that generates jwt>
SENSAE_AUTH_AUDIENCE=<website domain that consumes jwt>
```

File. `project/secrets/prod/device-management-slave-backend.env`

``` conf
SPRING_DATASOURCE_URL=jdbc:postgresql://sharespot-common-database:5432/records
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=<key to exchange with sharespot-common-database>
```

File. `project/secrets/prod/device-commander-backend.env`

``` conf
SPRING_DATASOURCE_URL=jdbc:postgresql://sharespot-common-database:5432/records
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=<key to exchange with sharespot-common-database>
```

File. `project/secrets/prod/sharespot-data-gateway.env`

``` conf
SHARESPOT_AUTH_KEY=<auth key to authenticate requests>
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest
```

File. `project/secrets/prod/sharespot-identity-management-backend.env`

``` conf
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest

SPRING_DATASOURCE_URL=jdbc:postgresql://sharespot-common-database:5432/identity
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=<key to exchange with sharespot-common-database>

SENSAE_AUTH_PRIV_KEY_PATH=<path to X509 private key>
SENSAE_AUTH_PUB_KEY_PATH=<path to X509 public key>
SENSAE_AUTH_ISSUER=<website domain that generates jwt>
SENSAE_AUTH_AUDIENCE=<website domain that consumes jwt>
SENSAE_AUTH_EXTERNAL_ISSUER=<website domain that generates id_tokens>
SENSAE_AUTH_EXTERNAL_AUDIENCE=<registered app id in identity provider service>
```

File. `project/secrets/prod/sharespot-identity-management-slave-backend.env`

``` conf
SPRING_DATASOURCE_URL=jdbc:postgresql://sharespot-common-database:5432/identity
SPRING_DATASOURCE_USERNAME=user
SPRING_DATASOURCE_PASSWORD=<key to exchange with sharespot-common-database>
```

File. `project/secrets/prod/data-validator-backend.env`

``` conf
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest
```

File. `project/secrets/prod/sharespot-fleet-management-backend.env`

``` conf
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest

SPRING_DATASOURCE_URL=jdbc:postgresql://questdb:8812/qdb?sslmode=disable
SPRING_DATASOURCE_USERNAME=admin
SPRING_DATASOURCE_PASSWORD=quest

SENSAE_AUTH_PUB_KEY_PATH=<path to X509 public key>
SENSAE_AUTH_ISSUER=<website domain that generates jwt>
SENSAE_AUTH_AUDIENCE=<website domain that consumes jwt>
```

File. `project/secrets/prod/sharespot-fleet-management-backend.env`

``` conf
SPRING_RABBITMQ_USERNAME=guest
SPRING_RABBITMQ_PASSWORD=guest

SPRING_DATASOURCE_DATA_JDBC_URL=jdbc:postgresql://irrigation-database:8812/qdb?sslmode=disable
SPRING_DATASOURCE_DATA_USERNAME=admin
SPRING_DATASOURCE_DATA_PASSWORD=quest

SPRING_DATASOURCE_BUSINESS_JDBC_URL=jdbc:postgresql://sharespot-common-database:5432/irrigation
SPRING_DATASOURCE_BUSINESS_USERNAME=user
SPRING_DATASOURCE_BUSINESS_PASSWORD=<key to exchange with sharespot-common-database>

SENSAE_AUTH_PUB_KEY_PATH=<path to X509 public key>
SENSAE_AUTH_ISSUER=<website domain that generates jwt>
SENSAE_AUTH_AUDIENCE=<website domain that consumes jwt>
```
