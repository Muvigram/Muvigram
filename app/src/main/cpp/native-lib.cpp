#include <jni.h>
#include <string>

extern "C"
jstring
Java_com_estsoft_muvigram_SplashActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}
