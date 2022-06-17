from django.core.validators import MaxValueValidator
from django.db import models
from model_utils import Choices


class Instrument(models.Model):
    name = models.CharField(max_length=128)

    def __str__(self):
        return self.name


class Note(models.Model):
    NAMES = Choices('C', 'C#', 'D', 'D#', 'E', 'F', 'G', 'G#', 'A', 'A#', 'B')
    name = models.CharField(choices=NAMES, max_length=128)
    fret_number = models.PositiveIntegerField()
    string_number = models.PositiveIntegerField(validators=[MaxValueValidator(6)])

    def __str__(self):
        return f"{self.name} (fret: {self.fret_number}, string: {self.string_number})"


class Chord(models.Model):
    name = models.CharField(max_length=128)
    instrument = models.ForeignKey(
        Instrument,
        on_delete=models.CASCADE,
    )
    notes = models.ManyToManyField(Note)

    def __str__(self):
        return self.name


class Author(models.Model):
    name = models.CharField(max_length=128)

    def __str__(self):
        return self.name


class Melody(models.Model):
    name = models.CharField(max_length=128)
    author = models.ForeignKey(Author, on_delete=models.CASCADE)
    chords = models.ManyToManyField(Chord)

    def __str__(self):
        return self.name
