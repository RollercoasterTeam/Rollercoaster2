package rcteam.rc2.util;

import com.google.common.collect.Lists;
import com.google.gson.*;
import rcteam.rc2.rollercoaster.*;

import java.lang.reflect.Type;
import java.util.List;

public class JsonParser {
	public static final Gson GSON = new GsonBuilder().registerTypeAdapter(TrackPieceInfo.class, JsonParser.Deserializer.INSTANCE).create();

	public static class Deserializer implements JsonDeserializer<TrackPieceInfo> {
		public static final Deserializer INSTANCE = new Deserializer();

		@Override
		public TrackPieceInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			CategoryEnum categoryEnum;
			String name;
			List<String> pieces = Lists.newArrayList();
			List<String> trainCars = Lists.newArrayList();
			JsonObject jsonObject = json.getAsJsonObject();
			JsonObject styleJson = jsonObject.getAsJsonObject("style_info");

			categoryEnum = CategoryEnum.getByName(styleJson.get("category").getAsString());
			name = styleJson.get("name").getAsString();
			JsonArray pieceArray = styleJson.getAsJsonArray("pieces");
			for (JsonElement element : pieceArray) {
				pieces.add(element.getAsString());
			}
			JsonArray carArray = styleJson.getAsJsonArray("train_cars");
			for (JsonElement element : carArray) {
				trainCars.add(element.getAsString());
			}

			List<TrackPiece> pieceList = Lists.newArrayList();
			pieces.forEach(s -> pieceList.add(TrackPiece.getByName(s)));

			if (categoryEnum.getInfo() != null) {
				categoryEnum.getInfo().buildStyle(name, pieceList, trainCars);
//				CoasterStyle style = new CoasterStyle(name, pieceList, trainCars, new TrackPiece(pieceList.get(0)));
//				info.addStyleToMap(style);
//				style.setParentInfo(info);
//				categoryEnum.setInfo(info);
			} else {
				TrackPieceInfo info = new TrackPieceInfo(categoryEnum);
				info.buildStyle(name, pieceList, trainCars);
				categoryEnum.setInfo(info);
//				Map<String, CoasterStyle> styleMap = Maps.newHashMap();
//				CoasterStyle style = new CoasterStyle(name, pieceList, trainCars, new TrackPiece(pieceList.get(0)));
//				styleMap.put(style.getName(), style);
//				TrackPieceInfo info = new TrackPieceInfo(categoryEnum, styleMap);
//				style.setParentInfo(info);
//				categoryEnum.setInfo(info);
			}
			return categoryEnum.getInfo();
		}
	}
}
