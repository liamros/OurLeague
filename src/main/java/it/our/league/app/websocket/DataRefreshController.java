package it.our.league.app.websocket;

import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import it.our.league.app.thread.DataRefreshHandler;

@Controller
public class DataRefreshController {

	@Autowired
	private DataRefreshHandler dataRefreshHandler;

	@MessageMapping("/requestRefreshData")
	@SendTo("/topic/dataRefresh")
	public ResponseEntity<String> refreshData() throws Exception {	
		
		Future<Integer> future = dataRefreshHandler.startDataRefresh();
		Integer result = future.get();
		ResponseEntity<String> response = new ResponseEntity<String>(String.valueOf(result), HttpStatus.ACCEPTED);
		return response;
	}


}
