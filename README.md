## TMBD Movie KMM app for Android,iOS and Web.
This project is the implementation of how one can adopt the Kmp (Kotlin multiplatform) for building Android , ios apps and for Web.
I have used the TMDB's developer apis to fetch the movie-related data. I have used the **Compose Multiplatform** to build the 
UI of both platforms. If you are new to Compose multiplatform please go through: https://www.jetbrains.com/lp/compose-multiplatform/ 


## How to build the project
To build the project you need some basic understanding of how the KMP projects are built.
For that, you have look at: https://kotlinlang.org/docs/multiplatform-mobile-getting-started.html
To build the project first you must generate the API key from the TMDB developers portal : 
https://developer.themoviedb.org/

Once you have got the api key follow the below steps:
 1. Search for the file [network.kt](/shared/src/commonMain/kotlin/data/network.kt) file.
 2. Replace the api key within quotes instead of text <Replace with your tmdb token>
 3. choose the platform and hit run

## Mobile Demo Video

https://github.com/youranshul/KmmMovieBuff/assets/618667/11caca75-5dc7-4cd9-bd3d-ed3462bc5450


## Web Demo

- Run the web project with command : ```./gradlew wasmJsBrowserDevelopmentRun ```

https://github.com/youranshul/KmmMovieBuff/assets/618667/a432f5cd-2547-4861-a154-16cec189108b


## What's Next
  - Some more UI improvement
  - Adding Login functionality
  - Improving overall Architecture
  - Write some unit test cases
  - Implementing videos from the TMDB APIs
  - working on some cool features
    
## Tech Stack
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Jetpack Compose](https://developer.android.com/jetpack/compose) - Android’s modern toolkit for building native UI.
- [Jetbrain's compose multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/) - UI development for Android, iOS, Web, Desktop and many more.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) and [Flow](https://kotlinlang.org/docs/reference/coroutines/flow.html#asynchronous-flow) - Official Kotlin's tooling for performing asynchronous work.
- [MVVM/MVI Architecture](https://developer.android.com/jetpack/guide) - Official recommended architecture for building robust, production-quality apps.
- [Kotlin multiplatform](https://kotlinlang.org/docs/multiplatform-mobile-getting-started.html) - Kotlin-based development for frontend applications for Android,iOS, Web and desktop.
- [Decompose](https://arkivanov.github.io/Decompose/) - Kotlin Multiplatform library for breaking down your code into lifecycle-aware business logic components (aka BLoC), with routing functionality and pluggable UI (Jetpack Compose, Android Views, SwiftUI, JS React, etc.)
- [Image Loading](https://coil-kt.github.io/coil/upgrading_to_coil3/) - Coil3 Image library for Kotlin Multiplatform.
- [Async data lading](https://ktor.io/docs/welcome.html) - Ktor is a framework to easily build connected applications – web applications, HTTP services, mobile and browser applications.

For more information about used dependencies, see [this](/gradle/libs.versions.toml) file.

## Questions

If you have any questions regarding the codebase, or you would like to discuss anything about the project hit/connect me up on :

 - [Twitter](https://twitter.com/RealLifeGyan).
 - [LinkedIN](https://www.linkedin.com/in/anshul-upadhyay-13189952/)



