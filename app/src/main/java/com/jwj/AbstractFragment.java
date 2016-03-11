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

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


/**
 * This is the base class for all the fragments used by the {@link MainActivity}.
 * This class provides useful methods for performing UI operations such as creating expandable text,
 * handling long clicks by copying the content of a view to clipboard and handling text that is
 * too long to be displayed.
 */
public abstract class AbstractFragment extends Fragment implements View.OnLongClickListener{
    private static final String STATE_FRAGMENT = "state_fragment";

    protected Bundle mFragmentState = new Bundle();


    protected void loadSavedState(Bundle savedState) {
        if (savedState != null) {
            mFragmentState = savedState.getBundle(STATE_FRAGMENT);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedState) {
        super.onSaveInstanceState(savedState);
        savedState.putBundle(STATE_FRAGMENT, mFragmentState);
    }

    @Override
    public boolean onLongClick(View v) {
        // Copy in the clipboard the value of the selected widget.
        String value = getValue(v.getId());
        if (value.length() > 0) {
            ClipboardManager clipboard =
                    (ClipboardManager) getActivity().getSystemService(Context.CLIPBOARD_SERVICE);
            clipboard.setPrimaryClip(ClipData.newPlainText("GcmTestApp clipboard", value));
            Toast.makeText(getActivity(),
                    value + "\nhas been copied in the clipboard", Toast.LENGTH_SHORT).show();
            return true;
        }
        return false;
    }

    protected String getValue(int viewId) {
        View view = getActivity().findViewById(viewId);
        return getValue(view);
    }

    public static String getValue(View view) {
        if (view instanceof TextView) {
            return ((TextView) view).getText().toString();
        }
        if (view instanceof Spinner) {
            return ((Spinner) view).getSelectedItem().toString();
        }
        return "";
    }


}
