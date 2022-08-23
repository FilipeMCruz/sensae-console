package pt.sensae.services.smart.irrigation.backend.infrastructure.persistence.questdb.utils;


import io.questdb.client.Sender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import pt.sensae.services.smart.irrigation.backend.domain.exceptions.SenderException;

import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.Set;
import java.util.Stack;

@Component
public class ILPSenderPool {

    Stack<Sender> freePool = new Stack<>();

    Set<Sender> occupiedPool = new HashSet<>();

    private final static Logger LOGGER = LoggerFactory.getLogger(ILPSenderPool.class);

    public ILPSenderPool(@Value("${sensae.questdb.ilp.address}") String address,
                         @Value("${sensae.questdb.ilp.poolSize}") int poolSize) {
        LOGGER.info("SenderPool - Starting...");
        for (int i = 0; i < poolSize; i++)
            this.freePool.push(Sender.builder().address(address).build());
        LOGGER.info("SenderPool - Start Completed.");
    }

    public synchronized Sender getSender() {
        if (freePool.empty())
            throw new SenderException("All senders are busy");

        var sender = freePool.pop();
        occupiedPool.add(sender);
        LOGGER.info("SenderPool - Sender taken");
        return sender;
    }

    public synchronized void returnSender(Sender sender) {
        if (!occupiedPool.remove(sender))
            throw new SenderException("The sender was returned already or it isn't for this pool");

        freePool.push(sender);
        LOGGER.info("SenderPool - Sender returned");
    }

    public synchronized void returnSenderAndFlush(Sender sender) {
        if (!occupiedPool.remove(sender))
            throw new SenderException("The sender was returned already or it isn't for this pool");

        sender.flush();
        
        freePool.push(sender);
        LOGGER.info("SenderPool - Sender returned");
    }

    @PreDestroy
    public void destroy() {
        LOGGER.info("SenderPool - Shutdown initiated...");
        freePool.forEach(Sender::flush);
        occupiedPool.forEach(Sender::flush);

        freePool.forEach(Sender::close);
        occupiedPool.forEach(Sender::close);
        LOGGER.info("SenderPool - Shutdown complete.");
    }
}
