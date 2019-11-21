Activity/Fragment has lifecycles, if we transfer the lifecycle to the layer

https://blog.csdn.net/guiying712/article/details/55213884
https://www.jianshu.com/p/1b1d77f58e84
https://www.jianshu.com/p/5746e50ed572


App

Feature 1 - Resource
Feature 2 - Resource
Feature 3 - Resource

Shared Resource

Foundation

dependencies + Router
component service


Development in isoland
    - "Feature X" could be executed in single app mode
    - "Feature 1" needs view widgets of "Feature 2"

Lose-couple
    - App cannot depends on "Feature X" directly
    - Communication between "Feature"s

--------------------
|       App        |
--------------------

----------------------------------------------------------
|    Feature 1     |    Feature 2     |    Feature 3     |
----------------------------------------------------------

--------------------
|    Foundation    |
--------------------