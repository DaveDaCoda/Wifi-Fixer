/*	    Wifi Fixer for Android
    Copyright (C) 2010-2013  David Van de Ven

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see http://www.gnu.org/licenses
 */

package org.wahtod.wififixer.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ScrollView;
import android.widget.TextView;
import org.wahtod.wififixer.R;
import org.wahtod.wififixer.prefs.PrefUtil;

public class LogFragment extends Fragment {
    public static final String HAS_LOGFRAGMENT = "HAS_LF";
    public static final String LOG_MESSAGE_INTENT = "org.wahtod.wififixer.LOG_MESSAGE";
    public static final String LOG_MESSAGE = "LOG_MESSAGE_KEY";
    public static final String TAG = "AKAKAKADOUHF";
    public ViewHolder _views;

    public static LogFragment newInstance(Bundle bundle) {
        LogFragment f = new LogFragment();
        f.setArguments(bundle);
        return f;
    }

    ;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.log_fragment, null);
        _views = new ViewHolder();
        _views.myTV = (TextView) v.findViewById(R.id.logText);
        _views.mySV = (ScrollView) v.findViewById(R.id.SCROLLER);
        return v;
    }

    @Override
    public void onPause() {
        /*
         * Set pref so LogService can send log lines to the broadcastreceiver
		 */
        PrefUtil.writeBoolean(getActivity(), HAS_LOGFRAGMENT, false);
        super.onPause();
    }

    @Override
    public void onResume() {
        PrefUtil.writeBoolean(getActivity(), HAS_LOGFRAGMENT, true);
        _views.mySV.post(new ScrollToBottom());
        super.onResume();
    }

    public void setText(String text) {
        _views.myTV.setText(text);
        _views.mySV.post(new ScrollToBottom());
    }

    private static class ViewHolder {
        public TextView myTV;
        public ScrollView mySV;
    }

    public class ScrollToBottom implements Runnable {
        @Override
        public void run() {
            _views.mySV.fullScroll(ScrollView.FOCUS_DOWN);
        }
    }
}
