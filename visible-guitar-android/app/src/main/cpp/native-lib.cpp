#include <jni.h>
#include "include/points/BoundingBoxPointsRepository.h"
#include "include/points/BoundingBoxPointsDrawer.h"
#include "include/GuitarRecognizer.h"
#include "include/frame/CannyThresholdFrameBuilder.h"


BoundingBoxPointsRepository pointsRepository = BoundingBoxPointsRepository();


extern "C" {
    JNIEXPORT void JNICALL
    Java_com_example_visible_1guitar_CameraActivity_drawBoundingBox(
            JNIEnv*,
            jobject,
            jlong address
    ) {
        BoundingBoxPointsDrawer pointsDrawer = BoundingBoxPointsDrawer(&pointsRepository);
        cv::Mat& mat = *(cv::Mat*)address;
        pointsDrawer.draw(mat);
    }

    JNIEXPORT void JNICALL
    Java_com_example_visible_1guitar_CameraActivity_addPoint(
            JNIEnv*,
            jobject,
            jint x,
            jint y
    ) {
        pointsRepository.addPoint(cv::Point(x, y));
    }

    JNIEXPORT void JNICALL
    Java_com_example_visible_1guitar_CameraActivity_convertFrame(
        JNIEnv*,
        jobject,
        jlong input_address,
        jlong output_address
    ) {
        cv::Mat& input = *(cv::Mat*)input_address;
        cv::Mat& output = *(cv::Mat*)output_address;
        auto* builder = new CannyThresholdFrameBuilder();
        auto* recognizer = new GuitarRecognizer(builder, &pointsRepository);
        recognizer->findEdges(input);
        recognizer->show(input);
        delete builder;
        delete recognizer;
    }
}