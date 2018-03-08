NDK:
Terminal:
    cd app/src/main/java
    javah -d ../jni com.wei.wanandroid.activity.ndk.JNIActivity

    新建main.c


编译通过后会在build/intermediates/cmake/debug/obj下面生成多个so包。
可以把这些so包拷贝到别的工程使用，只需保持路径一样即可，目前用在Hook工程上测试通过。
