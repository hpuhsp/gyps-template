package com.github.hpuhsp.gypstemplate.fly.temp

import com.github.hpuhsp.gypstemplate.fly.GypsPluginGeneratorProvider
import com.github.hpuhsp.gypstemplate.fly.config.commonAnnotation
import com.github.hpuhsp.gypstemplate.fly.utils.convertToBindingClassName

/**
 * @Description:
 * @Author:   Hsp
 * @Email:    1101121039@qq.com
 * @CreateTime:     2023/8/19 18:18
 * @UpdateRemark:   更新说明：
 */
fun gypsActivity(
    provider: GypsPluginGeneratorProvider,
    modulePackageName: String,
    activityName: String
): String {
    
    val viewModelName = "${activityName}ViewModel"
    val viewBindingName = convertToBindingClassName(provider.activityLayoutName.value)
    
    return """
package ${provider.activityGeneratedLocation.value}

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import ${provider.targetPackageName}.viewmodel.${viewModelName}
import ${modulePackageName}.R
import ${modulePackageName}.databinding.${viewBindingName}
import com.swallow.fly.base.view.BaseActivity
import dagger.hilt.android.AndroidEntryPoint

${commonAnnotation(provider)}

@@AndroidEntryPoint
class ${activityName}Activity :
    BaseActivity<$viewModelName, $viewBindingName>(){
    
    companion object {
        
        fun start(context: Context) {
            val intent = Intent(context, ${activityName}Activity::class.java)
            context.startActivity(intent)
        }
    }
    
    override val bindingInflater: (LayoutInflater) -> $viewBindingName
        get() = $viewBindingName::inflate
    override val modelClass: Class<$viewModelName>
        get() = $viewModelName::class.java
    
    override fun initView(savedInstanceState: Bundle?) {
    
    }
    
    override fun initData(savedInstanceState: Bundle?) {
    
    }
    
""".trimIndent()
}


