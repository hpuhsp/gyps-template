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
fun gypsFragment(
    provider: GypsPluginGeneratorProvider,
    modulePackageName: String,
    fragmentName: String,
    namePrefix: String
): String {
    
    val viewModelName = "${fragmentName}ViewModel"
    val viewBindingName =
        "${namePrefix}${convertToBindingClassName(provider.fragmentLayoutName.value)}"
    
    return """
package ${provider.fragmentGeneratedLocation.value}

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import ${provider.targetPackageName.value}.viewmodel.${viewModelName}
import ${modulePackageName}.R
import ${modulePackageName}.databinding.${viewBindingName}
import com.swallow.fly.base.view.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

${commonAnnotation(provider)}

@AndroidEntryPoint
class ${fragmentName}Fragment :
    BaseFragment<$viewModelName, $viewBindingName>(){
    
    companion object {
        
        @JvmStatic
        fun newInstance() =
            ${fragmentName}Fragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
    
    override val bindingInflater: (LayoutInflater) -> $viewBindingName
        get() = $viewBindingName::inflate
    override val modelClass: Class<$viewModelName>
        get() = $viewModelName::class.java
    
    override fun initView() {
    
    }
    
""".trimIndent()
}
