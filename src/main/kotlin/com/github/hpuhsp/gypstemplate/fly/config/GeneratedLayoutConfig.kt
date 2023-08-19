package com.github.hpuhsp.gypstemplate.fly.config

import com.github.hpuhsp.gypstemplate.fly.GypsPluginGeneratorProvider

/**
 * @Description:
 * @Author:   Hsp
 * @Email:    1101121039@qq.com
 * @CreateTime:     2023/8/19 17:41
 * @UpdateRemark:   更新说明：
 */
fun initLayout(provider: GypsPluginGeneratorProvider) = """
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:fitsSystemWindows="true"
    android:orientation="vertical">

    <include
        android:id="@+id/include_title"
        layout="@layout/fb_toolbar_title_layout" />


</LinearLayout>
"""

fun initEmptyLayout(provider: GypsPluginGeneratorProvider) = """
<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    android:layout_width="match_parent"
    android:layout_height="match_parent">

</androidx.constraintlayout.widget.ConstraintLayout>
"""