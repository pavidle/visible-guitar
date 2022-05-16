import base64
import json
from channels.generic.websocket import AsyncWebsocketConsumer
import cv2
import numpy as np
import random
from cv.mask import ImageMaskGenerator
from cv.recognizers import HoughLinesRecognizer
from cv.repositories import LinesRepository
from cv.transformers import CannyTransformer
import asyncio
# encode_param = [int(cv2.IMWRITE_JPEG_QUALITY), 195]


class CameraConsumer(AsyncWebsocketConsumer):

    def __init__(self, *args, **kwargs):
        super().__init__(*args, **kwargs)
        self.x = 0
        self.cascade = cv2.CascadeClassifier(
            'C:\\visible-guitar\\visible-guitar-backend\\visible_guitar_api\\visible_guitar\\haarcascade_frontalface_default.xml'
        )

    async def connect(self):
        await self.accept()

    async def disconnect(self, code):
        print('disconnecting...')

    async def receive(self, text_data=None, bytes_data=None):
        text = json.loads(text_data)
        message = text['base64']
        im_bytes = base64.b64decode(message)
        im_arr = np.frombuffer(im_bytes, dtype=np.uint8)
        img = cv2.imdecode(im_arr, flags=cv2.IMREAD_COLOR)

        # scale_percent = 160  # percent of original size
        # width = int(img.shape[1] * scale_percent / 100)
        # height = int(img.shape[0] * scale_percent / 100)
        # dim = (width, height)
        #
        # # resize image
        # resized = cv2.resize(img, dim, interpolation=cv2.INTER_AREA)

        boxes = self.cascade.detectMultiScale(img)
        for box in boxes:
            x, y, w, h = box
            cv2.putText(img, "TEST", (x, y), 1, 3, (255, 255, 255), 15, cv2.LINE_AA)
            cv2.rectangle(img, (x, y), (x + w, y + h), (0, 255, 0), 2)

        # cv2.imshow('t', img)

        result, imgencode = cv2.imencode('.jpg', img)
        data = np.array(imgencode)
        img = data.tobytes()
        img = base64.b64encode(img).decode()
        # await self.send(json.dumps({"x": self.x, "y": random_y}))
        await self.send(json.dumps({"base64": img}))


        # self.x += 1
        # random_y = 100

        # resize image
        # resized = cv2.resize(img, dim, interpolation=cv2.INTER_AREA)
        # # cv2.imshow("show", resized)
        # canny = CannyTransformer()
        # hough = HoughLinesRecognizer(canny)
        # repository = LinesRepository(hough)
        # repository.recognize_and_fill(resized)
        # mask_generator = ImageMaskGenerator(repository)
        # mask = mask_generator.generate(resized)
        # # await asyncio.sleep(0.1)
        # result, imgencode = cv2.imencode('.jpg', mask, encode_param)
        # data = np.array(imgencode)
        # img = data.tobytes()
        # img = base64.b64encode(img).decode()
        # await self.send(json.dumps({"x": self.x, "y": random_y}))

        #
        # ch = 0xFF & cv2.waitKey(1)
        # if ch == 27:
        #     exit(1)
        #     cv2.destroyAllWindows()



