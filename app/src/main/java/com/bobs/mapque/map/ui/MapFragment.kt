package com.bobs.mapque.map.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.afollestad.materialdialogs.MaterialDialog
import com.bobs.baselibrary.ext.boolean
import com.bobs.baselibrary.ext.toast
import com.bobs.baselibrary.util.loge
import com.bobs.mapque.R
import com.bobs.mapque.map.data.searchdialog.SearchModel
import com.bobs.mapque.map.viewmodel.MapViewModel
import com.bobs.mapque.network.response.IResult
import com.bobs.mapque.network.response.coord.DocumentsItem
import com.bobs.mapque.ui.MainActivity
import com.bobs.mapque.util.ext.sendCustomTemplate
import ir.mirrajabi.searchdialog.SimpleSearchDialogCompat
import ir.mirrajabi.searchdialog.core.SearchResultListener
import kotlinx.android.synthetic.main.fragment_map.*
import net.daum.mf.map.api.MapPOIItem
import net.daum.mf.map.api.MapPoint
import net.daum.mf.map.api.MapView
import org.joda.time.DateTime
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import java.util.*

class MapFragment : Fragment(), MapView.POIItemEventListener, MapView.CurrentLocationEventListener {
    companion object {
        @JvmStatic
        fun newInstance(args: Bundle? = null): MapFragment {
            val mapFragment = MapFragment()

            if (args != null) {
                mapFragment.arguments = args
            }

            return mapFragment
        }
    }

    enum class SearchType{
        ADDRESS,
        KEYWORD
    }

    private val mapViewModel: MapViewModel by viewModel { parametersOf() }

    private var onceMyLocationMove: Boolean = false
    private var selectedMyLocation: Boolean = false

    private var mylocation: MapPoint? = null
    private var curlocation: MapPoint? = null

    private var searchQuery: String? = null
    private var floatingAddress: String? = null

    private var searchType: SearchType = SearchType.ADDRESS
    private var placeName: String = ""

    val prefs: SharedPreferences by inject()
    var isFirstOpenHelpDialog by prefs.boolean("", false)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // 서버에서 주소 값을 받아오면 Observer 내용이 실행된다
        mapViewModel.searchAddressData.observe(
            viewLifecycleOwner,
            Observer { searchAddressResponse ->
                placeName = ""

                // 검색 다이얼로그용 리스트
                val searchModels = mutableListOf<SearchModel>()

                // 위 리스트에 서버에서 받아온 주소 이름들을 저장한다
                searchAddressResponse.documents?.forEach { document ->
                    document?.addressName?.let { addressname ->
                        searchModels.add(
                            SearchModel(
                                addressname
                            )
                        )
                    }
                }

                // 검색 다이얼로그 show
                SimpleSearchDialogCompat(activity,
                    getString(R.string.search_dialog_title),
                    getString(R.string.search_dialog_search_hint),
                    null,
                    searchModels as ArrayList<SearchModel>,
                    SearchResultListener { dialog, item, position ->
                        val selectedItem =
                            searchAddressResponse.documents?.find { documentsItem -> documentsItem?.addressName == item.title }

                        // y가 latitude, x가 longitude
                        setLocationAndMoveMap(
                            selectedItem!!.Y!!.toDouble(),
                            selectedItem.X!!.toDouble(),
                            selectedItem.addressName!!
                        )

                        // room에 검색한 주소 저장
                        mapViewModel.insertSearchAddress(
                            searchQuery,
                            item.title,
                            curlocation?.mapPointGeoCoord?.latitude!!,
                            curlocation?.mapPointGeoCoord?.longitude!!,
                            DateTime.now()
                        )

                        floatingAddress = selectedItem.addressName

                        dialog.dismiss()
                    }).show()
            })

        mapViewModel.searchKeywordData.observe(
            viewLifecycleOwner,
            Observer { searchKeywordResponse ->
                // 검색 다이얼로그용 리스트
                val searchModels = mutableListOf<SearchModel>()

                // 위 리스트에 서버에서 받아온 주소 이름들을 저장한다
                searchKeywordResponse.documents?.forEach { document ->
                    document?.placeName?.let { placeName ->
                        searchModels.add(
                            SearchModel(
                                placeName
                            )
                        )
                    }
                }

                // 검색 다이얼로그 show
                SimpleSearchDialogCompat(activity,
                    getString(R.string.search_dialog_title),
                    getString(R.string.search_dialog_search_hint),
                    null,
                    searchModels as ArrayList<SearchModel>,
                    SearchResultListener { dialog, item, position ->
                        val selectedItem =
                            searchKeywordResponse.documents?.find { documentsItem -> documentsItem?.placeName == item.title }

                        val address = selectedItem?.roadAddressName ?: selectedItem?.addressName!!

                        // y가 latitude, x가 longitude
                        setLocationAndMoveMap(
                            selectedItem!!.Y!!.toDouble(),
                            selectedItem.X!!.toDouble(),
                            address,
                            selectedItem.placeName.toString()
                        )

                        // room에 검색한 주소 저장
                        mapViewModel.insertSearchAddress(
                            searchQuery,
                            address,
                            curlocation?.mapPointGeoCoord?.latitude!!,
                            curlocation?.mapPointGeoCoord?.longitude!!,
                            DateTime.now(),
                            selectedItem.placeName.toString()
                        )

                        floatingAddress = address

                        dialog.dismiss()
                    }).show()
            })

        // 맵뷰를 세팅
        setMapView()

        // 서치뷰 세팅
        search_view.apply {
            isSubmitButtonEnabled = true

            setOnQueryTextListener(object : OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    // 검색 버튼 눌려졌을 때 처리
                    val mainActivity = activity as MainActivity
                    mainActivity.hideKeyboard()
                    mainActivity.showLoading()

                    query?.let {
                        val location = if (selectedMyLocation) mylocation?.mapPointGeoCoord else curlocation?.mapPointGeoCoord

                        val resultListener = object :
                            IResult<String> {
                            override fun success(result: String) {
                                mainActivity.hideLoading()
                                searchQuery = it
                            }

                            override fun fail(msg: String?) {
                                mainActivity.hideLoading()
                                mainActivity.toast(
                                    msg ?: getString(R.string.search_view_search_fail)
                                )
                            }
                        }

                        when(searchType){
                            SearchType.ADDRESS -> mapViewModel.getSearchAddress(it, resultListener)
                            SearchType.KEYWORD -> mapViewModel.getSearchKeyword(it, location?.latitude!!, location.longitude, resultListener)
                        }
                    }
                    return true
                }

                override fun onQueryTextChange(query: String?): Boolean {
                    return true
                }
            })
        }

        helpBtn.run {
            setOnClickListener {
                showHelpDialog()
            }
        }

        myLocationBtn.run {
            setOnClickListener {
                (activity as MainActivity).showLoading()

                // 내 위치 트랙킹을 다시 활성화 시킨다
                mapView?.currentLocationTrackingMode =
                    MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving
                onceMyLocationMove = false
            }
        }

        lastLocationBtn.run {
            setOnClickListener {
                // 마지막 검색 위치로 이동
                if (curlocation == null) {
                    (activity as MainActivity).toast(getString(R.string.last_location_empty))
                } else {
                    selectedMyLocation = false
                    moveMap(floatingAddress.toString())
                }
            }
        }

        spinner_searchtype.run {
            onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
                override fun onNothingSelected(p0: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?,
                                            view: View?,
                                            pos: Int,
                                            id: Long) {
                    when (pos) {
                        0 -> searchType = SearchType.ADDRESS
                        1 -> searchType = SearchType.KEYWORD
                    }
                }
            }
        }

        // 앱 최초 설치 시 도움말을 띄운다
        if (!isFirstOpenHelpDialog) {
            showHelpDialog()
            isFirstOpenHelpDialog = true
        }
    }

    private fun showHelpDialog() {
        val mainActivity = activity as MainActivity

        MaterialDialog(mainActivity).show {
            title(text = getString(R.string.help_title))
            message(text = getString(R.string.help_content).trimIndent())
            positiveButton(text = getString(R.string.marker_dialog_cancelbtn_title)) {
                it.dismiss()
            }
        }
    }

    private fun setMapView() {
        mapView?.run {
            // 맵 관련 이벤트
    //            setMapViewEventListener(this@MapFragment)

            // 마커 관련 이벤트
            setPOIItemEventListener(this@MapFragment)

            // 지도화면일 경우 viewpager swipe를 막는다(지도의 가로,세로도 움직여야 하므로)
            setOnTouchListener { view, motionEvent ->
                view.parent.requestDisallowInterceptTouchEvent(true)

                search_view.setQuery("", false)
                search_view.clearFocus()

                (activity as MainActivity).hideKeyboard()
                false
            }

            // 내 위치 좌표를 가져오기 위한 트랙킹모드 활성화 및 리스너 추가
            currentLocationTrackingMode =
                MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeadingWithoutMapMoving
            setCurrentLocationEventListener(this@MapFragment)
        }

        (activity as MainActivity).showLoading()
    }

    fun setLocationAndMoveMap(
        latitude: Double,
        longitude: Double,
        addressName: String,
        placeName: String = ""
    ) {
        // 위치를 세팅하고 지도를 이동시킨다
        curlocation = MapPoint.mapPointWithGeoCoord(latitude, longitude)
        floatingAddress = addressName
        this.placeName = placeName
        selectedMyLocation = false

        moveMap(addressName)
    }

    fun moveMap(addressName: String) {
        val mainActivity = (activity as MainActivity)
        mainActivity.showLoading()

        if (!selectedMyLocation) {
            // 내 위치로 이동하는게 아니면 트랙킹모드를 끈다
            mapView?.currentLocationTrackingMode =
                MapView.CurrentLocationTrackingMode.TrackingModeOff
            // 내 위치에 표시되어있는 파란색 마커 제거
            mapView?.setShowCurrentLocationMarker(false)
        }

        val location = if (selectedMyLocation) mylocation else curlocation

        location?.let {
            // 어차피 마커는 하나이므로 all로 제거한다
            mapView?.removeAllPOIItems()

            // 해당 위치에 마커를 생성한다
            val marker = MapPOIItem().apply {
                itemName = if(placeName.isEmpty()) addressName else placeName
                mapPoint = it
                markerType = MapPOIItem.MarkerType.BluePin
                selectedMarkerType = MapPOIItem.MarkerType.RedPin
            }

            // 마커를 추가하고 해당 위치로 지도를 이동한다
            mapView?.addPOIItem(marker)
            mapView?.selectPOIItem(marker, false)
            mapView?.setMapCenterPoint(it, true)
            mapView?.requestFocus()
        }

        onceMyLocationMove = true

        mainActivity.hideLoading()
    }

    override fun onCalloutBalloonOfPOIItemTouched(p0: MapView?, p1: MapPOIItem?) {
    }

    override fun onCalloutBalloonOfPOIItemTouched(
        p0: MapView?,
        p1: MapPOIItem?,
        p2: MapPOIItem.CalloutBalloonButtonType?
    ) {
    }

    override fun onDraggablePOIItemMoved(p0: MapView?, p1: MapPOIItem?, p2: MapPoint?) {
    }

    override fun onPOIItemSelected(p0: MapView?, p1: MapPOIItem?) {
        // 마커 터치시 상세 다이얼로그 띄우기
        val activity = (activity as MainActivity)

        activity.showLoading()

        val latitude =
            if (selectedMyLocation) mylocation?.mapPointGeoCoord?.latitude else curlocation?.mapPointGeoCoord?.latitude
        val longitude =
            if (selectedMyLocation) mylocation?.mapPointGeoCoord?.longitude else curlocation?.mapPointGeoCoord?.longitude

        loge("마커 좌표: latitude: ${p1?.mapPoint?.mapPointGeoCoord?.latitude!!}, longitude: ${p1.mapPoint.mapPointGeoCoord.longitude}")
        loge("실제 저장 좌표: latitude: $latitude, longitude: $longitude")

        mapViewModel.getCoordInfo(
            latitude!!,
            longitude!!,
            object : IResult<DocumentsItem?> {
                override fun success(result: DocumentsItem?) {
                    MaterialDialog(activity).show {
                        title(text = if(placeName.isEmpty()) result?.address?.addressName else placeName)
                        message(text = result?.convertDialogContent()) {
                            messageTextView.gravity = Gravity.CENTER
                        }
                        positiveButton(text = getString(R.string.marker_dialog_sharebtn_title)) {
                            sendCustomTemplate(
                                activity,
                                result?.address?.addressName!!,
                                latitude,
                                longitude
                            )

                            it.dismiss()
                        }
                        negativeButton(text = getString(R.string.marker_dialog_cancelbtn_title)) {

                            it.dismiss()
                        }
                    }

                    activity.hideLoading()
                }

                override fun fail(msg: String?) {
                    activity.toast(msg ?: getString(R.string.coord2Address_fail))
                    activity.hideLoading()
                }
            })
    }

    override fun onCurrentLocationUpdateFailed(p0: MapView?) {
    }

    override fun onCurrentLocationUpdate(p0: MapView?, p1: MapPoint?, p2: Float) {
        // 내 위치 트랙킹모드가 활성화되면 매번 호출된다.
        // 이 앱에선 한번만 호출하면되므로 boolean 타입으로 막는다.
        if (onceMyLocationMove)
            return

        mylocation = p1
        selectedMyLocation = true
        moveMap(getString(R.string.mylocation_title))

        (activity as MainActivity).hideLoading()
    }

    override fun onCurrentLocationUpdateCancelled(p0: MapView?) {
    }

    override fun onCurrentLocationDeviceHeadingUpdate(p0: MapView?, p1: Float) {
    }

    override fun onDestroyView() {
        super.onDestroyView()
        mapParent.removeAllViews()
    }
}
