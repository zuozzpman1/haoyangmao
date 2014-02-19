
package com.haoyangmao.haoyangmao.common.net;

import java.lang.reflect.Field;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xmlpull.v1.XmlPullParser;

/**
 * 反射生成对象的工厂
 * 
 * @author michaelzuo
 */
public class ReflectionFactory {

    /**
     * 根据xmlParser里 当前标签的属性 填充那个标签代表的对象
     * 
     * @param parser
     * @param tagClass
     * @return 填充完属性的反射对象
     */
    public static Object reflectObj(XmlPullParser parser, Class<?> tagClass) {
        Object obj = null;
        try {
            obj = tagClass.newInstance();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        }

        fillField(parser, obj);

        return obj;
    }

    /**
     * 填充属性
     * 
     * @param parser
     * @param obj
     */
    public static void fillField(XmlPullParser parser, Object obj) {
        Class<?> c = obj.getClass();
        Field[] fields = c.getFields();
        for (Field f : fields) {
            String typeName = f.getType().getName();
            String fieldName = f.getName();

            int attributeCount = parser.getAttributeCount();

            for (int index = 0; index < attributeCount; index++) {
                String attributeName = parser.getAttributeName(index);
                String attributeValue = parser.getAttributeValue(index);

                if (!attributeName.equalsIgnoreCase(fieldName)) {
                    continue;
                }

                try {
                    if (typeName.equals("boolean")) {
                        f.setBoolean(obj, Boolean.parseBoolean(attributeValue));
                    } else if (typeName.equals(Boolean.class.getName())) {
                        f.setBoolean(obj, new Boolean(attributeValue));
                    } else if (typeName.equals("byte")) {
                        f.setByte(obj, Byte.parseByte(attributeValue));
                    } else if (typeName.equals("int")) {
                        f.setInt(obj, Integer.parseInt(attributeValue));
                    } else if (typeName.equals(Byte.class.getName())) {
                        f.setByte(obj, new Byte(attributeValue));
                    } else if (typeName.equals(Integer.class.getName())) {
                        f.setInt(obj, new Integer(attributeValue));
                    } else if (typeName.equals(String.class.getName())) {
                        f.set(obj, attributeValue);
                    } else if (typeName.equals("double")) {
                        f.setDouble(obj, Double.parseDouble(attributeValue));
                    } else if (typeName.equals("float")) {
                        f.setFloat(obj, Float.parseFloat(attributeValue));
                    } else if (typeName.equals(Float.class.getName())) {
                        f.setFloat(obj, new Float(attributeValue));
                    } else if (typeName.equals(Double.class.getName())) {
                        f.setDouble(obj, new Double(attributeValue));
                    } else if (typeName.equals("long")) {
                        f.setLong(obj, Long.parseLong(attributeValue));
                    } else if (typeName.equals(Long.class.getName())) {
                        f.setLong(obj, new Long(attributeValue));
                    }
                } catch (Exception e) {
                    continue;
                }
            }

        }
    }
    
    /**
     * 
     * 根据JSON填充相应的对象
     * @param jsonObject
     * @param tagClass
     * @return
     * @throws JSONException
     */
    public static Object reflectObjFromJson(JSONObject jsonObject, Class<?> tagClass) throws JSONException {
        Object obj = null;
        try {
            obj = tagClass.newInstance();
        } catch (IllegalAccessException e1) {
            e1.printStackTrace();
        } catch (InstantiationException e1) {
            e1.printStackTrace();
        }
        
        fillFieldFromJson(jsonObject, obj);
        
        return obj;
    }
    
    
    /**
     * 由JSON填充属性
     * @param jsonObject
     * @param obj
     * @throws JSONException 
     */
    public static void fillFieldFromJson(JSONObject jsonObject, Object obj) throws JSONException {
        Class<?> c = obj.getClass();
        Field[] fields = c.getFields();
        for (Field f : fields) {
            String typeName = f.getType().getName();
            String fieldName = f.getName();
            
            int attributeCount = jsonObject.length();
            JSONArray jsonNames = jsonObject.names();
            
            for (int index = 0; index < attributeCount; index++) {
                String attributeName = jsonNames.getString(index);
                String attributeValue = jsonObject.getString(attributeName);
                
                if (!attributeName.equalsIgnoreCase(fieldName)) {
                    continue;
                }
                
                try {
                    if (typeName.equals("boolean")) {
                        f.setBoolean(obj, Boolean.parseBoolean(attributeValue));
                    } else if (typeName.equals(Boolean.class.getName())) {
                        f.setBoolean(obj, new Boolean(attributeValue));
                    } else if (typeName.equals("byte")) {
                        f.setByte(obj, Byte.parseByte(attributeValue));
                    } else if (typeName.equals("int")) {
                        f.setInt(obj, Integer.parseInt(attributeValue));
                    } else if (typeName.equals(Byte.class.getName())) {
                        f.setByte(obj, new Byte(attributeValue));
                    } else if (typeName.equals(Integer.class.getName())) {
                        f.setInt(obj, new Integer(attributeValue));
                    } else if (typeName.equals(String.class.getName())) {
                        f.set(obj, attributeValue);
                    } else if (typeName.equals("double")) {
                        f.setDouble(obj, Double.parseDouble(attributeValue));
                    } else if (typeName.equals("float")) {
                        f.setFloat(obj, Float.parseFloat(attributeValue));
                    } else if (typeName.equals(Float.class.getName())) {
                        f.setFloat(obj, new Float(attributeValue));
                    } else if (typeName.equals(Double.class.getName())) {
                        f.setDouble(obj, new Double(attributeValue));
                    } else if (typeName.equals("long")) {
                        f.setLong(obj, Long.parseLong(attributeValue));
                    } else if (typeName.equals(Long.class.getName())) {
                        f.setLong(obj, new Long(attributeValue));
                    }
                } catch (Exception e) {
                    continue;
                }
            }
            
        }
    }
}
