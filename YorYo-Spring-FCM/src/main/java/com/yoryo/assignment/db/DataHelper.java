package com.yoryo.assignment.db;

import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.yoryo.assignment.model.Location;

@Component
public class DataHelper {

	/*private static int pos = 0;
	static List<Location> locations;
	static {
		Gson gson = new Gson();
		TypeToken<List<Location>> token = new TypeToken<List<Location>>() {
		};
		ClassPathResource resource = new ClassPathResource("path");
		JsonReader reader = null;
		try {
			reader = new JsonReader(new FileReader(resource.getFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		locations = gson.fromJson(reader, token.getType());
	}

	public Location getNextLocation() {
		int len = locations.size();
		if (pos == len) {
			pos = 0;
		}
		return locations.get(pos);

	}*/
}
