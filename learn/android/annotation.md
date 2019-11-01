
## AbstractProcessor
AbstractProcessor is the plugin we can add into the compile pipeline, and analyze source code and generate code as well.
 
ProcessingEnvironment (contains Filer, generate files, and Messager, log purpose),
 
Override method getSupportedAnnotationTypes(), We need to define what the annotations we care about in getSupportedAnnotationTypes for compiler for performance perspective.
 
getSupportedSourceVersion() indicate your java version, usually you can leave it there if you don't have to stick to specific version.
 
process() is where we manipulate with the annotations. Here we only deal with type (like reflection does). The main idea here is to extract the annotate object information (name, package, addition tag) and then pass to our builder classes to generate code.
 
Finally, using javapoet to easy generate java code, and filer will do the rest to write files inside build folder. (Here velocity may could be a replacement)
 
RoundEnvironment.getElementsAnnotatedWith(AutoFactory.class)
Element.getAnnotation(AutoElement.class)
List<? extends TypeMirror> typeMirros = TypeElement.getInterfaces();
 
## Related Class in process()
- Element
- TypeElement
- AutoElement
- TypeMirror