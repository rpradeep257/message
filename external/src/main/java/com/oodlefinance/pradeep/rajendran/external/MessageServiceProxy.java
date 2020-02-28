package com.oodlefinance.pradeep.rajendran.external;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("internal")
public interface MessageServiceProxy {

	@RequestMapping(value = "/message", method = RequestMethod.GET)
	List<Message> get();

	@RequestMapping(value = "/message/{id}", method = RequestMethod.GET)
	Message get(@PathVariable("id") Long id);

	@RequestMapping(value = "/message", method = RequestMethod.POST)
	Long create(Message message);

	@RequestMapping(value = "/message/{id}", method = RequestMethod.DELETE)
	void delete(@PathVariable("id") Long id);

}
