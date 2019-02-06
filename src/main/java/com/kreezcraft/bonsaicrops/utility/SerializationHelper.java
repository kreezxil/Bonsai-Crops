package com.kreezcraft.bonsaicrops.utility;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.kreezcraft.bonsaicrops.api.TreeTypeSimple;
import com.kreezcraft.bonsaicrops.soils.BonsaiSoil;
import com.kreezcraft.bonsaicrops.soils.BonsaiSoilSerializer;
import com.kreezcraft.bonsaicrops.trees.TreeShapeSerializer;
import com.kreezcraft.bonsaicrops.trees.TreeShape;
import com.kreezcraft.bonsaicrops.trees.TreeTypeSimpleSerializer;

import java.util.List;

public class SerializationHelper {
    public static final Gson GSON = new GsonBuilder()
            .setPrettyPrinting()
            .enableComplexMapKeySerialization()
            .registerTypeAdapter(TreeShape.class, new TreeShapeSerializer())
            .registerTypeAdapter(TreeTypeSimple.class, new TreeTypeSimpleSerializer())
            .registerTypeAdapter(new TypeToken<List<BonsaiSoil>>(){}.getType(), new BonsaiSoilSerializer())
            .create();

}
