package com.yoryo.assignment.fcm;

import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;

import org.springframework.http.HttpEntity;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PushNotificationsService {

	private static final String FIREBASE_SERVER_KEY = "AAAAIArXzvI:APA91bEHc8A71i-rn_FdlUJ-BUu4olAbBE3udoLYdbUlkIYOTyIYjEzmGALVyDYCtr4S7q2fhTFnqavk2-_UymIbjuqttdT4sqJSktDdRL0ZSCslwhSCuI_f2bi20fsfaN_TyH7Wq9eb";
	private static final String FIREBASE_API_URL = "https://fcm.googleapis.com/fcm/send";

	@Async
	public CompletableFuture<String> send(HttpEntity<String> entity) {

		RestTemplate restTemplate = new RestTemplate();

		/**
		 * https://fcm.googleapis.com/fcm/send Content-Type:application/json
		 * Authorization:key=FIREBASE_SERVER_KEY
		 */

		ArrayList<ClientHttpRequestInterceptor> interceptors = new ArrayList<>();
		interceptors.add(new HeaderRequestInterceptor("Authorization", "key=" + FIREBASE_SERVER_KEY));
		interceptors.add(new HeaderRequestInterceptor("Content-Type", "application/json"));
		restTemplate.setInterceptors(interceptors);

		String firebaseResponse = restTemplate.postForObject(FIREBASE_API_URL, entity, String.class);

		return CompletableFuture.completedFuture(firebaseResponse);
	}

}
