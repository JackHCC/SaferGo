/**
 * 构造函数新建location对象
 * 每次地址更改的时候都要调用每个变量的set方法
 */
package xin.skingorz.isafety;

public class Location {
    private String email = "eqatfqsa";
    private double Latitude = 12;    //纬度
    private double Longitude = 132413;   //经度
    private float Accuracy = 311;     //精度信息
    private double Altitude = 312;    //海拔
    private float Speed = 132;        //速度
    private float Bearing = 1;      //方向角
    private String BuildingId ="132";  //室内定位建筑物id
    private String Floor;       //室内定位楼层
    private String Address;     //地址描述
    private String Country;     //国家
    private String Provice;     //省份
    private String City;        //城市
    private String District;    //城区
    private String Street;      //街道
    private String StreetNum;   //街道门牌号
    private String CityCode;    //城市编码
    private String AdCode;      //区域编码
    private String PoiName;     //当前位置POI名称
    private String AoiName;     //当前位置AOI名称

    public Location(){
        User user = (User) GlobalVariable.cache.getDataFromMemotyCache("user");
        this.email = user.getEmail();
    }

    public void setLatitude(double latitude) {
        Latitude = latitude;
    }

    public void setLongitude(double longitude) {
        Longitude = longitude;
    }

    public void setAccuracy(float accuracy) {
        Accuracy = accuracy;
    }

    public void setAltitude(double altitude) {
        Altitude = altitude;
    }

    public void setSpeed(float speed) {
        Speed = speed;
    }

    public void setBearing(float bearing) {
        Bearing = bearing;
    }

    public void setBuildingId(String buildingId) {
        BuildingId = buildingId;
    }

    public void setFloor(String floor) {
        Floor = floor;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public void setProvice(String provice) {
        Provice = provice;
    }

    public void setCity(String city) {
        City = city;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public void setStreet(String street) {
        Street = street;
    }

    public void setStreetNum(String streetNum) {
        StreetNum = streetNum;
    }

    public void setCityCode(String cityCode) {
        CityCode = cityCode;
    }

    public void setAdCode(String adCode) {
        AdCode = adCode;
    }

    public void setPoiName(String poiName) {
        PoiName = poiName;
    }

    public void setAoiName(String aoiName) {
        AoiName = aoiName;
    }

    public double getLatitude() {
        return Latitude;
    }

    public double getLongitude() {
        return Longitude;
    }

    public float getAccuracy() {
        return Accuracy;
    }

    public double getAltitude() {
        return Altitude;
    }

    public float getSpeed() {
        return Speed;
    }

    public float getBearing() {
        return Bearing;
    }

    public String getBuildingId() {
        return BuildingId;
    }

    public String getFloor() {
        return Floor;
    }

    public String getAddress() {
        return Address;
    }

    public String getCountry() {
        return Country;
    }

    public String getProvice() {
        return Provice;
    }

    public String getCity() {
        return City;
    }

    public String getDistrict() {
        return District;
    }

    public String getStreet() {
        return Street;
    }

    public String getStreetNum() {
        return StreetNum;
    }

    public String getCityCode() {
        return CityCode;
    }

    public String getAdCode() {
        return AdCode;
    }

    public String getPoiName() {
        return PoiName;
    }

    public String getAoiName() {
        return AoiName;
    }
}
