package com.alan.threefive.function.record;

import android.content.Context;
import android.os.Handler;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;

import com.alan.tfive_function.database.TFDatabaseCreator;
import com.alan.tfive_function.database.table.DailyRecord;
import com.alan.tfive_ui.recycler.group.ProgressStyle;
import com.alan.tfive_ui.recycler.group.XRecyclerView;
import com.alan.threefive.R;

import java.util.List;

/**
 * @author alan
 * function: 刷新工具
 */
public class DailyRefreshHandler {

    private XRecyclerView xRecyclerView;
    private int currentPosition = 0;
    private int totalPosition;
    private int totalNum;
    private int num = 5;
    private List<DailyRecord> dailyRecords;
    private RecordAdapter recordAdapter;
    private Context context;
    //开始加载数据的位置
    private int startPosition;
    //结束位置
    private int endPosition;
    //是否已经到结束位置
    private boolean isEnd;
    private static final String TAG = "dailyRecord";


    public DailyRefreshHandler(XRecyclerView xRecyclerView, Context context) {
        this.xRecyclerView = xRecyclerView;
        this.context = context;
        init();
    }


    public static DailyRefreshHandler create(XRecyclerView xRecyclerView, Context context) {
        return new DailyRefreshHandler(xRecyclerView, context);
    }

    /**
     * 初始化数据
     */
    private void init() {

        //查询所有的记录，获取最大条数
        totalNum = TFDatabaseCreator.with().withTable(DailyRecord.class)
                .applySearchAsList().size();
        if (totalNum != 0) {
            startPosition = currentPosition;
            if ((currentPosition + 1) * 5 > totalNum) {
                endPosition = totalNum;
                isEnd = true;
            } else {
                endPosition = (currentPosition + 1) * num;
            }
            dailyRecords = TFDatabaseCreator.with().withTable(DailyRecord.class).applySearchAsListBy(startPosition, endPosition);
            recordAdapter = new RecordAdapter(context, dailyRecords, R.layout.item_dailly_record);

            GridLayoutManager layoutManager = new GridLayoutManager(context,1);
            xRecyclerView.setLayoutManager(layoutManager);
            xRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
            xRecyclerView.setLoadingMoreProgressStyle(ProgressStyle.BallRotate);
            xRecyclerView.setAdapter(recordAdapter);

            xRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
                @Override
                public void onRefresh() {
                    onRefreshExecute();

                }

                @Override
                public void onLoadMore() {
                    onLoadMoreExecute();
                }
            });
        }

    }


    /**
     * 刷新
     */
    private void onRefreshExecute(){
        dailyRecords = TFDatabaseCreator.with().withTable(DailyRecord.class).applySearchAsListBy(0, endPosition);
        recordAdapter.notifyDataSetChanged();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                xRecyclerView.refreshComplete();
            }
        },2000);
    }

    /**
     * 加载更多
     */
    private void onLoadMoreExecute(){

        if (isEnd){
            Toast.makeText(context,"已经没有数据了",Toast.LENGTH_LONG).show();
            xRecyclerView.setNoMore(true);
        }else {
            if (endPosition+num>totalNum){
                isEnd = true;
                startPosition = endPosition;
                endPosition = totalNum;
            }
            dailyRecords.addAll(TFDatabaseCreator.with().withTable(DailyRecord.class).applySearchAsListBy(startPosition, endPosition));
            recordAdapter.notifyDataSetChanged();
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                xRecyclerView.loadMoreComplete();
            }
        },2000);
    }


}
