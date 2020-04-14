### dotCMS upgrader

To use this you may need to do three things

1. run `./gradlew jar`
2. copy the jar to `core/libs/plugins`
3. run the task:
```
./gradlew upgradeTask -Ptask=com.dotmarketing.startup.runonce.Task05200WorkflowTaskUniqueKey
```

You might need to add the upgradetask.jar to your classpath, e.g.
```
classpath = files("../libs/plugins/plugin-com.dotcms.upgradetask.jar") + files(sourceSets.main.output.classesDir) +  files(sourceSets.main.output.resourcesDir) + files(configurations.compile.files)
```

