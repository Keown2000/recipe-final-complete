package keown.cantrell.recipe.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import keown.cantrell.recipe.R
import kotlinx.android.synthetic.main.activity_recipe_maps.*
import kotlinx.android.synthetic.main.content_recipe_maps.*
import keown.cantrell.recipe.main.MainApp

class RecipeMapsActivity : AppCompatActivity(), GoogleMap.OnMarkerClickListener {

  lateinit var map: GoogleMap
  lateinit var app: MainApp

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    app = application as MainApp
    setContentView(R.layout.activity_recipe_maps)
    setSupportActionBar(toolbar)
    mapView.onCreate(savedInstanceState);
    mapView.getMapAsync {
      map = it
      configureMap()
    }
  }

  fun configureMap() {
    map.setOnMarkerClickListener(this)
    map.uiSettings.setZoomControlsEnabled(true)
    app.recipes.findAll().forEach {
      val loc = LatLng(it.lat, it.lng)
      val options = MarkerOptions().title(it.title).position(loc)
      map.addMarker(options).tag = it.id
      map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.zoom))
    }
  }

  override fun onMarkerClick(marker: Marker): Boolean {
    currentTitle.text = marker.title
    return false
  }

  override fun onDestroy() {
    super.onDestroy()
    mapView.onDestroy()
  }

  override fun onLowMemory() {
    super.onLowMemory()
    mapView.onLowMemory()
  }

  override fun onPause() {
    super.onPause()
    mapView.onPause()
  }

  override fun onResume() {
    super.onResume()
    mapView.onResume()
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    mapView.onSaveInstanceState(outState)
  }
}


