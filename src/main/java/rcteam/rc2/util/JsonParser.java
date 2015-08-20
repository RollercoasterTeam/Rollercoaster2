package rcteam.rc2.util;

import com.google.common.collect.Lists;
import com.google.gson.*;
import rcteam.rc2.rollercoaster.CategoryEnum;
import rcteam.rc2.rollercoaster.CoasterStyle;
import rcteam.rc2.rollercoaster.TrackPiece;
import rcteam.rc2.rollercoaster.TrackPieceInfo;

import javax.vecmath.Vector3f;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class JsonParser {
	public static final Gson GSON = new GsonBuilder().registerTypeAdapter(TrackPieceInfo.class, JsonParser.Deserializer.INSTANCE).create();

	public static class Deserializer implements JsonDeserializer<TrackPieceInfo> {
		public static final Deserializer INSTANCE = new Deserializer();

		@Override
		public TrackPieceInfo deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
			JsonObject jsonObject = json.getAsJsonObject();
			CategoryEnum categoryEnum;
			String name;
			List<String> pieces = Lists.newArrayList();
			List<Vector3f> dimensions = Lists.newArrayList();
			List<String> trainCars = Lists.newArrayList();

			categoryEnum = CategoryEnum.getByName(jsonObject.get("category").getAsString());
			name = jsonObject.get("name").getAsString();
			JsonObject pieceObj = jsonObject.getAsJsonObject("pieces");
			for (Map.Entry<String, JsonElement> entry : pieceObj.entrySet()) {
				pieces.add(entry.getKey());
				JsonArray dims = entry.getValue().getAsJsonArray();
				dimensions.add(new Vector3f(dims.get(0).getAsFloat(), dims.get(1).getAsFloat(), dims.get(2).getAsFloat()));
			}
			JsonArray carsArray = jsonObject.getAsJsonArray("train_cars");
			carsArray.forEach(jsonElement -> trainCars.add(jsonElement.getAsString()));

			TrackPieceInfo info = new TrackPieceInfo(categoryEnum);
			List<TrackPiece> pieceList = Lists.newArrayList();
			for (int i = 0; i < pieces.size(); i++) {
				pieceList.add(new TrackPiece(pieces.get(i), dimensions.get(i), categoryEnum));
			}
			CoasterStyle style = new CoasterStyle(name, pieceList, trainCars, pieceList.get(0));
			info.addStyleToMap(style);
			return info;
		}
	}
}
