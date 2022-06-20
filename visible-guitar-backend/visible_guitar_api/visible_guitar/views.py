from rest_framework import viewsets
from .models import Chord, Melody
from .serializers import ChordSerializer, MelodySerializer


class ChordViewSet(viewsets.ModelViewSet):
    queryset = Chord.objects.all()
    serializer_class = ChordSerializer


class MelodyViewSet(viewsets.ModelViewSet):
    queryset = Melody.objects.all()
    serializer_class = MelodySerializer
