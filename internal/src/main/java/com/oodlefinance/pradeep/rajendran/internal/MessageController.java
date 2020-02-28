package com.oodlefinance.pradeep.rajendran.internal;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/message")
public class MessageController {
	
	private MessageService messageService;
	
	@Autowired
	public MessageController(MessageService messageService) {
		this.messageService =  messageService;
	}

	@GetMapping
	public List<Message> get() {
		return messageService.get();
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<? extends Object> get(@PathVariable("id") Long id) {
		Message message = messageService.get(id);
		if (message == null) {
			return new ResponseEntity<String>("Message not found",HttpStatus.NOT_FOUND);
		}
		return ResponseEntity.ok(message);
	}
	
	@PostMapping
	public ResponseEntity<Long> create(@RequestBody Message message) {
		Long id = messageService.create(message);
		return new ResponseEntity<Long>(id, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<String> delete(@PathVariable("id") Long id) {
		messageService.delete(id);
		return new ResponseEntity<>("Message deleted", HttpStatus.OK);
	}
	
	
	@ExceptionHandler(MessageException.class)
    public ResponseEntity<String> handleMessageException(MessageException exception) {
        return new ResponseEntity<String>(exception.getMessage(), HttpStatus.BAD_REQUEST);
    }
}
