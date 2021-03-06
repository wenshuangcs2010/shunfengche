package com.windmillsteward.jukutech.activity.mine.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.windmillsteward.jukutech.R;
import com.windmillsteward.jukutech.activity.mine.adapter.MyCouponAdapter;
import com.windmillsteward.jukutech.activity.mine.presenter.MyCouponPresenter;
import com.windmillsteward.jukutech.base.BaseFragment;
import com.windmillsteward.jukutech.base.baseadapter.BaseQuickAdapter;
import com.windmillsteward.jukutech.bean.MyCouponBean;
import com.windmillsteward.jukutech.customview.CommonRefreshLayout;
import com.windmillsteward.jukutech.interfaces.Define;

import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;

/**
 * 描述：优惠券-已使用
 * author:cyq
 * 2018-03-05
 * Created by 2018 广州聚酷软件科技有限公司 All Right Reserved
 */

public class MyCouponAlreadyUseFragment extends BaseFragment implements MyCouponView  {

    private RecyclerView rv_content;
    private CommonRefreshLayout common_refresh;

    private int status = 0;//优惠状态【0：未使用，1：已使用，2：已过期】
    private List<MyCouponBean.ListBean> list;
    private MyCouponAdapter adapter;

    private MyCouponPresenter presenter;

    private int page,pageSize;

    public static MyCouponAlreadyUseFragment getInstance(int status) {
        MyCouponAlreadyUseFragment fragment = new MyCouponAlreadyUseFragment();
        Bundle args = new Bundle();
        args.putInt(Define.INTENT_DATA, status);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle arguments = getArguments();
        if(arguments!=null) {
            status = arguments.getInt(Define.INTENT_DATA);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflateView(R.layout.fragment_my_coupon);
        initView(view);
        initData();
        return view;
    }

    public void initData(){
        if (presenter == null){
            presenter = new MyCouponPresenter(this);
        }
        presenter.initData(getAccessToken(),1,10,status);
    }

    private void initView(View view) {
        rv_content = (RecyclerView) view.findViewById(R.id.rv_content);
        common_refresh = (CommonRefreshLayout) view.findViewById(R.id.common_refresh);

        rv_content.setLayoutManager(new LinearLayoutManager(getContext()));
        list = new ArrayList<>();
        adapter = new MyCouponAdapter(list);
        rv_content.setAdapter(adapter);
        rv_content.setHasFixedSize(true);

        adapter.setOnLoadMoreListener(new BaseQuickAdapter.RequestLoadMoreListener() {
            @Override
            public void onLoadMoreRequested() {
                if (page<pageSize) {
                    page++;
                    presenter.loadNextData(getAccessToken(),page,10,status);
                }
            }
        },rv_content);

        common_refresh.setPtrHandler(new PtrDefaultHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                presenter.initData(getAccessToken(),1,10,status);
            }
        });

        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                MyCouponBean.ListBean listBean = list.get(position);
                int coupon_status = listBean.getCoupon_status();
                if (coupon_status == 0){
                    //TODO 此处要接分享
                }
            }
        });
    }


    @Override
    public void initDataSuccess(MyCouponBean bean) {
        list.clear();
        list.addAll(bean.getList());
        page = bean.getPageNumber();
        pageSize = bean.getTotalPage();
        adapter.setNewData(list);
        adapter.notifyDataSetChanged();
        common_refresh.refreshComplete();
        checkEnd();
    }

    @Override
    public void refreshDataSuccess(MyCouponBean bean) {
        list.clear();
        list.addAll(bean.getList());
        page = bean.getPageNumber();
        pageSize = bean.getTotalPage();
        adapter.notifyDataSetChanged();
        common_refresh.refreshComplete();
        checkEnd();
    }

    @Override
    public void refreshDataFailure() {
        common_refresh.refreshComplete();
        checkEnd();
    }

    @Override
    public void loadNextDataSuccess(MyCouponBean bean) {
        list.addAll(bean.getList());
        adapter.notifyDataSetChanged();
        page = bean.getPageNumber();
        pageSize = bean.getTotalPage();
        checkEnd();
    }

    @Override
    public void loadNextDataFailure() {
        page--;
        adapter.loadMoreFail();
        checkEnd();
    }

    private void checkEnd() {
        if (page>=pageSize) {
            adapter.loadMoreEnd();
        } else {
            adapter.loadMoreComplete();
        }
    }

    @Override
    public int registStartMode() {
        return singleTask;
    }
}
