# spring-boot2-asciidoctorj
This is demo app to illustrate jruby-complete issue with Spring Boot 2.0.3

## Issue
When we create an executable jar using `./gradlew build` command, the below error occurs

````
2018-07-19 06:06:18.943 ERROR 94412 --- [           main] o.s.boot.SpringApplication               : Application run failed

org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'asciiDocService': Invocation of init method failed; nested exception is org.jruby.exceptions.RaiseException: (LoadError) no such file to load -- asciidoctor
	at org.springframework.beans.factory.annotation.InitDestroyAnnotationBeanPostProcessor.postProcessBeforeInitialization(InitDestroyAnnotationBeanPostProcessor.java:138) ~[spring-beans-5.0.7.RELEASE.jar!/:5.0.7.RELEASE]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.applyBeanPostProcessorsBeforeInitialization(AbstractAutowireCapableBeanFactory.java:424) ~[spring-beans-5.0.7.RELEASE.jar!/:5.0.7.RELEASE]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.initializeBean(AbstractAutowireCapableBeanFactory.java:1700) ~[spring-beans-5.0.7.RELEASE.jar!/:5.0.7.RELEASE]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.doCreateBean(AbstractAutowireCapableBeanFactory.java:581) ~[spring-beans-5.0.7.RELEASE.jar!/:5.0.7.RELEASE]
	at org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.createBean(AbstractAutowireCapableBeanFactory.java:503) ~[spring-beans-5.0.7.RELEASE.jar!/:5.0.7.RELEASE]
	at org.springframework.beans.factory.support.AbstractBeanFactory.lambda$doGetBean$0(AbstractBeanFactory.java:317) ~[spring-beans-5.0.7.RELEASE.jar!/:5.0.7.RELEASE]
	at org.springframework.beans.factory.support.DefaultSingletonBeanRegistry.getSingleton(DefaultSingletonBeanRegistry.java:222) ~[spring-beans-5.0.7.RELEASE.jar!/:5.0.7.RELEASE]
	at org.springframework.beans.factory.support.AbstractBeanFactory.doGetBean(AbstractBeanFactory.java:315) ~[spring-beans-5.0.7.RELEASE.jar!/:5.0.7.RELEASE]
	at org.springframework.beans.factory.support.AbstractBeanFactory.getBean(AbstractBeanFactory.java:199) ~[spring-beans-5.0.7.RELEASE.jar!/:5.0.7.RELEASE]
	at org.springframework.beans.factory.support.DefaultListableBeanFactory.preInstantiateSingletons(DefaultListableBeanFactory.java:760) ~[spring-beans-5.0.7.RELEASE.jar!/:5.0.7.RELEASE]
	at org.springframework.context.support.AbstractApplicationContext.finishBeanFactoryInitialization(AbstractApplicationContext.java:869) ~[spring-context-5.0.7.RELEASE.jar!/:5.0.7.RELEASE]
	at org.springframework.context.support.AbstractApplicationContext.refresh(AbstractApplicationContext.java:550) ~[spring-context-5.0.7.RELEASE.jar!/:5.0.7.RELEASE]
	at org.springframework.boot.web.servlet.context.ServletWebServerApplicationContext.refresh(ServletWebServerApplicationContext.java:140) ~[spring-boot-2.0.3.RELEASE.jar!/:2.0.3.RELEASE]
	at org.springframework.boot.SpringApplication.refresh(SpringApplication.java:759) [spring-boot-2.0.3.RELEASE.jar!/:2.0.3.RELEASE]
	at org.springframework.boot.SpringApplication.refreshContext(SpringApplication.java:395) [spring-boot-2.0.3.RELEASE.jar!/:2.0.3.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:327) [spring-boot-2.0.3.RELEASE.jar!/:2.0.3.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1255) [spring-boot-2.0.3.RELEASE.jar!/:2.0.3.RELEASE]
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1243) [spring-boot-2.0.3.RELEASE.jar!/:2.0.3.RELEASE]
	at com.example.demo.DemoApplication.main(DemoApplication.java:10) [classes!/:na]
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method) ~[na:1.8.0_181]
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62) ~[na:1.8.0_181]
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43) ~[na:1.8.0_181]
	at java.lang.reflect.Method.invoke(Method.java:498) ~[na:1.8.0_181]
	at org.springframework.boot.loader.MainMethodRunner.run(MainMethodRunner.java:48) [demo-0.0.1-SNAPSHOT.jar:na]
	at org.springframework.boot.loader.Launcher.launch(Launcher.java:87) [demo-0.0.1-SNAPSHOT.jar:na]
	at org.springframework.boot.loader.Launcher.launch(Launcher.java:50) [demo-0.0.1-SNAPSHOT.jar:na]
	at org.springframework.boot.loader.JarLauncher.main(JarLauncher.java:51) [demo-0.0.1-SNAPSHOT.jar:na]
Caused by: org.jruby.exceptions.RaiseException: (LoadError) no such file to load -- asciidoctor
	at org.jruby.RubyKernel.require(org/jruby/RubyKernel.java:956) ~[jruby-complete-9.1.16.0.jar!/:na]
	at RUBY.require(uri:classloader:/META-INF/jruby.home/lib/ruby/stdlib/rubygems/core_ext/kernel_require.rb:55) ~[na:na]
	at RUBY.<main>(<script>:15) ~[na:na]
````

## How to reproduce the issue
1. Create build using `./gradlew build` command
2. Start the executable from build/lib directory


## Update: Fix for the issue
Issue got fixed by adding the below lines in bootJar block

```groovy
bootJar {
    launchScript()
    mainClassName = "com.example.demo.DemoApplication"
	requiresUnpack '**/jruby-complete*.jar', '**/asciidoctorj-*.jar'
}
```

> To deal with any problematic libraries, an executable archive can be configured to unpack specific nested jars to a temporary folder when the executable archive is run. Libraries can be identified as requiring unpacking using Ant-style patterns that match against the absolute path of the source jar file.

**See this SO thread for more information:** https://stackoverflow.com/questions/51412834/asciidoctorj-library-does-not-work-with-spring-boot-2-executable-boot-jar
