## TimedDog
An Android library that automatically log out user after a set time has elapsed. I have come across many solutions on both Github and Stackoverflow but none of them work as expected due to the new constraints on background processes in Android API 26. ~~This library was built on   `WorkManager`, hence it will work on both apps targeting the latest Android SDKs down to Android API 16~~. 
I have stopped using `WorkManager` and migrated to `Bound Service`.

### Sample

##### Modify your application class or create one if it does not exist and initialize `TimedDog` in your onCreate() method.

```java
class MyApp extends Application{
    
    @Override
    public void onCreate(){
        TimedDog.with(this).run((100*60) * 10);
    }
}
```

##### It's worth noting that they're two ways of initializing `TimedDog`. The first way is by calling it direct which will kill the app processes without launching addition activity or fragment.
##### However, if you want to listen to the time out when the time elapsed, you can always add a callback to Timeddog initialization

##### Extend `TimeoutActivity` in your base activity or any other activity you wants to monitor. 
```java
class BaseActivity extends TimeoutActivity{
    //...
}
```
**Suggestion:** Extend `TimeOutActivity` on pages that can only be accessed after a user has successfully logged in to your app.

#### How To Install

##### Add the jitpack repository to your build.gradle(project level) file
```groovy
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

##### Add the library to your build.gradle(app level) file and rebuild your project
```groovy
implementation 'com.github.ogx09:timeddog:2.0.0-alpha'
```

**That's all**

License
-------

    Copyright 2020 Oladipupo Gbenga

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License atR

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

