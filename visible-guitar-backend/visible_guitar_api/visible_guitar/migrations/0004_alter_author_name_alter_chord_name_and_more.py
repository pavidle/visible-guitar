# Generated by Django 4.0.5 on 2022-06-10 10:48

from django.db import migrations, models


class Migration(migrations.Migration):

    dependencies = [
        ('visible_guitar', '0003_alter_chord_instrument_alter_chord_notes_and_more'),
    ]

    operations = [
        migrations.AlterField(
            model_name='author',
            name='name',
            field=models.CharField(max_length=128),
        ),
        migrations.AlterField(
            model_name='chord',
            name='name',
            field=models.CharField(max_length=128),
        ),
        migrations.AlterField(
            model_name='instrument',
            name='name',
            field=models.CharField(max_length=128),
        ),
        migrations.AlterField(
            model_name='melody',
            name='name',
            field=models.CharField(max_length=128),
        ),
        migrations.AlterField(
            model_name='note',
            name='name',
            field=models.CharField(choices=[('C', 'C'), ('C#', 'C#'), ('D', 'D'), ('D#', 'D#'), ('E', 'E'), ('F', 'F'), ('G', 'G'), ('G#', 'G#'), ('A', 'A'), ('A#', 'A#'), ('B', 'B')], max_length=128),
        ),
    ]
