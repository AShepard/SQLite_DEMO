package edu.ucsb.cs.cs185.aaronshepard;

import java.util.List;
import java.util.Random;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends ListActivity  {

		private DataBaseWrapper db_wrapper;
		private EditText et_name;
		private EditText et_amount;
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.main);
		
			et_name = (EditText) findViewById(R.id.et_name);
			et_amount = (EditText) findViewById(R.id.et_amount);
			
			db_wrapper = new DataBaseWrapper(this);
			List<Transaction> trans_list = db_wrapper.getAllTransactions();
			
			ArrayAdapter<Transaction> adapter = new ArrayAdapter<Transaction>(this,
					android.R.layout.simple_list_item_1, trans_list);
			
			setListAdapter(adapter);

			Button b_add = (Button)findViewById(R.id.b_add);
			Button b_remove_table = (Button)findViewById(R.id.b_remove_table);
			b_remove_table.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					ArrayAdapter<Transaction> adapter = (ArrayAdapter<Transaction>) getListAdapter();
					
					db_wrapper.dropTable();
					
					adapter.clear();
					adapter.notifyDataSetChanged();
				}
				
			});
			
			b_add.setOnClickListener(new OnClickListener() {

				public void onClick(View v) {
					ArrayAdapter<Transaction> adapter = (ArrayAdapter<Transaction>) getListAdapter();
					String name = et_name.getText().toString();
					String s_amount = et_amount.getText().toString();
					float amount = Float.valueOf(s_amount);
					
					Transaction new_trans = new Transaction(name, amount, "category", "date");
					
					//need to set new_trans to reflect that it gets a row_id upon insertion
					new_trans = db_wrapper.insertTransaction(new_trans);
					
					//add new transaction to array adapter
					adapter.add(new_trans);
					adapter.notifyDataSetChanged();
				}
	        });
		}
		

		@Override
		public void onListItemClick(ListView l, View v, int position, long id) {
			super.onListItemClick(l, v, position, id);
			
			ArrayAdapter<Transaction> adapter = (ArrayAdapter<Transaction>) getListAdapter();
			
			Transaction cur_trans = adapter.getItem((int)id);
			
			long row_id = db_wrapper.deleteTransaction(cur_trans);
			
			Toast.makeText(MainActivity.this, "Deleted: " + row_id + " rows", Toast.LENGTH_SHORT);
			adapter.remove(cur_trans);
			adapter.notifyDataSetChanged();
		}
}
