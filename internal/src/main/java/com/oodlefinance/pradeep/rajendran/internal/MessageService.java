package com.oodlefinance.pradeep.rajendran.internal;

import java.util.List;

public interface MessageService {
	
	List<Message> get();
	
	Message get(Long id);
	
	Long create(Message message);
	
	void delete(Long id);
	
	void clear();

}
