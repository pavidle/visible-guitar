package com.example.domain.ar.recognizer.aruco.drawer

//class GuitarNeckDrawer(
//    private val strings: List<Line>,
//    private val frets: List<Line>
//) : Drawer {
//
//    override fun draw(frame: Mat, chordEntity: ChordEntity): Mat {
//        strings.forEach { line ->
//            Imgproc.line(frame, line.first, line.second, Scalar(255.0, 255.0, 0.0), 12)
//        }
//
//        frets.forEach { line ->
//            Imgproc.line(frame, line.first, line.second, Scalar(255.0, 255.0, 0.0), 12)
//        }
//
////        val notes = chordEntity.notes
////        notes.forEach {
////            val point = neckCellGenerator.findCross(frame, Note(it.stringNumber, it.fretNumber))
////            Imgproc.circle(frame, point, 32, Scalar(255.0, 0.0, 0.0), -1)
////        }
//
//        return frame
//    }
//}