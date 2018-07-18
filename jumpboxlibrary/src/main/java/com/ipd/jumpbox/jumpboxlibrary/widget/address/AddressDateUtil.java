package com.ipd.jumpbox.jumpboxlibrary.widget.address;

import com.ipd.jumpbox.jumpboxlibrary.widget.address.model1.Data;
import com.ipd.jumpbox.jumpboxlibrary.widget.address.model1.Data2;
import com.ipd.jumpbox.jumpboxlibrary.widget.address.model1.Data3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/6/3 0003.
 */
public class AddressDateUtil {
    public List<Data> list;

    public AddressDateUtil() {
    }

    /**
     * 所有省
     */
    public String[] mProvinceDatas;
    /**
     * key - 省 value - 市
     */
    public Map<String, String[]> mCitisDatasMap = new HashMap<String, String[]>();
    /**
     * key - 市 values - 区
     */
    public Map<String, String[]> mDistrictDatasMap = new HashMap<String, String[]>();

    /**
     * key - 区 values - 邮编
     */
    public Map<String, String> mZipcodeDatasMap = new HashMap<String, String>();

    /**
     * 当前省的名称
     */
    public String mCurrentProviceName;
    /**
     * 当前市的名称
     */
    public String mCurrentCityName;
    /**
     * 当前区的名称
     */
    public String mCurrentDistrictName = "";

    /**
     * 当前区的邮政编码
     */
    public String mCurrentZipCode = "";

    public void initDatas() {

        //*/ 初始化默认选中的省、市、区
        if (list != null && !list.isEmpty()) {
            mCurrentProviceName = list.get(0).cndqmc;
            List<Data2> cityList = list.get(0).data2;
            if (cityList != null && !cityList.isEmpty()) {
                mCurrentCityName = cityList.get(0).cndqmc;
                List<Data3> districtList = cityList.get(0).data3;
                mCurrentDistrictName = districtList.get(0).cndqmc;
                mCurrentZipCode = districtList.get(0).id;
            }

            //*/
            mProvinceDatas = new String[list.size()];
            for (int i = 0; i < list.size(); i++) {
                // 遍历所有省的数据
                mProvinceDatas[i] = list.get(i).cndqmc;
//                List<Data2> cityList = list.get(i).data2;
                String[] cityNames = new String[cityList.size()];
                for (int j = 0; j < cityList.size(); j++) {
                    // 遍历省下面的所有市的数据
                    cityNames[j] = cityList.get(j).cndqmc;
                    List<Data3> districtList = cityList.get(j).data3;
                    String[] distrinctNameArray = new String[districtList.size()];
                    Data3[] distrinctArray = new Data3[districtList.size()];
                    for (int k = 0; k < districtList.size(); k++) {
                        // 遍历市下面所有区/县的数据
                        Data3 districtModel = new Data3(districtList.get(k).cndqmc, districtList.get(k).id);
                        // 区/县对于的邮编，保存到mZipcodeDatasMap
                        mZipcodeDatasMap.put(districtList.get(k).cndqmc, districtList.get(k).id);
                        distrinctArray[k] = districtModel;
                        distrinctNameArray[k] = districtModel.cndqmc;
                    }
                    // 市-区/县的数据，保存到mDistrictDatasMap
                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
                }
                // 省-市的数据，保存到mCitisDatasMap
                mCitisDatasMap.put(list.get(i).cndqmc, cityNames);
            }
        }
    }

//    /**
//     * 解析省市区的XML数据
//     */
//
//    public void initProvinceDatas() {
//        List<ProvinceModel> provinceList = null;
//        AssetManager asset = context.getAssets();
//
//
//        try {
//
//            InputStream input = asset.open("province_data.xml");
//            // 创建一个解析xml的工厂对象
//            SAXParserFactory spf = SAXParserFactory.newInstance();
//            // 解析xml
//            SAXParser parser = spf.newSAXParser();
//            XmlParserHandler handler = new XmlParserHandler();
//            parser.parse(input, handler);
//            input.close();
//            // 获取解析出来的数据
//            provinceList = handler.getDataList();
//            //*/ 初始化默认选中的省、市、区
//            if (provinceList != null && !provinceList.isEmpty()) {
//                mCurrentProviceName = provinceList.get(0).getName();
//                List<CityModel> cityList = provinceList.get(0).getCityList();
//                if (cityList != null && !cityList.isEmpty()) {
//                    mCurrentCityName = cityList.get(0).getName();
//                    List<DistrictModel> districtList = cityList.get(0).getDistrictList();
//                    mCurrentDistrictName = districtList.get(0).getName();
//                    mCurrentZipCode = districtList.get(0).getZipcode();
//                }
//            }
//            //*/
//            mProvinceDatas = new String[provinceList.size()];
//            for (int i = 0; i < provinceList.size(); i++) {
//                // 遍历所有省的数据
//                mProvinceDatas[i] = provinceList.get(i).getName();
//                List<CityModel> cityList = provinceList.get(i).getCityList();
//                String[] cityNames = new String[cityList.size()];
//                for (int j = 0; j < cityList.size(); j++) {
//                    // 遍历省下面的所有市的数据
//                    cityNames[j] = cityList.get(j).getName();
//                    List<DistrictModel> districtList = cityList.get(j).getDistrictList();
//                    String[] distrinctNameArray = new String[districtList.size()];
//                    DistrictModel[] distrinctArray = new DistrictModel[districtList.size()];
//                    for (int k = 0; k < districtList.size(); k++) {
//                        // 遍历市下面所有区/县的数据
//                        DistrictModel districtModel = new DistrictModel(districtList.get(k).getName(), districtList.get(k).getZipcode());
//                        // 区/县对于的邮编，保存到mZipcodeDatasMap
//                        mZipcodeDatasMap.put(districtList.get(k).getName(), districtList.get(k).getZipcode());
//                        distrinctArray[k] = districtModel;
//                        distrinctNameArray[k] = districtModel.getName();
//                    }
//                    // 市-区/县的数据，保存到mDistrictDatasMap
//                    mDistrictDatasMap.put(cityNames[j], distrinctNameArray);
//                }
//                // 省-市的数据，保存到mCitisDatasMap
//                mCitisDatasMap.put(provinceList.get(i).getName(), cityNames);
//            }
//        } catch (Throwable e) {
//            e.printStackTrace();
//        } finally {
//
//        }
//    }
}

