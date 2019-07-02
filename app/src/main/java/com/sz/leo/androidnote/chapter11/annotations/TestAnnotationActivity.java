package com.sz.leo.androidnote.chapter11.annotations;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.sz.leo.androidnote.R;

import java.lang.reflect.Field;

public class TestAnnotationActivity extends AppCompatActivity {

    @getViewTo(R.id.tv_name)
    private TextView tvName;
    @getViewTo(R.id.btn_get)
    private Button btnGet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_annotation);

        getAnnotationView();
    }

    /**
     * 解析注解，获取控件
     */
    private void getAnnotationView() {
        Field[] fields = getClass().getDeclaredFields();
        if (fields != null && fields.length > 0) {
            for (Field field : fields) {
                try {
                    if (field.getAnnotations() != null) {
                        if (field.isAnnotationPresent(getViewTo.class)) {
                            field.setAccessible(true);
                            getViewTo getViewTo = field.getAnnotation(getViewTo.class);
                            field.set(this, findViewById(getViewTo.value()));
                        }
                    }
                } catch (Exception ex) {
                    Log.e("TestAnnotationActivity:", ex.getMessage());
                }
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        tvName.setText("这里显示姓名");
        btnGet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(TestAnnotationActivity.this, "再点一次试试", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
