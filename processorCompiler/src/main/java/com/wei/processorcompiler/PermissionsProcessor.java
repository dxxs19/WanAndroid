package com.wei.processorcompiler;

import com.google.auto.service.AutoService;
import com.wei.processorlib.MyRuntimePermissions;

import java.io.IOException;
import java.io.Writer;
import java.util.Collections;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.tools.JavaFileObject;

@AutoService(Processor.class)
//@SupportedAnnotationTypes("com.wei.processorlib.MyRuntimePermissions")
public class PermissionsProcessor extends AbstractProcessor
{
    @Override
    public Set<String> getSupportedAnnotationTypes() {
        return Collections.singleton(MyRuntimePermissions.class.getCanonicalName());
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv)
    {
        for (Element element:roundEnv.getElementsAnnotatedWith(MyRuntimePermissions.class))
        {
            String className = element.getSimpleName().toString();
            StringBuilder builder = new StringBuilder()
                    .append("package com.wei.wanandroid.image;\n\n")
                    .append("public class " + className + "PermissionsDispatcher" + "{\n\n")
                    .append("\t public String withCheck() {\n")
                    .append("\t\t return \" ");
            builder.append(className + "PermissionsDispatcher")
                    .append(" create successfully!!!\\n");
            builder.append("\"; \n")
                    .append("\t}\n")
                    .append("}\n");

            try {
                JavaFileObject src = processingEnv.getFiler().createSourceFile(
                        "com.wei.wanandroid.image." + className + "PermissionsDispatcher");
                Writer writer = src.openWriter();
                writer.write(builder.toString());
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return true;
    }
}
