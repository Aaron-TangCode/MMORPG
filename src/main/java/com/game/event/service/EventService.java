package com.game.event.service;

import com.game.event.repository.EventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName EventService
 * @Description TODO
 * @Author DELL
 * @Date 2019/7/23 12:00
 * @Version 1.0
 */
@Service
public class EventService {
    @Autowired
    private EventRepository eventRepository;

    public int queryCount(int roleId) {

        return eventRepository.queryCount(roleId);
    }

    public void updateCount(int count,int roleId) {
        eventRepository.updateCount(count,roleId);
    }

    public void insert(int roleId, int count) {
        eventRepository.insert(roleId,count);
    }
}
