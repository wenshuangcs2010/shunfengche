package com.windmillsteward.jukutech.activity.newpage.yizhan.zhongdiangong;


import android.Manifest;
import android.content.ContentResolver;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.blankj.utilcode.util.PhoneUtils;
import com.blankj.utilcode.util.SpanUtils;
import com.google.gson.reflect.TypeToken;
import com.windmillsteward.jukutech.R;
import com.windmillsteward.jukutech.activity.home.family.activity.VideoPlayActivity;
import com.windmillsteward.jukutech.activity.newpage.model.ZhongdgInfoDetailsModel;
import com.windmillsteward.jukutech.activity.newpage.yizhan.VoiceUtils;
import com.windmillsteward.jukutech.base.BaseDialog;
import com.windmillsteward.jukutech.base.BaseInitFragment;
import com.windmillsteward.jukutech.base.BaseResultInfo;
import com.windmillsteward.jukutech.base.baseadapter.BaseQuickAdapter;
import com.windmillsteward.jukutech.base.baseadapter.BaseViewHolder;
import com.windmillsteward.jukutech.base.net.BaseNewNetModelimpl;
import com.windmillsteward.jukutech.base.net.NetUtil;
import com.windmillsteward.jukutech.customview.CircleImageView;
import com.windmillsteward.jukutech.interfaces.APIS;
import com.windmillsteward.jukutech.utils.GlideUtil;
import com.windmillsteward.jukutech.utils.MapNaviUtils;
import com.windmillsteward.jukutech.utils.MediaUtils;
import com.windmillsteward.jukutech.utils.ResUtils;
import com.windmillsteward.jukutech.utils.StringUtil;
import com.windmillsteward.jukutech.utils.view.ViewWrap;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 * 钟点工-我要找工作订单详情
 */
public class ZhongdgInfoDetailsFragment extends BaseInitFragment {
    public static final String TAG = "ZhongdgInfoDetailsFragment";
    @Bind(R.id.tv_tips)
    TextView tvTips;
    @Bind(R.id.lay_ll_tips)
    LinearLayout layLlTips;
    @Bind(R.id.iv_avatar)
    CircleImageView ivAvatar;
    @Bind(R.id.tv_name)
    TextView tvName;
    @Bind(R.id.tv_pipei)
    TextView tvPipei;
    @Bind(R.id.tv_phone)
    TextView tvPhone;
    @Bind(R.id.tv_time)
    TextView tvTime;
    @Bind(R.id.iv_phone)
    ImageView ivPhone;
    @Bind(R.id.lay_ll_header)
    LinearLayout layLlHeader;
    @Bind(R.id.tv_bianhao)
    TextView tvBianhao;
    @Bind(R.id.tv_bianti)
    TextView tvBianti;
    @Bind(R.id.tv_num)
    TextView tvNum;
    @Bind(R.id.lay_ll_shengyu)
    LinearLayout layLlShengyu;
    @Bind(R.id.tv_gongzhong)
    TextView tvGongzhong;
    @Bind(R.id.tv_diqu)
    TextView tvDiqu;
    @Bind(R.id.tv_address)
    TextView tvAddress;
    @Bind(R.id.lay_ll_address)
    LinearLayout layLlAddress;
    @Bind(R.id.tv_date)
    TextView tvDate;
    @Bind(R.id.tv_match_time)
    TextView tvMatchTime;
    @Bind(R.id.tv_xinzi_tips)
    TextView tvXinziTips;
    @Bind(R.id.tv_xinzi)
    TextView tvXinzi;
    @Bind(R.id.tv_desc)
    TextView tvDesc;
    @Bind(R.id.lay_ll_work_detail)
    LinearLayout layLlWorkDetail;
    @Bind(R.id.tv_pipei_status)
    TextView tvPipeiStatus;
    @Bind(R.id.tv_my_publish_xinxifei)
    TextView tvMyPublishXinxifei;
    @Bind(R.id.tv_my_publish_gongzhong)
    TextView tvMyPublishGongzhong;
    @Bind(R.id.tv_my_publish_diqu)
    TextView tvMyPublishDiqu;
    @Bind(R.id.tv_my_publish_gzrq)
    TextView tvMyPublishGzrq;
    @Bind(R.id.tv_my_publish_gzsj)
    TextView tvMyPublishGzsj;
    @Bind(R.id.tv_my_publish_xinzi_tips)
    TextView tvMyPublishXinziTips;
    @Bind(R.id.tv_my_publish_xinzi)
    TextView tvMyPublishXinzi;
    @Bind(R.id.tv_my_publish_desc)
    TextView tvMyPublishDesc;
    @Bind(R.id.tv_finish)
    TextView tvFinish;
    @Bind(R.id.iv_zhaopin_voice)
    ImageView ivZhaopinVoice;
    @Bind(R.id.tv_zhaopin_length)
    TextView tvZhaopinLength;
    @Bind(R.id.lay_ll_zhaopin_voice)
    LinearLayout layLlZhaopinVoice;
    @Bind(R.id.lay_ll_zhaopin_voice_content)
    LinearLayout layLlZhaopinVoiceContent;
    @Bind(R.id.zhaopin_recyclerview)
    RecyclerView zhaopinRecyclerview;
    @Bind(R.id.iv_zhaopin_video)
    ImageView ivZhaopinVideo;
    @Bind(R.id.iv_zhaopin_play)
    ImageView ivZhaopinPlay;
    @Bind(R.id.lay_rl_zhaopin_video)
    RelativeLayout layRlZhaopinVideo;
    @Bind(R.id.iv_my_publish_voice)
    ImageView ivMyPublishVoice;
    @Bind(R.id.tv_my_publish_length)
    TextView tvMyPublishLength;
    @Bind(R.id.lay_ll_my_publish_voice)
    LinearLayout layLlMyPublishVoice;
    @Bind(R.id.lay_ll_my_publish_voice_content)
    LinearLayout layLlMyPublishVoiceContent;
    @Bind(R.id.my_publish_recyclerview)
    RecyclerView myPublishRecyclerview;
    @Bind(R.id.iv_my_publish_video)
    ImageView ivMyPublishVideo;
    @Bind(R.id.iv_my_publish_play)
    ImageView ivMyPublishPlay;
    @Bind(R.id.lay_rl_my_publish_video)
    RelativeLayout layRlMyPublishVideo;
    @Bind(R.id.tv_zhaopin_pic_tips)
    TextView tvZhaopinPicTips;
    @Bind(R.id.tv_my_publish_pic_tips)
    TextView tvMyPublishPicTips;

    private ArrayList<String> myPublishListPic ;
    private ArrayList<String> zhaopinListPic;
    private PicRecyclerViewAdapter myPublishAdapter;
    private PicRecyclerViewAdapter zhaopinAdapter;
    private VoiceUtils myPublishvoiceUtils;
    private VoiceUtils zhaoPinvoiceUtils;


    public static ZhongdgInfoDetailsFragment newInstance(int hour_matching_id) {
        Bundle args = new Bundle();
        args.putInt("hour_matching_id", hour_matching_id);
        ZhongdgInfoDetailsFragment fragment = new ZhongdgInfoDetailsFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected int getContentViewId() {
        return R.layout.fragment_zhongdg_info_details;
    }

    @Override
    protected void initView(View v, Bundle savedInstanceState) {
        setMainTitle("钟点工-我要找工作详情");
        initPicAdapter();
    }

    @Override
    protected void initData() {
        addCall(new NetUtil().setUrl(APIS.URL_ZDG_YINGPIN_ORDER_DETAIL)
                .addParams("when_bell_worker_id", getArguments().getInt("hour_matching_id") + "")
                .setCallBackData(new BaseNewNetModelimpl<ZhongdgInfoDetailsModel>() {
                    @Override
                    protected void onFail(int type, String msg) {
                        showTips(msg);
                        dismiss();
                        showErrorView();
                    }

                    @Override
                    protected void onSuccess(int code, BaseResultInfo<ZhongdgInfoDetailsModel> respnse, String source) {
                        dismiss();
                        if (respnse.getData() != null) {
                            showContentView();
                            setData(respnse.getData());
                        }
                    }

                    @Override
                    protected Type getType() {
                        return new TypeToken<BaseResultInfo<ZhongdgInfoDetailsModel>>() {
                        }.getType();
                    }
                }).buildPost()
        );
    }

    private void setData(final ZhongdgInfoDetailsModel data) {
        tvFinish.setVisibility(View.GONE);
        layLlShengyu.setVisibility(View.GONE);
        int is_macth = data.getIs_macth();
        layLlTips.setVisibility(View.GONE);
        layLlHeader.setVisibility(View.GONE);
        layLlWorkDetail.setVisibility(View.GONE);
        tvMyPublishXinxifei.setText(data.getInfo_fee() + "元");//信息费
        //服务内容
        tvMyPublishGongzhong.setText(TextUtils.isEmpty(data.getService_name()) ? "" : data.getService_name());
        if (StringUtil.isAllNotEmpty(data.getWork_second_area_name(), data.getWork_third_area_name())) {//工作地区
            tvMyPublishDiqu.setText(data.getWork_second_area_name() + data.getWork_third_area_name());
        }
        tvMyPublishGzrq.setText(TextUtils.isEmpty(data.getWork_date()) ? "" : data.getWork_date() + "");//工作日期
        if (data.getWork_hour() == 0) {//工作时间
            tvMyPublishGzsj.setText("全天");
        } else {
            tvMyPublishGzsj.setText(data.getWork_hour() + "小时");
        }
        if (data.getSalary_type() == 1) {//日薪
            tvMyPublishXinzi.setText("市场定价");
        } else if (data.getSalary_type() == 2) {
            if (!TextUtils.isEmpty(data.getSalary_fee()) && !TextUtils.isEmpty(data.getEnd_salary_fee())) {
                tvMyPublishXinzi.setText(data.getSalary_fee() + "-" + data.getEnd_salary_fee() + "元/小时");
            }
        } else if (data.getSalary_type() == 3) {
            tvMyPublishXinzi.setText("面议");
        }
        tvMyPublishDesc.setText(TextUtils.isEmpty(data.getSelf_intro()) ? "" : data.getSelf_intro());
        String pic_urls = data.getImage_url();
        if (!TextUtils.isEmpty(pic_urls)) {
            List<String> mypublishPicList = Arrays.asList(pic_urls.split(","));
            myPublishListPic.clear();
            if (mypublishPicList != null) {
                if (mypublishPicList.size() == 0) {
                    tvMyPublishPicTips.setVisibility(View.GONE);
                } else {
                    tvMyPublishPicTips.setVisibility(View.VISIBLE);
                }
                myPublishListPic.addAll(mypublishPicList);
                myPublishAdapter.notifyDataSetChanged();
            }

        }else{
            tvMyPublishPicTips.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(data.getVideo_url())) {
            layRlMyPublishVideo.setVisibility(View.VISIBLE);
            ivMyPublishVideo.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (!TextUtils.isEmpty(data.getVideo_url())) {
                        Bundle bundle = new Bundle();
                        bundle.putString(VideoPlayActivity.VIDEO_URL, data.getVideo_url());
                        startAtvDonFinish(VideoPlayActivity.class, bundle);
                    }
                }
            });
            MediaUtils.getImageForVideo(data.getVideo_url(), new MediaUtils.OnLoadVideoImageListener() {
                @Override
                public void onLoadImage(File file) {
                    if (file == null) {
                        return;
                    }
                    final Uri uri = Uri.fromFile(file);
                    if (getActivity() != null) {
                        final ContentResolver contentResolver = getActivity().getContentResolver();
                        if (contentResolver != null) {
                            getActivity().runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    try {
                                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);
                                        ivMyPublishVideo.setImageBitmap(bitmap);
                                    } catch (IOException e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                        }
                    }
                }
            });
        } else {
            layRlMyPublishVideo.setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(data.getVoice_url())) {
            myPublishvoiceUtils = new VoiceUtils(ivMyPublishVoice);
            layLlMyPublishVoiceContent.setVisibility(View.VISIBLE);
            myPublishvoiceUtils.setFilePath(data.getVoice_url());
            myPublishvoiceUtils.getTime(tvMyPublishLength);
            layLlMyPublishVoice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean playing = myPublishvoiceUtils.isPlaying();
                    if (playing) {
                        myPublishvoiceUtils.stop();
                    } else {
                        myPublishvoiceUtils.play();
                    }
                }
            });
        } else {
            layLlMyPublishVoiceContent.setVisibility(View.GONE);
        }


        if (is_macth == 2) {//已匹配
            tvPipeiStatus.setTextColor(Color.parseColor("#3172F4"));
            tvPipeiStatus.setText("已匹配");
        } else if (is_macth == 3) {
            tvPipeiStatus.setTextColor(Color.parseColor("#fdbe44"));
            tvPipeiStatus.setText("匹配失败");
        } else if (is_macth == 4) {
            tvPipeiStatus.setTextColor(Color.parseColor("#FF0000"));
            tvPipeiStatus.setText("匹配成功");
            if (data.getRecruitment() != null) {
                final ZhongdgInfoDetailsModel.Recruitment workDetail = data.getRecruitment();
                layLlTips.setVisibility(View.VISIBLE);
                layLlHeader.setVisibility(View.VISIBLE);
                layLlWorkDetail.setVisibility(View.VISIBLE);
                tvBianhao.setText(data.getOrder_sn());//编号
                tvBianti.setText(workDetail.getTitle());//标题
                //服务内容
                tvGongzhong.setText(TextUtils.isEmpty(workDetail.getService_name()) ? "" : workDetail.getService_name());
                //工作地区
                if (StringUtil.isAllNotEmpty(workDetail.getWork_second_area_name(), workDetail.getWork_third_area_name())) {
                    tvDiqu.setText(workDetail.getWork_second_area_name() + workDetail.getWork_third_area_name());
                }
                tvAddress.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (StringUtil.isAllNotEmpty(workDetail.getLongitude(), workDetail.getLatitude())) {
                            MapNaviUtils.showDaoHangDialog(getActivity(), Double.parseDouble(workDetail.getLongitude()), Double.parseDouble(workDetail.getLatitude()), workDetail.getAddress());
                        }
                    }
                });
                tvAddress.setText(TextUtils.isEmpty(workDetail.getAddress()) ? "" : workDetail.getAddress());//详细地址
                tvDate.setText(TextUtils.isEmpty(workDetail.getWork_date()) ? "" : workDetail.getWork_date());//工作日期
                if (workDetail.getWork_hour() == 0) {//工作时间
                    tvMatchTime.setText("全天");
                } else {
                    tvMatchTime.setText(workDetail.getWork_hour() + "小时");
                }
                if (workDetail.getSalary_type() == 1) {//日薪
                    tvXinzi.setText("市场定价");
                } else if (workDetail.getSalary_type() == 2) {
                    if (!TextUtils.isEmpty(workDetail.getSalary_start()) && !TextUtils.isEmpty(workDetail.getSalary_end())) {
                        tvXinzi.setText(workDetail.getSalary_start() + "~" + workDetail.getSalary_end() + "元");
                    }
                } else if (workDetail.getSalary_type() == 3) {
                    tvXinzi.setText("面议");
                }
                //工作描述
                tvDesc.setText(TextUtils.isEmpty(workDetail.getJob_describe()) ? "" : workDetail.getJob_describe());
                String zhaopin_pic_urls = workDetail.getImage_url();
                if (!TextUtils.isEmpty(zhaopin_pic_urls)) {
                    List<String> zhaopinPicList = Arrays.asList(zhaopin_pic_urls.split(","));
                    zhaopinListPic.clear();
                    if (zhaopinPicList != null) {
                        if (zhaopinPicList.size() == 0) {
                            tvZhaopinPicTips.setVisibility(View.GONE);
                        } else {
                            tvZhaopinPicTips.setVisibility(View.VISIBLE);
                        }
                        zhaopinListPic.addAll(zhaopinPicList);
                        zhaopinAdapter.notifyDataSetChanged();
                    }
                }else{
                    tvZhaopinPicTips.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(workDetail.getVideo_url())) {
                    layRlZhaopinVideo.setVisibility(View.VISIBLE);
                    ivZhaopinVideo.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (!TextUtils.isEmpty(workDetail.getVideo_url())) {
                                Bundle bundle = new Bundle();
                                bundle.putString(VideoPlayActivity.VIDEO_URL, workDetail.getVideo_url());
                                startAtvDonFinish(VideoPlayActivity.class, bundle);
                            }
                        }
                    });
                    MediaUtils.getImageForVideo(workDetail.getVideo_url(), new MediaUtils.OnLoadVideoImageListener() {
                        @Override
                        public void onLoadImage(File file) {
                            if (file == null) {
                                return;
                            }
                            final Uri uri = Uri.fromFile(file);
                            if (getActivity() != null) {
                                final ContentResolver contentResolver = getActivity().getContentResolver();
                                if (contentResolver != null) {
                                    getActivity().runOnUiThread(new Runnable() {
                                        @Override
                                        public void run() {
                                            try {
                                                Bitmap bitmap = MediaStore.Images.Media.getBitmap(contentResolver, uri);
                                                ivZhaopinVideo.setImageBitmap(bitmap);
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                } else {
                    layRlZhaopinVideo.setVisibility(View.GONE);
                }
                if (!TextUtils.isEmpty(workDetail.getVoice_url())) {
                    layLlZhaopinVoiceContent.setVisibility(View.VISIBLE);
                    zhaoPinvoiceUtils = new VoiceUtils(ivZhaopinVoice);
                    zhaoPinvoiceUtils.setFilePath(workDetail.getVoice_url());
                    zhaoPinvoiceUtils.getTime(tvZhaopinLength);
                    layLlZhaopinVoice.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            boolean playing = zhaoPinvoiceUtils.isPlaying();
                            if (playing) {
                                zhaoPinvoiceUtils.stop();
                            } else {
                                zhaoPinvoiceUtils.play();
                            }
                        }
                    });
                } else {
                    layLlZhaopinVoiceContent.setVisibility(View.GONE);

                }
                //雇主信息
                tvName.setText(TextUtils.isEmpty(workDetail.getContact_person()) ? "" : workDetail.getContact_person());
                String match_value = workDetail.getMatch_value();
                GlideUtil.show(getActivity(), workDetail.getUser_avatar_url(), ivAvatar);
                tvPipei.setText(new SpanUtils().append("匹配度：")
                        .append(match_value + "%")
                        .setForegroundColor(ResUtils.getCommRed())
                        .create());
                final String contact_tel = workDetail.getContact_tel();
                final String contact_person = workDetail.getContact_person();
                tvPhone.setText(TextUtils.isEmpty(contact_tel) ? "" : contact_tel);
                ivPhone.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final BaseDialog baseDialog = new BaseDialog(getActivity());
                        baseDialog.showTwoButton("提示", "是否拨打电话?\n" + contact_person + "\n" + contact_tel
                                , "拨打", "取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (baseDialog != null) {
                                            baseDialog.dismiss();
                                        }
                                        String[] permissions = new String[]{Manifest.permission.CALL_PHONE};
                                        if (checkPermission(permissions)) {
                                            PhoneUtils.dial(contact_tel);
                                        }
                                    }
                                }, new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if (baseDialog != null) {
                                            baseDialog.dismiss();
                                        }
                                    }
                                });
                    }
                });
            }
        } else {
            tvPipeiStatus.setTextColor(Color.parseColor("#239491"));
            tvPipeiStatus.setText("正在匹配中");
        }
    }

    @Override
    protected void refreshPageData() {
        initData();
    }

    @OnClick({R.id.tv_address, R.id.tv_finish})
    public void onViewClicked(View view) {
        switch (view.getId()) {
//            case R.id.tv_address:
//                if (data != null) {
//                    MapNaviUtils.showDaoHangDialog(getActivity(), Double.parseDouble(data.getLongitude()), Double.parseDouble(data.getLatitude()), "");
//                }
//                break;
            case R.id.tv_finish:
                finishWork();
                break;
        }
    }

    //确认完成工作
    private void finishWork() {
        showDialog();
        addCall(new NetUtil().setUrl(APIS.URL_TALENT_CONFIRM_FINISH_WORK_ZDG)
                .addParams("hour_matching_id", getArguments().getInt("hour_matching_id") + "")
                .setCallBackData(new BaseNewNetModelimpl() {
                    @Override
                    protected void onFail(int type, String msg) {
                        dismiss();
                        showTips(msg);
                    }

                    @Override
                    protected void onSuccess(int code, BaseResultInfo respnse, String source) {
                        dismiss();
                        if (respnse.getCode() == 0) {
                            initData();
                            showTips("确认完成工作");
                        }
                    }

                    @Override
                    protected Type getType() {
                        return new TypeToken<BaseResultInfo>() {
                        }.getType();
                    }
                }).buildPost()
        );
    }

    public void initPicAdapter() {
        myPublishListPic = new ArrayList<>();
        myPublishAdapter = new PicRecyclerViewAdapter(myPublishListPic);
        myPublishRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 3));
        myPublishRecyclerview.setNestedScrollingEnabled(false);
        myPublishRecyclerview.setAdapter(myPublishAdapter);

        myPublishAdapter.setEnableLoadMore(false);

        myPublishAdapter.isUseEmpty(false);

        //事件监听
        myPublishAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ViewWrap.showPicActivity(getActivity(), myPublishListPic, position);
            }
        });

        zhaopinListPic = new ArrayList<>();
        zhaopinAdapter = new PicRecyclerViewAdapter(zhaopinListPic);
        zhaopinRecyclerview.setLayoutManager(new GridLayoutManager(getContext(), 4));
        zhaopinRecyclerview.setNestedScrollingEnabled(false);
        zhaopinRecyclerview.setAdapter(zhaopinAdapter);

        zhaopinAdapter.setEnableLoadMore(false);

        zhaopinAdapter.isUseEmpty(false);

        //事件监听
        zhaopinAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ViewWrap.showPicActivity(getActivity(), zhaopinListPic, position);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }


    class PicRecyclerViewAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

        public PicRecyclerViewAdapter(@Nullable List<String> data) {
            super(R.layout.item_recycler_add_pic, data);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.getView(R.id.iv_close).setVisibility(View.GONE);
            GlideUtil.show(getActivity(), item, (ImageView) helper.getView(R.id.iv_add_pic));
        }
    }
}
