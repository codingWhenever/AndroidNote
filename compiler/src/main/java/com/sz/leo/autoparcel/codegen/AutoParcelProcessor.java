package com.sz.leo.autoparcel.codegen;

import com.google.common.collect.ImmutableList;
import com.sz.leo.autoparcel.AutoParcel;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

/**
 * @author：leo
 * @date：2019/7/1
 * @email：lei.lu@e-at.com
 */
@SupportedAnnotationTypes("com.sz.leo.autoparcel.AutoParcel")
public final class AutoParcelProcessor extends AbstractProcessor {

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {
        Collection<? extends Element> annotatedElements =
                roundEnvironment.getElementsAnnotatedWith(AutoParcel.class);
        List<TypeElement> types = new ImmutableList.Builder<TypeElement>()
                .addAll(ElementFilter.typesIn(annotatedElements))
                .build();
        for (TypeElement element : types) {
            processType(element);
        }
        //返回true 其他处理器不关心AutoParcel注解
        return true;
    }

    private void processType(TypeElement type) {
        AutoParcel autoParcel = type.getAnnotation(AutoParcel.class);
        if (autoParcel == null) {

        }
        if (type.getKind() != ElementKind.CLASS) {

        }
        String className = generatedSubclassName(type);
        String source = generateClass(type, className);
        writeSourceFile(className, source, type);
    }

    private String generatedSubclassName(TypeElement element) {
        return null;
    }

    private String generateClass(TypeElement type, String className) {
        return null;
    }

    private void writeSourceFile(String className, String text, TypeElement originatingType) {
        try {
            JavaFileObject sourceFile = processingEnv.getFiler().createSourceFile(className, originatingType);
            Writer writer = sourceFile.openWriter();
            try {
                writer.write(text);
            } finally {
                writer.close();
            }

        } catch (IOException e) {
            processingEnv.getMessager().printMessage(Diagnostic.Kind.WARNING,
                    "Could not write generated class " + className + ": " + e);
        }
    }
}
