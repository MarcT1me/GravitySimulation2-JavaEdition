package com.gravitysimulation2.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.gravitysimulation2.objects.GameObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SceneParser {
    public static GameObject createObject(Map.Entry<String, Object> entry) {
        String objectName = entry.getKey();
        Map<String, Object> objectData = (Map<String, Object>) entry.getValue();

        List<Number> posList = (List<Number>) objectData.get("pos");
        Vector2 pos = new Vector2(
            posList.get(0).floatValue(),
            posList.get(1).floatValue()
        );

        List<Number> velocityList = (List<Number>) objectData.get("vel");
        Vector2 velocity = new Vector2(
            velocityList.get(0).floatValue(),
            velocityList.get(1).floatValue()
        );

        float angle = ((Number) objectData.get("angle")).floatValue();
        float mass = ((Number) objectData.get("mass")).floatValue();
        float density = ((Number) objectData.get("density")).floatValue();
        float radius = ((Number) objectData.get("radius")).floatValue();
        float angularVelocity = ((Number) objectData.get("angularVelocity")).floatValue();

        Map<String, Object> objectTypeData = (Map<String, Object>) objectData.get("objectType");
        String objectTypeName = (String) objectTypeData.get("type");
        List<Double> colorList = (List<Double>) objectTypeData.get("color");
        Vector3 color = new Vector3(
            colorList.get(0).floatValue(),
            colorList.get(1).floatValue(),
            colorList.get(2).floatValue()
        );

        return new GameObject(objectName,
//            Enum.valueOf(ObjectTypes.class, objectTypeName),
//            new RendererObjectData().color(color),
            pos, angle,
            mass, density, radius,
            velocity, angularVelocity
        );
    }

    public static Map<String, Object> loadSave(String filePath) {
        FileHandle file = Gdx.files.internal(filePath); // Для файлов в папке assets
        JsonReader reader = new JsonReader();
        JsonValue root = reader.parse(file);
        return (Map<String, Object>) parseJson(root);
    }

    private static Object parseJson(JsonValue jsonValue) {
        if (jsonValue.isObject()) {
            Map<String, Object> map = new HashMap<>();
            JsonValue child = jsonValue.child;
            while (child != null) {
                map.put(child.name, parseJson(child));
                child = child.next;
            }
            return map;
        } else if (jsonValue.isArray()) {
            List<Object> list = new ArrayList<>();
            JsonValue child = jsonValue;
            while (child != null) {
                list.add(parseJson(child));
                child = child.next;
            }
            return list;
        } else {
            if (jsonValue.isBoolean()) return jsonValue.asBoolean();
            else if (jsonValue.isDouble()) return jsonValue.asDouble();
            else if (jsonValue.isString()) return jsonValue.asString();
            else return null;
        }
    }
}
