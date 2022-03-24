from django.core.validators import MaxValueValidator
from django.db import models
from model_utils import Choices


class Instrument(models.Model):
    name = models.CharField(max_length=30)

    def __str__(self):
        return self.name


class Note(models.Model):
    NAMES = Choices('C', 'C#', 'D', 'D#', 'E', 'F', 'G', 'G#', 'A', 'A#', 'B')
    name = models.CharField(choices=NAMES, max_length=30)
    fret_number = models.PositiveIntegerField()
    string_number = models.PositiveIntegerField(validators=[MaxValueValidator(6)])

    def __str__(self):
        return f"{self.name} (fret: {self.fret_number}, string: {self.string_number})"


class Chord(models.Model):
    name = models.CharField(max_length=30)
    instrument = models.ForeignKey(
        Instrument,
        on_delete=models.CASCADE,
        related_name='chords'
    )
    notes = models.ManyToManyField(Note, related_name='notes')

    def __str__(self):
        return self.name
