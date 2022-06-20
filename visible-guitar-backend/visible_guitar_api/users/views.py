from rest_framework import viewsets
from visible_guitar.models import Chord, Melody
from visible_guitar.serializers import ChordSerializer, MelodySerializer


class AddUserChordViewSet(viewsets.ModelViewSet):

    queryset = Chord.objects.all()
    serializer_class = ChordSerializer

    def perform_update(self, serializer):
        instance = self.get_object()
        user = self.request.user
        if user.is_authenticated:
            user.chords.add(instance)


class AddUserMelodyViewSet(viewsets.ModelViewSet):

    queryset = Melody.objects.all()
    serializer_class = MelodySerializer

    def perform_update(self, serializer):
        instance = self.get_object()
        user = self.request.user
        if user.is_authenticated:
            user.melodies.add(instance)
