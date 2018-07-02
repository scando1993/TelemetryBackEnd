package net.pacificsoft.microservices.loka.core.model.DAO;

import net.pacificsoft.microservices.loka.core.model.Message;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

@Transactional
public interface MessageDao extends CrudRepository<Message, Long> {
    public Message findByTimestamp(String timestamp);
    public  Message findBySrc(String src);
    public Message findByDst(String dst);

}
