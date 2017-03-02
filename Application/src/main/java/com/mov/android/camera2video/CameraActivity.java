/*
 * Copyright 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.mov.android.camera2video;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

public class CameraActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //get obligated screen orientation
        boolean isLadscape = getIntent().getExtras().getBoolean("isLandscape");
        if (isLadscape)
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        else
            setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);


        setContentView(R.layout.activity_camera);
        if (null == savedInstanceState) {
            boolean isFrontCamera = getIntent().getExtras().getBoolean("isFrontCamera");
            int     Duration = getIntent().getExtras().getInt("duration");
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2VideoFragment.newInstance(isFrontCamera, Duration ))
                    .commit();
        }
    }

}
