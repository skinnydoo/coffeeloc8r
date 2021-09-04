object Deps {

  const val googleServices = "com.google.gms:google-services:${Versions.googleServices}"
  const val desugarJdkLib = "com.android.tools:desugar_jdk_libs:${Versions.desugarJdkLib}"
  const val chucker = "com.github.chuckerteam.chucker:library:${Versions.chucker}"
  const val chuckerNoOp = "com.github.chuckerteam.chucker:library-no-op:${Versions.chucker}"

  const val glide = "com.github.bumptech.glide:okhttp3-integration:${Versions.glide}"
  const val kaptGlide = "com.github.bumptech.glide:compiler:${Versions.glide}"

  const val gson = "com.google.code.gson:gson:${Versions.gson}"

  const val leakCanary = "com.squareup.leakcanary:leakcanary-android:${Versions.leakCanary}"

  const val timber = "com.jakewharton.timber:timber:${Versions.timber}"

  object GradlePlugins {
    const val gradle = "com.android.tools.build:gradle:${Versions.gradle}"
    const val kotlin = "org.jetbrains.kotlin:kotlin-gradle-plugin:${Versions.kotlin}"
    const val crashlytics =
      "com.google.firebase:firebase-crashlytics-gradle:${Versions.crashlytics}"
    const val hilt = "com.google.dagger:hilt-android-gradle-plugin:${Versions.hiltAndroid}"
    const val safeArgs =
      "androidx.navigation:navigation-safe-args-gradle-plugin:${Versions.navigation}"
    const val apollo = "com.apollographql.apollo:apollo-gradle-plugin:${Versions.apollo}"
  }

  object Kotlin {
    const val stdlib = "org.jetbrains.kotlin:kotlin-stdlib-jdk8:${Versions.kotlin}"
    const val reflect = "org.jetbrains.kotlin:kotlin-reflect:${Versions.kotlin}"
    const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Versions.coroutines}"
    const val coroutinesPlayServices = "org.jetbrains.kotlinx:kotlinx-coroutines-play-services:${Versions.coroutines}"
    const val serializationJson = "org.jetbrains.kotlinx:kotlinx-serialization-json:${Versions.serializationJson}"
  }

  object UI {
    const val appCompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val constraintLayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val activityKtx = "androidx.activity:activity-ktx:${Versions.activity}"
    const val fragmentKtx = "androidx.fragment:fragment-ktx:${Versions.fragment}"
    const val browser = "androidx.browser:browser:${Versions.browser}"
    const val exif = "androidx.exifinterface:exifinterface:${Versions.exif}"
    const val flexbox = "com.google.android.flexbox:flexbox:${Versions.flexbox}"
    const val preferenceKtx = "androidx.preference:preference-ktx:${Versions.preference}"
    const val recyclerview = "androidx.recyclerview:recyclerview:${Versions.recycler}"
    const val materialDialogBottomSheet = "com.afollestad.material-dialogs:bottomsheets:${Versions.materialDialog}"
    const val materialDialogCore = "com.afollestad.material-dialogs:core:${Versions.materialDialog}"
    const val materialDialogInput = "com.afollestad.material-dialogs:input:${Versions.materialDialog}"
    const val materialDialogLifeCycle = "com.afollestad.material-dialogs:lifecycle:${Versions.materialDialog}"
    const val swipeRefresh = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swipeRefresh}"
  }

  object Apollo {
    const val runtime = "com.apollographql.apollo:apollo-runtime:${Versions.apollo}"
    const val coroutines = "com.apollographql.apollo:apollo-coroutines-support:${Versions.apollo}"
    const val sqliteCache = "com.apollographql.apollo:apollo-normalized-cache-sqlite:${Versions.apollo}"
    const val httpCache = "com.apollographql.apollo:apollo-http-cache:${Versions.apollo}"
  }

  object JetPacks {
    const val coreKtx = "androidx.core:core-ktx:${Versions.core}"
    const val collectionKtx = "androidx.collection:collection-ktx:${Versions.collection}"
    const val viewModelKtx = "androidx.lifecycle:lifecycle-viewmodel-ktx:${Versions.lifecycle}"
    const val viewModelSaveState = "androidx.lifecycle:lifecycle-viewmodel-savedstate:${Versions.lifecycle}"
    const val runtimeKtx = "androidx.lifecycle:lifecycle-runtime-ktx:${Versions.lifecycle}"
    const val livedataKtx = "androidx.lifecycle:lifecycle-livedata-ktx:${Versions.lifecycle}"
    const val lifecycleService = "androidx.lifecycle:lifecycle-service:${Versions.lifecycle}"
    const val lifecycleProcess = "androidx.lifecycle:lifecycle-process:${Versions.lifecycle}"
    const val lifecycleCommonJava8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle}"
    const val playCore = "com.google.android.play:core:${Versions.playCore}"
    const val hilt = "com.google.dagger:hilt-android:${Versions.hiltAndroid}"
    const val kaptHiltCompilerDagger = "com.google.dagger:hilt-compiler:${Versions.hiltAndroid}"
    const val hiltWork = "androidx.hilt:hilt-work:${Versions.hiltJetPack}"
    const val hiltNavFragment = "androidx.hilt:hilt-navigation-fragment:${Versions.hiltJetPack}"
    const val kaptHiltCompiler = "androidx.hilt:hilt-compiler:${Versions.hiltJetPack}"
    const val navigationRuntimeKtx = "androidx.navigation:navigation-runtime-ktx:${Versions.navigation}"
    const val navigationFragmentKtx = "androidx.navigation:navigation-fragment-ktx:${Versions.navigation}"
    const val navigationUIKtx = "androidx.navigation:navigation-ui-ktx:${Versions.navigation}"
    const val roomRuntime = "androidx.room:room-runtime:${Versions.room}"
    const val roomKtx = "androidx.room:room-ktx:${Versions.room}"
    const val kaptRoomCompiler = "androidx.room:room-compiler:${Versions.room}"
    const val transitionKtx = "androidx.transition:transition-ktx:${Versions.transitionKtx}"
  }

  object Firebase {
    const val bom = "com.google.firebase:firebase-bom:${Versions.firebaseBom}"
    const val analytics = "com.google.firebase:firebase-analytics-ktx"
    const val messaging = "com.google.firebase:firebase-messaging-ktx"
    const val crashlytics = "com.google.firebase:firebase-crashlytics-ktx"
  }


  object Okhttp {
    const val bom = "com.squareup.okhttp3:okhttp-bom:${Versions.okhttpBom}"
    const val okhttp = "com.squareup.okhttp3:okhttp"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor"
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit2}"
    const val retrofitGsonConverter = "com.squareup.retrofit2:converter-gson:${Versions.retrofit2}"
  }

  object PlayServices {
    const val maps = "com.google.android.gms:play-services-maps:${Versions.playServices}"
    const val location = "com.google.android.gms:play-services-location:${Versions.playServices}"
  }

  object Testing {
    const val archCoreTesting = "androidx.arch.core:core-testing:${Versions.coreTestingArch}"
    const val mockWebServer = "com.squareup.okhttp3:mockwebserver"
    const val jUnit = "junit:junit:${Versions.jUnit}"
    const val mockitoCore = "org.mockito:mockito-core:${Versions.mockito}"
    const val mockitoKt = "com.nhaarman.mockitokotlin2:mockito-kotlin:${Versions.mockitoKotlin}"
    const val coroutinesTest = "org.jetbrains.kotlinx:kotlinx-coroutines-test:${Versions.coroutines}"
    const val truth = "com.google.truth:truth:${Versions.truth}"
    const val coreKtx = "androidx.test:core-ktx:${Versions.testCore}"
    const val jUnitKtx = "androidx.test.ext:junit-ktx:1.1.3"
  }
}
