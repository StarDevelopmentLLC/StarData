# StarData
A Java SQL library to make working with databases a little easier  
[![](https://www.jitpack.io/v/StarDevelopmentLLC/StarData.svg)](https://www.jitpack.io/#StarDevelopmentLLC/StarData)  
## To use this Library
You must add JitPack as a repo, below is for Gradle  
```groovy
repositories {
    maven {
        url = 'https://www.jitpack.io'
    }
}
```  
Then to use this library as a dependency  
```goovy
dependencies {
    implementation 'com.github.StarDevelopmentLLC:StarData:{MODULE}:{VERSION}'
}
```  
Replace {MODULE} with your chosen MySQL Type or the api module to compile against it.  
Replace {VERSION} with the module version. Go here: https://www.jitpack.io/#StarDevelopmentLLC/StarData/ and click on "Get It" on the version you want, then you can select the "Subproject" to get a copy/paste link, doing this can also get you the Maven stuff for it.  

For some reason, the shading and relocation of the StarLib files didn't work as I had hoped, I will have to solve this later, but you will need to add StarLib as a dependency. The lastest version should work just fine.  

Please see the Wiki for more information and a tutorial with examples on how to use this library. 
