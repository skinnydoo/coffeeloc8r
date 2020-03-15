package com.skinnydoo.coffeeloc8r.ui.home

import android.app.Activity
import android.content.Intent
import android.content.IntentSender
import android.location.Location
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.skinnydoo.coffeeloc8r.R
import com.skinnydoo.coffeeloc8r.common.AppExecutors
import com.skinnydoo.coffeeloc8r.common.AppNavigator
import com.skinnydoo.coffeeloc8r.databinding.FragmentHomeBinding
import com.skinnydoo.coffeeloc8r.domain.models.CoffeeShop
import com.skinnydoo.coffeeloc8r.ui.MainViewModel
import com.skinnydoo.coffeeloc8r.ui.home.adapter.BottomSheetAdapter
import com.skinnydoo.coffeeloc8r.ui.home.models.HomeAction
import com.skinnydoo.coffeeloc8r.ui.home.models.HomeActor
import com.skinnydoo.coffeeloc8r.utils.DividerItemDecorator
import com.skinnydoo.coffeeloc8r.utils.OnMapAndViewReadyListener
import com.skinnydoo.coffeeloc8r.utils.event.EventObserver
import com.skinnydoo.coffeeloc8r.utils.extensions.exhaustive
import com.skinnydoo.coffeeloc8r.utils.extensions.showToast
import org.threeten.bp.LocalTime
import org.threeten.bp.format.DateTimeFormatter
import timber.log.Timber
import java.lang.ref.WeakReference
import javax.inject.Inject

private const val DEFAULT_ZOOM = 11f // city level

// constant used in the location settings dialog
private const val REQUEST_CHECK_SETTINGS = 101

private const val KEY_CAMERA_POSITION = "camera_position"
private const val KEY_LOCATION = "location"
private const val KEY_LOCATION_LAT = "location_lat"
private const val KEY_LOCATION_LON = "location_lon"

class HomeFragment @Inject constructor(
    appExecutors: AppExecutors,
    private val fusedLocationClient: FusedLocationProviderClient, // provide access to the Fused Location Provider API
    private val locationRequest: LocationRequest, // stores parameters for requests to the Fused Location Provider API
    private val settingsClient: SettingsClient, // provide access to the Location Settings API
    private val locationSettingsRequest: LocationSettingsRequest, // Used to determine if the device has optimal location settings
    private val viewModelFactory: ViewModelProvider.Factory,
    private val appNavigator: AppNavigator,
    private val actor: HomeActor
) : Fragment(), OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding
        get() = _binding!!

    private val navController by lazy { findNavController() }

    private val viewModel by viewModels<HomeViewModel> { viewModelFactory }
    private val activityViewModel by activityViewModels<MainViewModel> { viewModelFactory }

    private val bottomSheetAdapter by lazy { BottomSheetAdapter(appExecutors, actor) }

    private lateinit var locationCallback: LocationCallback
    private lateinit var locationCallbackStrongRef: LocationCallback

    private var map: GoogleMap? = null
    private var currentLocation: Location? = null
    private val coffeeMarkers = hashMapOf<String, Marker>()
    private val shops = mutableListOf<CoffeeShop>()
    private var firstDataLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createLocationCallback()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.vm = viewModel
        observeViewModel()
        initView(savedInstanceState)
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
        // Make sure that all permissions are still present, since the
        // user could have removed them while the app was in paused state.
        if (!PermissionFragment.hasPermission(requireContext())) {
            navController.navigate(HomeFragmentDirections.toPermissionDest())
        } else requestLocationUpdates()
    }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }


    override fun onPause() {
        super.onPause()
        // Remove location updates to save battery
        stopLocationUpdates()
        binding.mapView.onPause()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStop()
    }

    override fun onDestroyView() {
        clearResources()
        super.onDestroyView()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun clearResources() {
        map?.clear()
        map?.mapType = GoogleMap.MAP_TYPE_NONE
        shops.clear()
        coffeeMarkers.clear()
        binding.mapView.onDestroy()
        binding.bottomSheetRecycler.adapter = null
        _binding = null
        map = null
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        map = googleMap ?: return // return early if map was not initialized properly
        Timber.d("Map is ready...making initial setup")
        map?.apply {
            mapType = GoogleMap.MAP_TYPE_NORMAL

            if (PermissionFragment.hasPermission(requireContext())) {
                isMyLocationEnabled = true
                uiSettings.isMyLocationButtonEnabled = true
            }

            setOnMarkerClickListener { handleMarkerClicked(it) }
        }
        shops.forEach(this::addCoffeeShopOnMap)
    }

    private fun handleMarkerClicked(marker: Marker?): Boolean {
        marker?.let {
            val shop = it.tag as CoffeeShop
            Timber.d("Marker Clicked: $shop")
            navController.navigate(HomeFragmentDirections.toShopDetails(shop))
        }
        return true
    }

    private fun initView(savedInstanceState: Bundle?) {
        setupMap(savedInstanceState)
        setupRecycler()
    }

    private fun setupRecycler() {
        binding.bottomSheetRecycler.apply {
            adapter = bottomSheetAdapter
            addItemDecoration(
                DividerItemDecorator(
                    ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.view_divider
                    )
                )
            )
        }
    }

    private fun setupMap(savedInstanceState: Bundle?) {
        binding.mapView.run {
            isHapticFeedbackEnabled = true
            onCreate(savedInstanceState)
            OnMapAndViewReadyListener(
                this,
                this@HomeFragment
            )
        }
    }


    private fun observeViewModel() {
        viewModel.viewState.observe(viewLifecycleOwner, Observer { state ->
            val viewState = state ?: return@Observer
            Timber.d(viewState.toString())

            viewState.error?.takeIf { !it.consumed }?.consume()?.let {
                requireContext().showToast(it)
            }

            viewState.shops?.let {
                map?.clear()
                shops.clear()

                it.forEach(::addCoffeeShopOnMap)
                shops.addAll(it)
                bottomSheetAdapter.submitList(it)
            }
        })

        activityViewModel.homeActions.observe(viewLifecycleOwner, EventObserver(::onHomeAction))
    }

    private fun addCoffeeShopOnMap(shop: CoffeeShop) {
        val map = map ?: return
        with(map) {
            val latLng = LatLng(shop.lat, shop.lon)

            val marker = addMarker(
                MarkerOptions()
                    .position(latLng)
                    .draggable(false)
                    .title(shop.name)
            )
            marker.tag = shop
            coffeeMarkers[shop.id] = marker
        }
    }

    private fun onHomeAction(action: HomeAction) {
        when (action) {
            is HomeAction.ShowCoffeeShopDetails -> {
                Timber.d("Clicked ${action.shop}")
                navController.navigate(HomeFragmentDirections.toShopDetails(action.shop))
            }
        }.exhaustive
    }


    private fun createLocationCallback() {
        locationCallbackStrongRef = object : LocationCallback() {
            override fun onLocationResult(location: LocationResult?) {
                if (location != null) this@HomeFragment.onLocationResult(location.lastLocation)
                else {
                    appNavigator.showErrorDialog(msgRes = R.string.error_failed_to_get_device_location) {
                        this@HomeFragment.onLocationResult(null)
                    }
                }
            }
        }
        locationCallback = LocationCallBackRef(locationCallbackStrongRef)
    }

    private fun onLocationResult(location: Location?) {
        val time = LocalTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME)
        Timber.d("Location update received: $location. Time: $time")
        currentLocation = location
        currentLocation?.let {
            centerMapAroundLocation(it)
            if (firstDataLoad) {
                loadCoffeeShop(it)
                firstDataLoad = false
            }
        }
    }

    private fun centerMapAroundLocation(location: Location) {
        map?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(location.latitude, location.longitude),
                DEFAULT_ZOOM
            )
        )
    }

    private fun loadCoffeeShop(location: Location) {
        viewModel.searchCoffeeShop(
            LatLng(location.latitude, location.longitude), location.accuracy.toDouble()
        )
    }

    /**
     * Request location updates from the Fused Location Provider
     */
    private fun requestLocationUpdates() {
        Timber.d("Let's check if device has the optimal location settings...")
        settingsClient.checkLocationSettings(locationSettingsRequest)
            .addOnSuccessListener {
                Timber.d("Location Settings OK!")
                Timber.d("Requesting location updates...")

                fusedLocationClient.requestLocationUpdates(
                    locationRequest,
                    locationCallback,
                    Looper.myLooper()
                )

                Timber.d("Location update requested...")
            }
            .addOnFailureListener { ex ->
                Timber.i("Device doesn't have the optimal location settings...")
                if (ex !is ApiException) return@addOnFailureListener

                when (ex.statusCode) {
                    LocationSettingsStatusCodes.RESOLUTION_REQUIRED -> {
                        Timber.d("Attempting to upgrade device location settings...")

                        try {
                            // show the dialog by calling startResolutionForResult(), and check
                            // the result in onActivityResult
                            val rae = ex as ResolvableApiException
                            startIntentSenderForResult(
                                rae.resolution.intentSender,
                                REQUEST_CHECK_SETTINGS,
                                null,
                                0,
                                0,
                                0,
                                null
                            )
                        } catch (e: IntentSender.SendIntentException) {
                            Timber.e(e, "Attempt to upgrade device location settings failed.")
                            appNavigator.showErrorDialog(msgRes = R.string.error_failed_to_get_device_location)
                        }
                    }

                    LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE -> {
                        Timber.i("Location settings are inadequate and cannot be fixed")
                        appNavigator.showErrorDialog(msgRes = R.string.error_location_settings_inadequate)
                    }
                }
            }
    }

    private fun stopLocationUpdates() {
        Timber.d("Removing location updates...")
        fusedLocationClient.removeLocationUpdates(locationCallback)
            .addOnSuccessListener {
                Timber.d("Location updates removed....")
            }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != REQUEST_CHECK_SETTINGS) return

        when (resultCode) {
            Activity.RESULT_OK -> {
                // Nothing to do requestLocationUpdates gets called in onStart again
                Timber.d("Used agreed to make the required locations settings change")
            }
            Activity.RESULT_CANCELED -> {
                // User chose not to make the required location changed
                Timber.d("User chose not to make the required location changed")
                appNavigator.showErrorDialog(msgRes = R.string.error_failed_to_get_device_location)
            }
            else -> super.onActivityResult(requestCode, resultCode, data)
        }
    }


    // WorkAround to avoid activity/fragment leak
    // issue: https://issuetracker.google.com/issues/37126862
    class LocationCallBackRef(locationCallback: LocationCallback) : LocationCallback() {
        private val callbackRef: WeakReference<LocationCallback> = WeakReference(locationCallback)

        override fun onLocationResult(locationResult: LocationResult?) {
            super.onLocationResult(locationResult)
            callbackRef.get()?.onLocationResult(locationResult)
        }
    }


}
