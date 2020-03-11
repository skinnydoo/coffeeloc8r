package com.skinnydoo.coffeeloc8r.ui.details.viewholder

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.skinnydoo.coffeeloc8r.common.BaseViewHolder
import com.skinnydoo.coffeeloc8r.databinding.ListItemShopDetailsMapBinding
import com.skinnydoo.coffeeloc8r.ui.details.models.MapItem
import com.skinnydoo.coffeeloc8r.utils.OnMapAndViewReadyListener
import com.skinnydoo.coffeeloc8r.utils.extensions.executeAfter

private const val DEFAULT_ZOOM = 16f

class MapItemViewHolder(
    private val binding: ListItemShopDetailsMapBinding
) : BaseViewHolder<MapItem>(binding.root),
    OnMapAndViewReadyListener.OnGlobalLayoutAndMapReadyListener {
    private lateinit var shopLocation: LatLng

    override fun bind(item: MapItem) {
        binding.executeAfter {
            this.item = item
            shopLocation = LatLng(item.lat, item.lon)
            with(binding.mapView) {
                onCreate(null)
                OnMapAndViewReadyListener(this, this@MapItemViewHolder)
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val map = googleMap ?: return
        with(map) {
            mapType = GoogleMap.MAP_TYPE_NORMAL
            addMarker(MarkerOptions().position(shopLocation))
            moveCamera(CameraUpdateFactory.newLatLngZoom(shopLocation, DEFAULT_ZOOM))
        }
    }
}
