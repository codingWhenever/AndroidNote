package com.sz.leo.androidnote.chapter07.sqlite;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sz.leo.androidnote.R;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author：leo
 * @date：2019/6/4
 * @email：lei.lu@e-at.com
 */
public class OrderActivity extends AppCompatActivity implements View.OnClickListener {
    private OrderDao mOrderDao;
    private ListView mListView;
    private OrderAdapter mOrderAdapter;
    private List<Order> mOrderList;
    private TextView showSQLMsg;
    private EditText etInputSQL;
    private AtomicInteger mAtomicInteger = new AtomicInteger();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        initView();

        initData();
    }

    private void initData() {
        mOrderDao = new OrderDao(this);
        if (!mOrderDao.isDataExist()) {
            mOrderDao.initTable();
        }
        mOrderList = mOrderDao.getAllData();
        if (mOrderList != null) {
            mOrderAdapter = new OrderAdapter(this, mOrderList);
            mListView.setAdapter(mOrderAdapter);
        }
    }

    private void initView() {
        Button executeButton = (Button) findViewById(R.id.executeButton);
        Button insertButton = (Button) findViewById(R.id.insertButton);
        Button deleteButton = (Button) findViewById(R.id.deleteButton);
        Button updateButton = (Button) findViewById(R.id.updateButton);
        Button query1Button = (Button) findViewById(R.id.query1Button);
        Button query2Button = (Button) findViewById(R.id.query2Button);
        Button query3Button = (Button) findViewById(R.id.query3Button);

        executeButton.setBackgroundDrawable(PressUtil.getBgDrawable(executeButton.getBackground()));
        insertButton.setBackgroundDrawable(PressUtil.getBgDrawable(insertButton.getBackground()));
        deleteButton.setBackgroundDrawable(PressUtil.getBgDrawable(deleteButton.getBackground()));
        updateButton.setBackgroundDrawable(PressUtil.getBgDrawable(updateButton.getBackground()));
        query1Button.setBackgroundDrawable(PressUtil.getBgDrawable(query1Button.getBackground()));
        query2Button.setBackgroundDrawable(PressUtil.getBgDrawable(query2Button.getBackground()));
        query3Button.setBackgroundDrawable(getBgDrawable(query3Button.getBackground()));


        executeButton.setOnClickListener(this);
        insertButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        updateButton.setOnClickListener(this);
        query1Button.setOnClickListener(this);
        query2Button.setOnClickListener(this);
        query3Button.setOnClickListener(this);

        etInputSQL = (EditText) findViewById(R.id.inputSqlMsg);
        showSQLMsg = (TextView) findViewById(R.id.showSQLMsg);
        mListView = (ListView) findViewById(R.id.showDateListView);
        mListView.addHeaderView(LayoutInflater.from(this).inflate(R.layout.item_order, null), null, false);
    }

    private void refresh() {
        if (mOrderList != null) {
            mOrderList.clear();
            mOrderList.addAll(mOrderDao.getAllData());
            mOrderAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.executeButton:
                showSQLMsg.setVisibility(View.GONE);
                String sql = etInputSQL.getText().toString();
                if (!TextUtils.isEmpty(sql)) {
                    mOrderDao.execSQL(sql);
                } else {
                    Toast.makeText(OrderActivity.this, R.string.strInputSql, Toast.LENGTH_SHORT).show();
                }
                refresh();
                break;
            case R.id.insertButton:
                showSQLMsg.setVisibility(View.VISIBLE);
                showSQLMsg.setText("新增一条数据：\n添加数据(7, \"Jne\", 700, \"China\")\ninsert into Orders(Id, CustomName, OrderPrice, Country) values (7, \"Jne\", 700, \"China\")");
                mOrderDao.insertData();
                refresh();
                break;
            case R.id.deleteButton:
                showSQLMsg.setVisibility(View.VISIBLE);
                showSQLMsg.setText("删除一条数据：\n删除Id为7的数据\ndelete from Orders where Id = 7");
                mOrderDao.deleteData();
                refresh();
                break;
            case R.id.updateButton:
                showSQLMsg.setVisibility(View.VISIBLE);
                showSQLMsg.setText("修改一条数据：\n将Id为6的数据的OrderPrice修改了800\nupdate Orders set OrderPrice = 800 where Id = 6");
                mOrderDao.updateOrder();
                refresh();
                break;
            case R.id.query1Button:
                showSQLMsg.setVisibility(View.VISIBLE);
                StringBuilder msg = new StringBuilder();
                msg.append("数据查询：\n此处将用户名为\"Bor\"的信息提取出来\nselect * from Orders where CustomName = 'Bor'");
                List<Order> borOrders = mOrderDao.getBorOrder();
                for (Order order : borOrders) {
                    msg.append("\n(" + order.id + ", " + order.customName + ", " + order.orderPrice + ", " + order.country + ")");
                }
                showSQLMsg.setText(msg);
                break;
            case R.id.query2Button:
                showSQLMsg.setVisibility(View.VISIBLE);
                int chinaCount = mOrderDao.getChinaCount();
                showSQLMsg.setText("统计查询：\n此处查询Country为China的用户总数\nselect count(Id) from Orders where Country = 'China'\ncount = " + chinaCount);

                break;
            case R.id.query3Button:
                showSQLMsg.setVisibility(View.VISIBLE);
                StringBuilder msg2 = new StringBuilder();
                msg2.append("比较查询：\n此处查询单笔数据中OrderPrice最高的\nselect Id, CustomName, Max(OrderPrice) as OrderPrice, Country from Orders");
                Order order = mOrderDao.getMaxOrderPrice();
                msg2.append("\n(" + order.id + ", " + order.customName + ", " + order.orderPrice + ", " + order.country + ")");
                showSQLMsg.setText(msg2);
                break;
            default:
                break;

        }
    }

    private int getXColor(int resId) {
        return getResources().getColor(resId);
    }

    private Drawable getXDrawable(int resId){
        return getResources().getDrawable(resId);
    }

    public AutoBuildBackgroundDrawable getBgDrawable(Drawable drawable){
        return new AutoBuildBackgroundDrawable(drawable);
    }

    public AutoBuildBackgroundDrawable getBgDrawable(int resId){
        return new AutoBuildBackgroundDrawable(getXDrawable(resId));
    }
}
