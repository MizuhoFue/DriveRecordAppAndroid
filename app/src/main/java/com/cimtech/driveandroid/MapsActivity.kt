//package com.example.driveandroid
//
//import android.Manifest
//import android.content.Intent
//import android.content.pm.PackageManager
//import android.os.Bundle
//import android.os.Looper
//import android.widget.Toast
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.app.ActivityCompat
//import androidx.core.content.ContextCompat
//import com.google.android.gms.location.FusedLocationProviderClient
//import com.google.android.gms.location.LocationCallback
//import com.google.android.gms.location.LocationRequest
//import com.google.android.gms.location.LocationResult
//import com.google.android.gms.maps.CameraUpdateFactory
//import com.google.android.gms.maps.CameraUpdateFactory.newLatLng
//import com.google.android.gms.maps.GoogleMap
//import com.google.android.gms.maps.OnMapReadyCallback
//import com.google.android.gms.maps.SupportMapFragment
//import com.google.android.gms.maps.model.LatLng
//import com.google.android.gms.maps.model.LatLngBounds
//import kotlinx.android.synthetic.main.activity_maps.*
//
//@Suppress("DEPRECATION")
//class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
//
//    private lateinit var mMap: GoogleMap
//
//    //現在値取得用変数
//    private lateinit var fusedLocationClient: FusedLocationProviderClient
//
//    //使用許可ダイアログ用
//    private val REQUEST_CODE_LOCATION = 100
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_maps)
//
//        //許可されてなかったらダイアログ表示
//        if (ContextCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            ActivityCompat.requestPermissions(
//                this,
//                arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
//                REQUEST_CODE_LOCATION
//            )
//        }
//
//        //ナビゲーションアイテムのリスナー
//        return_home.setOnClickListener {
//            val intent = Intent(this@MapsActivity, FolderListActivity::class.java)
//            startActivity(intent)
//        }
//        settings.setOnClickListener {
//            val intent = Intent(this@MapsActivity, SupportActivity::class.java)
//            startActivity(intent)
//        }
//    }
//
//    //google map起動
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        // どのような取得方法を要求
//        val locationRequest = LocationRequest().apply {
//            // 精度重視(電力大)と省電力重視(精度低)を両立するため2種類の更新間隔を指定
//            interval = 10000                                   // 最遅の更新間隔
//            fastestInterval = 5000                             // 最短の更新間隔
//            priority = LocationRequest.PRIORITY_HIGH_ACCURACY  // 精度重視
//        }
//
//        // コールバック、現在地表示
//        val locationCallback = object : LocationCallback() {
//            override fun onLocationResult(locationResult: LocationResult?) {
//
//                // 更新直後の位置が格納
//                val location = locationResult?.lastLocation ?: return
//                val currentLocation = LatLng(location.latitude, location.longitude) //現在地
//                mMap.moveCamera(newLatLng(currentLocation))
//
//                zoomMap(location.latitude, location.longitude)
//
//                // 初回起動でしか位置情報を取得しないので、位置情報の定期取得を止める
//                //(地図画面操作中に現在地に自動で戻ってしまう処理をなくす)
//                fusedLocationClient.removeLocationUpdates(this)
//            }
//        }
//
//        // 位置情報を更新
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
//        //現在地定期取得
//        fusedLocationClient.requestLocationUpdates(
//            locationRequest,
//            locationCallback,
//            Looper.myLooper()
//        )
//
//        //現在地表示ボタン
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_FINE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
//                this,
//                Manifest.permission.ACCESS_COARSE_LOCATION
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            return
//        }
//        mMap.setMyLocationEnabled(true)
//    }
//
//    //ユーザーの選択結果を受け取る
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//
//        if (requestCode == 100) {
//            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                // 許可された場合
//                Toast.makeText(this, "許可されました。", Toast.LENGTH_SHORT).show()
//            } else {
//                // 拒否された場合
//                Toast.makeText(this, "拒否されました。", Toast.LENGTH_SHORT).show()
//                //東京駅表示
//                zoomMap(35.681167, 139.767052)
//            }
//        }
//    }
//
//    //Resume処理(使用許可後画面更新のため)
//    override fun onResume() {
//        super.onResume()
//
//        //融合された位置予測プロバイダ クライアントのインスタンス
//        fusedLocationClient = FusedLocationProviderClient(this)
//
//        val mapFragment = supportFragmentManager
//            .findFragmentById(R.id.map) as SupportMapFragment
//        mapFragment.getMapAsync(this)
//    } //onResume閉じ
//
//    //地図の拡大
//    private fun zoomMap(latitude: Double, longitude: Double) {
//        // 表示する東西南北の緯度経度を設定
//        val south = latitude * (1 - 0.00005)
//        val west = longitude * (1 - 0.00005)
//        val north = latitude * (1 + 0.00005)
//        val east = longitude * (1 + 0.00005)
//
//        // LatLngBounds (LatLng southwest, LatLng northeast)
//        val bounds = LatLngBounds.builder()
//            .include(LatLng(south, west))
//            .include(LatLng(north, east))
//            .build()
//        val width = resources.displayMetrics.widthPixels
//        val height = resources.displayMetrics.heightPixels
//
//        // static CameraUpdate.newLatLngBounds(LatLngBounds bounds, int width, int height, int padding)
//        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bounds, width, height, 0))
//    }
//}