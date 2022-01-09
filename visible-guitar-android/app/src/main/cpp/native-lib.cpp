#include <jni.h>
#include <iostream>
#include <opencv2/core/core.hpp>
#include "include/BoundingBoxDrawer.h"
#include "include/FrameConverter.h"

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
    int length = drawer.get_points().size();
    if (length < 4)
        drawer.add_point(cv::Point(x, y));
}

extern "C" JNIEXPORT void JNICALL
Java_com_example_visible_1guitar_CameraActivity_convertFrame(
        JNIEnv*,
        jobject,
        jlong input_address,
        jlong output_address
) {
    cv::Mat& input = *(cv::Mat*) input_address;
    cv::Mat& output = *(cv::Mat*) output_address;
    FrameConverter frameConverter = FrameConverter(drawer.get_points());
    frameConverter.convert(input, input);
}