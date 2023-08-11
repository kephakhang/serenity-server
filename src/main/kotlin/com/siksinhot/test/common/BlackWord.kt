package com.siksinhot.test.common

import java.util.regex.Matcher
import java.util.regex.Pattern


/**
 * 금지어 object
 */
object BlackWord {
    fun getFiteredBlackWords(blackWords: List<String>, content: String): String {
        var blackWordsRegEx = ""
        for (bWord in blackWords) {
            blackWordsRegEx += "$bWord|"
        }

        if (blackWordsRegEx.length > 0) {
            blackWordsRegEx = blackWordsRegEx.substring(0, blackWordsRegEx.length - 1)
        }

        val p: Pattern = Pattern.compile(blackWordsRegEx, Pattern.CASE_INSENSITIVE)
        val m: Matcher = p.matcher(content)

        val sb = StringBuffer()
        while (m.find()) {
            //System.out.println(m.group());
            m.appendReplacement(sb, maskWord(m.group()))
        }
        m.appendTail(sb)

//        println("sb.toString() === $sb");
        return sb.toString()
    }

    fun maskWord(word: String): String? {
        val buff = StringBuffer()
        val ch = word.toCharArray()
        for (i in ch.indices) {
            if (i < 1) {
                buff.append(ch[i])
            } else {
                buff.append("*")
            }
        }
        return buff.toString()
    }

    fun getFiteredBlackWordsCnt(blackWords: List<String>, content: String): Int {
        var blackWordsRegEx = ""
        for (bWord in blackWords) {
            blackWordsRegEx += "$bWord|"
        }

        if (blackWordsRegEx.length > 0) {
            blackWordsRegEx = blackWordsRegEx.substring(0, blackWordsRegEx.length - 1)
        }

        val p: Pattern = Pattern.compile(blackWordsRegEx, Pattern.CASE_INSENSITIVE)
        val m: Matcher = p.matcher(content)

        var cnt = 0
        if(m.find()){
            cnt += 1
        }
//        println(m.find())
//        while (m.find()) {
//            m.group()
//            println(m.group());
//            cnt += 1
//        }

        return cnt
    }
}

