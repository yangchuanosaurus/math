package arch.anmobile.processor;

import com.google.common.collect.ImmutableList;

import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.Element;
import javax.lang.model.element.TypeElement;
import javax.lang.model.util.ElementFilter;
import javax.tools.JavaFileObject;

import arch.anmobile.annotations.Framework;

@SupportedAnnotationTypes("arch.anmobile.annotations.Framework")
public class AnFrameworkProcessor extends AbstractProcessor {

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment env) {

        Collection<? extends Element> annotatedElements =
                env.getElementsAnnotatedWith(Framework.class);
        List<TypeElement> types =
                new ImmutableList.Builder<TypeElement>()
                        .addAll(ElementFilter.typesIn(annotatedElements))
                        .build();

        for (TypeElement type : types) {
            processType(type);
        }

        return false;
    }

    private void processType(TypeElement type) {
        String className = "arch.anmobile.AnFramework";
        String source = "package arch.anmobile;\npublic class AnFramework {}";
        writeSourceFile(className, source, type);
    }

    private void writeSourceFile(
            String className,
            String text,
            TypeElement originatingType) {
        try {
            JavaFileObject sourceFile =
                    processingEnv.getFiler().
                            createSourceFile(className, originatingType);
            Writer writer = sourceFile.openWriter();
            try {
                writer.write(text);
            } finally {
                writer.close();
            }
        } catch (IOException e) {// silent}
        }

    }

}
