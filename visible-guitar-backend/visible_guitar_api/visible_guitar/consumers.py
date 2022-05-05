import base64
import json
from channels.generic.websocket import AsyncWebsocketConsumer
import cv2
import numpy as np

encode_param = [int(cv2.IMWRITE_JPEG_QUALITY), 95]


class CameraConsumer(AsyncWebsocketConsumer):

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
        # img = cv2.cvtColor(img, cv2.COLOR_BGR2GRAY)
        window_name = 'Image'
        font = cv2.FONT_HERSHEY_SIMPLEX
        org = (50, 50)
        fontScale = 1
        color = (255, 0, 0)
        thickness = 2
        img = cv2.putText(img, 'OpenCV', org, font,
                            fontScale, color, thickness, cv2.LINE_AA)

        result, imgencode = cv2.imencode('.jpg', img, encode_param)
        data = np.array(imgencode)
        img = data.tobytes()
        img = base64.b64encode(img).decode()
        await self.send(json.dumps({"base64": img}))

        # cv2.imshow('t', img)
        #
        # ch = 0xFF & cv2.waitKey(1)
        # if ch == 27:
        #     exit(1)
        #     cv2.destroyAllWindows()




