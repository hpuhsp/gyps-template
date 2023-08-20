package com.github.hpuhsp.gypstemplate.fly.utils

import java.io.BufferedReader
import java.io.File
import java.util.Locale

/**
 * @Description:
 * @Author:   Hsp
 * @Email:    1101121039@qq.com
 * @CreateTime:     2023/8/20 15:58
 * @UpdateRemark:   更新说明：
 */
fun getSeparatorPackageName(value: String): String {
    return "src/main/java/${value.replace('.', '/')}"
}

/**
 * 获取资源前缀
 */
fun getResourcePrefixFromBuildFile(filePath: String): String? {
    val buildFile = File(filePath)
    if (!buildFile.exists()) {
        return null
    }
    
    val resourcePrefixPattern = """resourcePrefix\s+["']([^"']+)["']""".toRegex()
    
    val bufferedReader = BufferedReader(buildFile.reader())
    var line: String?
    while (bufferedReader.readLine().also { line = it } != null) {
        val match = resourcePrefixPattern.find(line ?: "")
        if (match != null) {
            return match.groupValues[1]
        }
    }
    
    return null
}

fun convertToBindingClassName(input: String): String {
    val words = input.split("_")
    val capitalizedWords = words.map { firstUppercase(it) }
    return capitalizedWords.joinToString("") + "Binding"
}

fun firstUppercase(param: String): String {
    return param.replaceFirstChar { it.uppercase() }
}