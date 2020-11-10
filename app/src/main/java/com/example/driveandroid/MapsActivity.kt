package com.example.driveandroid

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)
        //mapView.onCreate(savedInstanceState)
        //mapView.getMapAsync(this)

        mapsReturnHome.setOnClickListener {
            val intent3 = Intent(this@MapsActivity, FolderListActivity::class.java)
            startActivity(intent3)
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)


    }

    /*   override fun onStart() {
           super.onStart()
           mapView.onStart()
       }

       override fun onResume() {
           super.onResume()
           mapView.onResume()
       }

       override fun onPause() {
           super.onPause()
           mapView.onPause()
       }

       override fun onStop() {
           super.onStop()
           mapView.onStop()
       }

       override fun onDestroy() {
           super.onDestroy()
           mapView.onDestroy()
       }

       override fun onSaveInstanceState(outState: Bundle) {
           super.onSaveInstanceState(outState)
           mapView.onSaveInstanceState(outState)
       }

       override fun onLowMemory() {
           super.onLowMemory()
           mapView.onLowMemory()
       }
   */


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(0.0, 0.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))


    }


}