package com.example.serviceserver.dao;

import com.example.serviceserver.entity.ServiceMessage;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ServiceMessageDao {
    Page<ServiceMessage> getServiceMessages(long serviceId, Pageable pageable);

    String likeMessageByServiceMessageId(long serviceMessageId, long likerId);

    String unlikeMessageByServiceMessageId(long serviceMessageId, long unlikerId);

    String putMessage(long serviceId, long userId, String content);

    String deleteMessage(long serviceMessageId, long userId);
}
