//package com.box.lib.utils;
//
//
//import androidx.recyclerview.widget.RecyclerView;
//
//import java.util.List;
//
///**
// * 分页加载帮助类，默认分页逻辑写在这里
// *
// * @author Linsr 2018/6/21 下午4:50
// */
//public class PageLoadHelper {
//
//    /**
//     * 分页请求失败
//     * @param pageIndex 页码
//     * @param listener 空数据监听
//     * @return 处理后的页码
//     */
//    public static int onFailure(int pageIndex, IView listener) {
//        if (pageIndex == NetConstants.DEFAULT_PAGE_INDEX) {
//            listener.showNoData();
//        } else {
//            pageIndex--;
//        }
//        return pageIndex;
//    }
//
//
//    public static <T> int onSuccess(int pageIndex,
//                                    List<T> adapterList,
//                                    List<T> netList,
//                                    RecyclerView.Adapter adapter,
//                                    IView listener) {
//        //第一次加载
//        if (pageIndex == NetConstants.DEFAULT_PAGE_INDEX) {
//            adapterList.clear();
//            adapter.notifyDataSetChanged();
//            //如果刷新为空，则说明是空数据
//            if (netList == null || netList.size() == 0) {
//                listener.showNoData();
//                return pageIndex;
//            }
//        } else {
//            if (null == netList || netList.size() == 0) {
//                return pageIndex;
//            }
//        }
//        pageIndex++;
//        listener.hideNoData();
//        adapterList.addAll(netList);
//        adapter.notifyDataSetChanged();
//        return pageIndex;
//    }
//
//    /**
//     * 分页请求成功 (RecyclerAdapterBase)
//     * @param pageIndex 页码
//     * @param adapter 适配器
//     * @param list 网络请求回来的数据
//     * @param listener 空数据监听
//     * @return 页码
//     */
//    public static int onSuccess(int pageIndex, BaseRecyclerAdapter adapter,
//                                List list, IView listener) {
//        //第一次加载
//        if (pageIndex == NetConstants.DEFAULT_PAGE_INDEX) {
//            adapter.clear();
//            //如果刷新为空，则说明是空数据
//            if (null == list || list.size() == 0) {
//                listener.showNoData();
//                return pageIndex;
//            }
//        } else {
//            if (null == list || list.size() == 0) {
//                return pageIndex;
//            }
//        }
//        pageIndex++;
//        listener.hideNoData();
//        adapter.addData(list);
//        return pageIndex;
//    }
//
//    /**
//     * 加载完毕
//     * @param refreshLayout 刷新控件
//     */
//    public static void onCompleted(RefreshLayout refreshLayout) {
//        refreshLayout.finishRefresh();
//        refreshLayout.finishLoadMore();
//    }
//}
