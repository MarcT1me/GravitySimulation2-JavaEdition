package com.gravitysimulation2.save;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

import com.gravitysimulation2.objects.GameScene;
import com.gravitysimulation2.objects.object.GameObject;
import com.gravitysimulation2.objects.object.objectypes.ObjectTypes;
import com.gravitysimulation2.objects.physic.PhysicBody;
import com.gravitysimulation2.objects.physic.Vector2D;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SceneParser {
    public static Map<String, Object> loadSave(String filePath) {
        FileHandle file = Gdx.files.local(filePath);
        JsonReader reader = new JsonReader();
        JsonValue root = reader.parse(file);
        return (Map<String, Object>) parseJson(root);
    }

    public static GameObject createObject(GameScene scene, Map.Entry<String, Object> entry) {
        String gameObjectName = entry.getKey();
        Map<String, Object> gameObjectData = (Map<String, Object>) entry.getValue();

        Vector2D pos = parseVector2D(gameObjectData.get("pos"));
        Vector2D velocity = parseVector2D(gameObjectData.get("vel"));
        double angle = ((Number) gameObjectData.get("angle")).doubleValue();
        double mass = ((Number) gameObjectData.get("mass")).doubleValue();
        double density = ((Number) gameObjectData.get("density")).doubleValue();
        double radius = ((Number) gameObjectData.get("radius")).doubleValue();
        double angularVelocity = ((Number) gameObjectData.get("angularVelocity")).doubleValue();

        // object data
        Map<String, Object> objectData = (Map<String, Object>) gameObjectData.get("objectData");
        String objectTypeName = (String) objectData.get("type");

        return new GameObject(
            scene,
            gameObjectName,
            new PhysicBody(
                scene.simulation,
                pos, angle,
                mass, density, radius,
                velocity, angularVelocity
            ),
            Enum.valueOf(ObjectTypes.class, objectTypeName),
            objectData
        );
    }

    public static Vector3 parseVector3(Object value) {
        ArrayList<Double> array = (ArrayList<Double>) value;
        return new Vector3(array.get(0).floatValue(), array.get(1).floatValue(), array.get(2).floatValue());
    }

    public static Vector2D parseVector2D(Object value) {
        ArrayList<Double> array = (ArrayList<Double>) value;
        return new Vector2D(array.get(0), array.get(1));
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
            JsonValue child = jsonValue.child; // Начинаем с первого элемента массива
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
