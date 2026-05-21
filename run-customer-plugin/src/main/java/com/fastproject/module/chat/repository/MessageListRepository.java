package com.fastproject.module.chat.repository;

import com.fastproject.module.chat.domain.MessageList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MessageListRepository extends JpaRepository<MessageList, Long>, JpaSpecificationExecutor<MessageList> {

}
