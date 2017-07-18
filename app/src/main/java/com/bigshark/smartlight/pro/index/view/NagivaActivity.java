package com.bigshark.smartlight.pro.index.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.navi.AMapNavi;
import com.amap.api.navi.AMapNaviListener;
import com.amap.api.navi.AMapNaviView;
import com.amap.api.navi.AMapNaviViewListener;
import com.amap.api.navi.enums.NaviType;
import com.amap.api.navi.model.AMapLaneInfo;
import com.amap.api.navi.model.AMapNaviCameraInfo;
import com.amap.api.navi.model.AMapNaviCross;
import com.amap.api.navi.model.AMapNaviInfo;
import com.amap.api.navi.model.AMapNaviLocation;
import com.amap.api.navi.model.AMapNaviTrafficFacilityInfo;
import com.amap.api.navi.model.AMapServiceAreaInfo;
import com.amap.api.navi.model.AimLessModeCongestionInfo;
import com.amap.api.navi.model.AimLessModeStat;
import com.amap.api.navi.model.NaviInfo;
import com.amap.api.navi.model.NaviLatLng;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Inputtips;
import com.amap.api.services.help.InputtipsQuery;
import com.amap.api.services.help.Tip;
import com.autonavi.tbt.TrafficFacilityInfo;
import com.bigshark.smartlight.R;
import com.bigshark.smartlight.mvp.presenter.impl.MVPBasePresenter;
import com.bigshark.smartlight.pro.base.view.BaseActivity;
import com.bigshark.smartlight.utils.SupportMultipleScreensUtil;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NagivaActivity extends BaseActivity implements AMapNaviViewListener,Inputtips.InputtipsListener{

    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.btn_sure)
    Button btnSure;
    @BindView(R.id.list)
    RecyclerView list;
    @BindView(R.id.navi_view)
    AMapNaviView naviView;
    @BindView(R.id.rootview)
    View rootView;

    AMapNavi aMapNavi;//骑行路径规划

    private boolean isCanNavi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nagiva);
        ButterKnife.bind(this);
        SupportMultipleScreensUtil.scale(rootView);
        naviView.setAMapNaviViewListener(this);
        aMapNavi =  AMapNavi.getInstance(getApplicationContext());
        aMapNavi.addAMapNaviListener(new AMapNaviListener() {
            @Override
            public void onInitNaviFailure() {
                showMsg("骑格导航问题，请稍后再试");
            }

            @Override
            public void onInitNaviSuccess() {

            }

            @Override
            public void onStartNavi(int i) {

            }

            @Override
            public void onTrafficStatusUpdate() {

            }

            @Override
            public void onLocationChange(AMapNaviLocation aMapNaviLocation) {

            }

            @Override
            public void onGetNavigationText(int i, String s) {

            }

            @Override
            public void onEndEmulatorNavi() {

            }

            @Override
            public void onArriveDestination() {

            }

            @Override
            public void onCalculateRouteFailure(int i) {
                aMapNavi.startNavi(NaviType.GPS);
            }

            @Override
            public void onReCalculateRouteForYaw() {

            }

            @Override
            public void onReCalculateRouteForTrafficJam() {

            }

            @Override
            public void onArrivedWayPoint(int i) {

            }

            @Override
            public void onGpsOpenStatus(boolean b) {

            }

            @Override
            public void onNaviInfoUpdate(NaviInfo naviInfo) {

            }

            @Override
            public void onNaviInfoUpdated(AMapNaviInfo aMapNaviInfo) {

            }

            @Override
            public void updateCameraInfo(AMapNaviCameraInfo[] aMapNaviCameraInfos) {

            }

            @Override
            public void onServiceAreaUpdate(AMapServiceAreaInfo[] aMapServiceAreaInfos) {

            }

            @Override
            public void showCross(AMapNaviCross aMapNaviCross) {

            }

            @Override
            public void hideCross() {

            }

            @Override
            public void showLaneInfo(AMapLaneInfo[] aMapLaneInfos, byte[] bytes, byte[] bytes1) {

            }

            @Override
            public void hideLaneInfo() {

            }

            @Override
            public void onCalculateRouteSuccess(int[] ints) {

            }

            @Override
            public void notifyParallelRoad(int i) {

            }

            @Override
            public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo aMapNaviTrafficFacilityInfo) {

            }

            @Override
            public void OnUpdateTrafficFacility(AMapNaviTrafficFacilityInfo[] aMapNaviTrafficFacilityInfos) {

            }

            @Override
            public void OnUpdateTrafficFacility(TrafficFacilityInfo trafficFacilityInfo) {

            }

            @Override
            public void updateAimlessModeStatistics(AimLessModeStat aimLessModeStat) {

            }

            @Override
            public void updateAimlessModeCongestionInfo(AimLessModeCongestionInfo aimLessModeCongestionInfo) {

            }

            @Override
            public void onPlayRing(int i) {

            }
        });
        naviView.onCreate(savedInstanceState);
        MyLocationStyle myLocationStyle;
        myLocationStyle = new MyLocationStyle();//初始化定位蓝点样式类myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATION_ROTATE);//连续定位、且将视角移动到地图中心点，定位点依照设备方向旋转，并且会跟随设备移动。（1秒1次定位）如果不设置myLocationType，默认也会执行此种模式。
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        naviView.getMap().setMyLocationStyle(myLocationStyle);//设置定位蓝点的Style
        //aMap.getUiSettings().setMyLocationButtonEnabled(true);设置默认定位按钮是否显示，非必需设置。
        naviView.getMap().setMyLocationEnabled(true);// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。

        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(!etSearch.getText().toString().equals("")){
                    //第二个参数传入null或者“”代表在全国进行检索，否则按照传入的city进行检索
                    InputtipsQuery inputquery = new InputtipsQuery(etSearch.getText().toString(), currentCity);
                    inputquery.setCityLimit(true);//限制在当前城市
                    Inputtips inputTips = new Inputtips(NagivaActivity.this, inputquery);
                    inputTips.setInputtipsListener(NagivaActivity.this);
                    inputTips.requestInputtipsAsyn();
                }
            }
        });

        this.list.setLayoutManager(new LinearLayoutManager(this));
        this.list.addItemDecoration(new HorizontalDividerItemDecoration.Builder(this).size(20).colorResId(R.color.tongming).build());


        AMapLocationClient mlocationClient = new AMapLocationClient(getApplicationContext());
        AMapLocationClientOption  mLocationOption = new AMapLocationClientOption();
        mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
        mlocationClient.setLocationOption(mLocationOption);
        mLocationOption.setSensorEnable(true);
        mlocationClient.setLocationListener(new AMapLocationListener() {
            @Override
            public void onLocationChanged(AMapLocation aMapLocation) {
                currentCity = aMapLocation.getCity();
                fromLatLng = new LatLng(aMapLocation.getLatitude(),aMapLocation.getLongitude());
            }
        });

        mlocationClient.startLocation();
        adapter = new NagiAdapter(this);
        adapter.setOnPoiClickListner(new NagivaActivity.OnPoiClickListner(){

            @Override
            public void onPoiClickListner(int postion) {
                LatLonPoint point = adapter.getTips().get(postion).getPoint();
                aMapNavi.calculateRideRoute(new NaviLatLng(fromLatLng.latitude,fromLatLng.longitude), new NaviLatLng(point.getLatitude(),point.getLongitude()));
            }
        });
        this.list.setAdapter(adapter);
    }

    private NagiAdapter adapter;

    private LatLng fromLatLng;
    private String currentCity;

    @Override
    protected void onResume() {
        super.onResume();
        naviView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        naviView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        naviView.onDestroy();
    }


    public  static  void openNagivaActivity(Context context){
        context.startActivity(new Intent(context,NagivaActivity.class));
    }

    @Override
    public MVPBasePresenter bindPresneter() {
        return null;
    }

    @Override
    public void onNaviSetting() {

    }

    @Override
    public void onNaviCancel() {

    }

    @Override
    public boolean onNaviBackClick() {
        return false;
    }

    @Override
    public void onNaviMapMode(int i) {

    }

    @Override
    public void onNaviTurnClick() {

    }

    @Override
    public void onNextRoadClick() {

    }

    @Override
    public void onScanViewButtonClick() {

    }

    @Override
    public void onLockMap(boolean b) {

    }

    @Override
    public void onNaviViewLoaded() {

    }

    @Override
    public void onGetInputtips(List<Tip> list, int i) {
        if(list != null&&list.size() != 0){
            this.list.setVisibility(View.VISIBLE);

        }
    }


    class NagiAdapter extends RecyclerView.Adapter<NagivaActivity.NagiVaViewHold>{
        private Context context;
        private List<Tip> dataTips = new ArrayList<>();
        public NagiAdapter(Context context){
            this.context = context;
        }

        public List<Tip> getTips(){
            return dataTips;
        }

        public void setData(List<Tip> dataTips ){
            this.dataTips = dataTips;
            notifyDataSetChanged();
        }
        private OnPoiClickListner mOnPoiClickListner;
        public void setOnPoiClickListner(OnPoiClickListner  onPoiClickListner){
            this.mOnPoiClickListner = onPoiClickListner;
        }

        @Override
        public NagiVaViewHold onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_pio,parent,false);
            SupportMultipleScreensUtil.scale(view);
            NagiVaViewHold vaViewHold = new NagiVaViewHold(view,mOnPoiClickListner);
            return vaViewHold;
        }

        @Override
        public void onBindViewHolder(NagiVaViewHold holder, int position) {
            holder.textView.setText(dataTips.get(position).getName());
        }

        @Override
        public int getItemCount() {
            return dataTips.size();
        }
    }

    class  NagiVaViewHold extends  RecyclerView.ViewHolder{
        TextView textView;

        public NagiVaViewHold(View itemView, final OnPoiClickListner listner) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.tv_pio);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listner.onPoiClickListner(getLayoutPosition());
                }
            });
        }
    }

    interface OnPoiClickListner{
        void onPoiClickListner(int postion);
    }
}
