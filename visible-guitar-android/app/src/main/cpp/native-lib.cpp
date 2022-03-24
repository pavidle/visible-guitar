#include <jni.h>
#include "include/recognizers/PCARecognizer.h"
#include "include/models/Chord.h"
#include "include/factories/MaskFactory.h"
#include "include/painters/PointPainter.h"
#include "android/log.h"


extern "C" {
    JNIEXPORT void JNICALL
    Java_com_example_visible_1guitar_CameraActivity_find(
        JNIEnv* env,
        jobject,
        jlong input_address,
        jobject json
    ) {


        jclass cls = env->FindClass("org/json/JSONObject");

        jmethodID method2 = env->GetMethodID(cls, "optString", "(Ljava/lang/String;)Ljava/lang/String;");
        auto strVal = (jstring) env->CallObjectMethod(json, method2, env->NewStringUTF("data"));
        const char *ptr = env->GetStringUTFChars(strVal, nullptr);
        std::string strVal_copy(ptr);
        __android_log_print(ANDROID_LOG_ERROR, "JSON", "%s", strVal_copy.c_str());


//        cv::Mat& input = *(cv::Mat*)input_address;
//        Frame frame(input);
//        MaskFactory maskFactory(frame);
//        Frame mask = maskFactory.create();
//        std::vector<Note> notes {Note(1, 1)};
//        PCARecognizer recognizer = PCARecognizer(mask);
//        std::vector<cv::Point> points = recognizer.get(com.example.visible_guitar.model.Chord(notes));
//        PointPainter pointPainter(points);
//        pointPainter.draw(frame);
    }
}