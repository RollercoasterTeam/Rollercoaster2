package rcteam.rc2.util;

import com.google.common.collect.Lists;
import com.google.gson.*;
import rcteam.rc2.rollercoaster.CategoryEnum;
import rcteam.rc2.rollercoaster.CoasterStyle;

import java.lang.reflect.Type;
import java.util.List;

public class JsonParser {
	public static final Gson GSON = new GsonBuilder().registerTypeAdapter(CoasterStyle.class, JsonParser.Deserializer.INSTANCE).create();

	public static class Deserializer implements JsonDeserializer<CoasterStyle> {
		public static final Deserializer INSTANCE = new Deserializer();

		@Override
		public CoasterStyle deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();
			String name = "";
			CategoryEnum category;
			List<String> pieces = Lists.newArrayList();
			List<String> trainCars = Lists.newArrayList();

			name = jsonObject.get("name").getAsString();
			category = CategoryEnum.getByName(jsonObject.get("category").getAsString());
			JsonArray pieceArray = jsonObject.getAsJsonArray("pieces");
			pieceArray.forEach(jsonElement -> pieces.add(jsonElement.getAsString()));
			JsonArray carsArray = jsonObject.getAsJsonArray("train_cars");
			carsArray.forEach(jsonElement -> trainCars.add(jsonElement.getAsString()));
			return new CoasterStyle(name, category, pieces, trainCars);
		}
	}
}
