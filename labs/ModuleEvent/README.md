# Module App Development
https://www.jianshu.com/p/027dabfd47ce
## What is Module App
Module provides the module for android app and also be run and debugged separately.

### As a Module
Set ```isModuleApp=false``` in gradle.properties, which is a default option.

```src/main/AndroidManifest.xml``` is the default manifest file.

Consider:
- publish to local maven repository for *testing*
- publish to remote maven repository for *integration*

### As a App
Set ```isModuleApp=true``` in gradle.properties

```src/main/AndroidManifest_ModuleApp.xml``` is the default manifest file.