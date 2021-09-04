plugins {
  id("com.android.application")
  kotlin("android")
  kotlin("kapt")
  id("dagger.hilt.android.plugin")
  id("com.google.gms.google-services")
  id("com.google.firebase.crashlytics")
  id("androidx.navigation.safeargs.kotlin")
  id("org.jetbrains.kotlin.plugin.serialization") version "1.5.30"
  id("com.apollographql.apollo")
  id("kotlin-parcelize")
}

android {
  signingConfigs {
    create("release") {
      /*keyAlias = "release"
      keyPassword = "my release key password"
      storeFile = file("/home/miles/keystore.jks")
      storePassword = "my keystore password"*/
    }
  }
  buildToolsVersion = Sdk.buildTools
  compileSdkVersion(Sdk.compileSdk)
  defaultConfig {
    applicationId = "com.skinnydoo.coffeeloc8r"
    minSdkVersion(Sdk.minSdk)
    targetSdkVersion(Sdk.targetSdk)
    versionCode = Versions.versionCode()
    versionName = Versions.versionName()
    testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

    resValue("integer", "cache_duration", properties["cache_duration"] as String)

    vectorDrawables.useSupportLibrary = true
  }

  buildTypes {
    getByName("release") {
      buildConfigField("String", "BASE_API_URL", properties["BASE_API_URL"] as String)
      signingConfig = signingConfigs.getByName("release")

      isMinifyEnabled = true
      isShrinkResources = true
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }

    getByName("debug") {
      applicationIdSuffix = ".debug"
      versionNameSuffix = "-debug"

      buildConfigField("String", "BASE_API_URL", properties["DEBUG_BASE_API_URL"] as String)

      isMinifyEnabled = false
      proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
    }
  }

  buildFeatures {
    dataBinding = true
    viewBinding = true
  }

  compileOptions {
    sourceCompatibility = JavaVersion.VERSION_1_8
    targetCompatibility = JavaVersion.VERSION_1_8
    isCoreLibraryDesugaringEnabled = true
  }
}

dependencies {
  implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
  coreLibraryDesugaring(Deps.desugarJdkLib)
  // BoM for the Firebase platform
  implementation(platform(Deps.Firebase.bom))
  // Okhttp BoM
  implementation(platform(Deps.Okhttp.bom))

  // Kotlin & Components
  implementation(Deps.Kotlin.stdlib)
  implementation(Deps.Kotlin.reflect)
  implementation(Deps.Kotlin.coroutinesAndroid)
  implementation(Deps.Kotlin.coroutinesPlayServices)
  implementation(Deps.Kotlin.serializationJson)

  // UI
  implementation(Deps.UI.appCompat)
  implementation(Deps.UI.constraintLayout)
  implementation(Deps.UI.material)
  implementation(Deps.UI.activityKtx)
  implementation(Deps.UI.fragmentKtx)
  implementation(Deps.UI.browser)
  implementation(Deps.UI.exif)
  implementation(Deps.UI.flexbox)
  implementation(Deps.UI.preferenceKtx)
  implementation(Deps.UI.recyclerview)

  // Architecture & Lifecycle component
  implementation(Deps.JetPacks.coreKtx)
  implementation(Deps.JetPacks.collectionKtx)
  implementation(Deps.JetPacks.viewModelKtx)
  implementation(Deps.JetPacks.viewModelSaveState)
  implementation(Deps.JetPacks.runtimeKtx)
  implementation(Deps.JetPacks.livedataKtx)
  implementation(Deps.JetPacks.lifecycleService)
  implementation(Deps.JetPacks.lifecycleProcess)
  implementation(Deps.JetPacks.lifecycleCommonJava8)
  implementation(Deps.JetPacks.playCore)

  // Apollo-Android
  implementation(Deps.Apollo.runtime)
  implementation(Deps.Apollo.coroutines)
  implementation(Deps.Apollo.sqliteCache)
  implementation(Deps.Apollo.httpCache)

  // Chucker
  debugImplementation(Deps.chucker)
  releaseImplementation(Deps.chuckerNoOp)


  // Dagger Hilt
  implementation(Deps.JetPacks.hilt)
  kapt(Deps.JetPacks.kaptHiltCompilerDagger)

  implementation(Deps.JetPacks.hiltWork)
  implementation(Deps.JetPacks.hiltNavFragment)
  kapt(Deps.JetPacks.kaptHiltCompiler)

  // Firebase
  // with firebase BoM imported, no need to specify versions in Firebase library dependencies
  implementation(Deps.Firebase.analytics)
  implementation(Deps.Firebase.messaging)
  implementation(Deps.Firebase.crashlytics)

  // Glide components
  implementation(Deps.glide) {
    // exclude Glide’s OkHttp peer-dependency module and define our own module import
    exclude(group = "com.squareup.okhttp3", module = "okhttp")
  }
  kapt(Deps.kaptGlide)

  // GSON component
  implementation(Deps.gson)

  //Material Dialog
  implementation(Deps.UI.materialDialogCore)
  implementation(Deps.UI.materialDialogBottomSheet)
  implementation(Deps.UI.materialDialogInput)
  implementation(Deps.UI.materialDialogLifeCycle)

  // Leak Canary
  debugImplementation(Deps.leakCanary)

  // Location Services components
  implementation(Deps.PlayServices.location)
  implementation(Deps.PlayServices.maps)

  // Navigation component
  implementation(Deps.JetPacks.navigationRuntimeKtx)
  implementation(Deps.JetPacks.navigationFragmentKtx)
  implementation(Deps.JetPacks.navigationUIKtx)

  // Okhttp component
  implementation(Deps.Okhttp.okhttp)
  implementation(Deps.Okhttp.loggingInterceptor)

  // Retrofit component
  implementation(Deps.Okhttp.retrofit) {
    // exclude Retrofit’s OkHttp peer-dependency module and define our own module import
    exclude(group = "com.squareup.okhttp3", module = "okhttp")
  }
  implementation(Deps.Okhttp.retrofitGsonConverter)

  // Room
  implementation(Deps.JetPacks.roomRuntime)
  implementation(Deps.JetPacks.roomKtx)
  kapt(Deps.JetPacks.kaptRoomCompiler)

  // Swipe refresh
  implementation(Deps.UI.swipeRefresh)

  // Timber Logging component
  implementation(Deps.timber)

  // Transition
  implementation(Deps.JetPacks.transitionKtx)

  // System tracing
  implementation("androidx.tracing:tracing-ktx:1.0.0")

  // Local unit tests
  testImplementation(Deps.Testing.archCoreTesting)
  testImplementation(Deps.Testing.coreKtx)
  testImplementation(Deps.Testing.mockWebServer)
  testImplementation(Deps.Testing.jUnit)
  testImplementation(Deps.Testing.jUnitKtx)
  testImplementation(Deps.Testing.coroutinesTest)
  testImplementation(Deps.Testing.mockitoCore)
  testImplementation(Deps.Testing.mockitoKt)
  testImplementation(Deps.Testing.truth)
}

kapt {
  correctErrorTypes = true
}
