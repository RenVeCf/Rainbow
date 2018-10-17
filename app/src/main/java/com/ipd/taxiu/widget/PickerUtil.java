package com.ipd.taxiu.widget;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.builder.OptionsPickerBuilder;
import com.bigkoo.pickerview.builder.TimePickerBuilder;
import com.bigkoo.pickerview.listener.CustomListener;
import com.bigkoo.pickerview.listener.OnOptionsSelectListener;
import com.bigkoo.pickerview.listener.OnTimeSelectListener;
import com.bigkoo.pickerview.view.OptionsPickerView;
import com.bigkoo.pickerview.view.TimePickerView;
import com.google.gson.Gson;
import com.ipd.jumpbox.jumpboxlibrary.utils.ToastCommom;
import com.ipd.taxiu.R;
import com.ipd.taxiu.bean.BankTypeListBean;
import com.ipd.taxiu.bean.ProvinceBean;

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
    private OptionsPickerView pvCustomOptions, bankCardOption, returnMoneyOption, pvOptions;

    //    private ArrayList<ProvinceBean> options1Items = new ArrayList<>();//省
    private List<ProvinceBean> options1Items = new ArrayList<>();
    private List<List<ProvinceBean>> options2Items = new ArrayList<>();//市
    private List<List<List<ProvinceBean>>> options3Items = new ArrayList<>();//区
    private static final int MSG_LOAD_DATA = 0x0001;
    private static final int MSG_LOAD_SUCCESS = 0x0002;
    private static final int MSG_LOAD_FAILED = 0x0003;

    /**
     * 选择日期
     */
    public void initLunarPicker(final Context context, final TextView textView, final String title) {
        hideSoftInput(context, textView);
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
                SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                if (newStr.compareTo(df.format(new Date())) > 0) {
                    new ToastCommom().show(context, "时间不能大于当前日期");
                    return;
                }
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
        hideSoftInput(context, textView);
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
    public void initBankCardOption(final Context context, final List<BankTypeListBean> list, final TextView textView, OnOptionsSelectListener listener) {
        hideSoftInput(context, textView);
        bankCardOption = new OptionsPickerBuilder(context, listener)
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
     *
     * @param context
     * @param list
     * @param textView
     */
    public void initRetrunMoneyOption(final Context context, final List<String> list, final TextView textView) {
        hideSoftInput(context, textView);
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
        hideSoftInput(context, textView);
        pvOptions = new OptionsPickerBuilder(context, new OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText() + " " +
                        options2Items.get(options1).get(options2).getPickerViewText() + " " +
                        options3Items.get(options1).get(options2).get(options3).getPickerViewText();
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
        String JsonData = getJson(context, "city.json");//获取assets目录下的json文件数据
        List<ProvinceBean> provinceList = parseData(JsonData);//用Gson 转成实体

        options1Items.addAll(provinceList);
        for (int i = 0; i < provinceList.size(); i++) {
            List<ProvinceBean> cityList = new ArrayList<>();//城市
            List<List<ProvinceBean>> cityAreaList = new ArrayList<>();//区域
            cityList.addAll(provinceList.get(i).RegionList);

            for (int j = 0; j < cityList.size(); j++) {
                List<ProvinceBean> areaList = provinceList.get(i).RegionList.get(j).RegionList;
                cityAreaList.add(areaList);
            }
            options2Items.add(cityList);
            options3Items.add(cityAreaList);
        }
    }


    public ArrayList<ProvinceBean> parseData(String result) {
        ArrayList<ProvinceBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                ProvinceBean entity = gson.fromJson(data.optJSONObject(i).toString(), ProvinceBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return detail;
    }


    public String getJson(Context context, String fileName) {

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

    /**
     * 隐藏软键盘
     *
     * @param context
     * @param view
     */
    private void hideSoftInput(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }

}
