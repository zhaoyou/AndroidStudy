/*
 * Copyright (c) 2015 Razeware LLC
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package com.raywenderlich.todolist;

import static android.R.attr.data;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class MainActivity extends Activity {
  private static final String TAG = MainActivity.class.getName();

  private final int ADD_TASK_REQUEST = 1;

  private final String PREFS_TASKS = "prefs_tasks";
  private final String KEY_TASKS_LIST = "list";

  private ArrayList<String> mList;
  private ArrayAdapter<String> mAdapter;
  private TextView mDateTimeTextView;

  private BroadcastReceiver mTickReceiver;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    // 1
    super.onCreate(savedInstanceState);

    // 2 -Make the activity full screen
    getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);

    // 3
    setContentView(R.layout.activity_main);

    // 4
    mDateTimeTextView = (TextView) findViewById(R.id.dateTimeTextView);
    final Button addTaskBtn = (Button) findViewById(R.id.addTaskBtn);
    final ListView listview = (ListView) findViewById(R.id.taskListview);
    mList = new ArrayList<String>();

    String items = getSharedPreferences(PREFS_TASKS, MODE_PRIVATE).getString(KEY_TASKS_LIST, null);
    if (items != null) {
      mList = new ArrayList<String>(Arrays.asList(items.split(",")));
    }


    // 5
    mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mList);
    listview.setAdapter(mAdapter);

    // 6
    listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        taskSelected(i);
      }
    });

    mTickReceiver = new BroadcastReceiver() {
      @Override
      public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_TIME_TICK)) {
          mDateTimeTextView.setText(getCurrentTimeStamp());
        }
      }
    };
  }


  @Override
  protected void onResume() {
    super.onResume();

    mDateTimeTextView.setText(getCurrentTimeStamp());

    registerReceiver(mTickReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));

  }

  @Override
  protected void onPause() {
    super.onPause();

    if (mTickReceiver != null) {
      try {
        unregisterReceiver(mTickReceiver);
      } catch (Exception e) {
        Log.e(TAG, "onPause: mTickReceiver not Register", e);
      }
    }
  }

  @Override
  protected void onStop() {
    super.onStop();

    StringBuilder sb = new StringBuilder();
    for (String item: mList) {
      sb.append(item);
      sb.append(",");
    }
    getSharedPreferences(PREFS_TASKS, MODE_PRIVATE).
        edit().putString(KEY_TASKS_LIST, sb.toString()).commit();
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    // 1 - Check which request you're responding to
    if (requestCode == ADD_TASK_REQUEST) {
      // 2 - Make sure the request was successful
      if (resultCode == RESULT_OK) {
        // 3 - The user entered a task. Add a task to the list.
        String task = data.getStringExtra(TaskDescriptionActivity.EXTRA_TASK_DESCRIPTION);
        mList.add(task);
        // 4
        mAdapter.notifyDataSetChanged();
      }
    }
  }

  @Override
  public void onConfigurationChanged(Configuration newConfig) {
    super.onConfigurationChanged(newConfig);
  }

  private void taskSelected(final int position) {
    // 1
    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);

    // 2
    alertDialogBuilder.setTitle(getString(R.string.alert_title));

    // 3
    alertDialogBuilder
        .setMessage(mList.get(position))
        .setPositiveButton(getString(R.string.delete), new OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            mList.remove(position);
            mAdapter.notifyDataSetChanged();
          }
        })
        .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface dialog, int id) {
            dialog.cancel();
          }
        });

    // 4
    AlertDialog alertDialog = alertDialogBuilder.create();

    // 5
    alertDialog.show();
  }

  public void addTaskClicked(View view) {
    Intent intent = new Intent(MainActivity.this, TaskDescriptionActivity.class);
    startActivityForResult(intent, ADD_TASK_REQUEST);
  }

  private static String getCurrentTimeStamp() {
    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");//dd/MM/yyyy
    Date now = new Date();
    String strDate = sdf.format(now);
    return strDate;
  }
}
