package com.oodlefinance.pradeep.rajendran.internal;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Service;

@Service
public class MessageServiceImpl implements MessageService {
	
	private Map<Long, Message> messages = new ConcurrentHashMap<>();
	
    private AtomicLong counter = new AtomicLong(0);

	@Override
	public List<Message> get() {
		return new ArrayList<>(messages.values());
	}

	@Override
	public Message get(Long id) {
		return messages.get(id);
	}

	@Override
	public Long create(Message message) {
		if (message.getMessage().isEmpty()) {
			throw new MessageException("Message is empty");
		}
		message.setId(counter.incrementAndGet());
		messages.put(message.getId(), message);
		return message.getId();
	}

	@Override
	public void delete(Long id) {
		messages.remove(id);
	}

	@Override
	public void clear() {
		this.messages.clear();
	}

}
