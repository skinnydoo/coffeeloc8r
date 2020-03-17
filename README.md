# CoffeeLoc8r - Find coffee shops around you!

## How to use this project

You need Android Studio to work with this repository.

First thing you will need to compile this project is to create a Foursquare developer account. So you can start by visiting the [Foursquare for Developers](https://developer.foursquare.com/) website and creating a new app. Once you’ve done this, you should have a corresponding `Client ID` and  `Client Secret` for the app. 

Secondly, you will also need to be a Google Maps developer, in order to create and display the map. You can get an API key by visiting the [Google Maps Android API](https://developers.google.com/maps/documentation/android-sdk/get-api-key) website.

Then lastly create a resource file `.../res/values/api_keys.xml` (this path is ignored by git) with the following content:

```xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
    <string name="google_maps_key" templateMergeStrategy="preserve" translatable="false">
        YOUR_GOOGLE_MAPS_API_KEY
    </string>
    <string name="foursquare_client_id" templateMergeStrategy="preserve" translatable="false">
        YOUR_FOURSQUARE_CLIENT_ID
    </string>
    <string name="foursquare_client_secret" templateMergeStrategy="preserve" translatable="false">
        YOUR_FOURSQUARE_CLIENT_SECRET
    </string>
</resources>
```

The `Kotlin` plugin for Android Studio is also required.
