#ifndef VISIBLE_GUITAR_IFRAMEBUILDER_H
#define VISIBLE_GUITAR_IFRAMEBUILDER_H


class IFrameBuilder {
public:
    virtual ~IFrameBuilder() {}
    virtual void add_mask() const = 0;
    virtual void add_grayscale_canny_with_dilate() const = 0;

};

#endif //VISIBLE_GUITAR_IFRAMEBUILDER_H
