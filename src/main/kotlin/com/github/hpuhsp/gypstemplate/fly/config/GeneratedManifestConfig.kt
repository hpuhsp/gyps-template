package com.github.hpuhsp.gypstemplate.fly.config

/**
 * @Description:
 * @Author:   Hsp
 * @Email:    1101121039@qq.com
 * @CreateTime:     2023/8/19 17:51
 * @UpdateRemark:   更新说明：
 */
import com.github.hpuhsp.gypstemplate.fly.GypsPluginGeneratorProvider

fun modifyManifest(
    provider: GypsPluginGeneratorProvider,
    modulePackageName: String,
    activityName: String
) = """
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="$modulePackageName">
    <application>
        <activity
	        android:name="${provider.activityGeneratedLocation.value}.${activityName}Activity"
            android:screenOrientation="portrait"
	        />
    </application>
</manifest>
"""