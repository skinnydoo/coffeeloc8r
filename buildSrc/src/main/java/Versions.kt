object Versions {
    private const val versionMajor = 1
    private const val versionMinor = 1
    private const val versionPatch = 2
    private const val versionCodeIncrementer = 0
    private var versionClassifier: String? = null
    private const val isSnapshot = false

    const val gradle = "7.0.2"
    const val kotlin = "1.5.30"

    const val apollo = "2.5.9"
    const val activity = "1.3.1"
    const val annotations = "1.2.0"
    const val appcompat = "1.3.1"

    const val browser = "1.3.0"

    const val chucker = "3.5.2"
    const val collection = "1.1.0"
    const val constraintlayout = "2.1.0"
    const val core = "1.7.0-alpha01"
    const val coreTestingArch = "2.1.0"
    const val coroutines = "1.5.1"
    const val crashlytics = "2.5.2"

    const val dagger = "2.28"
    const val desugarJdkLib = "1.1.5"

    const val exif = "1.3.3"

    const val firebaseBom = "26.0.0"
    const val flexbox = "3.0.0"
    const val fragment = "1.4.0-alpha05"

    const val glide = "4.12.0"
    const val googleServices = "4.3.4"
    const val gson = "2.8.7"

    const val hiltAndroid = "2.38.1"
    const val hiltJetPack = "1.0.0"

    const val jUnit = "4.13.2"
    const val ktlin = "0.39.0"

    const val leakCanary = "2.7"
    const val lifecycle = "2.4.0-alpha03"

    const val material = "1.5.0-alpha02"
    const val materialDialog = "3.3.0"
    const val mockito = "3.11.2"
    const val mockitoKotlin = "2.2.0"

    const val navigation = "2.4.0-alpha06"
    const val okhttpBom = "4.9.1"

    const val playCore = "1.10.0"
    const val playServices = "17.0.0"
    const val preference = "1.1.1"

    const val recycler = "1.2.1"
    const val retrofit2 = "2.9.0"
    const val room = "2.3.0"

    const val serializationJson = "1.2.2"
    const val swipeRefresh = "1.2.0-alpha01"

    const val testCore = "1.4.0"
    const val timber = "5.0.1"
    const val transitionKtx = "1.4.1"
    const val truth = "1.1.3"


    /**
     * Builds an Android version code from the version of the project.
     *
     * I.e. when the application versionName is 3.1.0, the versionCode for a minimum API level 21 APK
     * would be something like 21030100. The first two digits are reserved for the minimum
     * API Level (21 in this case), and the last six digits are for the application’s version name (3.1.0).
     *
     * NOTE: The greatest value Google Play allows for versionCode is 2100000000
     * @return
     */
    fun versionCode(): Int {
        return (Sdk.minSdk * 1000000) +
                (versionMajor * 10000) + (versionMinor * 100) + versionPatch + versionCodeIncrementer
    }

    /**
     * Builds an Android version name from the version of the project.
     * This is designed to handle the -SNAPSHOT
     *
     * I.e. during development the version ends with -SNAPSHOT and the final release is without any suffix.
     * @return
     */
    fun versionName(): String {
        var name = "${versionMajor}.${versionMinor}.${versionPatch}"
        if (versionClassifier.isNullOrEmpty() && isSnapshot) {
            versionClassifier = "SNAPSHOT"
        }

        if (versionClassifier != null) {
            name += "-${versionClassifier}"
        }
        return name
    }
}
