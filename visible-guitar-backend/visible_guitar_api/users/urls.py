from rest_framework import routers

from users.views import AddUserChordViewSet, AddUserMelodyViewSet

router = routers.DefaultRouter()

router.register('addUserChord', AddUserChordViewSet, basename='updateChordForUser')
router.register('addUserMelody', AddUserMelodyViewSet, basename='updateChordForUser')

users_urlpatterns = [] + router.urls
