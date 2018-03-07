//
// Created by Administrator on 2018/3/6.
//
#include "com_wei_wanandroid_activity_ndk_JNIActivity.h"

/**
 * 调用java类的普通方法
 * @param env
 * @param jobj
 * @return
 */
JNIEXPORT jstring JNICALL Java_com_wei_wanandroid_activity_ndk_JNIActivity_getAppID (JNIEnv *env, jobject jobj)
{
    jstring code = (*env) -> NewStringUTF(env, "1236547895");

    // 调用java类的普通方法
    //1.获得实例对应的class类
    jclass jcls = (*env) -> GetObjectClass(env, jobj);
    //2.通过class类找到对应的method id
    //invokedByNative 为java类中方法名，Ljava/lang/String; 表示传过去的参数是String类型，V表示没有返回值，即void
    jmethodID jmid = (*env) -> GetMethodID(env, jcls, "invokedByNative", "(Ljava/lang/String;)V");
    jstring jmsg = (*env) -> NewStringUTF(env, "来自jni的回调信息");
    // 3.调用java方法
    (*env) -> CallVoidMethod(env, jobj, jmid, jmsg);
    return code;
}

/**
 * 调用java类的static方法
 * @param env
 * @param jclz
 * @param jstr
 * @return
 */
JNIEXPORT jstring JNICALL Java_com_wei_wanandroid_activity_ndk_JNIActivity_setAppID (JNIEnv *env , jclass jclz, jstring jstr)
{
    // 调用java类的static方法
    jmethodID jmethodID1 = (*env) -> GetStaticMethodID(env, jclz, "changeId", "(Ljava/lang/String;)V");
    (*env) -> CallStaticVoidMethod(env, jclz, jmethodID1, jstr);
    return jstr;
}

/**
 * 访问java中父类方法
 * @param env
 * @param jobj
 * @return
 */
JNIEXPORT jstring JNICALL Java_com_wei_wanandroid_activity_ndk_JNIActivity_invokeSuperClassMethodShowMsg (JNIEnv *env, jobject jobj)
{
    //1.通过反射获得父类的class类
    jclass jclass1 = (*env) -> FindClass(env, "com/wei/wanandroid/activity/BaseActivity");
    if ( jclass1 == NULL)
    {
        char c[10] = "error";
        return (*env) -> NewStringUTF(env, c);
    }
    //2.通过class类找到对应的method id
    jmethodID jmethodID1 = (*env) -> GetMethodID(env, jclass1, "showMsg", "(Ljava/lang/String;I)V");
    char msg[35] = "Hello, java, I'm come from jni!";
    jstring jstring1 = (*env) -> NewStringUTF(env, msg);
    //3.调用方法
    (*env) -> CallVoidMethod(env, jobj, jmethodID1, jstring1, 1000);
    return jstring1;
}

