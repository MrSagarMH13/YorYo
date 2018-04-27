package com.yoryo.assignment.atmosphere;

import org.atmosphere.cpr.MetaBroadcaster;

import com.yoryo.assignment.constants.YoryoNotificationConstants;
import com.yoryo.assignment.util.JsonConverter;

/**
 * @author Sagar
 *
 */
public class YoryonotificationHelper {

	@SuppressWarnings("deprecation")
	public static void broadcastNotification(YoryoNotification notification) {
		String roomURL = YoryoNotificationConstants.NOTIFICATION_URL + notification.getRoom();
		String message = JsonConverter.createJson(notification);
		MetaBroadcaster.getDefault().broadcastTo(roomURL, message);
	}

	@SuppressWarnings("deprecation")
	public static void broadcastHeartbeatNotification() {
		for (String subsID : YoryoNotificationConstants.CURRENT_CHAT_ROOMS.keySet()) {
			YoryoNotification notification = new YoryoNotification();
			notification.setType("HeartBeat");
			notification.setData("Stay Awake...");
			String roomURL = YoryoNotificationConstants.NOTIFICATION_URL + subsID;
			String message = JsonConverter.createJson(notification);
			MetaBroadcaster.getDefault().broadcastTo(roomURL, message);
		}
	}

	public static void sendNotification(String userID, String dataString, String notificationType,
			String notificationToken) {
		YoryoNotification notification = new YoryoNotification();
		notification.setAuthor(userID);
		notification.setData(dataString);
		// notification.setRoom();
		notification.setType(notificationType);
		notification.setNotificationToken(notificationToken);
		YoryonotificationHelper.broadcastNotification(notification);
	}

}