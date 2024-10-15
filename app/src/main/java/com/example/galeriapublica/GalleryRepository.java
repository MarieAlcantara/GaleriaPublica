package com.example.galeriapublica;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class GalleryRepository {

    Context context;

    public GalleryRepository(Context context) {
        Integer limit, Integer offSet) throw FileNotFoundException {

        List<ImageData> imageDataList = new ArrayList<>();
        int w = (int) context.getResources().getDimension(R.dimen.im_width);
        int h = (int) context.getResources().getDimension(R.dimen.im_height);

        String[] projection = new String[] {MediaStore.Images.Media._ID,
                MediaStore.Images.Media.DISPLAY_NAME,
                MediaStore.Images.Media.DATE_ADDED,
                MediaStore.Images.Media.SIZE};
        String selection = null;
        String seletionArgs[] = null;
        String sort = MediaStore.Images.Media.DATE_ADDED;

        Cursor cursor = null;
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.Q) {
            Bundle queryArgs = new Bundle();

            queryArgs.putString(ContentResolver.QUERY_ARG_SQL_SELECTION, selection);
            queryArgs.putStringArray(ContentResolver.QUERY_ARG_SQL_SELECTION_ARGS, selectionArgs);

            //sort
            queryArgs.putString(ContentResolver.QUERY_ARG_SORT_COLUMNS, sort);
            queryArgs.putInt(ContentResolver.QUERY_ARG_SORT_DIRECTION, ContentResolver.QUERY_SORT_DIRECTION_ASCENDING);

            //limit, offset
            queryArgs.putInt(ContentResolver.QUERY_ARG_LIMIT, limit);
            queryArgs.putInt(ContentResolver.QUERY_ARG_OFFSET,offSet);

            cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, queryArgs, null);
        }
        else {
            cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, projection, selection, selectionArgs, sort + "ASC + LIMIT" + String.valueOf(limit) + "OFFSET" + String.valueOf(offSet);
        }

        int idCollumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
        int nameColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME);
        int dateAddedColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATE_ADDED);
        int sizeColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE);

        }


    }
}
