package net.pacificsoft.microservices.telemetry.sms.repository;

import net.pacificsoft.microservices.telemetry.sms.models.Devices;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DevicesRepository extends MongoRepository<Devices, ObjectId>{

}
