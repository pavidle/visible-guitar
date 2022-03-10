#include <jni.h>
#include "include/recognizers/PCARecognizer.h"
#include "include/models/Chord.h"
#include "include/factories/MaskFactory.h"
#include "include/painters/PointPainter.h"


extern "C" {
    JNIEXPORT void JNICALL
    Java_com_example_visible_1guitar_CameraActivity_convertFrame(
        JNIEnv*,
        jobject,
        jlong input_address,
        jlong output_address
    ) {
        cv::Mat& input = *(cv::Mat*)input_address;
        cv::Mat& output = *(cv::Mat*)output_address;

        Frame frame(input);
        MaskFactory maskFactory(frame);
        Frame mask = maskFactory.create();
        std::vector<Note> notes {Note(1, 1), Note(2, 2)};

        PCARecognizer recognizer = PCARecognizer(mask);
        std::vector<cv::Point> points = recognizer.get(Chord(notes));

        PointPainter pointPainter(points);
        pointPainter.draw(frame);
    }
}