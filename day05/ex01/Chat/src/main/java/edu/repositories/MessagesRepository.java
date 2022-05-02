package edu.repositories;

import edu.models.Message;

import java.util.Optional;

public interface MessagesRepository {

    Optional<Message> findById(Long id);
}
