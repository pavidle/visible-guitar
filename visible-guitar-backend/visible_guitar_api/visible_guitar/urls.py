from rest_framework import routers

from visible_guitar.views import ChordViewSet, MelodyViewSet

router = routers.SimpleRouter()
router.register('chords', ChordViewSet)
router.register('melodies', MelodyViewSet)

visible_guitar_urlpatterns = [] + router.urls
