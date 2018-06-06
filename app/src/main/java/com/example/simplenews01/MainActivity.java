package com.example.simplenews01;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.simplenews01.custom.CustomSimpleAdapter;
import com.example.simplenews01.util.DensityUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.simplenews01.R.array.categories;

public class MainActivity extends AppCompatActivity {
    Button btnCategoryArrowRrigth;
    HorizontalScrollView svCategory;
    private  final int COLUMNWIDTHPX=350;//列宽
    private  final int mFlingVelocityPx=2500;

    private int mColumWidthDip;
    private int mFlingVelocityDip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String[] categroyArray = this.getResources().getStringArray(categories);

        List<HashMap<String, String>> categoriesList = new ArrayList<HashMap<String, String>>();
        for(int i=0;i<categroyArray.length;i++)
        {
            HashMap<String,String>hashMap = new HashMap<String,String>();
            hashMap.put("category_title",categroyArray[i]);
            categoriesList.add(hashMap);
        }
        CustomSimpleAdapter categoryAdapter = new CustomSimpleAdapter(this,categoriesList,
                R.layout.category_title,new String[]{"category_title"},
                new int[]{R.id.category_title});
        mColumWidthDip = DensityUtil.px2dip(this, COLUMNWIDTHPX);
        mFlingVelocityDip = DensityUtil.px2dip(this, mFlingVelocityPx);
        GridView gvCategory = new GridView(this);
        gvCategory.setColumnWidth(mColumWidthDip);
        gvCategory.setNumColumns(GridView.AUTO_FIT);
        gvCategory.setGravity(Gravity.CENTER);

        int width = mColumWidthDip * categoriesList.size();
        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(width,ViewGroup.LayoutParams.WRAP_CONTENT  );
        gvCategory.setLayoutParams(params);
        gvCategory.setAdapter(categoryAdapter);
        LinearLayout categoryLayout = (LinearLayout) this.findViewById(R.id.category_layout);
        categoryLayout.addView(gvCategory);
        btnCategoryArrowRrigth = (Button) findViewById(R.id.category_arrow_right);
        svCategory = (HorizontalScrollView) findViewById(R.id.category_scrollview);
        //-----------
        btnCategoryArrowRrigth.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                svCategory.fling(mFlingVelocityDip);
            }
        });
        //------------

        gvCategory.setOnItemClickListener(new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent,View view, int position, long id) {

                TextView tvCategoryTitle;
                for (int i=0;i<parent.getCount();i++){
                    tvCategoryTitle =(TextView)parent.getChildAt(i);
                    tvCategoryTitle.setTextColor(getResources().getColor(R.color.category_title_normal_background));
                    tvCategoryTitle.setBackgroundDrawable(null);

                }

                tvCategoryTitle= (TextView) view;
                tvCategoryTitle.setTextColor(getResources().getColor(R.color.white));
                tvCategoryTitle.setBackgroundResource(R.drawable.categorybar_item_background);
            }
        });
        //----------------显示新闻列表
        //----1)准备数据
        List<HashMap<String, String>> newsList = new ArrayList<>();
        for(int i=0;i<10;i++)
        {
            HashMap<String, String> hashMap = new HashMap<String, String>();
            hashMap.put("newslist_item_title","Android课开课了" );
            hashMap.put("newslist_item_digest","学习Android系统基础" );
            hashMap.put("newslist_item_source", "G16531 class");
            hashMap.put("newslist_item_ptime", "2018-05-16 ");
            newsList.add(hashMap);
        }
        //--------2)制作适配器
        SimpleAdapter newsListAdapter = new SimpleAdapter(this, newsList,
                R.layout.newslist_item,
                new String[]{"newslist_item_title",
                        "newslist_item_digest",
                        "newslist_item_source",
                        "newslist_item_ptime"},
                new int[]{R.id.newslist_item_title,
                        R.id.newslist_item_digest,
                        R.id.newslist_item_source,
                        R.id.newslist_item_ptime});
        //----------3)把适配器设置到ListView
        ListView newslist = (ListView) findViewById(R.id.news_list);
        newslist.setAdapter(newsListAdapter);
    }
}

