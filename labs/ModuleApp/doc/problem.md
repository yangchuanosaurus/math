# 关于组件化的疑问

## 1. 流行的组件化方案都是主App会对共享模块进行隐藏式的引用，而不是直接引用，其调用方式从直接调用换成用类似OpenURI的方式调用，前者为紧耦合，而后者为松耦合。
疑问：这两种到底有没有本质的区别？

在调用者模块Book中的一个页面，需要调用Share模块的share功能：
- Book应该完全遵循share的数据接口
- share的数据不依赖于Book

分析两种调用方式：

1.1 隐藏式调用
```java
Bundle bundle = new Bundle();
bundle.putString("title",)
UIRouter.getInstance().openUri(getActivity(), "App://share/shareBook", bundle);
```

1.2 直接调用
```java
ShareModule.openShareBook(getActivity(), bundle);
```