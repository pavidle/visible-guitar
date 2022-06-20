from rest_framework import serializers
from .models import Chord, Melody


class ChordSerializer(serializers.ModelSerializer):

    class Meta:
        model = Chord
        fields = '__all__'
        depth = 1


class MelodySerializer(serializers.ModelSerializer):

    class Meta:
        model = Melody
        fields = '__all__'
        depth = 2
