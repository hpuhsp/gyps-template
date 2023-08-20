package com.github.hpuhsp.gypstemplate.fly.temp

import com.github.hpuhsp.gypstemplate.fly.GypsPluginGeneratorProvider
import com.github.hpuhsp.gypstemplate.fly.config.commonAnnotation

/**
 * @Description:
 * @Author:   Hsp
 * @Email:    1101121039@qq.com
 * @CreateTime:     2023/8/19 18:18
 * @UpdateRemark:   更新说明：
 */
fun gypsViewModel(
    provider: GypsPluginGeneratorProvider,
    pageName: String
) = """
package ${provider.targetPackageName.value}.viewmodel

import androidx.hilt.lifecycle.ViewModelInject
import com.swallow.fly.base.viewmodel.BaseViewModel
import ${provider.targetPackageName.value}.${pageName}Repository

${commonAnnotation(provider)}

class ${pageName}ViewModel @ViewModelInject constructor(private val repository: ${pageName}Repository) : BaseViewModel() {

}

""".trimIndent()