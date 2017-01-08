package com.keren.sortlistview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.keren.activity.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SearchActivity extends Activity {

		private ListView sortListView;

		private TextView dialog;
		private SortAdapter adapter;
		private ClearEditText mClearEditText;
private LinearLayout l;
		/**
		 * 汉字转换成拼音的类
		 */
		private CharacterParser characterParser;
		private List<SortModel> SourceDateList;

		/**
		 * 根据拼音来排列ListView里面的数据类
		 */
		private PinyinComparator pinyinComparator;

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.search);
			initViews();
		}

		private void initViews() {
			//实例化汉字转拼音类
			characterParser = CharacterParser.getInstance();

			pinyinComparator = new PinyinComparator();


			dialog = (TextView) findViewById(R.id.dialog);


l=(LinearLayout)findViewById(R.id.search_back);
			l.setOnClickListener(new View.OnClickListener() {
				@Override
				public void onClick(View v) {
					finish();
				}
			});

			sortListView = (ListView) findViewById(R.id.country_lvcountry);
			sortListView.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					//这里要利用adapter.getItem(position)来获取当前position所对应的对象
				//	Toast.makeText(getApplication(), ((SortModel) adapter.getItem(position)).getName(), Toast.LENGTH_SHORT).show();
					Intent i=new Intent();
					i.putExtra("company",((SortModel)adapter.getItem(position)).getName())
;					setResult(1, i);
					finish();
				}
			});

			SourceDateList = filledData(getResources().getStringArray(R.array.company));

			// 根据a-z进行排序源数据
			Collections.sort(SourceDateList, pinyinComparator);
			adapter = new SortAdapter(this, SourceDateList);
			sortListView.setAdapter(adapter);
			//sortListView.setVisibility(View.INVISIBLE);

			mClearEditText = (ClearEditText) findViewById(R.id.filter_edit);

			//根据输入框输入值的改变来过滤搜索
			mClearEditText.addTextChangedListener(new TextWatcher() {

				@Override
				public void onTextChanged(CharSequence s, int start, int before, int count) {
					//当输入框里面的值为空，更新为原来的列表，否则为过滤数据列表
					filterData(s.toString());
				}

				@Override
				public void beforeTextChanged(CharSequence s, int start, int count,
											  int after) {

				}

				@Override
				public void afterTextChanged(Editable s) {
				}
			});
		}


		/**
		 * 为ListView填充数据
		 * @param date
		 * @return
		 */
		private List<SortModel> filledData(String[] date){
			sortListView.setVisibility(View.VISIBLE);
			List<SortModel> mSortList = new ArrayList<SortModel>();

			for(int i=0; i<date.length; i++){
				SortModel sortModel = new SortModel();
				sortModel.setName(date[i]);
				//汉字转换成拼音
				String pinyin = characterParser.getSelling(date[i]);
				String sortString = pinyin.substring(0, 1).toUpperCase();

				// 正则表达式，判断首字母是否是英文字母
				if(sortString.matches("[A-Z]")){
					sortModel.setSortLetters(sortString.toUpperCase());
				}else{
					sortModel.setSortLetters("#");
				}

				mSortList.add(sortModel);
			}
			return mSortList;

		}

		/**
		 * 根据输入框中的值来过滤数据并更新ListView
		 * @param filterStr
		 */
		private void filterData(String filterStr){
			List<SortModel> filterDateList = new ArrayList<SortModel>();
			sortListView.setVisibility(View.VISIBLE);
			if(TextUtils.isEmpty(filterStr)){
				filterDateList = SourceDateList;

			}else{
				filterDateList.clear();
				for(SortModel sortModel : SourceDateList){
					String name = sortModel.getName();
					if(name.indexOf(filterStr.toString()) != -1 || characterParser.getSelling(name).startsWith(filterStr.toString())){
						filterDateList.add(sortModel);
					}
				}
			}

			// 根据a-z进行排序
			Collections.sort(filterDateList, pinyinComparator);
			adapter.updateListView(filterDateList);
		}

	}
