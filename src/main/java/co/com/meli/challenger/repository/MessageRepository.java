package co.com.meli.challenger.repository;

import co.com.meli.challenger.model.DataMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MessageRepository extends JpaRepository<DataMessage, Long> {

    List<DataMessage> findAll();

}
