package com.wei.processorcompiler;

import com.google.auto.service.AutoService;
import com.wei.processorlib.IsNull;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

/**
 * 校验字段是否为空
 * @author: WEI
 * @date: 2018/8/2
 */
@AutoService(Processor.class)
public class IsFieldNullValidateProcessor extends AbstractProcessor
{
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(IsNull.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
        for (Element element:roundEnv.getElementsAnnotatedWith(IsNull.class))
        {
            System.out.println("element :" + element);
            String className = element.getSimpleName().toString();
            StringBuilder builder = new StringBuilder()
                    .append("package com.wei.wanandroid.isnullvalidate;\n\n")
                    .append("public class " + className + "IsNullValidate" + "{\n\n")
                    .append("\t public static boolean isNull() {\n")
                    .append("\t\t return " + ( "".equals( element) ))
                    .append(";\n")
                    .append("}\n")
                    .append("}\n");
            try {
                JavaFileObject src = processingEnv.getFiler().createSourceFile(
                        "com.wei.wanandroid.isnullvalidate." + className + "IsNullValidate"
                );
                Writer writer = src.openWriter();
                writer.write(builder.toString());
                writer.flush();
                writer.close();
            }catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        return true;
    }
}
