/*
 * Copyright (C) 2014 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mov.android.camera2video.data;

import android.provider.BaseColumns;
import android.text.format.Time;

/**
 * Defines table and column names for the weather database.
 */
public class umov_bitsContract {

    /* Inner class that defines the contents of the weather table */
    public static final class BitsEntry implements BaseColumns {

        public static final String TABLE_NAME = "umov_bits";

        // The name of the bit
        public static final String COLUMN_NAME = "name";

        // Short description and long description
        public static final String COLUMN_SHORT_DESC = "short_desc";

        //TYPE   by bytes  --1:Text 2:Audio 4:Video 8:karaoke
        public static final String COLUMN_TYPE = "type";

        //CAMERA_LAYOUT    NOT NULL, -- 0: Landscape 1: Portrait 2: both
        public static final String COLUMN_CAMERA_LAYOUT = "camera_layout";

        //CAMERA_DIR     BOOLEAN  NOT NULL, -- 0: front camera(selfie) 1: back camera
        public static final String COLUMN_CAMERA_DIRECTION = "camera_direction";

        //DURATION       INTEGER  NOT NULL, --in msec
        public static final String COLUMN_DURATION = "duration";

        //ICON_URI             TEXT       ,--- URI to an icon image for the bit
        public static final String COLUMN_ICON_URI = "icon_uri";

        //TEXT_INSTRACTION TEXT       , --for text directed type task
        public static final String COLUMN_TEXT_INSTRUCTION = "text_instruction";

        //VIDEO_INSTRUCTION_URI  TEXT       , --for video directed type task
        public static final String COLUMN_VIDEO_INSTRUCTION_URI = "video_instruction_uri";

        //KRK_AUDIO_URI          TEXT       , --audio background for karaoke type
        public static final String COLUMN_KARAOKE_AUDIO_URI = "karaoke_audio_uri";

        //KRK_LYRICS             TEXT       , --Lyrics with time stamps for karaoke
        public static final String COLUMN_KARAOKE_LYRICS = "karaoke_lyrics";

        //AUDIO_INSTRUCTION_URI  TEXT         --for audio directed type tasks
        public static final String COLUMN_AUDIO_INSTRUCTION_URI = "audio_instruction_uri";
    }
}
