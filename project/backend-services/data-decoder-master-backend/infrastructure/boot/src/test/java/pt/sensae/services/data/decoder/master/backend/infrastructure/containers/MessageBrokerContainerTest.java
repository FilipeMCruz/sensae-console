package pt.sensae.services.data.decoder.master.backend.infrastructure.containers;

import org.testcontainers.containers.RabbitMQContainer;
import org.testcontainers.utility.DockerImageName;

public class MessageBrokerContainerTest extends RabbitMQContainer {

    private static MessageBrokerContainerTest container;

    private MessageBrokerContainerTest() {
        super(DockerImageName.parse("rabbitmq"));
    }
    public static MessageBrokerContainerTest getInstance() {
        if (container == null) {
            container = new MessageBrokerContainerTest();
        }
        return container;
    }

    @Override
    public void stop() {
        //do nothing, JVM handles shut down
    }

}
