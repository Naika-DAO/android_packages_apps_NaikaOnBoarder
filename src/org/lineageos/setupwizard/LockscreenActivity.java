/*
 * Copyright (C) 2016 The CyanogenMod Project
 * Copyright (C) 2017-2021 The LineageOS Project
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

package org.lineageos.setupwizard;

import static org.lineageos.setupwizard.SetupWizardApp.ACTION_SETUP_LOCKSCREEN;
import static org.lineageos.setupwizard.SetupWizardApp.EXTRA_DETAILS;
import static org.lineageos.setupwizard.SetupWizardApp.EXTRA_TITLE;
import static org.lineageos.setupwizard.SetupWizardApp.REQUEST_CODE_SETUP_LOCKSCREEN;

import android.app.KeyguardManager;
import android.content.Intent;
import android.util.Log;

import org.lineageos.setupwizard.util.SetupWizardUtils;

public class LockscreenActivity extends WrapperSubBaseActivity {

    public static final String TAG = LockscreenActivity.class.getSimpleName();

    @Override
    protected void onStartSubactivity() {
        if (isKeyguardSecure()) {
            Log.v(TAG, "Screen lock already set up; skipping LockscreenActivity");
            nextAction(RESULT_OK);
            SetupWizardUtils.disableComponent(this, LockscreenActivity.class);
            finish();
            return;
        }
        Intent intent = new Intent(ACTION_SETUP_LOCKSCREEN);
        intent.putExtra(EXTRA_TITLE,
                getString(R.string.settings_lockscreen_setup_title));
        intent.putExtra(EXTRA_DETAILS,
                getString(R.string.settings_lockscreen_setup_details));
        startSubactivity(intent, REQUEST_CODE_SETUP_LOCKSCREEN);
    }

    private boolean isKeyguardSecure() {
        return getSystemService(KeyguardManager.class).isKeyguardSecure();
    }

    @Override
    protected boolean headerNavigationIsEnabled() {
        return true;
    }

}
