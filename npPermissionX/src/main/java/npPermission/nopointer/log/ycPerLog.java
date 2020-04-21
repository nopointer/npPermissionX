/*
 * Copyright (c) 2013. wyouflf (wyouflf@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package npPermission.nopointer.log;

import android.util.Log;

/**
 * Log工具，类似android.util.Log。
 * tag自动产生，格式: customTagPrefix:className.methodName(L:lineNumber),
 * customTagPrefix为空时只输出：className.methodName(L:lineNumber)。
 * <p/>
 * Date: 13-7-24
 * Time: 下午12:23
 */
public class ycPerLog {

    private ycPerLog() {
    }

    static String logTag = "ycPermission";

    public static void setLogTag(String Tag) {
        logTag = Tag;
    }


    public static boolean allowD = true;
    public static boolean allowE = true;
    public static boolean allowI = true;
    public static boolean allowV = true;
    public static boolean allowW = true;
    public static boolean allowWtf = true;


    public static void e(String content) {
        if (!allowE) return;
        Log.e(logTag, content);
    }

    public static void w(String content) {
        if (!allowW) return;
        Log.w(logTag, content);
    }

    public static void d(String content) {
        if (!allowD) return;
        Log.d(logTag, content);
    }


}
