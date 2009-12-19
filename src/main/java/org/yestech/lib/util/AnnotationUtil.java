/*
 * Copyright LGPL3
 * YES Technology Association
 * http://yestech.org
 *
 * http://www.opensource.org/licenses/lgpl-3.0.html
 */

package org.yestech.lib.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.yestech.lib.web.Location;

import javax.servlet.RequestDispatcher;
import java.lang.reflect.Method;
import java.lang.reflect.Field;
import java.lang.annotation.Annotation;
import java.util.List;

/**
 * A collection of utility methods for dealing with annotations
 */
public class AnnotationUtil {
    final private static Logger logger = LoggerFactory.getLogger(AnnotationUtil.class);


    /**
     * Locates and returns an Annotation from array of Annotations
     *
     * @param annotationClass annotation to look for
     * @param annotations the annotations to search 
     * @param <A> Type of the annotation
     * @return the Found annotation else null if not found
     */
    @SuppressWarnings("unchecked")
    public static <A> A getAnnotation(Class<A> annotationClass, Annotation[] annotations) {
        A result = null;
        if (annotations != null && annotationClass != null) {
            int totalAnnotations = annotations.length;
            for (int i = 0; i < totalAnnotations; i++) {
                Annotation annotation = annotations[i];
                if (annotationClass.equals(annotation.annotationType())) {
                    result = (A) annotation;
                }
            }
        }
        return result;
    }

    /**
     * Locates and returns an Annotation from array of Annotations
     *
     * @param annotationClass annotation to look for
     * @param annotations the annotations to search
     * @param <A> Type of the annotation
     * @return the Found annotation else null if not found
     */
    @SuppressWarnings("unchecked")
    public static <A> A getAnnotation(Class<A> annotationClass, List<Annotation> annotations) {
        A result = null;
        if (annotations != null && annotationClass != null) {
            result = getAnnotation(annotationClass, annotations.toArray(new Annotation[annotations.size()]));
        }
        return result;
    }
}
