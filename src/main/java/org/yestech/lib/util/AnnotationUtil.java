/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.util;

import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.annotation.Annotation;

/**
 *
 *
 */
public class AnnotationUtil
{


    public static void setValueForAnnotatedField(Class<Annotation> annotation, Class<?> clazz) {

        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields)
        {

            if (field.isAnnotationPresent(annotation))
            {





            }
        }

    }







    




}
