/*
 * Copyright 2013 Google Inc.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pixplicity.wizardpager.wizard.model;

import android.database.Cursor;
import android.os.Bundle;

import com.pixplicity.wizardpager.wizard.ui.SingleChoiceCursorFragment;
import com.pixplicity.wizardpager.wizard.ui.WizardFragment;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * A page offering the user a number of mutually exclusive choices from a {@link Cursor}.
 */
public abstract class SingleFixedChoiceCursorPage extends Page {

    protected ArrayList<String> mChoices = new ArrayList<String>();
    private Cursor mCursor;
    private SingleChoiceCursorFragment mFragment;

    public SingleFixedChoiceCursorPage(ModelCallbacks callbacks, String title) {
        super(callbacks, title);
    }

    @Override
    public WizardFragment createFragment() {
        mFragment = SingleChoiceCursorFragment.create(getKey());
        return mFragment;
    }

    public String getOptionAt(int position) {
        return mChoices.get(position);
    }

    public int getOptionCount() {
        return mChoices.size();
    }

    @Override
    public void getReviewItems(ArrayList<ReviewItem> dest) {
        dest.add(new ReviewItem(getTitle(), mData.getString(SIMPLE_DATA_KEY), getKey()));
    }

    @Override
    public boolean isCompleted() {
        return mData.containsKey(SIMPLE_DATA_KEY);
    }

    public SingleFixedChoiceCursorPage setChoices(String... choices) {
        mChoices.clear();
        mChoices.addAll(Arrays.asList(choices));
        return this;
    }

    @Override
    public void notifyDataChanged() {
        if (mFragment != null) mFragment.notifyDataChanged();
        super.notifyDataChanged();
    }

    public Cursor getCursor() {
        return mCursor;
    }

    public void invalidate() {
        mData = new Bundle();
    }

    public void setCursor(Cursor cursor) {
        this.mCursor = cursor;
    }

    public abstract String getColumnName();

    public abstract String getColumnIdName();

    public long getValue() {
        return mData.getLong(Page.SIMPLE_DATA_KEY);
    }

    public void setValue(long id) {
        mData.putLong(Page.SIMPLE_DATA_KEY, id);
    }

}
