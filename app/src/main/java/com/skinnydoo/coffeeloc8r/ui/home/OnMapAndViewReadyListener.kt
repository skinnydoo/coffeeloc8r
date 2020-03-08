package com.skinnydoo.coffeeloc8r.ui.home

import android.view.ViewTreeObserver.OnGlobalLayoutListener
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback

/**
 * Helper class that will delay triggering the OnMapReady callback until both the GoogleMap and the
 * View having completed initialization. This is only necessary if a developer wishes to immediately
 * invoke any method on the GoogleMap that also requires the View to have finished layout
 * (ie. anything that needs to know the View's true size like snapshotting).
 */
class OnMapAndViewReadyListener(
        private val mapView: MapView?,
        private val toBeNotified: OnGlobalLayoutAndMapReadyListener
) : OnGlobalLayoutListener,
        OnMapReadyCallback {

    private var isViewReady = false
    private var isMapReady = false
    private var map: GoogleMap? = null

    /** A listener that needs to wait for both the GoogleMap and the View to be initialized.  */
    interface OnGlobalLayoutAndMapReadyListener {
        fun onMapReady(googleMap: GoogleMap?)
    }

    init {
        registerListeners()
    }

    private fun registerListeners() {
        // View layout.
        if (mapView?.width != 0 && mapView?.height != 0) {
            // View has already completed layout.
            isViewReady = true
        } else {
            // Map has not undergone layout, register a View observer.
            mapView.viewTreeObserver.addOnGlobalLayoutListener(this)
        }

        // GoogleMap. Note if the GoogleMap is already ready it will still fire the callback later.
        mapView?.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        // NOTE: The GoogleMap API specifies the listener is removed just prior to invocation.
        map = googleMap ?: return
        isMapReady = true
        fireCallbackIfReady()
    }

    // We use the new method when supported
    override fun onGlobalLayout() {
        // Remove our listener.
        mapView?.viewTreeObserver?.removeOnGlobalLayoutListener(this)
        isViewReady = true
        fireCallbackIfReady()
    }

    private fun fireCallbackIfReady() {
        if (isViewReady && isMapReady) {
            toBeNotified.onMapReady(map)
        }
    }
}
