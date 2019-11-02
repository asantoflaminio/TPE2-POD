package ar.edu.itba.pod.server;

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author Grupo 2
 *
 */
public class Server {
    private static Logger logger = LoggerFactory.getLogger(Server.class);

    /**
     * Server
     */
    public static void main(String[] args) {
        logger.info("The server is starting");
        HazelcastInstance hz = Hazelcast.newHazelcastInstance();
    }
}
