/*
Copyright 2015 Google Inc. All Rights Reserved.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
 */
package com.jwj;

import android.view.MenuItem;

import com.jwj.common.CommMainActivity;
import com.jwj.multimemo.MultiMemoActivity;

import com.jwj.multimemo.display.WebviewFragment;

import java.util.LinkedHashMap;

/**
 * Drawer menu for the app
 */
public class MainMenu {
    private final MainActivity mActivity;
    private LinkedHashMap<CharSequence, Class<?>> mMenu = new LinkedHashMap<>();

    public MainMenu(MainActivity mActivity) {
        this.mActivity = mActivity;
        addMenuEntry(R.string.main_menu_home, WebviewFragment.class);
        addMenuEntry(R.string.main_menu_multimemo, MultiMemoActivity.class);


        addMenuEntry(R.string.main_menu_downstream, CheeseListFragment.class);
        /*
        addMenuEntry(R.string.main_menu_upstream, null);
        addMenuEntry(R.string.main_menu_notification, null);
        addMenuEntry(R.string.main_menu_groups, null);
        addMenuEntry(R.string.main_menu_topics, null);
        addMenuEntry(R.string.main_menu_network_manager, null);
        */
    }

    private void addMenuEntry(int title, Class<?> fragment) {
        mMenu.put(mActivity.getText(title), fragment);
    }

    public CharSequence[] getEntries() {
        return mMenu.keySet().toArray(new CharSequence[mMenu.size()]);
    }

    public Object createFragment(int position) throws InstantiationException, IllegalAccessException {
        return mMenu.get(getEntries()[position]).newInstance();
    }


    //Log 오버플로우 메뉴
    public boolean onOverflowMenuItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.toggle_logs: {
                CharSequence showLogs = mActivity.getString(R.string.show_logs);
                if (showLogs.equals(item.getTitle())) {
                    mActivity.toggleLogsView(true);
                    item.setTitle(R.string.hide_logs);
                    item.setIcon(R.drawable.visibility_off_white);
                } else {
                    mActivity.toggleLogsView(false);
                    item.setTitle(R.string.show_logs);
                    item.setIcon(R.drawable.visibility_white);
                }
                return true;
            }
            case R.id.clear_logs: {
                //(new LoggingService.Logger(mActivity)).clearLogs();
                CommMainActivity.clearLog();
                return true;
            }
            default:
                return false;
        }
    }



    /*
    public static LinkedHashMap<String, QuickTest> getTests(Context context) {
        LinkedHashMap<String, QuickTest> tests = new LinkedHashMap<>();
        addTest(context, tests, new GetTokenQuickTest());
        addTest(context, tests, new DownstreamHttpJsonQuickTest());
        return tests;
    }

    private static void addTest(Context context, LinkedHashMap<String, QuickTest> arrayMap,
                                QuickTest test) {
        arrayMap.put(context.getText(test.getName()).toString(), test);
    }
    */

}
