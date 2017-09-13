package com.citi.training.spring.map;

import org.springframework.beans.BeanUtils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Properties;

/**
 * Created by sxj on 2017/3/1.
 */
public class ConverterImpl implements Converter {
    private Map<String, String> fieldMap;

    public void setFieldMap(Map<String, String> fieldMap) {
        this.fieldMap = fieldMap;
    }

    @Override
    public <S, D> D convert(S sourceInstance, Class<D> destClass) {
        try {
            D destInstance = null;
            destInstance = destClass.newInstance();

            //copy the same name and same type fields
            BeanUtils.copyProperties(sourceInstance, destInstance);

            Field[] fields = sourceInstance.getClass().getDeclaredFields();
            for(Field field: fields) {
                String sourceFieldName = field.getName();
                String destFieldName = fieldMap.get(sourceFieldName);

                if(destFieldName != null) {
                    String sourceGetMethodName = "get" + toUpperCaseFirstLetter(sourceFieldName);

                    String destSetMethodName = "set" + toUpperCaseFirstLetter(destFieldName);

                    Method getMethod = sourceInstance.getClass().getDeclaredMethod(sourceGetMethodName);
                    getMethod.setAccessible(true);
                    Object attrSource = getMethod.invoke(sourceInstance); //get source field value

                    Method setMethod = destClass.getDeclaredMethod(destSetMethodName, field.getType());
                    setMethod.setAccessible(true);
                    setMethod.invoke(destInstance, attrSource); //set dest field value

                }

            }


            return destInstance;
        } catch (IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return null;
    }

    private Properties read(String propertiesPath) throws IOException {
        Properties pps = new Properties();
        InputStream inputStream = new BufferedInputStream(new FileInputStream(propertiesPath));
        pps.load(inputStream);
        return pps;
    }


    private static String toUpperCaseFirstLetter(String str) {
        char[] ch = str.toCharArray();
        if (ch[0] >= 'a' && ch[0] <= 'z') {
            ch[0] = (char) (ch[0] - 32);
        }
        return new String(ch);
    }
}
