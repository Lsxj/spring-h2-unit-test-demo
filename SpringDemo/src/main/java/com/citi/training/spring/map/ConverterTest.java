package com.citi.training.spring.map;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;

import static org.junit.Assert.assertEquals;

/**
 * Created by sxj on 2017/3/2.
 */
@ContextConfiguration("/applicationContext.xml")
public class ConverterTest {
    @Autowired
    Converter converter;

    @Test
    public void testConverter() {
        User user = new User(11, "haha");
        Employee e = converter.convert(user, Employee.class);
        assertEquals(11, e.getEmployeeID());
        assertEquals("haha", e.getName());
    }
}
