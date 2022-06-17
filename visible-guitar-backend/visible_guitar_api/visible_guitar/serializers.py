from rest_framework import serializers
from .models import Chord


class ChordSerializer(serializers.ModelSerializer):

    class Meta:
        model = Chord
        fields = '__all__'
        depth = 1

