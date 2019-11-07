package arch.anmobile.processor;

import com.google.common.collect.ImmutableList;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Messager;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementKind;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.PackageElement;
import javax.lang.model.element.TypeElement;
import javax.lang.model.element.TypeParameterElement;
import javax.lang.model.type.DeclaredType;
import javax.lang.model.type.TypeMirror;
import javax.lang.model.util.ElementFilter;
import javax.tools.Diagnostic;

import arch.anmobile.annotations.ParamClass;
import arch.anmobile.annotations.RsService;

/**
 * Created by Albert Zhao on 2019-11-07.
 * Copyright (c) 2019 Android Mobile ActiveNetwork. All rights reserved.
 */
@SupportedAnnotationTypes("arch.anmobile.annotations.RsService")
public class RsServiceProcessor extends AbstractProcessor {

    private Messager messager;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnv) {
        super.init(processingEnv);

        messager = processingEnv.getMessager();
    }

    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }

    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment env) {
        for (Element typeElement : env.getElementsAnnotatedWith(RsService.class)) {
            List<? extends AnnotationMirror> annotationMirrors = typeElement.getAnnotationMirrors();
            for (AnnotationMirror annotationMirror : annotationMirrors) {
                Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues
                        = annotationMirror.getElementValues();
                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry
                        : elementValues.entrySet()) {
                    String key = entry.getKey().getSimpleName().toString();
                    Object value = entry.getValue().getValue();

                    switch (key) {
                        case "name":
                            String name = (String) value;
                            warning("RsService", "name: " + name);
                            break;
                        case "method":
                            String method = (String) value;
                            warning("RsService", "method: " + method);
                            break;
                        case "action":
                            String action = (String) value;
                            warning("RsService", "action: " + action);
                            break;
                        case "body":
                            TypeMirror typeMirror = (TypeMirror) value;
                            warning("RsService", "body: " + typeMirror.toString());
                            break;
                        case "query":
//                            AnnotationMirror query = (AnnotationMirror) value;
                            List<? extends AnnotationMirror> typeMirrors
                                    = (List<? extends AnnotationMirror>) value;
                            for (AnnotationMirror mirro : typeMirrors) {
                                Map<? extends ExecutableElement, ? extends AnnotationValue> queryMap = mirro.getElementValues();
                                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> queryEntry : queryMap.entrySet()) {
                                    String queryKey = queryEntry.getKey().getSimpleName().toString();
                                    Object queryValue = queryEntry.getValue().getValue();

                                    if (queryKey.equals("name")) {
                                        String queryName = (String) queryValue;
                                        warning("RsService", "queryName: " + queryName);
                                    } else if (queryKey.equals("type")) {
                                        TypeMirror queryTypeMirror = (TypeMirror) queryValue;
                                        warning("RsService", "queryType: " + queryTypeMirror.toString());
                                    }
                                }

                            }
                            break;
                    }
                }
            }
        }

        return false;
    }

    public boolean process3(Set<? extends TypeElement> set, RoundEnvironment env) {
        Collection<? extends Element> annotatedElements =
                env.getElementsAnnotatedWith(RsService.class);
        List<TypeElement> types =
                new ImmutableList.Builder<TypeElement>()
                        .addAll(ElementFilter.typesIn(annotatedElements))
                        .build();

        for (TypeElement type : types) {
            RsService annotation = type.getAnnotation(RsService.class);
            warning("Annotation", "getRsServiceAnnotation: " + annotation);
            warning("RsService", "name: " + annotation.name());
            warning("RsService", "method: " + annotation.method());
//            Class bodyClass = annotation.body();
//            warning("RsService", "body: " + bodyClass);
            warning("RsService", "action: " + annotation.action());
            ParamClass[] paramClasses = annotation.query();
            if (paramClasses.length > 0) {
//                warning("RsService-ParamClass", ": " + paramClasses);
                for (ParamClass paramClass : paramClasses) {
                    warning("RsService-ParamClass", "name: " + paramClass.type());
//                    warning("RsService-ParamClass", "name: " + paramClass.name());
//                    warning("RsService-ParamClass", "type: " + paramClass.type());
                }
            }

            warning("TypeElement", "qualifiedName: " + type.getQualifiedName().toString());
            PackageElement packageElement = (PackageElement) type.getEnclosingElement();
            warning("TypeElement", "getEnclosingElement: " + packageElement);

        }

        return false;
    }

    public boolean process2(Set<? extends TypeElement> set, RoundEnvironment env) {
        Collection<? extends Element> annotatedElements = env.getElementsAnnotatedWith(RsService.class);
        List<TypeElement> types = ElementFilter.typesIn(annotatedElements);

//        for (TypeElement type : types) {
//            PackageElement packageElement = (PackageElement) type.getEnclosingElement();
//            warning("PackageName", "packageName: " + packageElement.getQualifiedName().toString());
//            String action = type.getAnnotation(RsService.class).action();
//            warning("PackageName", "action: " + action);
//            Class body = type.getAnnotation(RsService.class).body();
//            warning("PackageName", "body: " + body);
//            String method = type.getAnnotation(RsService.class).method();
//            warning("PackageName", "method: " + method);
//            String name = type.getAnnotation(RsService.class).name();
//            warning("PackageName", "name: " + name);
//            ParamClass[] paramClasses = type.getAnnotation(RsService.class).query();
//            warning("PackageName", "paramClasses: " + paramClasses);
//        }

        return false;
    }

    public boolean process1(Set<? extends TypeElement> set, RoundEnvironment env) {
        Collection<? extends Element> annotatedElements =
                env.getElementsAnnotatedWith(RsService.class);
        List<TypeElement> types =
                new ImmutableList.Builder<TypeElement>()
                        .addAll(ElementFilter.typesIn(annotatedElements))
                        .build();

        if (null != set) {
            for (TypeElement element : set) {
                warning(element);
            }
        }

        for (TypeElement type : types) {
            warning(type);
        }

        return false;
    }

    private void warning(TypeElement type) {
        warning("TypeElement", "getQualifiedName: " + type.getQualifiedName().toString());

        TypeMirror superClassTypeMirror = type.getSuperclass();
        warningTypeMirror(superClassTypeMirror);

        List<? extends TypeMirror> interfaces = type.getInterfaces();
        if (null != interfaces) {
            for (TypeMirror typeMirror : interfaces) {
                warningTypeMirror(typeMirror);
            }
        }
        List<? extends Element> enclosedElements = type.getEnclosedElements();
        if (null != enclosedElements) {
            for (Element element : enclosedElements) {
                warningElement(element);
            }
        }
        List<? extends TypeParameterElement> typeParameters = type.getTypeParameters();
        if (null != typeParameters) {
            for (TypeParameterElement typeParameterElement : typeParameters) {
                warning("TypeParameterElement", "getTypeParameter: " + typeParameterElement.getSimpleName());
            }
        }
    }

    private void warningTypeMirror(TypeMirror typeMirror) {
        if (null != typeMirror) {
            warning("TypeMirror", "getInterface: " + typeMirror.getKind().name());
        }
    }

    private void warningElement(Element element) {
        warning("Element", "getEnclosedElement: " + element.getSimpleName());
        List<? extends Element> enclosedElements = element.getEnclosedElements();
        if (null != enclosedElements) {
            for (Element ele : enclosedElements) {
                warningElement(ele);
            }
        }
        List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
        warning("Element-AnnotationMirrors", "getAnnotationMirrors " + (null != annotationMirrors && !annotationMirrors.isEmpty()));
        if (null != annotationMirrors) {
            for (AnnotationMirror annotationMirror : annotationMirrors) {
                warningAnnotationMirror(annotationMirror);
            }
        }

        Element enclosingElement = element.getEnclosingElement();
        if (null != enclosingElement) {
            warning("Element-Element", "getEnclosingElement: " + enclosingElement.getSimpleName());
        }

        ElementKind elementKind = element.getKind();
        if (null != elementKind) {
            warning("Element", "getKind: " + elementKind.name());
        }
    }

    private void warningAnnotationMirror(AnnotationMirror annotationMirror) {
        warning("AnnotationMirror", "---" + annotationMirror);
        DeclaredType declaredType = annotationMirror.getAnnotationType();
        warningTypeMirror(declaredType.getEnclosingType());
    }

    protected void warning(String tag, String msg) {
        messager.printMessage(Diagnostic.Kind.WARNING, "[" + tag + "] " + msg);
    }
}
