package com.sz.leo.androidnote.chapter07.sqlite;

import android.app.admin.DnsEvent;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;

/**
 * @author：leo
 * @date：2019/6/4
 * @email：lei.lu@e-at.com
 */
public class OrderDao {
    private static final String TAG = "OrderDao";
    private Context mContext;
    private OrderDBHelper mOrderDBHelper;
    private final String[] ORDER_COLUMNS = new String[]{"Id", "CustomName", "OrderPrice", "Country"};

    public OrderDao(Context context) {
        this.mContext = context;
        this.mOrderDBHelper = new OrderDBHelper(context);
    }

    public boolean isDataExist() {
        int count = 0;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = mOrderDBHelper.getReadableDatabase();
            cursor = db.query(OrderDBHelper.TABLE_NAME, new String[]{"COUNT(Id)"},
                    null, null, null, null, null);
            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
            if (count > 0) {
                return true;
            }
        } catch (Exception ex) {
            Log.e(TAG, "", ex);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return false;
    }

    /**
     * 初始化部分数据
     */
    public void initTable() {
        SQLiteDatabase db = null;
        try {
            db = mOrderDBHelper.getWritableDatabase();
            db.beginTransaction();

            db.execSQL("insert into " + OrderDBHelper.TABLE_NAME + " (Id, CustomName, OrderPrice, Country) values (1, 'Arc', 100, 'China')");
            db.execSQL("insert into " + OrderDBHelper.TABLE_NAME + " (Id, CustomName, OrderPrice, Country) values (2, 'Bor', 200, 'USA')");
            db.execSQL("insert into " + OrderDBHelper.TABLE_NAME + " (Id, CustomName, OrderPrice, Country) values (3, 'Cut', 500, 'Japan')");
            db.execSQL("insert into " + OrderDBHelper.TABLE_NAME + " (Id, CustomName, OrderPrice, Country) values (4, 'Bor', 300, 'USA')");
            db.execSQL("insert into " + OrderDBHelper.TABLE_NAME + " (Id, CustomName, OrderPrice, Country) values (5, 'Arc', 600, 'China')");
            db.execSQL("insert into " + OrderDBHelper.TABLE_NAME + " (Id, CustomName, OrderPrice, Country) values (6, 'Doom', 200, 'China')");

            db.setTransactionSuccessful();
        } catch (Exception ex) {
            Log.e(TAG, "", ex);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    /**
     * 执行指定sql
     *
     * @param sql
     */
    public void execSQL(String sql) {
        SQLiteDatabase db = null;
        try {
            if (sql.contains("select")) {
                Toast.makeText(mContext, "不可执行的sql语句", Toast.LENGTH_SHORT).show();
            } else if (sql.contains("insert") || sql.contains("update") || sql.contains("delete")) {
                db = mOrderDBHelper.getWritableDatabase();
                db.beginTransaction();
                db.execSQL(sql);
                db.setTransactionSuccessful();
                Toast.makeText(mContext, "sql执行成功", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {
            Toast.makeText(mContext, "sql执行出错", Toast.LENGTH_SHORT).show();
            Log.e(TAG, "", ex);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
    }

    /**
     * 查询所以数据
     *
     * @return
     */
    public List<Order> getAllData() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = mOrderDBHelper.getReadableDatabase();
            cursor = db.query(OrderDBHelper.TABLE_NAME, ORDER_COLUMNS, null, null, null, null, null);
            if (cursor.getCount() > 0) {
                List<Order> list = new ArrayList<>(cursor.getCount());
                while (cursor.moveToNext()) {
                    list.add(parseOrder(cursor));
                }
                return list;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
    }

    /**
     * 插入一条数据
     *
     * @return
     */
    public boolean insertData() {
        SQLiteDatabase db = null;
        try {
            db = mOrderDBHelper.getWritableDatabase();
            db.beginTransaction();
            // insert into Orders(Id, CustomName, OrderPrice, Country) values (7, "Jne", 700, "China");
            ContentValues contentValues = new ContentValues();
            contentValues.put("Id", 7);
            contentValues.put("CustomName", "Jne");
            contentValues.put("OrderPrice", 700);
            contentValues.put("Country", "China");
            db.insertOrThrow(OrderDBHelper.TABLE_NAME, null, contentValues);

            db.setTransactionSuccessful();
            return true;
        } catch (SQLiteConstraintException e) {
            Toast.makeText(mContext, "主键重复", Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }


    /**
     * 删除数据
     * 此处模拟删除id为7的数据
     *
     * @return
     */
    public boolean deleteData() {
        SQLiteDatabase db = null;
        try {
            db = mOrderDBHelper.getWritableDatabase();
            db.beginTransaction();
            db.delete(OrderDBHelper.TABLE_NAME, "ID = ?", new String[]{String.valueOf(7)});
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    /**
     * 更新操作
     * 此处模拟将id为6的订单价格改为800
     *
     * @return
     */
    public boolean updateOrder() {
        SQLiteDatabase db = null;
        try {
            db = mOrderDBHelper.getWritableDatabase();
            db.beginTransaction();
            ContentValues contentValues = new ContentValues();
            contentValues.put("OrderPrice", 800);
            db.update(OrderDBHelper.TABLE_NAME, contentValues, "Id = ?",
                    new String[]{String.valueOf(6)});
            db.setTransactionSuccessful();
            return true;
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (db != null) {
                db.endTransaction();
                db.close();
            }
        }
        return false;
    }

    /**
     * 查询数据
     * 此处模拟查询CustomName为Bor的订单
     *
     * @return
     */
    public List<Order> getBorOrder() {
        SQLiteDatabase db = null;
        Cursor cursor = null;

        try {
            db = mOrderDBHelper.getReadableDatabase();
            // select * from Orders where CustomName = 'Bor'
            cursor = db.query(OrderDBHelper.TABLE_NAME,
                    ORDER_COLUMNS,
                    "CustomName = ?",
                    new String[]{"Bor"},
                    null, null, null);

            if (cursor.getCount() > 0) {
                List<Order> orderList = new ArrayList<Order>(cursor.getCount());
                while (cursor.moveToNext()) {
                    Order order = parseOrder(cursor);
                    orderList.add(order);
                }
                return orderList;
            }
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }

        return null;
    }

    /**
     * 查询数据
     * 此处模拟查找country为china的订单数量
     *
     * @return
     */
    public int getChinaCount() {
        int count = 0;
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = mOrderDBHelper.getReadableDatabase();
            // select count(Id) from Orders where Country = 'China'
            cursor = db.query(OrderDBHelper.TABLE_NAME,
                    new String[]{"COUNT(Id)"},
                    "Country = ?",
                    new String[]{"China"},
                    null, null, null);

            if (cursor.moveToFirst()) {
                count = cursor.getInt(0);
            }
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return count;
    }

    /**
     * 查询OrderPrice最大的订单
     *
     * @return
     */
    public Order getMaxOrderPrice() {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        try {
            db = mOrderDBHelper.getReadableDatabase();
            // select Id, CustomName, Max(OrderPrice) as OrderPrice, Country from Orders
            cursor = db.query(OrderDBHelper.TABLE_NAME, new String[]{"Id", "CustomName", "Max(OrderPrice) as OrderPrice", "Country"},
                    null, null, null, null, null);
            if (cursor.getCount() > 0) {
                if (cursor.moveToFirst()) {
                    return parseOrder(cursor);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "", e);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return null;
    }

    private Order parseOrder(Cursor cursor) {
        Order o = new Order();
        o.setId(cursor.getInt(cursor.getColumnIndex("Id")));
        o.setCustomName(cursor.getString(cursor.getColumnIndex("CustomName")));
        o.setOrderPrice(cursor.getInt(cursor.getColumnIndex("OrderPrice")));
        o.setCountry(cursor.getString(cursor.getColumnIndex("Country")));
        return o;
    }
}
