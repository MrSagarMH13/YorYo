package com.yoryo.assignment.fcm;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Component;

@Component
public class NotificationHelper {

	private final String TOPIC = "Tracking";

	@Autowired
	PushNotificationsService pushNotificationsService;

	public String updateLocation(String location) {

		JSONObject body = new JSONObject();
		body.put("to", "/topics/" + TOPIC);
		body.put("priority", "high");

		/*JSONObject notification = new JSONObject();
		notification.put("title", "Location Updated");
		notification.put("body", location);*/

		JSONObject data = new JSONObject();
		data.put("body", location);
//		data.put("Key-2", "JSA Data 2");

//		body.put("notification", notification);
		body.put("data", data);

		HttpEntity<String> request = new HttpEntity<>(body.toString());

		CompletableFuture<String> pushNotification = pushNotificationsService.send(request);
		CompletableFuture.allOf(pushNotification).join();

		try {
			String firebaseResponse = pushNotification.get();
			return firebaseResponse;
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}

		return "Push Notification ERROR!";
	}
}
