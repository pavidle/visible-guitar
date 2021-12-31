#include <jni.h>
#include <opencv2/core/core.hpp>
#include "include/BoundingBoxDrawer.h"


BoundingBoxDrawer drawer = BoundingBoxDrawer();


extern "C" JNIEXPORT void JNICALL
Java_com_example_visible_1guitar_CameraActivity_drawBoundingBox(
        JNIEnv* env,
        jobject,
        jlong address
) {
    cv::Mat& mat = *(cv::Mat*) address;
    drawer.draw(mat);
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_visible_1guitar_CameraActivity_addPoint(
        JNIEnv*,
        jobject,
        jint x,
        jint y
) {
    int length = drawer.getPoints().size();
    if (length < 4)
        drawer.addPoint(cv::Point(x, y));
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_visible_1guitar_CameraActivity_convertFrame(
        JNIEnv*,
        jobject,
        jlong address
) {
    cv::Mat& mat = *(cv::Mat*) address;

}