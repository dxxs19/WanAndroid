//
// Created by Administrator on 2018/3/6.
//
#include "com_wei_wanandroid_activity_ndk_JNIActivity.h"

JNIEXPORT jstring JNICALL Java_com_wei_wanandroid_activity_ndk_JNIActivity_getAppID (JNIEnv *env, jobject jobj)
{
    jstring code = (*env) -> NewStringUTF(env, "1236547895");
    return code;
}

