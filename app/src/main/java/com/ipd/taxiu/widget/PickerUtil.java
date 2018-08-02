package com.ipd.taxiu.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.CommentReplyBean;
import com.ipd.taxiu.bean.JsonBean;

import org.jetbrains.annotations.Nullable;
import org.json.JSONArray;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * Created by Miss on 2018/7/26
 * 选择器
 */
public class PickerUtil {

    private TimePickerView pvCustomLunar;
    private OptionsPickerView pvCustomOptions,bankCardOption, returnMoneyOption,pvOptions;

    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    /**
     * 选择日期
     */
    public void initLunarPicker(final Context context, final TextView textView, final String title) {
        final Calendar selectedDate = Calendar.getInstance();//系统当前时间
        Calendar startDate = Calendar.getInstance();
        startDate.set(1900, 1, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(2100, 12, 31);
        //时间选择器 ，自定义布局
        pvCustomLunar = new TimePickerBuilder(context, new OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                String newStr = getTime(date).substring(0, getTime(date).indexOf(" "));
                textView.setText(newStr);
            }
        })
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setLayoutRes(R.layout.dialog_date, new CustomListener() {

                    @Override
                    public void customLayout(final View v) {
                        TextView tv_choose_birthday = v.findViewById(R.id.tv_choose_birthday);
                        final ImageView ivCancel = v.findViewById(R.id.iv_close);
                        TextView tvSubmit = v.findViewById(R.id.btn_submit);
                        RelativeLayout linearLayout = v.findViewById(R.id.ll_layout);
                        tv_choose_birthday.setText(title);
                        linearLayout.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.returnData();
                                pvCustomLunar.dismiss();
                            }
                        });
                        ivCancel.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomLunar.dismiss();
                            }
                        });

                    }
                })
                .setType(new boolean[]{true, true, true, false, false, false})
                .isCenterLabel(false) //是否只显示中间选中项的label文字，false则每项item全部都带有label。
                .build();
        pvCustomLunar.show();
    }

    private String getTime(Date date) {//可根据需要自行截取数据显示
        Log.d("getTime()", "choice date millis: " + date.getTime());
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return format.format(date);
    }


    /**
     * 宠物状态选项卡
     */
    public void initCustomOptionPicker(final Context context, final List<String> list, final TextView textView) {//条件选择器初始化，自定义布局
        pvCustomOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = list.get(options1);
                textView.setText(tx);
            }
        })
                .setLayoutRes(R.layout.dialog_multiple_choice, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        final TextView tvSubmit = v.findViewById(R.id.btn_submit);
                        ImageView tvClose = v.findViewById(R.id.iv_close);
                        RelativeLayout rl_item = v.findViewById(R.id.rl_item);
                        rl_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.returnData();
                                pvCustomOptions.dismiss();
                            }
                        });

                        tvClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                pvCustomOptions.dismiss();
                            }
                        });

                    }
                })
                .isDialog(false)
                .build();

        pvCustomOptions.setPicker(list);//添加数据
        pvCustomOptions.show();
    }

    /**
     * 选择银行卡
     */
    public void initBankCardOption(final Context context, final List<String> list, final TextView textView) {
        bankCardOption = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                String tx = list.get(options1);
                textView.setText(tx);
            }
        })
                .setLayoutRes(R.layout.dialog_choice_bank_card, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvSubmit = v.findViewById(R.id.btn_submit);
                        ImageView tvClose = v.findViewById(R.id.iv_close);
                        RelativeLayout rl_item = v.findViewById(R.id.rl_item);
                        rl_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bankCardOption.returnData();
                                bankCardOption.dismiss();
                            }
                        });

                        tvClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                bankCardOption.dismiss();
                            }
                        });

                    }
                })
                .isDialog(false)
                .build();

        bankCardOption.setPicker(list);//添加数据
        bankCardOption.show();
    }

    /**
     * 选择退款原因
     * @param context
     * @param list
     * @param textView
     */
    public void initRetrunMoneyOption(final Context context, final List<String> list, final TextView textView) {
        returnMoneyOption = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int option2, int options3, View v) {
                //返回的分别是三个级别的选中位置

                String tx = list.get(options1);
                textView.setText(tx);
            }
        })
                .setLayoutRes(R.layout.dialog_return_reason, new CustomListener() {
                    @Override
                    public void customLayout(View v) {
                        TextView tvSubmit = v.findViewById(R.id.btn_submit);
                        ImageView tvClose = v.findViewById(R.id.iv_close);
                        RelativeLayout rl_item = v.findViewById(R.id.rl_item);
                        rl_item.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        });
                        tvSubmit.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnMoneyOption.returnData();
                                returnMoneyOption.dismiss();
                            }
                        });

                        tvClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                returnMoneyOption.dismiss();
                            }
                        });

                    }
                })
                .isDialog(false)
                .build();

        returnMoneyOption.setPicker(list);//添加数据
        returnMoneyOption.show();
    }

    /**
     * 选择省市区
     */
    public void showPickerView(Context context, final TextView textView) {
         pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() +
                        options2Items.get(options1).get(options2) +
                        options3Items.get(options1).get(options2).get(options3);
                textView.setText(tx);
            }
        })

                 .setLayoutRes(R.layout.dialog_choice_city, new CustomListener() {
                     @Override
                     public void customLayout(View v) {
                         TextView tvSubmit = v.findViewById(R.id.btn_submit);
                         ImageView tvClose = v.findViewById(R.id.iv_close);
                         RelativeLayout rl_item = v.findViewById(R.id.rl_item);
                         rl_item.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {

                             }
                         });
                         tvSubmit.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 pvOptions.returnData();
                                 pvOptions.dismiss();
                             }
                         });

                         tvClose.setOnClickListener(new View.OnClickListener() {
                             @Override
                             public void onClick(View v) {
                                 pvOptions.dismiss();
                             }
                         });
                     }
                 })
                .build();

        pvOptions.setPicker(options1Items, options2Items, options3Items);//三级选择器
        pvOptions.show();
    }

    public void initJsonData(Context context) {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData =getJson(context, "province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i = 0; i < jsonBean.size(); i++) {//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c = 0; c < jsonBean.get(i).getCityList().size(); c++) {//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市
                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        || jsonBean.get(i).getCityList().get(c).getArea().size() == 0) {
                    City_AreaList.add("");
                } else {
                    City_AreaList.addAll(jsonBean.get(i).getCityList().get(c).getArea());
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }


    }


    private ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }

    private String getJson(Context context,String fileName) {

        StringBuilder stringBuilder = new StringBuilder();
        try {
            AssetManager assetManager = context.getAssets();
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

}
