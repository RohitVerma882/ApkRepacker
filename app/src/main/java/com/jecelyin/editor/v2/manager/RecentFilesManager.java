/*
 * Copyright 2018 Mr Duy
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.jecelyin.editor.v2.manager;

import android.app.AlertDialog;
import android.content.Context;

import com.duy.ide.database.ITabDatabase;
import com.duy.ide.database.RecentFileItem;
import com.duy.ide.database.SQLHelper;
import com.duy.ide.editor.editor.R;

import java.util.ArrayList;

/**
 * @author Jecelyin Peng <jecelyin@gmail.com>
 */
public class RecentFilesManager {
    private final ITabDatabase dbHelper;
    private OnFileItemClickListener onFileItemClickListener;
    private ArrayList<RecentFileItem> list;

    public RecentFilesManager(Context context) {
        dbHelper = SQLHelper.getInstance(context.getApplicationContext());
    }

    public void show(Context context) {
        list = dbHelper.getRecentFiles();
        String[] items = new String[list.size()];
        for (int i = list.size() - 1; i >= 0; i--) {
            items[i] = list.get(i).path;
        }
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setItems(items, (dialog, which) -> {
            if (onFileItemClickListener == null)
                return;
            RecentFileItem item = list.get(which);
            onFileItemClickListener.onClick(item.path, item.encoding);
        });
        builder.setNegativeButton(R.string.clear_history, (dialog, which) -> {
            dbHelper.clearRecentFiles();
            dialog.dismiss();
        });
        builder.setPositiveButton(R.string.close, (dialog, which) -> dialog.cancel());
        builder.setTitle(R.string.recent_files);
        builder.create().show();
    }


    public void setOnFileItemClickListener(OnFileItemClickListener onFileItemClickListener) {
        this.onFileItemClickListener = onFileItemClickListener;
    }

    public interface OnFileItemClickListener {
        void onClick(String file, String encoding);
    }
}
