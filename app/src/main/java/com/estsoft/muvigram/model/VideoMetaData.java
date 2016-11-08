package com.estsoft.muvigram.model;

import android.graphics.Bitmap;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class VideoMetaData {
        private final Bitmap scaledBitmap;
        private final int runtime;
        private final String videoPath;
    }