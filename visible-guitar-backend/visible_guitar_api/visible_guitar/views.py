from rest_framework import generics, viewsets
from .models import Chord
from .serializers import ChordSerializer


# class ChordAPIView(generics.ListAPIView):
#     queryset = Chord.objects.all()
#     serializer_class = ChordSerializer


class ChordViewSet(viewsets.ModelViewSet):
    queryset = Chord.objects.all()
    serializer_class = ChordSerializer
