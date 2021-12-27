#include <jni.h>
#include <string>
//#include <opencv2/core.hpp>

extern "C" JNIEXPORT jstring JNICALL
Java_com_example_visible_1guitar_CameraActivity_stringFromJNI(
        JNIEnv* env,
        jobject /* this */) {
    std::string hello = "Hello from C++";
    return env->NewStringUTF(hello.c_str());
}

